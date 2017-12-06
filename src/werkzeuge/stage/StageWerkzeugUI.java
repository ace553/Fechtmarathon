package werkzeuge.stage;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageWerkzeugUI
{

	final Stage _stage;
	
	public StageWerkzeugUI(Stage s)
	{
		_stage = s;

	}
	
	public void setupStage(Node... children)
	{
		VBox box = new VBox();
		box.getChildren().addAll(children);
		Scene scene = new Scene(box);
		_stage.setTitle("Marathon Tunier Manager");
		_stage.setMaximized(true);
		
		_stage.setScene(scene);
	}
}
