package MainKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import BaseKit.Object;
import BaseKit.View;
import javafx.scene.paint.Color;

public class OverLayController extends Object {

	public OverLayController(MainView parent) {
		super(parent);
		setObjectName("OverlayController");
		textObjects = new ArrayList<>();
	}
	
	public void drawObjects()
	{
		for (TextObject obj : textObjects)
			obj.draw();
	}
	
	public void showText(String txt, double x, double y, int pointSize, Color textColor, double maxWidth)
	{
		MainView parent = (MainView) Parent();
		TextObject obj = new TextObject(parent,txt, x, y, pointSize, textColor,maxWidth);
		textObjects.add(obj);
	}
	
	public void showText(String txt, double x, double y, int pointSize, Color textColor, double maxWidth,int milliseconds)
	{
		MainView parent = (MainView) Parent();
		TextObject obj = new TextObject(parent,txt, x, y, pointSize, textColor,maxWidth);
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
