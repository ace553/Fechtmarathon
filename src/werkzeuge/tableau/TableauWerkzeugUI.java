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

class TableauWerkzeugUI
{
	final Tab _tab;

	final Button _startTunierButton;
	final Label _info;

	final TableView<Teilnehmer> _tableauView;
	final TableColumn<Teilnehmer, Integer> _colID;
	final TableColumn<Teilnehmer, String> _colNamen;

	final VBox _tableauContent;

	final GridPane _startContent;

	TableauWerkzeugUI()
	{
		_tab = new Tab("Tableau");
		_startTunierButton = new Button("Starte Tunier");
		_info = new Label("Tunier kann nicht gestartet werden, da es keine Teilnehmer gibt.");
		_tableauView = new TableView<>();
		_colID = new TableColumn<Teilnehmer, Integer>("ID");
		_colID.setStyle("-fx-alignment: CENTER;");
		_colNamen = new TableColumn<Teilnehmer, String>("Name");

		_tableauContent = new VBox();
		setupTableauContent();

		_startContent = new GridPane();

		erstelleTab();
		startContent();

	}

	@SuppressWarnings("unchecked")
	private void setupTableauContent()
	{
		_colNamen.setCellValueFactory(new PropertyValueFactory<Teilnehmer, String>("name"));
		_colNamen.setSortable(false);

		_colID.setCellValueFactory(new PropertyValueFactory<Teilnehmer, Integer>("id"));
		_colID.setSortable(false);

		_tableauView.getColumns().addAll(_colID, _colNamen);
		_tableauView.getSelectionModel().setCellSelectionEnabled(true);
		_tableauView.setPrefHeight(10000);
		_tableauContent.getChildren().add(_tableauView);
	}

	private void erstelleTab()
	{
		_tab.setClosable(false);
		VBox vbox = new VBox();

		_startContent.setAlignment(Pos.CENTER);
		_startContent.getChildren().add(vbox);

		_startTunierButton.setPrefWidth(100);
		_startTunierButton.setPrefHeight(50);
		vbox.getChildren().addAll(_startTunierButton, _info);
		vbox.setAlignment(Pos.CENTER);
	}

	void startContent()
	{

		_tab.setContent(_startContent);
	}

	void startTunier()
	{
		_tab.setContent(_tableauContent);
	}

}
