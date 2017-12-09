package fechten;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tunier
{
	private ObservableList<Fechter> _fechter;

	private Tableau _tableau;

	private TunierStatus _status;

	private ObservableList<Fechter> _keineGruppe;

	private List<Gruppe> _gruppen;

	private static final int MAX_TREFFER = 5;

	public Tunier()
	{
		_fechter = FXCollections.observableArrayList();
		_keineGruppe = FXCollections.observableArrayList();
		_tableau = null;
		_gruppen = new ArrayList<>();
		_status = TunierStatus.MELDEN;
	}

	public List<Gruppe> getGruppen()
	{
		return _gruppen;
	}

	public Gruppe addGruppe()
	{
		String name = "Gruppe " + (char) ('A' + _gruppen.size());
		Gruppe g = new Gruppe(name);
		_gruppen.add(g);
		return g;
	}

	public void removeGruppe(Gruppe g)
	{
		_keineGruppe.addAll(g.getGruppe());
		_gruppen.remove(g);
		updateGruppenNamen();
	}

	public void putFechterInGruppe(Fechter f, Gruppe newGruppe)
	{
		for (Gruppe g : _gruppen)
		{
			if (g.nameProperty().get().endsWith(f.gruppeProperty().get()))
			{
				g.getGruppe().remove(f);
			}
		}
		newGruppe.add(f);
		f.gruppeProperty().set(newGruppe.nameProperty().get().substring(newGruppe.nameProperty().get().length() - 2));
	}

	private void updateGruppenNamen()
	{
		for (int i = 0; i < _gruppen.size(); i++)
		{
			_gruppen.get(i).nameProperty().set("Gruppe " + (char) ('A' + i));
		}
	}

	public void melde(Fechter fechter)
	{
		_fechter.add(fechter);
		_keineGruppe.add(fechter);
	}

	public void loesche(Fechter fechter)
	{
		_fechter.remove(fechter);
	}

	public void starteTunier()
	{
		assert _status == TunierStatus.MELDEN : "Das Tunier kann nur aus dem MELDEN Status gestartet werden.";

		_status = TunierStatus.GESTARTET;

		_tableau = new Tableau(_fechter.size(), MAX_TREFFER);
	}

	public Tableau getTableau()
	{
		return _tableau;
	}

	public ObservableList<Fechter> getFechter()
	{
		return _fechter;
	}

	public TunierStatus getStatus()
	{
		return _status;
	}

	public ObservableList<Fechter> getKeineGruppe()
	{
		return _keineGruppe;
	}
}
