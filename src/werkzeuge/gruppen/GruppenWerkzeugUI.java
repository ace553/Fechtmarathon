package werkzeuge.gruppen;

import java.util.ArrayList;
import java.util.List;

import fechten.Fechter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

class GruppenWerkzeugUI
{
	final Tab _tab;
	final GruppenTable _fechterTable;

	final List<GruppenTable> _gruppen;
	
	final FlowPane _pane;
	
	GruppenWerkzeugUI()
	{
		_tab = new Tab("Gruppen");
		_gruppen = new ArrayList<GruppenTable>();
		_pane = new FlowPane();
		_pane.setHgap(50);
		_pane.setVgap(10);
		_fechterTable = newTable(new SimpleStringProperty("Keine Gruppe"), null, false);
		erstelleTab();
	}
	
	private void erstelleTab()
	{
		_tab.setClosable(false);
		VBox box = new VBox();
		box.setPadding(new Insets(10, 10, 10, 10));
		box.getChildren().add(_pane);
		_tab.setContent(box);
	}
	
	
	public GruppenTable newTable(StringProperty title, ObservableList<Fechter> data, boolean closeable)
	{
		GruppenTable table = new GruppenTable(title, closeable);
		table.getTable().setItems(data);
		_pane.getChildren().add(table);
		_gruppen.add(table);
		return table;
	}
}
