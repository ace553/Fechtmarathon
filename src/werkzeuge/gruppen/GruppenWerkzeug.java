package werkzeuge.gruppen;

import fechten.Gruppe;
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
		
		_ui._fechterTable.getTable().setItems(_tunier.getKeineGruppe());
		for(int i = 0; i < 10; i++)
		{
			registriereNeueGruppe();
		}
	}
	
	public Tab getTab()
	{
		return _ui._tab;
	}
	
	private void registriereNeueGruppe()
	{
		Gruppe g = _tunier.addGruppe();
		FechterTable f = _ui.newTable(g.nameProperty(), g.getGruppe(), true);
		f.getCloseButton().setOnAction(new EventHandler<ActionEvent>()
		{
			
			@Override
			public void handle(ActionEvent event)
			{
				_tunier.removeGruppe(g);
				_ui._pane.getChildren().remove(f);
				_ui._gruppen.remove(f);
				for(FechterTable t : _ui._gruppen)
				{
					t.updateTitle();
				}
			}
		});
	}
}