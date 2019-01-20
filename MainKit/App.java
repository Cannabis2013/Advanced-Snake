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
		
		// if row and column arguments is invalid then standard values will be set.
		
		SettingsClass.parseArguments(getParameters().getRaw());
		
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
