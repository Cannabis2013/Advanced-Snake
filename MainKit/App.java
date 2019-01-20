package MainKit;

import BaseKit.Object;
import PreKit.Introscreen;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		/*
		 * Main thread
		 */
		
		SettingsClass.parseArguments(getParameters().getRaw());
		
		/*
		 * Checks if arguments interval is appropiate. Otherwise throw exception.
		 */
		
		if(SettingsClass.rows < 5 || SettingsClass.rows > 100 || SettingsClass.columns < 5 || SettingsClass.columns > 100)
			throw new IllegalArgumentException();
		
		Introscreen startScreen = new Introscreen();
		startScreen.Show();
		}
	public void exec(String[] args)
	{
		launch(args);
	}
	
	public void exit()
	{
		Platform.exit();
	}
	
	public static void main(String[] args) {
		App a = new App();
		a.exec(args);
	}
}
