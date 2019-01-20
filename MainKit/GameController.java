package MainKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import BaseKit.Object;
import BaseKit.View;
import BaseKit.PointD;
import MainKit.SnakeObject.direction;
import MainKit.TextObject.fillMode;
import Workers.ObjectAnimator;
import Workers.Worker;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
		oController = new OverLayController((View) parent);
		
		initializeSnakePosition(level.columnCount()/2, level.rowCount()/2);
		generateFoodObject();
	}
	
	
	// Public methods
	
	public void keyEvent(KeyCode key)
	{	
		if(key == KeyCode.R)
		{
			oController.clear();
			lController.resetScoreboard();
			snakeAnimator.Stop();
			Parent.RemoveChild(snake);
			snake = new SnakeObject(Parent,1,Worker.PollRate());
			initializeSnakePosition(level.columnCount()/2, level.rowCount()/2);
			generateFoodObject();
		}
		else if(key == KeyCode.P)
		{
			snakeAnimator.Stop();
			Text txt = new Text("Pause");
			txt.setFont(new Font(64));
			double x = level.LeftBound() + level.gridWidth()/2 - txt.getLayoutBounds().getWidth()/2; 
			oController.showText("Pause", x, level.LowerBound()/2 + 32, 64, SettingsClass.textColor, fillMode.cleanText, level.Width());
			
		}
		else if(key == KeyCode.ENTER && !snakeAnimator.isWorking())
		{
			if(snake.isDead())
				return;
			oController.clear();
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
				
				// Checks if new direction isn't opposite of the current and emsures Snake is changing direction according to the grid.
				
				checkAndCorrelateDirection(nPos, dx, dy);
				
				
				// Checks if the new position is part of the border and therefor correlates the Snakes head position.
				
				CheckAndCorrelateBoundaries(nPos, snake);
				
				// Check for collusion between the Snake and itself, or the Snake and the food object.
				
				checkForCollision(nPos);
				snake.moveToCoordinates(nPos);
			}
		});
	}
	
	/*
	 * Draw all objects
	 */
	
	public void drawObjects()
	{
		snake.draw();
		for (View obj : semiInteractiveObjects)
			obj.draw();
		oController.drawObjects();
	}
	
	// Initialize the Snake object
	
	public void initializeSnakePosition(double x, double y)
	{
		snake = new SnakeObject(Parent,1, Worker.PollRate());
		snake.setWidth(level.BlockSize());
		snake.setPosition(level.translate(x, y));
		snake.setObjectName("Snake");
	}
	
	public void addSemiInteractiveObject(ViewObject obj)
	{
		semiInteractiveObjects.add(obj);
	}
	
	// Private methods
	
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
	
	/*
	 * Check section
	 *  - Checks and correlates position of Snake when this hit the boundaries.
	 *  - Check for collision with either itself or food.
	 */
	
	private void checkAndCorrelateDirection(PointD nPos, double dx, double dy)
	{
		/*
		 * First condition checks if the next position request is opposite of the current. If yes, returns false.
		 * Second condition ensures that the shift in direction is safe which means that the Snake is positionen accordingly to the grid..
		 * The body correlates the position with regard to minor differencies as a consequence of rounding issues.
		 */
		
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
	
	private void checkForCollision(PointD nPos)
	{
		/*
		 * Check for collison
		 *  - Checks if the new position is part of the snakes body
		 *  - Checks if Snake collides with Food.
		 */
		
		if(snake.containsCoordinate(nPos,false))
		{
			snake.Kill();
			snakeAnimator.Stop();
			double x = level.LeftBound(),
					y = level.UpperBound() + level.Height()/2 + 128;
			oController.showText("Game Over", x, y, 
					512, 
					Color.RED, 
					fillMode.cleanText, 
					level.Width() - 2*SettingsClass.LevelBorderWidth);
			return;					
		}
		
		FoodObject food = (FoodObject) SemiInteractiveObject("Food");
		PointD newPos = level.relative(nPos);
		PointD foodPos = level.relative(food.Position());
		if(newPos.Equals(foodPos))
		{
			snake.eat(food);
			lController.addPoints(food.getPoint());
			generateFoodObject();
			oController.showText("Point",food.X() , food.Y(), 32, Color.WHITE,fillMode.cleanText, level.Width(), 1000);
		}
	}
	
	/*
	 * Genereate food object
	 */
	
	private void generateFoodObject()
	{
		removeSemiInteractiveObject("Food");
		
		Random generator = new Random();
		double x = generator.nextInt(level.columnCount()),
				y = generator.nextInt(level.rowCount());
		
		while(snake.containsCoordinate(level.translate(new PointD(x, y)),true))
		{
			x = generator.nextInt(level.columnCount());
			y = generator.nextInt(level.rowCount());
		}
		
		FoodObject obj = new FoodObject(Parent,new PointD(level.translateX(x),level.translateY(y)),level.BlockSize());
		obj.setObjectName("Food");
		obj.setColor(Color.RED);
		addSemiInteractiveObject(obj);
	}
	
	/*
	 * Retrieve or remove non-moveable objects.
	 */
	
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
	OverLayController oController;
}
