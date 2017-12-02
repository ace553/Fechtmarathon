package werkzeuge.tableau;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TableauWerkzeugUI
{
	final Tab _tab;

	final Button _startTunierButton;
	final Label _info;

	final TableView<Teilnehmer> _tableauView;
	final TableColumn<Teilnehmer, Integer> _colID;
	final TableColumn<Teilnehmer, String> _colNamen;
	

	public TableauWerkzeugUI()
	{
		_tab = new Tab("Tableau");
		_startTunierButton = new Button("Starte Tunier");
		_info = new Label(
		        "Tunier kann nicht gestartet werden, da es keine Teilnehmer gibt.");
		_tableauView = new TableView<>();
		_colID = new TableColumn<Teilnehmer, Integer>("ID");
		_colID.setStyle( "-fx-alignment: CENTER;");
		_colNamen = new TableColumn<Teilnehmer, String>("Name");
		erstelleTab();
	}

	private void erstelleTab()
	{
		_tab.setClosable(false);
		//_startTunierButton.setDisable(true);
		GridPane innergrid = new GridPane();
		VBox vbox = new VBox();

		innergrid.setAlignment(Pos.CENTER);
		innergrid.getChildren().add(vbox);

		_startTunierButton.setPrefWidth(100);
		_startTunierButton.setPrefHeight(50);
		vbox.getChildren().addAll(_startTunierButton, _info);
		vbox.setAlignment(Pos.CENTER);
		_tab.setContent(innergrid);
	}
	

	@SuppressWarnings("unchecked")
	void startTunier()
	{
		VBox vbox = new VBox();
		_colNamen.setCellValueFactory(
		        new PropertyValueFactory<Teilnehmer, String>("name"));
		_colID.setCellValueFactory(
		        new PropertyValueFactory<Teilnehmer, Integer>("id"));
		
		_tableauView.getColumns().addAll(_colID,_colNamen);
		vbox.getChildren().add(_tableauView);
		_tab.setContent(vbox);
	}

}
