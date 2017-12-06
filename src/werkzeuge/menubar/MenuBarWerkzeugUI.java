package werkzeuge.menubar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

class MenuBarWerkzeugUI
{
	final MenuBar _menu;
	final Menu _fileMenu;

	final MenuItem _speichern;
	final MenuItem _speichernUnter;
	final MenuItem _oeffnen;

	MenuBarWerkzeugUI()
	{
		_menu = new MenuBar();
		_fileMenu = new Menu("File");
		
		
		_speichern = new MenuItem("Speichern");
		_speichern.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		
		_speichernUnter = new MenuItem("Speichern unter");
		_speichernUnter.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
		
		
		_oeffnen = new MenuItem("Öffnen");
		_oeffnen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		_fileMenu.getItems().addAll(_speichern, _speichernUnter, _oeffnen);

		_menu.getMenus().addAll(_fileMenu);
	}
}
