package werkzeuge.tabmenu;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import service.TunierService;
import werkzeuge.fechter.FechterWerkzeug;
import werkzeuge.gruppen.GruppenWerkzeug;
import werkzeuge.menubar.MenuBarWerkzeug;
import werkzeuge.tableau.TableauWerkzeug;

public class TabmenuWerkzeug
{
	private final TabmenuWerkzeugUI _ui;
	
	private final TunierService _tunier;
	
	
	public TabmenuWerkzeug(TunierService tunier)
	{
		_tunier = tunier;
		_ui = new TabmenuWerkzeugUI();
	}
	
	public TabPane getTabPane()
	{
		return _ui._tabs;
	}

	public void setupTabs(Tab... tabs)
	{
		_ui._tabs.getTabs().addAll(tabs);
	}
	

}
