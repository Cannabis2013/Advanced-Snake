package PreKit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


import BaseKit.View;
import MainKit.MainView;
import MainKit.OverLayController;
import MainKit.SoundController;
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
	public Introscreen() {
		
		setFullScreen(true);
		super.show();
		URL imgUrl = Introscreen.class.getResource("/Snake.jpg");
		Image img = new Image(imgUrl.toString());
		setBackground(img);
		pWorker = new PaintWorker(this);
		sController = new SoundController(this);
		oController = new OverLayController(this);
		
		Worker.setGlobalPollRate(2);
		showIntroText();
		sController.playRepeatet(Introscreen.class.getResource("/Intro_music.mp3").toString());
	}
	
	@Override
	protected void keyPressEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER)
		{
			new MainView();
			pWorker.Stop();
			sController.stopRepeatet();
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
	private SoundController sController;
	private OverLayController oController;
	private PaintWorker pWorker;
	private int counter = 0, r, c;
}
