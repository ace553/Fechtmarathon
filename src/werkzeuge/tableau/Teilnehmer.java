package werkzeuge.tableau;

import java.util.ArrayList;
import java.util.List;

import fechten.Fechter;
import fechten.Tableau;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Teilnehmer
{
	private final SimpleIntegerProperty _id;
	private final SimpleStringProperty _name;
	private final List<SimpleStringProperty> _gefechte;

	private final SimpleIntegerProperty _siege;
	private final SimpleIntegerProperty _gefochten;
	private final SimpleIntegerProperty _gegeben;
	private final SimpleIntegerProperty _erhalten;
	private final SimpleIntegerProperty _index;

	public Teilnehmer(Fechter f, Tableau t)
	{
		_id = new SimpleIntegerProperty(t.getNext());
		_name = f.nameProperty();
		_gefechte = new ArrayList<>();

		_siege = t.gewonnenProperty(_id.get() - 1);
		_gefochten = t.gefochtenProperty(_id.get() - 1);
		_gegeben = t.gegebenProperty(_id.get() - 1);
		_erhalten = t.erhaltenProperty(_id.get() - 1);
		_index = t.indexProperty(_id.get() - 1);

		for (int i = 0; i < t.getTableau().size(); i++)
		{
			_gefechte.add(t.getTableau().get(_id.get() - 1).get(i).styledProperty());
		}
	}

	public SimpleIntegerProperty idProperty()
	{
		return _id;
	}

	public SimpleStringProperty nameProperty()
	{
		return _name;
	}

	public SimpleStringProperty gefechtProperty(int i)
	{
		return _gefechte.get(i);
	}

	public SimpleIntegerProperty siegeProperty()
	{
		return _siege;
	}

	public SimpleIntegerProperty indexProperty()
	{
		return _index;
	}

	public IntegerProperty gegebenProperty()
	{
		return _gegeben;
	}

	public IntegerProperty erhaltenProperty()
	{
		return _erhalten;
	}

	public IntegerProperty gefochtenProperty()
	{
		return _gefochten;
	}

}
