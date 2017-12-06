package fechten;

import static fechten.ErgebnisStatus.AUSSTEHEND;
import static fechten.ErgebnisStatus.UNGUELTIG;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import werkzeuge.tableau.Teilnehmer;

public class Tableau
{

	private List<List<Ergebnis>> _tableau;

	private SimpleIntegerProperty[] _gefochten;
	private SimpleIntegerProperty[] _siege;
	private SimpleIntegerProperty[] _erhalten;
	private SimpleIntegerProperty[] _gegeben;
	private SimpleIntegerProperty[] _index;

	private int _anzahlTeilnehmer;
	private int _anzahlAngenommen;

	public Tableau(int anzahlTeilnehmer, int maxTreffer)
	{
		_anzahlTeilnehmer = anzahlTeilnehmer;
		_anzahlAngenommen = 0;
		_tableau = new ArrayList<List<Ergebnis>>();
		for (int i = 0; i < anzahlTeilnehmer; i++)
		{
			_tableau.add(new ArrayList<Ergebnis>());
			for (int k = 0; k < anzahlTeilnehmer; k++)
			{
				if (i == k)
				{
					_tableau.get(i)
					        .add(new Ergebnis(UNGUELTIG, maxTreffer, this));
				} else
				{
					_tableau.get(i)
					        .add(new Ergebnis(AUSSTEHEND, maxTreffer, this));
				}
			}
		}

		_gefochten = new SimpleIntegerProperty[anzahlTeilnehmer];
		_siege = new SimpleIntegerProperty[anzahlTeilnehmer];
		_erhalten = new SimpleIntegerProperty[anzahlTeilnehmer];
		_gegeben = new SimpleIntegerProperty[anzahlTeilnehmer];
		_index = new SimpleIntegerProperty[anzahlTeilnehmer];

		for (int i = 0; i < anzahlTeilnehmer; i++)
		{
			_gefochten[i] = new SimpleIntegerProperty();
			_siege[i] = new SimpleIntegerProperty();
			_erhalten[i] = new SimpleIntegerProperty();
			_gegeben[i] = new SimpleIntegerProperty();
			_index[i] = new SimpleIntegerProperty();
		}

		update();
	}

	public List<List<Ergebnis>> getTableau()
	{
		return _tableau;
	}

	public void gefechtEintragen(Teilnehmer f1, Teilnehmer f2, int treffer1,
	        int treffer2, boolean gewonnen1)
	{
		getErgebnis(f1, f2).eintragen(treffer1, gewonnen1);

		getErgebnis(f2, f1).eintragen(treffer2, !gewonnen1);
		update();
	}

	public Ergebnis getErgebnis(Teilnehmer f1, Teilnehmer f2)
	{
		return _tableau.get(f1.idProperty().get() - 1)
		        .get(f2.idProperty().get() - 1);
	}

	private void update()
	{
		for (int i = 0; i < _anzahlTeilnehmer; i++)
		{
			_siege[i].set(0);
			_gegeben[i].set(0);
			_gefochten[i].set(0);
			_erhalten[i].set(0);
			_index[i].set(0);
		}

		for (int i = 0; i < _anzahlTeilnehmer; i++)
		{
			for (int k = 0; k < _anzahlTeilnehmer; k++)
			{
				if (_tableau.get(i).get(k)
				        .getZustand() == ErgebnisStatus.GEFOCHTEN)
				{
					_gefochten[i].set(_gefochten[i].get() + 1);
					
					_gegeben[i].set(_gegeben[i].get()
					        + _tableau.get(i).get(k).getTreffer());
					
					_erhalten[k].set(_erhalten[k].get()
					        + _tableau.get(i).get(k).getTreffer());
					
					if (_tableau.get(i).get(k).hatGewonnen())
					{
						_siege[i].set(_siege[i].get() + 1);
					}
				}
			}
		}
		for(int i = 0; i < _anzahlTeilnehmer; i++)
		{
			_index[i].set(_gegeben[i].get() - _erhalten[i].get());
		}
	}

	public int getNext()
	{
		_anzahlAngenommen++;
		if (_anzahlAngenommen > _anzahlTeilnehmer)
		{
			throw new IllegalArgumentException();
		}
		return _anzahlAngenommen;
	}

	public SimpleIntegerProperty gewonnenProperty(int teilnehmer)
	{
		return _siege[teilnehmer];
	}

	public SimpleIntegerProperty gefochtenProperty(int i)
	{
		return _gefochten[i];
	}

	public SimpleIntegerProperty gegebenProperty(int i)
	{
		return _gegeben[i];
	}

	public SimpleIntegerProperty erhaltenProperty(int i)
	{
		return _erhalten[i];
	}

	public SimpleIntegerProperty indexProperty(int i)
	{
		return _index[i];
	}
}
