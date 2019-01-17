package PreKit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import BaseKit.View;
import javafx.scene.image.Image;

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
	
	public void Show()
	{
		paintClear();
		paintUpdate();
	}

}
