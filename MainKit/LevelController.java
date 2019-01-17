package MainKit;

import BaseKit.Object;
import java.util.ArrayList;
import java.util.List;

import BaseKit.View;

public class LevelController extends Object {
	public LevelController(MainView parent, int rows, int columns) {
		super(parent);
		setObjectName("Levelcontroller");
		levelObjects = new ArrayList<>();
		level = new LevelObject(parent);
		level.setObjectName("Level");
		setupLevel(rows, columns, 0, 0, 20, 50, 50);
		
		levelObjects.add(level);
		Scoreboard box = new Scoreboard(parent);
		box.setObjectName("Scoreboard");
		setupScoreBoard();
		levelObjects.add(box);
	}

	public void addPoints(int points)
	{
		Scoreboard box = (Scoreboard) object("Scoreboard");
		box.addGamePoints(points);
	}
	
	public void resetScoreboard()
	{
		Scoreboard box = (Scoreboard) object("Scoreboard");
		box.resetGamePoints();
	}
	
	private View object(String name)
	{
		for (View view : levelObjects) {
			if(view.ObjectName().equals(name))
				return view;
		}
		return null;
	}
	
	public void drawObjects()
	{
		for (View obj : levelObjects)
			obj.draw();
	}
	
	private void setupLevel(int rows, 
			int columns,
			double x, 
			double y, 
			double borderwidth, 
			double vTopMargin, 
			double vBottomMargin)
	{
		level.setRows(rows);
		level.setColumns(columns);
		level.setBorderWidth(borderwidth);
		level.setverticalTopMargin(vTopMargin);
		level.setVerticalBottomMargin(vBottomMargin);
		level.setX(x);
		level.setY(y);
		
		level.setHorizontalCenter();
		level.MoveObjectHorizontally(150);
	}
	
	private void setupScoreBoard()
	{
		MainView Parent = (MainView) Parent();
		Scoreboard box = (Scoreboard) Parent.Child("Scoreboard");
		if(box == null)
			return;
		box.setX(20);
		box.setY(50);
		box.setWidth(300);
		box.setHeight(Parent.Height() - 60 );
		box.setBorderWidth(20);
		box.setRoundedCorners(20);
	}
	private LevelObject level;
	private List<View> levelObjects;
}
