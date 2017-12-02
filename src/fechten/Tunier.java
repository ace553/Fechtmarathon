package fechten;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tunier
{
	private ObservableList<Fechter> _teilnehmer;


	private Tableau _tableau;
	
	private TunierStatus _status;
	
	private static final int MAX_TREFFER = 5;

	public Tunier()
	{
		_teilnehmer = FXCollections.observableArrayList();
		_tableau = null;
		_status = TunierStatus.MELDEN;
	}
	

	public void melde(Fechter fechter)
	{
		_teilnehmer.add(fechter);
		updateTeilnehmer();
	}
	
	public void loesche(Fechter fechter)
	{
		_teilnehmer.remove(fechter);
		updateTeilnehmer();
	}
	
	public void updateTeilnehmer()
	{
		if(_status != TunierStatus.GESTARTET)
		{
			for(int i = 0; i < _teilnehmer.size(); i++)
			{
				_teilnehmer.get(i).setzeID(i);
			}
		}
	}
	
	public void starteTunier()
	{
		assert _status != TunierStatus.MELDEN : "Das Tunier kann nur aus dem MELDEN Status gestartet werden.";
		
		_status = TunierStatus.GESTARTET;
		
		_tableau = new Tableau(_teilnehmer.size(), MAX_TREFFER);
	}

	public Tableau getTableau()
	{
		return _tableau;
	}

	public ObservableList<Fechter> getFechter()
	{
		return _teilnehmer;
	}


	public TunierStatus getStatus()
	{
		return _status;
	}
}
