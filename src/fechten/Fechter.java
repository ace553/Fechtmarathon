package fechten;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Fechter
{
	private static final int UNGUELTIGE_ID = -1;
	private final SimpleStringProperty _vorname;
	private final SimpleStringProperty _nachname;
	private final SimpleStringProperty _verein;
	private final SimpleBooleanProperty _gestrichen;
	private final SimpleBooleanProperty _anwesend;
	private final SimpleStringProperty _name;
	
	private Tunier _tunier;

	private final SimpleIntegerProperty _id;

	public Fechter(String vorname, String nachname, String verein)
	{
		_vorname = new SimpleStringProperty(vorname);
		_nachname = new SimpleStringProperty(nachname);
		_verein = new SimpleStringProperty(verein);
		_gestrichen = new SimpleBooleanProperty(false);
		_anwesend = new SimpleBooleanProperty(false);
		
		_name = new SimpleStringProperty(vorname+" "+nachname);
		_tunier = null;
		_id = new SimpleIntegerProperty(UNGUELTIGE_ID);
	}
	
	

	public int anzahlGefochten()
	{
		assert _id.get() != UNGUELTIGE_ID : "ID ist ungueltig";

		return _tunier.getTableau().getGefochten(this);
	}

	public int anzahlGewonnen()
	{
		assert _id.get() != UNGUELTIGE_ID : "ID ist ungueltig";

		return _tunier.getTableau().getGewonnen(this);
	}

	public int erhaltendeTreffer()
	{
		assert _id.get() != UNGUELTIGE_ID : "ID ist ungueltig";
		return _tunier.getTableau().getErhalten(this);

	}

	public int gegebeneTreffer()
	{
		assert _id.get() != UNGUELTIGE_ID : "ID ist ungueltig";

		return _tunier.getTableau().getGegeben(this);
	}

	public int index()
	{
		return gegebeneTreffer() - erhaltendeTreffer();
	}

	public int getID()
	{
		return _id.get() - 1;
	}

	public void setzteAnwesend(boolean anwesend)
	{
		_anwesend.set(anwesend);;
	}

	public void setzteGestrichen(boolean gestrichen)
	{
		_gestrichen.set(gestrichen);
	}

	public boolean istAnwesend()
	{
		return _anwesend.get();
	}

	public boolean istGestrichen()
	{
		return _gestrichen.get();
	}
	
	public String getVorname()
	{
		return _vorname.get();
	}
	
	public String getNachname()
	{
		return _nachname.get();
	}

	public void setzeID(int id)
	{
		_id.set(id+1);
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
	
	public SimpleIntegerProperty idProperty()
	{
		return _id;
	}
	
	public SimpleStringProperty nameProperty()
	{
		return _name;
	}



	public String getVerein()
	{
		return _verein.get();
	}
}
