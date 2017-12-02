package application;

import fechten.Tunier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.TunierService;
import werkzeuge.menubar.MenuBarWerkzeug;
import werkzeuge.tableau.TableauWerkzeug;
import werkzeuge.teilnehmer.TeilnehmerWerkzeug;

public class Main extends Application
{

	private TeilnehmerWerkzeug _teilnehmerWerkzeug;
	private MenuBarWerkzeug _menuBarWerkzeug;
	private TableauWerkzeug _tableauWerkzeug;
	
	
	private TunierService _tunier;
	
	public Main()
	{
		_tunier = new TunierService(new Tunier());
		_teilnehmerWerkzeug = new TeilnehmerWerkzeug(_tunier);
		_menuBarWerkzeug = new MenuBarWerkzeug(_tunier);
		_tableauWerkzeug = new TableauWerkzeug(_tunier);
	}
	
	@Override
	public void start(Stage stage)
	{
		VBox box = new VBox();
		TabPane layout = new TabPane();
		
		layout.getTabs().addAll(_tableauWerkzeug.getTab(), _teilnehmerWerkzeug.getTab());
		
		box.getChildren().addAll(_menuBarWerkzeug.getMenu(),layout);
		Scene scene = new Scene(box);
		stage.setTitle("Marathon Tunier Manager");
		stage.setWidth(700);
		stage.setHeight(500);

		
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
