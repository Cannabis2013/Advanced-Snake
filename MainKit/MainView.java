package MainKit;

import BaseKit.View;
import Workers.PaintWorker;
import Workers.Worker;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
	
public class MainView extends View{
	public  MainView() 
	{
		Worker.setGlobalPollRate(120);
		setBackgroundColor(SettingsClass.backgroundColor);
		setFullScreen(true);
		super.show();
		
		/*
		 * Initialize the controllers
		 */
		lController = new LevelController(this);
		pWorker = new PaintWorker(this);
		sController = new SoundController(this);
		gController = new GameController(this);
		
		sController.playRepeatet("Music/game_music.mp3");
		pWorker.start();
	}
	
	@Override
	protected void keyPressEvent(KeyEvent event) 
	{
		if(event.getCode() == KeyCode.Q && event.isControlDown())
		{
			Worker.StopAll(); // Stop all worker instances
			Platform.exit();
			
		}
		gController.keyEvent(event.getCode());
	}
	
	@Override
	protected void mouseMoveEvent(MouseEvent event) {
		//print(String.format("x: %1$,.2f y: %2$,.2f", event.getX(),event.getY()));
	}
	
	public void draw() 
	{
		Platform.runLater(new Runnable() {
			public void run() {
				paintClear();
				lController.drawObjects();
				gController.drawObjects();
				paintUpdate();
			}
		});
	}
	
	private GameController gController;
	private LevelController lController;
	private SoundController sController;
	private PaintWorker pWorker;
}
