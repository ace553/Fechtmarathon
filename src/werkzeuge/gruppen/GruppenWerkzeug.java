package werkzeuge.gruppen;

import fechten.Gruppe;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import service.TunierService;

public class GruppenWerkzeug
{
	GruppenWerkzeugUI _ui;
	TunierService _tunier;

	public GruppenWerkzeug(TunierService tunier)
	{
		_ui = new GruppenWerkzeugUI();
		_tunier = tunier;

		_ui._keineGruppeTable.getTable().setItems(_tunier.getKeineGruppe());

		registriereZufaelligVerteilenButton();
		registriereNeueGruppeButton();
		registriereNachVereinVerteilenButton();
		registriereGruppenChangeEvent();

		for (int i = 0; i < 3; i++)
		{
			_tunier.addGruppe();
		}
	}

	public Tab getTab()
	{
		return _ui._tab;
	}

	private void registriereZufaelligVerteilenButton()
	{
		_ui._zufaelligAufteilen.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				_tunier.verteileFechterZufaellig();
			}
		});
	}

	private void registriereNachVereinVerteilenButton()
	{
		_ui._nachVereinAufteilen.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				_tunier.verteileFechterNachVerein();
			}
		});
	}

	private void registriereNeueGruppeButton()
	{
		_ui._neueGruppeButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				_tunier.addGruppe();
			}
		});
	}

	private void registriereGruppenChangeEvent()
	{
		_tunier.getGruppen().addListener(new ListChangeListener<Gruppe>()
		{

			@Override
			public void onChanged(ListChangeListener.Change<? extends Gruppe> c)
			{
				c.next();
				if (c.getAddedSize() > 0)
				{
					Gruppe g = _tunier.getGruppen().get(_tunier.getGruppen().size() - 1);
					GruppenTable f = _ui.newTable(g.nameProperty(), g.getFechter(), true);
					f.getCloseButton().setOnAction(new EventHandler<ActionEvent>()
					{

						@Override
						public void handle(ActionEvent event)
						{
							if(_tunier.getGruppen().size() > 1)
							{
								_tunier.removeGruppe(g);
							}
						}
					});
				} else if (c.getRemovedSize() > 0)
				{
					int f = c.getFrom();
					_ui._pane.getChildren().remove(f+1);
					_ui._gruppen.remove(f);
				}
			}
		});
	}
}
