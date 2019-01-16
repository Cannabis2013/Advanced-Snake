package MainKit;


import BaseKit.View;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
	
public class MainView extends View{
	public  MainView(int gridRows, int gridColumns) 
	{
		setBackgroundColor(Color.DARKBLUE);
		setFullScreen(true);
		super.show();
		
		/*
		 * Initialize the controllers
		 */
		lController = new LevelController(this, gridRows, gridColumns);
		pWorker = new PaintWorker(this);
		gController = new GameController(this);
		sController = new SoundController();
		
		sController.playMusic();
		
		pWorker.setPollRate(100);
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
	
	public void show()
	{
		draw();
		pWorker.start();
	}
	
	private GameController gController;
	private LevelController lController;
	private SoundController sController;
	private PaintWorker pWorker;
}
