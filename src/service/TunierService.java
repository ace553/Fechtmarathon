package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
			if (!f.istAnwesend())
				f.setzteGestrichen(true);
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
			f.setzteAnwesend(parts[3].equals("true"));
			f.setzteGestrichen(parts[4].equals("true"));
			_tunier.melde(f);
		}
		_gespeichert = true;
		notifyListeners();
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
		for (Fechter f : _tunier.getFechter())
		{
			writer.print(f.getVorname() + ";" + f.getNachname() + ";" + f.getVerein() + ";" + f.istAnwesend() + ";" + f.istGestrichen() + "\n");
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
			if (!_tunier.getFechter().get(i).istGestrichen())
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

	public boolean alleFechterDa()
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
}
