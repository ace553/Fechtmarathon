package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fechten.Ergebnis;
import fechten.Fechter;
import fechten.Gruppe;
import fechten.Tunier;
import fechten.TunierStatus;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import werkzeuge.tableau.Teilnehmer;

public class TunierService implements Observable
{
	private Tunier _tunier;
	private List<InvalidationListener> _listeners;

	private ObservableList<Teilnehmer> _teilnehmer;

	private boolean _gespeichert;

	public TunierService(Tunier t)
	{
		_tunier = t;
		_listeners = new ArrayList<>();
		_gespeichert = true;
		_teilnehmer = FXCollections.observableArrayList();

	}

	public ObservableList<Teilnehmer> getTeilnehmer()
	{
		return _teilnehmer;
	}

	public ObservableList<Fechter> getFechter()
	{
		return _tunier.getFechter();
	}

	public void melde(Fechter fechter)
	{
		_tunier.melde(fechter);
		_gespeichert = false;
		notifyListeners();
	}

	public void streicheNichtAnwesendeFechter()
	{
		for (Fechter f : _tunier.getFechter())
		{
			if (!f.anwesendProperty().get())
				f.gestrichenProperty().set(true);
		}
	}

	public boolean istGespeichert()
	{
		return _gespeichert;
	}

	public void updateFechter()
	{
		bearbeitet();
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
	}

	public void ladeTunier(File file)
	{
		List<String> zeilen = null;
		try
		{
			zeilen = Files.readAllLines(file.toPath());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		_tunier.getFechter().clear();

		for (String zeile : zeilen)
		{
			String[] parts = zeile.replace("\n", "").split(";");
			if (parts.length != 5)
			{
				return;
			}
			Fechter f = new Fechter(parts[0], parts[1], parts[2]);
			f.anwesendProperty().set(parts[3].equals("true"));
			f.gestrichenProperty().set(parts[4].equals("true"));
			_tunier.melde(f);
		}
		_gespeichert = true;
		notifyListeners();
	}

	public void speichern2(File file)
	{

		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		for (Fechter f : _tunier.getFechter())
		{
			filePrintFechter(writer, f);
		}
		writer.close();
		_gespeichert = true;
		notifyListeners();
	}
	
	private void filePrintFechter(PrintWriter writer, Fechter f)
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
		
		writer.println(_tunier.getStatus());
		
		writer.println("keine Gruppe");
		for(Fechter f: _tunier.getKeineGruppe())
		{
			filePrintFechter(writer, f);
		}
		
		
		for(Gruppe g : _tunier.getGruppen())
		{
			writer.println(g.nameProperty().get());
			for(Fechter f: g.getFechter())
			{
				filePrintFechter(writer, f);
			}
		}
		
		if(_tunier.getStatus() == TunierStatus.GESTARTET)
		{
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
		_gespeichert = true;
		notifyListeners();
	}

	public TunierStatus getStatus()
	{
		return _tunier.getStatus();
	}

	public void starte()
	{
		_tunier.starteTunier();

		for (int i = 0; i < _tunier.getFechter().size(); i++)
		{
			if (!_tunier.getFechter().get(i).gestrichenProperty().get())
			{
				_teilnehmer.add(new Teilnehmer(_tunier.getFechter().get(i), _tunier.getTableau()));
			}
		}
		notifyListeners();
	}

	public void bearbeitet()
	{
		_gespeichert = false;
		notifyListeners();
	}

	public void notifyListeners()
	{
		for (InvalidationListener l : _listeners)
		{
			l.invalidated(this);
		}
	}

	@Override
	public void addListener(InvalidationListener listener)
	{
		_listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener)
	{
		_listeners.remove(listener);

	}

	public void loesche(Fechter f)
	{
		_tunier.loesche(f);
		notifyListeners();
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
		return _tunier.addGruppe();
	}

	public void removeGruppe(Gruppe g)
	{
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
	}
}
