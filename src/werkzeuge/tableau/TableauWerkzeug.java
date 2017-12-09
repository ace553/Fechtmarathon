package werkzeuge.tableau;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import service.TunierService;

public class TableauWerkzeug
{

	final TableauWerkzeugUI _ui;
	final TunierService _tunier;
	final List<TableColumn<Teilnehmer, String>> _gefechte;

	public TableauWerkzeug(TunierService tunier)
	{
		_ui = new TableauWerkzeugUI();
		_tunier = tunier;

		_gefechte = new ArrayList<TableColumn<Teilnehmer, String>>();

		_ui._tableauView.setItems(_tunier.getTeilnehmer());
		registriereStartTunierButton();
	}

	public Tab getTab()
	{
		return _ui._tab;
	}

	private void registriereStartTunierButton()
	{
		_ui._startTunierButton.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				if (_tunier.getFechter().size() > 0)
				{
					if (_tunier.alleFechterDa())
					{
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Tunier starten");
						alert.setHeaderText("Soll das Tunier wirklich gestartet werden?\nEs können nach Tunierstart keine Fechter mehr hinzugefügt werden\nund keine Gruppen mehr bearbeitet werden.");

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK)
						{
							starteTunier();
						}
					} else
					{
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Tunier kann nicht gestartet werden");
						alert.setHeaderText("Alle nicht anwesenden Fechter streichen?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.isPresent() && result.get() == ButtonType.OK)
						{
							_tunier.streicheNichtAnwesendeFechter();
							starteTunier();
						}
					}
				} else
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Keine Teilnehmer");
					alert.setHeaderText("Es sind keine Fechter gemeldet.");
					alert.setContentText("Melden sie mindestens zwei Fechter.");
					alert.showAndWait();
				}

			}
		});
	}

	private void starteTunier()
	{
		_ui.startTunier();
		_tunier.starte();

		for (int i = 0; i < _tunier.getFechter().size(); i++)
		{
			TableColumn<Teilnehmer, String> c = new TableColumn<Teilnehmer, String>();
			c.setText("" + (i + 1));
			c.setStyle("-fx-alignment: CENTER;");
			c.setSortable(false);
			final int t = i;
			c.setCellValueFactory(data -> data.getValue().gefechtProperty(t));
			c.setCellFactory(new Callback<TableColumn<Teilnehmer, String>, TableCell<Teilnehmer, String>>()
			{
				public TableCell<Teilnehmer, String> call(TableColumn<Teilnehmer, String> p)
				{
					TableCell<Teilnehmer, String> cell = new TableCell<Teilnehmer, String>()
					{
						@Override
						public void updateItem(String item, boolean empty)
						{
							super.updateItem(item, empty);
							setText(empty ? null : item);
							if (!empty && item.equals("O"))
							{
								setText("");
								setStyle("-fx-background-color:" + getString());
							}
						}

						private String getString()
						{
							return "black";
						}
					};

					return cell;
				}
			});
			_ui._tableauView.getColumns().add(c);
		}

		TableColumn<Teilnehmer, Integer> siege = new TableColumn<Teilnehmer, Integer>("Sg");
		siege.setStyle("-fx-alignment: CENTER;");
		siege.setCellValueFactory(data -> data.getValue().siegeProperty().asObject());
		siege.setSortable(false);
		_ui._tableauView.getColumns().add(siege);

		TableColumn<Teilnehmer, Integer> index = new TableColumn<Teilnehmer, Integer>("Ind");
		index.setStyle("-fx-alignment: CENTER;");
		index.setCellValueFactory(data -> data.getValue().indexProperty().asObject());
		index.setSortable(false);
		_ui._tableauView.getColumns().add(index);

		TableColumn<Teilnehmer, Integer> gegeben = new TableColumn<Teilnehmer, Integer>("geg");
		gegeben.setStyle("-fx-alignment: CENTER;");
		gegeben.setCellValueFactory(data -> data.getValue().gegebenProperty().asObject());
		gegeben.setSortable(false);
		_ui._tableauView.getColumns().add(gegeben);

		TableColumn<Teilnehmer, Integer> erhalten = new TableColumn<Teilnehmer, Integer>("erh");
		erhalten.setStyle("-fx-alignment: CENTER;");
		erhalten.setCellValueFactory(data -> data.getValue().erhaltenProperty().asObject());
		erhalten.setSortable(false);
		_ui._tableauView.getColumns().add(erhalten);
	}
}
