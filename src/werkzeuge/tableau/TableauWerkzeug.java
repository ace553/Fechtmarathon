package werkzeuge.tableau;



import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
				_ui.startTunier();
				_tunier.starte();
				
				for(int i = 0; i < _tunier.getFechter().size(); i++)
				{
					TableColumn<Teilnehmer, String> c = new TableColumn<Teilnehmer, String>();
					c.setText(""+(i+1));
					c.setStyle( "-fx-alignment: CENTER;");
					final int t = i;
					c.setCellValueFactory(data -> data.getValue().gefechtProperty(t));
					c.setCellFactory(new Callback<TableColumn<Teilnehmer, String>, TableCell<Teilnehmer, String>>() {
			            public TableCell<Teilnehmer, String> call(TableColumn<Teilnehmer, String> p) {
			                TableCell<Teilnehmer, String> cell = new TableCell<Teilnehmer, String>() {
			                    @Override
			                    public void updateItem(String item, boolean empty) {
			                        super.updateItem(item, empty);
			                        setText(empty ? null : item);
			                        if(!empty && item.equals("O"))
			                        {
			                        	setText("");
			                        	setStyle("-fx-background-color:"+getString());
			                        }
			                    }

			                    private String getString() {
			                        return "black";
			                    }
			                };


			                return cell;
			            }
			        });
					_ui._tableauView.getColumns().add(c);
				}
				
			}
		});
	}
}
