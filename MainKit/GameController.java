package MainKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import BaseKit.Object;
import BaseKit.View;
import BaseKit.PointD;
import MainKit.SnakeObject.direction;
import Workers.ObjectAnimator;
import Workers.Worker;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameController extends Object {
	public GameController(Object parent) {
		super(parent);
		setObjectName("GameController");
		Parent = (MainView) Parent();
		snakeAnimator = new ObjectAnimator(this);
		lController = (LevelController) Parent.Child("LevelController");
		level = (LevelObject) Parent.Child("Level");
		semiInteractiveObjects = new ArrayList<>();
		blockRemainer = level.BlockSize();
		
		initializeSnakePosition(level.columnCount()/2, level.rowCount()/2);
		generateFoodObject();
	}
	
	public void initializeSnakePosition(double x, double y)
	{
		snake = new SnakeObject(Parent,1, Worker.PollRate());
		snake.setWidth(level.BlockSize());
		snake.setPosition(level.translate(x, y));
		snake.setObjectName("Snake");
		snake.setColor(Color.GREEN);
	}
	
	public void addSemiInteractiveObject(ViewObject obj)
	{
		semiInteractiveObjects.add(obj);
	}
		
	public void keyEvent(KeyCode key)
	{	
		if(key == KeyCode.R)
		{
			lController.resetScoreboard();
			snakeAnimator.Stop();
			Parent.RemoveChild(snake);
			snake = new SnakeObject(Parent,1,Worker.PollRate());
			initializeSnakePosition(level.columnCount()/2, level.rowCount()/2);
			generateFoodObject();
		}
		else if(key == KeyCode.S)
		{
			snakeAnimator.Stop();
		}
		else if(key == KeyCode.ENTER && !snakeAnimator.isWorking())
		{
			snakeAnimator = new ObjectAnimator(this);
			snakeAnimator.setTarget(snake);
			snakeAnimator.start();
		}
		else if(key.isArrowKey())
		{	
			snake.setNextDirection(DirectionFromKey(key));
		}
	}
	
	public void moveObject(double dx,double dy)
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				blockRemainer -= dx;
				PointD nPos = snake.position().copy();
				if((!isOpposite(snake.NextDirection(),snake.CurrentDirection()))
						&& blockRemainer <= 0)
				{					
					snake.setCurrentDirection(snake.NextDirection());
					blockRemainer = level.BlockSize();
					updateCoordinates(nPos, snake.CurrentDirection(),dx,dy);
					int c = level.relativeX(nPos.X()), r = level.relativeY(nPos.Y());
					nPos = level.translate(c, r);
				}
				else
					updateCoordinates(nPos, snake.CurrentDirection(),dx,dy);
				
				CheckAndCorrelateBoundaries(nPos, snake);
				
				/*
				 * Check for collison
				 * First checks if the new position is part of the snakes body
				 * Then checks if it is a 'Head meets head' scenario
				 */
				
				if(snake.containsCoordinate(nPos))
				{
					snake.Kill();
					snakeAnimator.Stop();
					return;					
				}
				FoodObject food = (FoodObject) SemiInteractiveObject("Food");
				if(nPos.Equals(food.Position()))
				{
					snake.eat(food);
					lController.addPoints(food.getPoint());
					generateFoodObject();
					OverLayController  oController = (OverLayController) Parent.Child("OverlayController");
					oController.showText("Point",food.X() , food.Y(), 32, Color.WHITE, level.Width(), 1000);
				}
				snake.moveToCoordinates(nPos);
			}
		});
	}
	
	/*
	 * Draw section
	 */
	
	public void drawObjects()
	{
		snake.draw();
		for (View obj : semiInteractiveObjects)
			obj.draw();
	}
	
	private direction DirectionFromKey(KeyCode key)
	{
		if(key == KeyCode.LEFT)
			return direction.left;
		else if(key == KeyCode.RIGHT)
			return direction.right;
		else if(key == KeyCode.UP)
			return direction.up;
		else if(key == KeyCode.DOWN)
			return direction.down;
		else
			return null;
	}
	
	private void updateCoordinates(PointD nPos, direction dir, double dx, double dy)
	{
		if(dir == direction.right)
			nPos.incrementX(dx);
		else if(dir == direction.left)
			nPos.decrementX(dx);
		else if(dir == direction.up)
			nPos.decrementY(dy);
		else if(dir == direction.down)
			nPos.incrementY(dy);
	}
	
	private boolean isOpposite(direction nextDir,direction currentDir)
	{
		if(nextDir == direction.left && currentDir == direction.right)
			return true;
		else if(nextDir == direction.right && currentDir== direction.left)
			return true;
		else if(nextDir == direction.up && currentDir == direction.down)
			return true;
		else if(nextDir == direction.down && currentDir== direction.up)
			return true;
		else
			return false;
	}
	
	private void CheckAndCorrelateBoundaries(PointD nPos, SnakeObject obj)
	{
		if(nPos.X() - 1 > level.RightBound())
			nPos.setX(level.translateX(0));
		else if(nPos.X() + 1 < level.LeftBound())
			nPos.setX(level.translateX((int) level.columnCount() - 1));
		else if((nPos.Y() + obj.BlockSize() - 1) > level.LowerBound())
			nPos.setY(level.translateY(0));
		else if(nPos.Y() + 1 < level.UpperBound())
			nPos.setY(level.lastColumn());
	}
	
	private void generateFoodObject()
	{
		removeSemiInteractiveObject("Food");
		
		Random generator = new Random();
		double x = generator.nextInt(level.columnCount()),
				y = generator.nextInt(level.rowCount());
		
		while(snake.containsCoordinate(level.translate(new PointD(x, y))))
		{
			x = generator.nextInt(level.columnCount());
			y = generator.nextInt(level.rowCount());
		}
		
		FoodObject obj = new FoodObject(Parent,new PointD(level.translateX(x),level.translateY(y)),level.BlockSize());
		obj.setObjectName("Food");
		obj.setColor(Color.RED);
		addSemiInteractiveObject(obj);
	}
	
	private ViewObject SemiInteractiveObject(String objectName)
	{
		for (ViewObject obj : semiInteractiveObjects) {
			if(obj.ObjectName().equals(objectName))
				return obj;
		}
		return null;
	}
	
	private void removeSemiInteractiveObject(String objectName)
	{
		View obj = SemiInteractiveObject(objectName);
		if(obj != null)
			semiInteractiveObjects.remove(obj);
	}
	
	private List<ViewObject> semiInteractiveObjects;
	private SnakeObject snake;
	private MainView Parent;
	private LevelController lController;
	private LevelObject level;
	private ObjectAnimator snakeAnimator;
	double blockRemainer;
}
