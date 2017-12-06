package werkzeuge.stage;

import fechten.Tunier;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import service.TunierService;
import werkzeuge.ActionListener;
import werkzeuge.fechter.FechterWerkzeug;
import werkzeuge.gruppen.GruppenWerkzeug;
import werkzeuge.menubar.MenuBarWerkzeug;
import werkzeuge.tableau.TableauWerkzeug;
import werkzeuge.tabmenu.TabmenuWerkzeug;

public class StageWerkzeug
{
	private final StageWerkzeugUI _ui;
	
	private final FechterWerkzeug _fechterWerkzeug;
	private final MenuBarWerkzeug _menuBarWerkzeug;
	private final TableauWerkzeug _tableauWerkzeug;
	private final GruppenWerkzeug _gruppenWerkzeug;
	private final TabmenuWerkzeug _tabmenuWerkzeug;
	
	private final TunierService _tunier;
	
	public StageWerkzeug(Stage s, TunierService tunier)
	{
		_ui = new StageWerkzeugUI(s);
		_tunier = new TunierService(new Tunier());
		_fechterWerkzeug = new FechterWerkzeug(_tunier);
		_menuBarWerkzeug = new MenuBarWerkzeug(_tunier);
		_tableauWerkzeug = new TableauWerkzeug(_tunier);
		_gruppenWerkzeug = new GruppenWerkzeug(_tunier);
		_tabmenuWerkzeug = new TabmenuWerkzeug(_tunier);
		
		_tabmenuWerkzeug.setupTabs(_tableauWerkzeug.getTab(), _fechterWerkzeug.getTab(), _gruppenWerkzeug.getTab());
		_ui.setupStage(_menuBarWerkzeug.getMenu(), _tabmenuWerkzeug.getTabPane());
		
		registriereMenubarWerkzeug();
	}
	
	
	private void registriereMenubarWerkzeug()
	{
		_menuBarWerkzeug.addListener(new ActionListener()
		{
			
			@Override
			public void handle(ActionEvent event)
			{
				if(event.getSource().equals("Loeschen"))
				{
					_fechterWerkzeug.loescheAusgewaehlteFechter();
				}
			}
		});
	}
	
	
}
