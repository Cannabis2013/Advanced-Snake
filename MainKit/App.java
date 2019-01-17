package MainKit;

import BaseKit.Object;
import BaseKit.View;
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
		
		/*
		 * Retrieves the list of arguments passed by command line
		 */
		List<String> parameters = getParameters().getRaw();
		/*
		 * Checks if any arguments is passed. Only pair of integers is accepted.
		 * If no arguments passed, standard values will be chosen.
		 */
		int r = 30, c = 40;
		if (parameters.size() == 2) {
		
			String rows = parameters.get(0);
			String columns = parameters.get(1);
			
			if(Object.isPureDigit(rows) && Object.isPureDigit(columns))
			{
				r = Integer.parseInt(rows);
				c = Integer.parseInt(columns);				
			}
		}
		
		/*
		 * Checks if arguments interval is appropiate. Otherwise throw exception.
		 */
		
		if(r < 5 || r > 100 || c < 5 || c > 100)
			throw new IllegalArgumentException();
		
		Introscreen startScreen = new Introscreen();
		startScreen.Show();
		//MainView mWiew = new MainView(r, c);
		//mWiew.show();
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
