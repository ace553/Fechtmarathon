package werkzeuge.tableau;

import java.util.ArrayList;
import java.util.List;

import fechten.Fechter;
import fechten.Tableau;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Teilnehmer
{
	private final SimpleIntegerProperty _id;
	private final SimpleStringProperty _name;
	private final List<SimpleStringProperty> _gefechte;
	
	public Teilnehmer(Fechter f, Tableau t)
	{
		_id = f.idProperty();
		_name = f.nameProperty();
		_gefechte = new ArrayList<>();
		for(int i = 0; i < t.getTableau().size(); i++)
		{
			_gefechte.add(t.getTableau().get(_id.get()-1).get(i).styledProperty());
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
	
}
