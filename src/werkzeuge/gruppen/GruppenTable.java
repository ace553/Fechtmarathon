package werkzeuge.gruppen;

import fechten.Fechter;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GruppenTable extends VBox
{
	private final TableView<Fechter> _table;
	private final Button _closeButton;
	private final Label _title;
	private final StringProperty _titProp;

	@SuppressWarnings("unchecked")
	public GruppenTable(StringProperty title, boolean closeable)
	{
		_titProp = title;
		_table = new TableView<>();
		_closeButton = new Button("\u00D7");
		_title = new Label();
		_title.setText(title.get());
		_title.setAlignment(Pos.BASELINE_CENTER);
		_title.setFont(new Font(15));
		BorderPane titleBar = new BorderPane();
		if (closeable)
			titleBar.setRight(_closeButton);
		titleBar.setCenter(_title);
		titleBar.setPadding(new Insets(10, 0, 5, 0));
		getChildren().addAll(titleBar, _table);
		setAlignment(Pos.CENTER);

		setMaxWidth(200);
		TableColumn<Fechter, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<Fechter, String>("name"));
		name.setPrefWidth(120);
		TableColumn<Fechter, String> verein = new TableColumn<>("Verein");
		verein.setCellValueFactory(new PropertyValueFactory<Fechter, String>("verein"));
		verein.setPrefWidth(70);
		_table.getColumns().addAll(name, verein);
		registriereNameChange();
	}
	
	private void registriereNameChange()
	{
		_titProp.addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				
				_title.setText(newValue);
			}
		});
	}

	public TableView<Fechter> getTable()
	{
		return _table;
	}

	public Button getCloseButton()
	{
		return _closeButton;
	}
}
