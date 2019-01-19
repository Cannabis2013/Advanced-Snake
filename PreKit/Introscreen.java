package PreKit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import BaseKit.View;
import MainKit.MainView;
import MainKit.OverLayController;
import MainKit.TextObject.fillMode;
import Workers.PaintWorker;
import Workers.Worker;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Introscreen extends View {
	public Introscreen(int rows, int columns) {
		r = rows;
		c = columns;
		setFullScreen(true);
		super.show();
		FileInputStream inputstream;
		try {
			inputstream = new FileInputStream("Pictures\\Snake.jpg");
			double w = Width(), h = Height();
			setBackground(new Image(inputstream,w,h,false,true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pWorker = new PaintWorker(this);
		oController = new OverLayController(this);
		
		
		Worker.setGlobalPollRate(2);
		showIntroText();
	}
	
	@Override
	protected void keyPressEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER)
		{
			MainView mView = new MainView(this,r, c);
			pWorker.Stop();
			mView.show();
			Close();
		}
		else if(event.isControlDown() && event.getCode() == KeyCode.Q)
		{
			Worker.StopAll();
			Platform.exit();
		}
	}
	
	@Override
	public void draw() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				paintClear();
				if(counter % 2 == 0)
					oController.drawObjects();
				paintUpdate();
				counter++;
			}
		});
	}
	
	public void Show()
	{
		super.show();
		paintClear();
		paintUpdate();
		pWorker.start();
	}
	
	private void showIntroText()
	{
		int pSize = 64;
		Text txt = new Text("Press Enter to play or CTRL + Q to quit!");
		txt.setFont(new Font(pSize));
		double x = Width()/2 - txt.getLayoutBounds().getWidth()/2;
		oController.showText("Press Enter to play or CTRL + Q to quit!", x, 
				Height()/1.2, 
				pSize, Color.WHITE,fillMode.boxedText, Width());
	}
	private OverLayController oController;
	private PaintWorker pWorker;
	int counter = 0, r, c;
}
