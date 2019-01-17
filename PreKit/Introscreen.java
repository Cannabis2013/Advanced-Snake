package PreKit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import BaseKit.View;
import MainKit.MainView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Introscreen extends View {
	public Introscreen() {
		setFullScreen(true);
		FileInputStream inputstream;
		try {
			inputstream = new FileInputStream("Pictures\\Snake.jpg");
			double w = 2048, h = 1152;
			setBackground(new Image(inputstream,w,h,false,true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.show();
	}
	
	@Override
	protected void keyPressEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER)
		{
		}
	}
	
	@Override
	public void draw() {
		
	}
	
	public void Show()
	{
		paintClear();
		paintUpdate();
	}

}
