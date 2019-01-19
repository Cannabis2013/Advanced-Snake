package MainKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import BaseKit.Object;
import BaseKit.View;
import MainKit.TextObject.fillMode;
import javafx.scene.paint.Color;

public class OverLayController extends Object {

	public OverLayController(View parent) {
		super(parent);
		setObjectName("OverlayController");
		textObjects = new ArrayList<>();
	}
	
	public void drawObjects()
	{
		for (TextObject obj : textObjects)
			obj.draw();
	}
	
	public void showText(String txt, double x, double y, int pointSize, Color textColor,fillMode Mode, double maxWidth)
	{
		View parent = (View) Parent();
		TextObject obj = new TextObject(parent,txt, x, y, pointSize, textColor, Mode, maxWidth);
		obj.setBoxColor(Color.BLACK);
		textObjects.add(obj);
	}
	
	public void showText(String txt, double x, double y, int pointSize, Color textColor, fillMode Mode,double maxWidth,int milliseconds)
	{
		View parent = (View) Parent();
		TextObject obj = new TextObject(parent,txt, x, y, pointSize, textColor, Mode, maxWidth);
		textObjects.add(obj);
		Timer tm = new Timer();
		tm.schedule(new TimerTask() {
			@Override
			public void run() {
				textObjects.remove(obj);
			}
		}, milliseconds);
	}
	
	public void clear()
	{
		textObjects.clear();
	}
	
	private List<TextObject> textObjects;
}
