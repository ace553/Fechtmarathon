package application;

import fechten.Tunier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.TunierService;
import werkzeuge.fechter.FechterWerkzeug;
import werkzeuge.gruppen.GruppenWerkzeug;
import werkzeuge.menubar.MenuBarWerkzeug;
import werkzeuge.stage.StageWerkzeug;
import werkzeuge.stage.StageWerkzeugUI;
import werkzeuge.tableau.TableauWerkzeug;

public class Main extends Application
{
	
	private StageWerkzeug _stageWerkzeug;
	private TunierService _tunier;
	
	public Main()
	{
		_tunier = new TunierService(new Tunier());
	}
	
	@Override
	public void start(Stage stage)
	{
		_stageWerkzeug = new StageWerkzeug(stage, _tunier);
		
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
