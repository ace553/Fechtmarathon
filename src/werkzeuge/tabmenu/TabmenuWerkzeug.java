package werkzeuge.tabmenu;

import javafx.scene.control.TabPane;
import service.TunierService;
import werkzeuge.gruppen.GruppenWerkzeug;
import werkzeuge.menubar.MenuBarWerkzeug;
import werkzeuge.tableau.TableauWerkzeug;
import werkzeuge.teilnehmer.TeilnehmerWerkzeug;

public class TabmenuWerkzeug
{
	private final TabmenuWerkzeugUI _ui;
	
	private final TunierService _tunier;
	
	private TeilnehmerWerkzeug _teilnehmerWerkzeug;
	private MenuBarWerkzeug _menuBarWerkzeug;
	private TableauWerkzeug _tableauWerkzeug;
	private GruppenWerkzeug _gruppenWerkzeug;
	
	
	public TabmenuWerkzeug(TunierService tunier)
	{
		_tunier = tunier;
		_ui = new TabmenuWerkzeugUI();
		
		_teilnehmerWerkzeug = new TeilnehmerWerkzeug(_tunier);
		_menuBarWerkzeug = new MenuBarWerkzeug(_tunier);
		_tableauWerkzeug = new TableauWerkzeug(_tunier);
		_gruppenWerkzeug = new GruppenWerkzeug(_tunier);
		_ui._tabs.getTabs().addAll(_tableauWerkzeug.getTab(), _teilnehmerWerkzeug.getTab(), _gruppenWerkzeug.getTab());
	}
	
	public TabPane getTabPane()
	{
		return _ui._tabs;
	}
	

}
