package werkzeuge.fechter;

import fechten.Fechter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import service.TunierService;

public class FechterWerkzeug
{

	private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	private final FechterWerkzeugUI _ui;

	private TunierService _tunier;

	public FechterWerkzeug(TunierService tunier)
	{
		_tunier = tunier;

		_ui = new FechterWerkzeugUI();
		_ui._table.setItems(_tunier.getFechter());
		registriereTableChange();
		registriereMeldenButton();
		registriereDragAndDropListener();
		registriereTunierService();
	}

	public Tab getTab()
	{
		return _ui._tab;
	}

	public void loescheAusgewaehlteFechter()
	{
		for (Fechter f : _ui._table.getSelectionModel().getSelectedItems())
		{
			_tunier.loesche(f);
		}
	}

	private void registriereMeldenButton()
	{
		_ui._meldenButton.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				Fechter f = new Fechter(_ui._vorname.getText(), _ui._nachname.getText(), _ui._verein.getText());
				_tunier.melde(f);
				_ui._vorname.clear();
				_ui._nachname.clear();
				_ui._verein.clear();
			}
		});
	}

	private void registriereTunierService()
	{
		_tunier.addListener(new InvalidationListener()
		{

			@Override
			public void invalidated(Observable observable)
			{
				if (_tunier.istGespeichert())
				{
					_ui._tab.setText("Teilnehmer");
				} else
				{
					_ui._tab.setText("Teilnehmer *");
				}
			}
		});
	}

	private void registriereDragAndDropListener()
	{
		_ui._table.setRowFactory(tv ->
		{
			TableRow<Fechter> row = new TableRow<>();

			row.setOnDragDetected(event ->
			{
				if (!row.isEmpty())
				{
					Integer index = row.getIndex();
					Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
					db.setDragView(row.snapshot(null, null));
					ClipboardContent cc = new ClipboardContent();
					cc.put(SERIALIZED_MIME_TYPE, index);
					db.setContent(cc);
					event.consume();
				}
			});

			row.setOnDragOver(event ->
			{
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE))
				{
					if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue())
					{
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
						event.consume();
					}
				}
			});

			row.setOnDragDropped(event ->
			{
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE))
				{
					int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
					Fechter draggedPerson = _ui._table.getItems().remove(draggedIndex);

					int dropIndex;

					if (row.isEmpty())
					{
						dropIndex = _ui._table.getItems().size();
					} else
					{
						dropIndex = row.getIndex();
					}

					_ui._table.getItems().add(dropIndex, draggedPerson);

					event.setDropCompleted(true);
					_ui._table.getSelectionModel().select(dropIndex);
					_tunier.updateFechter();
					event.consume();
				}
			});

			return row;
		});

	}

	private void registriereTableChange()
	{
		_tunier.getFechter().addListener(new ListChangeListener<Fechter>()
		{

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Fechter> pChange)
			{
				_tunier.updateFechter();
			}
		});

		_ui._colAnwesend.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Fechter, Boolean>>()
		{

			@Override
			public void handle(CellEditEvent<Fechter, Boolean> event)
			{
				_tunier.bearbeitet();
			}
		});
		_ui._colGestrichen.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Fechter, Boolean>>()
		{

			@Override
			public void handle(CellEditEvent<Fechter, Boolean> event)
			{
				_tunier.bearbeitet();
			}
		});
		_ui._colNachnamen.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Fechter, String>>()
		{

			@Override
			public void handle(CellEditEvent<Fechter, String> event)
			{
				event.getRowValue().nachnameProperty().set(event.getNewValue());
				event.getRowValue().nameProperty().set(event.getRowValue().getVorname() + " " + event.getRowValue().getNachname());
				_tunier.bearbeitet();
			}
		});
		_ui._colVornamen.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Fechter, String>>()
		{

			@Override
			public void handle(CellEditEvent<Fechter, String> event)
			{

				event.getRowValue().vornameProperty().set(event.getNewValue());
				event.getRowValue().nameProperty().set(event.getRowValue().getVorname() + " " + event.getRowValue().getNachname());
				_tunier.bearbeitet();
			}
		});

		_ui._colVerein.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Fechter, String>>()
		{

			@Override
			public void handle(CellEditEvent<Fechter, String> event)
			{

				event.getRowValue().vereinProperty().set(event.getNewValue());
				_tunier.bearbeitet();
			}
		});
	}
}
