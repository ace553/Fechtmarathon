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

	final Menu _bearbeitenMenu;

	final MenuItem _rueckgaengig;
	final MenuItem _wiederherstellen;
	final MenuItem _delete;
	final MenuItem _koperien;
	final MenuItem _einfuegen;

	MenuBarWerkzeugUI()
	{
		_menu = new MenuBar();
		_fileMenu = new Menu("Datei");

		_speichern = new MenuItem("Speichern");
		_speichern.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

		_speichernUnter = new MenuItem("Speichern unter");
		_speichernUnter.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));

		_oeffnen = new MenuItem("Öffnen");
		_oeffnen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		_fileMenu.getItems().addAll(_speichern, _speichernUnter, _oeffnen);

		_bearbeitenMenu = new Menu("Bearbeiten");

		_rueckgaengig = new MenuItem("Rückgängig");
		_rueckgaengig.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));

		_wiederherstellen = new MenuItem("Wiederherstellen");
		_wiederherstellen.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));

		_delete = new MenuItem("Löschen");
		_delete.setAccelerator(KeyCombination.keyCombination("Delete"));

		_koperien = new MenuItem("Kopieren");
		_koperien.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));

		_einfuegen = new MenuItem("Einfügen");
		_einfuegen.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));

		_bearbeitenMenu.getItems().addAll(_rueckgaengig, _wiederherstellen, _delete, _koperien, _einfuegen);

		_menu.getMenus().addAll(_fileMenu, _bearbeitenMenu);
	}
}
