package fechten;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Fechter
{
	private final SimpleStringProperty _vorname;
	private final SimpleStringProperty _nachname;
	private final SimpleStringProperty _verein;
	private final SimpleBooleanProperty _gestrichen;
	private final SimpleBooleanProperty _anwesend;
	private final SimpleStringProperty _name;

	private final SimpleStringProperty _gruppe;

	public Fechter(String vorname, String nachname, String verein)
	{
		_vorname = new SimpleStringProperty(vorname);
		_nachname = new SimpleStringProperty(nachname);
		_verein = new SimpleStringProperty(verein);
		_gestrichen = new SimpleBooleanProperty(false);
		_anwesend = new SimpleBooleanProperty(false);
		_gruppe = new SimpleStringProperty("-");

		_name = new SimpleStringProperty(vorname + " " + nachname);
	}



	public SimpleStringProperty vornameProperty()
	{
		return _vorname;
	}

	public SimpleStringProperty nachnameProperty()
	{
		return _nachname;
	}

	public SimpleStringProperty vereinProperty()
	{
		return _verein;
	}

	public SimpleBooleanProperty anwesendProperty()
	{
		return _anwesend;
	}

	public SimpleBooleanProperty gestrichenProperty()
	{
		return _gestrichen;
	}

	public SimpleStringProperty nameProperty()
	{
		return _name;
	}

	public SimpleStringProperty gruppeProperty()
	{
		return _gruppe;
	}
}
