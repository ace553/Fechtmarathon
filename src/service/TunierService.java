package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fechten.Ergebnis;
import fechten.Fechter;
import fechten.Gruppe;
import fechten.Tunier;
import fechten.TunierStatus;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import werkzeuge.tableau.Teilnehmer;

public class TunierService
{
	private Tunier _tunier;
	
	private ChangeListener<Object> _fechterChanged;

	private ObservableList<Teilnehmer> _teilnehmer;

	private SimpleBooleanProperty _gespeichert;
	
	public TunierService(Tunier t)
	{
		_tunier = t;
		_gespeichert = new SimpleBooleanProperty(true);
		_teilnehmer = FXCollections.observableArrayList();
		_fechterChanged = new ChangeListener<Object>()
		{

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
			{
				if(!oldValue.equals(newValue))
				{
					_gespeichert.set(false);
				}
			}
		};

	}

	public ObservableList<Teilnehmer> getTeilnehmer()
	{
		return _teilnehmer;
	}

	public ObservableList<Fechter> getFechter()
	{
		return _tunier.getFechter();
	}

	public void fuegeHinzu(Fechter fechter)
	{
		_tunier.melde(fechter);
		fechter.nachnameProperty().addListener(_fechterChanged);
		fechter.vornameProperty().addListener(_fechterChanged);
		fechter.vereinProperty().addListener(_fechterChanged);
		fechter.gestrichenProperty().addListener(_fechterChanged);
		fechter.anwesendProperty().addListener(_fechterChanged);
		_gespeichert.set(false);
	}

	public void streicheNichtAnwesendeFechter()
	{
		for (Fechter f : _tunier.getFechter())
		{
			if (!f.anwesendProperty().get())
				f.gestrichenProperty().set(true);
		}
		_gespeichert.set(false);
	}
	
	public void verteileFechterZufaellig()
	{
		Collections.shuffle(_tunier.getKeineGruppe());
		int gSize = _tunier.getGruppen().size();
		int fSize = _tunier.getKeineGruppe().size();
		int gruppenGroesse = fSize / gSize; 
		int nochZuVerteilen =  fSize % gSize;

		for(Gruppe g : _tunier.getGruppen())
		{
			for(int i = 0; i < gruppenGroesse; i++)
			{
				_tunier.putFechterInGruppe(_tunier.getKeineGruppe().get(0), g);
			}
		}
		
		for(int i = 0; i < nochZuVerteilen; i++)
		{
			_tunier.putFechterInGruppe(_tunier.getKeineGruppe().get(0), _tunier.getGruppen().get(i));
		}
		_gespeichert.set(false);
	}
	
	public void resetTunier()
	{

		_tunier.getFechter().clear();
		int gSize = _tunier.getGruppen().size();
		for(int i = 0; i < gSize; i++)
		{
			_tunier.removeGruppe(_tunier.getGruppen().get(0));
		}
		_tunier.setStatus(TunierStatus.MELDEN);
		_tunier.getKeineGruppe().clear();
	}

	public void ladeTunier(File file)
	{
		resetTunier();
		List<String> zeilen = null;
		try
		{
			zeilen = Files.readAllLines(file.toPath());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		int id = 0;
		int status = 0;
		Gruppe gruppe = null;
		
		for (String zeile : zeilen)
		{
			if(zeile.startsWith("#KeineGruppe"))
			{
				continue;
			}
			if(zeile.startsWith("#Gruppe"))
			{
				status = 1;
				gruppe = _tunier.addGruppe();
				continue;
			}
			if(zeile.startsWith("#Tableau"))
			{
				status = 2;
				continue;
			}
			
			switch(status)
			{
				case 0:
				{
					Fechter f = ladeFechter(zeile);
					fuegeHinzu(f);
					break;
				}
				case 1:
				{
					Fechter f = ladeFechter(zeile);
					fuegeHinzu(f);
					_tunier.putFechterInGruppe(f, gruppe);
					break;
				}
				case 2:
				{
					if(_tunier.getStatus().get() == TunierStatus.MELDEN)
					{
						starte();
					}	
					String[] parts = zeile.split(";");
					for(int i = 0; i < parts.length; i++)
					{
						_tunier.getTableau().ergebnisUpdaten(id, i, parts[i]);
					}
					id++;
				}
			}
		}
		
		_gespeichert.set(true);
	}
	

	
	private Fechter ladeFechter(String zeile)
	{
		String[] parts = zeile.split(";");
		Fechter f = new Fechter(parts[0], parts[1], parts[2]);
		f.anwesendProperty().set(parts[3].equals("true"));
		f.gestrichenProperty().set(parts[4].equals("true"));
		return f;
	}
	
	private void speichereFechter(PrintWriter writer, Fechter f)
	{
		writer.print(f.vornameProperty().get() + ";" + f.nachnameProperty().get() + ";" + f.vereinProperty().get() + ";" + f.anwesendProperty().get() + ";" + f.gestrichenProperty().get() + "\n");
		
	}
	
	public void speichern(File file)
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		writer.println("#KeineGruppe");
		for(Fechter f: _tunier.getKeineGruppe())
		{
			speichereFechter(writer, f);
		}
		
		
		for(Gruppe g : _tunier.getGruppen())
		{
			writer.println("#Gruppe");
			for(Fechter f: g.getFechter())
			{
				speichereFechter(writer, f);
			}
		}
		
		if(_tunier.getStatus().get() == TunierStatus.GESTARTET)
		{
			writer.println("#Tableau");
			for(List<Ergebnis> ergebnisse: _tunier.getTableau().getTableau())
			{
				writer.print(ergebnisse.get(0));
				for(int i = 1; i < ergebnisse.size(); i++)
				{
					writer.print(";"+ergebnisse.get(i));
				}
				writer.println();
			}
		}
		
		writer.close();
		_gespeichert.set(true);
	}

	public SimpleObjectProperty<TunierStatus> getStatus()
	{
		return _tunier.getStatus();
	}

	public void starte()
	{
		_tunier.starteTunier();
		_teilnehmer.clear();

		for (int i = 0; i < _tunier.getFechter().size(); i++)
		{
			if (!_tunier.getFechter().get(i).gestrichenProperty().get())
			{
				_teilnehmer.add(new Teilnehmer(_tunier.getFechter().get(i), _tunier.getTableau()));
			}
		}
		_gespeichert.set(false);
	}


	public void loesche(Fechter f)
	{
		_tunier.loesche(f);
	}

	public boolean alleFechterAnwesendOderGestrichen()
	{
		for (Fechter f : _tunier.getFechter())
		{
			if (!f.anwesendProperty().get() && !f.gestrichenProperty().get())
			{
				return false;
			}
		}
		return true;
	}

	public Gruppe addGruppe()
	{
		_gespeichert.set(false);
		return _tunier.addGruppe();
	}

	public void removeGruppe(Gruppe g)
	{
		_gespeichert.set(false);
		_tunier.removeGruppe(g);
	}

	public ObservableList<Fechter> getKeineGruppe()
	{
		return _tunier.getKeineGruppe();
	}

	public void verteileFechterNachVerein()
	{
		Collections.shuffle(getKeineGruppe());
		Comparator<Fechter> com = new GroessterVereinComperator(getKeineGruppe());
		Collections.sort(_tunier.getKeineGruppe(), com);
		
		int gSize = _tunier.getGruppen().size();
		int fSize = _tunier.getKeineGruppe().size();

		int gruppenGroesse = fSize / gSize; 
		int nochZuVerteilen =  fSize % gSize;

		for(Gruppe g : _tunier.getGruppen())
		{
			int groesse = gruppenGroesse;
			if(nochZuVerteilen > 0)
			{
				nochZuVerteilen--;
				groesse++;
			}
			_tunier.putFechterInGruppe(_tunier.getKeineGruppe().get(0), g);
			for(int i = 1; i < groesse; i++)
			{
				if(g.getFechter().get(0).vereinProperty().get().equals(_tunier.getKeineGruppe().get(0).vereinProperty().get()))
				{
					_tunier.putFechterInGruppe(_tunier.getKeineGruppe().get(0), g);
				}
				else
				{
					_tunier.putFechterInGruppe(_tunier.getKeineGruppe().get(_tunier.getKeineGruppe().size()-1), g);
				}
			}
		}
		
		for(int i = 0; i < nochZuVerteilen; i++)
		{
			_tunier.putFechterInGruppe(_tunier.getKeineGruppe().get(0), _tunier.getGruppen().get(i));
		}
		_gespeichert.set(false);
	}

	public ObservableList<Gruppe> getGruppen()
	{
		return _tunier.getGruppen();
	}

	public SimpleBooleanProperty propertyGespeichert()
	{
		return _gespeichert;
	}

	
}
