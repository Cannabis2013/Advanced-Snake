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
		currentDirection = direction.left;
		nextDirection = currentDirection;
		lenght = 2;
		speed = 19;
		PollRate = pollRate;
		setWidth(1);
		grow = 0;
		
		String path = "SoundFx\\death.wav";
		dieSound = new Media(new File(path).toURI().toString());
		path = "SoundFx\\attack.wav";

		eatSound = new Media(new File(path).toURI().toString());
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void Kill()
	{
		dead = true;
		playSound(dieSound);
	}
	
	public void eat()
	{
		playSound(eatSound);
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
		PointD tempPos = pos.copy();
		for (int i = bodyCoordinates.size() - 1;i >= 0;i--) {
			PointD pointD = bodyCoordinates.get(i);
			if(tempPos.Equals(pointD))
				return true;
		}
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
	
	private int lenght;
	public enum direction{up, down, left, right};
	private direction currentDirection, nextDirection;
	private List<PointD> bodyCoordinates;
	private double speed, PollRate, grow;
	private boolean dead = false;
	private Media eatSound, dieSound;
}
