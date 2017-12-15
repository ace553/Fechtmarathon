package application;

import fechten.Tunier;
import javafx.application.Application;
import javafx.stage.Stage;
import service.TunierService;
import werkzeuge.stage.StageWerkzeug;

public class Main extends Application
{

	@SuppressWarnings("unused")
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
