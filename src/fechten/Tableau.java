package fechten;

import static fechten.Ergebniszustand.AUSSTEHEND;
import static fechten.Ergebniszustand.UNGUELTIG;

import java.util.ArrayList;
import java.util.List;

public class Tableau
{
	//private Ergebnis[][] _tablo;
	private List<List<Ergebnis>> _tableau;

	private boolean _bearbeitet;
	
	private int[] _gefochten;
	private int[] _gewonnen;
	private int[] _erhalten;
	private int[] _gegeben;
	
	private int _anzahlTeilnehmer;
	
	public Tableau(int anzahlTeilnehmer, int maxTreffer)
	{
		_anzahlTeilnehmer = anzahlTeilnehmer;
		_tableau = new ArrayList<List<Ergebnis>>();
		for(int i = 0; i < anzahlTeilnehmer; i++)
		{
			_tableau.add(new ArrayList<Ergebnis>());
			for(int k = 0; k < anzahlTeilnehmer; k++)
			{
				if(i==k)
				{
					_tableau.get(i).add(new Ergebnis(UNGUELTIG, maxTreffer, this));
				}
				else
				{
					_tableau.get(i).add(new Ergebnis(AUSSTEHEND, maxTreffer, this));
				}
			}
		}

		_tableau.get(0).get(1).eintragen(5, true);
		_tableau.get(1).get(0).eintragen(0, false);
		_tableau.get(2).get(1).eintragen(4, true);
		
		_gefochten = new int[anzahlTeilnehmer];
		_gewonnen = new int[anzahlTeilnehmer];
		_erhalten = new int[anzahlTeilnehmer];
		_gegeben = new int[anzahlTeilnehmer];
		
		_bearbeitet = false;
	}
	
	public List<List<Ergebnis>> getTableau()
	{
		return _tableau;
	}
	
	public void gefechtEintragen(Fechter f1, Fechter f2, int treffer1, int treffer2, boolean gewonnen1)
	{
		assert f1.getID() < _anzahlTeilnehmer : "Teilnehmer 1 nicht gültig";
		assert f2.getID() < _anzahlTeilnehmer : "Teilnehmer 2 nicht gültig";
		
		assert f1.getID() >= 0 : "Teilnehmer 1 nicht gültig";
		assert f2.getID() >= 0: "Teilnehmer 2 nicht gültig";
		
		assert f1.getID() != f2.getID() : "Teilnehmer kann nicht gegen sich selbst fechten";
		
		_tableau.get(f1.getID()).get(f2.getID()).eintragen(treffer1, gewonnen1);
		_tableau.get(f2.getID()).get(f1.getID()).eintragen(treffer2, !gewonnen1);
		
		_bearbeitet = true;
	}
	
	public Ergebnis getErgebnis(Fechter f1, Fechter f2)
	{
		return _tableau.get(f1.getID()).get(f2.getID());
	}
	
	private void update()
	{
		for(int i = 0; i < _anzahlTeilnehmer; i++)
		{
			_gewonnen[i] = 0;
			_gegeben[i] = 0;
			_gefochten[i] = 0;
			_erhalten[i] = 0;
		}
		for(int i = 0; i < _anzahlTeilnehmer; i++)
		{
			for(int k = 0; k < _anzahlTeilnehmer; k++)
			{
				if(_tableau.get(i).get(k).getZustand() == Ergebniszustand.GEFOCHTEN)
				{
					_gefochten[i]++;
					_gegeben[i] += _tableau.get(i).get(k).getTreffer();
					_erhalten[k] += _tableau.get(i).get(k).getTreffer();
					if(_tableau.get(i).get(k).hatGewonnen())
					{
						_gewonnen[i]++;
					}
				}
			}
		}
		_bearbeitet = false;
	}
	
	int getGefochten(Fechter t)
	{
		if(_bearbeitet)
		{
			update();
		}
		return _gefochten[t.getID()];
	}
	
	int getGewonnen(Fechter t)
	{
		if(_bearbeitet)
		{
			update();
		}
		return _gewonnen[t.getID()];
	}
	
	int getGegeben(Fechter t)
	{
		if(_bearbeitet)
		{
			update();
		}
		return _gegeben[t.getID()];
	}
	
	int getErhalten(Fechter t)
	{
		if(_bearbeitet)
		{
			update();
		}
		return _erhalten[t.getID()];
	}
}
