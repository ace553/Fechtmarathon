package werkzeuge.menubar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import service.TunierService;
import werkzeuge.ActionListener;
import werkzeuge.ObservableAction;

public class MenuBarWerkzeug implements ObservableAction
{
	private MenuBarWerkzeugUI _ui;
	private TunierService _tunier;
	private File _file;
	private ExtensionFilter _filter;

	private List<ActionListener> _listeners;

	public MenuBarWerkzeug(TunierService tunier)
	{
		_ui = new MenuBarWerkzeugUI();
		_tunier = tunier;
		_file = null;
		_filter = new ExtensionFilter("Tunier Datei", "*.tunier");

		_listeners = new ArrayList<>();
		registriereSpeichern();
		registriereSpeichernUnter();
		registriereOeffnen();
		registriereLoeschen();
	}

	public MenuBar getMenu()
	{
		return _ui._menu;
	}

	private void registriereSpeichern()
	{
		_ui._speichern.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				if (_file == null)
				{
					_file = getNewFileLoc();
				}
				if (_file == null)
				{
					return;
				}
				_tunier.speichern(_file);
			}
		});
	}

	private void registriereSpeichernUnter()
	{
		_ui._speichernUnter.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				File f = getNewFileLoc();
				if (f == null)
				{
					return;
				}
				_tunier.speichern(f);
			}
		});
	}

	private void registriereLoeschen()
	{
		_ui._delete.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				notifyListeners(new ActionEvent("Loeschen", null));
			}
		});
	}

	private void registriereOeffnen()
	{
		_ui._oeffnen.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Öffnen");

				if (_file == null)
				{
					chooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
				} else
				{
					chooser.setInitialDirectory(_file.getParentFile());
				}

				chooser.getExtensionFilters().add(_filter);
				File selected = chooser.showOpenDialog(_ui._menu.getScene().getWindow());

				if (selected == null)
				{
					return;
				}

				if (selected.exists())
				{
					_tunier.ladeTunier(selected);
					_file = selected;
				}
			}
		});
	}

	private File getNewFileLoc()
	{
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Speichern unter");

		if (_file == null)
		{
			chooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		} else
		{
			chooser.setInitialDirectory(_file.getParentFile());
		}

		chooser.getExtensionFilters().add(_filter);
		File selected = chooser.showSaveDialog(_ui._menu.getScene().getWindow());

		if (selected == null)
		{
			return null;
		}

		if (!selected.exists())
		{
			try
			{
				selected.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return selected;
	}

	@Override
	public void addListener(ActionListener listener)
	{
		_listeners.add(listener);

	}

	@Override
	public void removeListener(ActionListener listener)
	{
		_listeners.remove(listener);
	}

	@Override
	public void notifyListeners(ActionEvent event)
	{
		for (ActionListener lis : _listeners)
		{
			lis.handle(event);
		}

	}
}
