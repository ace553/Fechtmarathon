package werkzeuge.teilnehmer;

import fechten.Fechter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TeilnehmerWerkzeugUI
{
	final Tab _tab;

	final Button _loeschenButton;

	final TableView<Fechter> _table;
	final TableColumn<Fechter, Integer> _colID;
	final TableColumn<Fechter, String> _colVornamen;
	final TableColumn<Fechter, String> _colNachnamen;
	final TableColumn<Fechter, String> _colVerein;
	final TableColumn<Fechter, Boolean> _colAnwesend;
	final TableColumn<Fechter, Boolean> _colGestrichen;

	final TextField _vorname;
	final TextField _nachname;
	final TextField _verein;
	final Button _meldenButton;

	TeilnehmerWerkzeugUI()
	{
		_tab = new Tab("Teilnehmer");

		_loeschenButton = new Button("Löschen");
		_loeschenButton.setDisable(true);
		_table = new TableView<Fechter>();

		_colID = new TableColumn<Fechter, Integer>("ID");
		
		_colVornamen = new TableColumn<Fechter, String>("Vorname");
		_colNachnamen = new TableColumn<Fechter, String>("Nachname");
		_colVerein = new TableColumn<Fechter, String>("Verein");
		_colAnwesend = new TableColumn<Fechter, Boolean>("Anwesend");
		_colGestrichen = new TableColumn<Fechter, Boolean>("Gestrichen");
		_vorname = new TextField();
		_nachname = new TextField();
		_verein = new TextField();
		_meldenButton = new Button("Melden");

		erstelleTab();
	}

	@SuppressWarnings("unchecked")
	private void erstelleTab()
	{

		_tab.setClosable(false);

		_table.setEditable(true);
		_table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		_colID.setCellValueFactory(
		        new PropertyValueFactory<Fechter, Integer>("id"));
		_colID.setSortable(false);
		_colID.setStyle( "-fx-alignment: CENTER;");
		_colID.setPrefWidth(30);
		_colVornamen.setCellValueFactory(
		        new PropertyValueFactory<Fechter, String>("vorname"));
		_colVornamen
		        .setCellFactory(TextFieldTableCell.<Fechter>forTableColumn());

		_colNachnamen.setCellValueFactory(
		        new PropertyValueFactory<Fechter, String>("nachname"));
		_colNachnamen
		        .setCellFactory(TextFieldTableCell.<Fechter>forTableColumn());

		_colVerein.setCellValueFactory(
		        new PropertyValueFactory<Fechter, String>("verein"));
		_colVerein.setCellFactory(TextFieldTableCell.<Fechter>forTableColumn());

		_colAnwesend.setCellValueFactory(
		        new PropertyValueFactory<Fechter, Boolean>("anwesend"));

		_colAnwesend.setCellFactory(
		        new Callback<TableColumn<Fechter, Boolean>, TableCell<Fechter, Boolean>>()
		        {

			        public TableCell<Fechter, Boolean> call(
			                TableColumn<Fechter, Boolean> p)
			        {
				        return new CheckBoxTableCell<Fechter, Boolean>();
			        }
		        });

		_colGestrichen.setCellValueFactory(
		        new PropertyValueFactory<Fechter, Boolean>("gestrichen"));
		_colGestrichen.setCellFactory(
		        new Callback<TableColumn<Fechter, Boolean>, TableCell<Fechter, Boolean>>()
		        {

			        public TableCell<Fechter, Boolean> call(
			                TableColumn<Fechter, Boolean> p)
			        {
				        return new CheckBoxTableCell<Fechter, Boolean>();
			        }
		        });

		_table.getColumns().addAll(_colID, _colVornamen, _colNachnamen,
		        _colVerein, _colAnwesend, _colGestrichen);

		_vorname.setMaxWidth(_colVornamen.getPrefWidth());
		_vorname.setPromptText("Vorname");

		_nachname.setMaxWidth(_colNachnamen.getPrefWidth());
		_nachname.setPromptText("Nachname");

		_verein.setMaxWidth(_colVerein.getPrefWidth());
		_verein.setPromptText("Verein");

		final BorderPane buttonLayout = new BorderPane();
		buttonLayout.setPadding(new Insets(0, 10, 5, 0));
		buttonLayout.setRight(_loeschenButton);

		final HBox meldeBox = new HBox();
		meldeBox.setSpacing(3);
		meldeBox.setPadding(new Insets(10, 0, 10, 10));
		meldeBox.getChildren().addAll(_vorname, _nachname, _verein,
		        _meldenButton);

		final VBox vbox = new VBox();
		vbox.setSpacing(0);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(buttonLayout, _table, meldeBox);

		_tab.setContent(vbox);
	}
}