package fechten;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gruppe
{
	private SimpleStringProperty _name;
	private ObservableList<Fechter> _gruppe;

	public Gruppe(String name)
	{
		_gruppe = FXCollections.observableArrayList();
		_name = new SimpleStringProperty(name);
	}

	public ObservableList<Fechter> getGruppe()
	{
		return _gruppe;
	}

	public void add(Fechter f)
	{
		_gruppe.add(f);
	}

	public SimpleStringProperty nameProperty()
	{
		return _name;
	}
}
