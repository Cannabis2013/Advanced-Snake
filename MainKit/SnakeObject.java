package MainKit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import BaseKit.PointD;
import BaseKit.View;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;

public class SnakeObject extends ViewObject {
	public SnakeObject(View parent, int l, int pollRate) {
		super(parent);
		bodyCoordinates = new ArrayList<PointD>();
		relativeCoordinates = new ArrayList<PointD>();
		currentDirection = direction.left;
		nextDirection = currentDirection;
		speed = 5;
		PollRate = pollRate;
		setWidth(1);
		grow = 0;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void Kill()
	{
		dead = true;
		SoundController.playSoundEffect(dieSound);
	}
	
	public void eat(FoodObject obj)
	{
		grow += obj.GrowAmount();
		SoundController.playSoundEffect(eatSound);
		incrementSpeed(0.25);
	}
	
	// Position section
	
	public void setPosition(PointD pos)
	{
		double xPos = pos.X(), yPos = pos.Y(), dx = Speed()*BlockSize()/PollRate;
		for (double i = BlockSize(); i >= 0; i -= dx)
		{
			bodyCoordinates.add(new PointD(xPos, yPos));
			xPos -= dx;
		}
		initializeRelativeList();
	}
	
	public PointD Position()
	{
		return bodyCoordinates.get(bodyCoordinates.size() -1);
	}
	
	/*
	 * Set body properties like lenght and width
	 */
	
	/*
	 * Movement and direction related
	 * - Move
	 * - Direction
	 * - Speed
	 */
	
	public void moveToCoordinates(PointD pos)
	{
		bodyCoordinates.add(pos);
		initializeRelativeList();
		if(grow <= 0)
			bodyCoordinates.remove(0);
		else
		{
			grow -= Speed()*BlockSize()/PollRate;
		}
	}
	
	public void setNextDirection(direction dir)
	{
		nextDirection = dir;
	}
	
	public direction NextDirection()
	{
		return nextDirection;
	}
	
	public void setCurrentDirection(direction dir)
	{
		currentDirection = dir;
	}
	
	public direction CurrentDirection()
	{
		return currentDirection;
	}
	
	public void incrementSpeed(double dT)
	{
		speed += dT;
	}
	
	public double Speed()
	{
		return speed;
	}
	
	public boolean containsCoordinate(PointD pos)
	{
		LevelObject level = (LevelObject) Parent().Child("Level");
		PointD tempPos = level.relative(pos.copy());
		for (int i = 1;i < relativeCoordinates.size() - 2;i++) {
			PointD point = relativeCoordinates.get(i);
			if(tempPos.Equals(point))
			{
				return true;
			}
		}
		return false;
	}
	
	// Draw section
	
	public void draw()
	{
		painter.setFill(bodyColor);
		for (int i = bodyCoordinates.size() - 1; i >= 0; i--) {
			PointD pos = bodyCoordinates.get(i);
			painter.fillRoundRect(pos.X(), pos.Y(),Width(),Width(), 45,45);
		}
		painter.setFill(Color.BLACK);
	}
	
	private void initializeRelativeList()
	{
		LevelObject level = (LevelObject) Parent().Child("Level");
		relativeCoordinates.clear();
		PointD startPos = level.relative(Position());
		relativeCoordinates.add(startPos);
		int n = 0;
		for (int i = bodyCoordinates.size() - 1; i >= 0; i--) {
			PointD part = level.relative(bodyCoordinates.get(i));
			if(!relativeCoordinates.get(n).Equals(part))
			{
				relativeCoordinates.add(part);
				n++;
			}
		}
	}
	
	public enum direction{up, down, left, right};
	private direction currentDirection, nextDirection;
	private List<PointD> bodyCoordinates, relativeCoordinates;
	private double speed, PollRate, grow;
	private boolean dead = false;
	private String eatSound = "SoundFx\\attack.wav", dieSound = "SoundFx\\death.wav";;
}
