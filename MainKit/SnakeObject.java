package MainKit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import BaseKit.PointD;
import BaseKit.View;
import javafx.scene.media.Media;

public class SnakeObject extends ViewObject {
	public SnakeObject(View parent, int l, int pollRate) {
		super(parent);
		bodyCoordinates = new ArrayList<PointD>();
		relativeCoordinates = new ArrayList<PointD>();
		currentDirection = direction.left;
		nextDirection = currentDirection;
		lenght = 2;
		speed = 5;
		PollRate = pollRate;
		setWidth(1);
		grow = 0;
		
		dieSound = "SoundFx\\death.wav";
		eatSound = "SoundFx\\attack.wav";
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
	
	public void eat()
	{
		SoundController.playSoundEffect(eatSound);
	}
	
	// Position section
	
	public void setPosition(double xPos, double yPos)
	{
		for (int i = lenght; i >= 0; i--)
			bodyCoordinates.add(new PointD(xPos + i*BlockSize(), yPos));
	}
	
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
	public int Lenght()
	{
		return lenght;
	}
	
	public double BlockSize()
	{
		return Width();
	}
	
	public void setLenght(int l)
	{
		lenght = l;
	}
	
	/*
	 * Movement and direction related
	 * - Move
	 * - Direction
	 * - Speed
	 */
	
	public void moveToCoordinates(PointD pos, double g)
	{
		grow += g;
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
	
	public void setSpeed(double dT)
	{
		speed = dT;
	}
	
	public double Speed()
	{
		return speed;
	}
	
	public boolean containsCoordinate(PointD pos)
	{
		print("Test");
		LevelObject level = (LevelObject) Parent().Child("Level");
		PointD tempPos = level.relative(pos.copy());
		for (int i = 1;i < relativeCoordinates.size() - 2;i++) {
			PointD point = relativeCoordinates.get(i);
			print("New point:" + tempPos.toString() + " Point in Snake: " + point.toString());
			if(tempPos.Equals(point))
			{
				print("test3");
				return true;
			}
		}
		print("test2");
		return false;
	}
	
	// Draw section
	
	public void draw()
	{	
		for (int i = bodyCoordinates.size() - 1; i >= 0; i--) {
			PointD pos = bodyCoordinates.get(i);
			painter.fillRoundRect(pos.X(), pos.Y(),Width(),Width(), 45,45);
		}
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
				n++;
				relativeCoordinates.add(part);
			}
		}
		
		for (PointD pointD : relativeCoordinates) {
			print(pointD.toString());
		}
	}
	
	private int lenght;
	public enum direction{up, down, left, right};
	private direction currentDirection, nextDirection;
	private List<PointD> bodyCoordinates, relativeCoordinates;
	private double speed, PollRate, grow;
	private boolean dead = false;
	private String eatSound, dieSound;
}
