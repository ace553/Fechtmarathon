package fechten;

import static fechten.ErgebnisStatus.*;

import javafx.beans.property.SimpleStringProperty;

public class Ergebnis
{

	private final SimpleStringProperty _styled;

	private final int MAX_TREFFER;
	private int _treffer;
	private boolean _gewonnen;

	private ErgebnisStatus _zustand;

	private Tableau _tablo;

	public Ergebnis(ErgebnisStatus startzustand, int maxTreffer, Tableau tablo)
	{
		_styled = new SimpleStringProperty();
		_zustand = startzustand;
		MAX_TREFFER = maxTreffer;
		_treffer = 0;
		_gewonnen = false;
		_tablo = tablo;
		updateStyled();
	}

	public Tableau getTablo()
	{
		return _tablo;
	}

	public ErgebnisStatus getZustand()
	{
		return _zustand;
	}

	public boolean hatGewonnen()
	{
		return _gewonnen;
	}

	public int getTreffer()
	{
		return _treffer;
	}

	public void eintragen(int treffer, boolean gewonnen)
	{
		if (treffer > MAX_TREFFER)
		{
			throw new IllegalArgumentException("Es können nicht mehr als " + MAX_TREFFER + " eingetragen werden.");
		}
		_zustand = GEFOCHTEN;
		_treffer = treffer;
		_gewonnen = gewonnen;

		updateStyled();
	}

	private void updateStyled()
	{

		StringBuilder b = new StringBuilder();

		if (_zustand == ErgebnisStatus.GEFOCHTEN)
		{
			if (_gewonnen)
			{
				b.append("V");
				if (MAX_TREFFER != _treffer)
				{
					b.append((char) (8320 + _treffer));
				}
			} else
			{
				b.append(_treffer);
			}
		} else if (_zustand == ErgebnisStatus.UNGUELTIG)
		{
			b.append("X");
		}
		_styled.set(b.toString());

	}

	@Override
	public String toString()
	{
		switch (_zustand)
		{
			case GEFOCHTEN:
				return _gewonnen ? "V" + _treffer : "" + _treffer;
			case AUSSTEHEND:
				return "-";
			case UNGUELTIG:
				return "X";
		}
		return null;
	}

	public SimpleStringProperty styledProperty()
	{
		return _styled;
	}

}
