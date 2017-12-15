package fechten;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tunier
{
	private ObservableList<Fechter> _fechter;

	private Tableau _tableau;

	private SimpleObjectProperty<TunierStatus> _status;

	private ObservableList<Fechter> _keineGruppe;

	private ObservableList<Gruppe> _gruppen;

	private static final int MAX_TREFFER = 5;

	public Tunier()
	{
		_fechter = FXCollections.observableArrayList();
		_keineGruppe = FXCollections.observableArrayList();
		_tableau = null;
		_gruppen = FXCollections.observableArrayList();
		_status = new SimpleObjectProperty<TunierStatus>(TunierStatus.MELDEN);
	}

	public ObservableList<Gruppe> getGruppen()
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
		_keineGruppe.addAll(g.getFechter());
		_gruppen.remove(g);
		updateGruppenNamen();
	}

	public void putFechterInGruppe(Fechter f, Gruppe newGruppe)
	{
		for (Gruppe g : _gruppen)
		{
			if (g.nameProperty().get().endsWith(f.gruppeProperty().get()))
			{
				g.getFechter().remove(f);
			}
		}
		_keineGruppe.remove(f);
		newGruppe.add(f);
		f.gruppeProperty().set(newGruppe.nameProperty().get().substring(newGruppe.nameProperty().get().length() - 2));
	}

	private void updateGruppenNamen()
	{
		for (int i = 0; i < _gruppen.size(); i++)
		{
			Gruppe g = _gruppen.get(i);
			g.nameProperty().set("Gruppe " + (char) ('A' + i));
			for(Fechter f : g.getFechter())
			{
				f.gruppeProperty().set((char)('A'+i)+"");
			}
		}
		for(Fechter f : _keineGruppe)
		{
			f.gruppeProperty().set("-");
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
		assert _status.get() == TunierStatus.MELDEN : "Das Tunier kann nur aus dem MELDEN Status gestartet werden.";

		_status.set(TunierStatus.GESTARTET);

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

	public SimpleObjectProperty<TunierStatus> getStatus()
	{
		return _status;
	}

	public ObservableList<Fechter> getKeineGruppe()
	{
		return _keineGruppe;
	}

	public void setStatus(TunierStatus s)
	{
		_status.set(s);;
	}
}
