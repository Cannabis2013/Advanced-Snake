package MainKit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import BaseKit.PointD;
import BaseKit.View;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;

public class SnakeObject extends ViewObject {
	public SnakeObject(View parent, int l) {
		super(parent);
		bodyCoordinates = new ArrayList<PointD>();
		currentDirection = direction.left;
		nextDirection = currentDirection;
		lenght = l;
		speed = -1;
		setWidth(1);
		
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
		double xPos = pos.X(), yPos = pos.Y();
		for (int i = lenght; i >= 0; i--)
			bodyCoordinates.add(new PointD(xPos + i*BlockSize(), yPos));
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
	
	public void moveToCoordinates(PointD pos, int g)
	{
		grow += g;
		bodyCoordinates.add(pos);
		if(grow <= 0)
			bodyCoordinates.remove(0);
		else
		{
			grow--;
			lenght++;
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
		if (speed == -1)
			return BlockSize();
		else
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
		PointD pos = bodyCoordinates.get(bodyCoordinates.size() -1);
		painter.setFill(Color.BLUE);
		painter.fillRoundRect(pos.X(), pos.Y(), Width(), Width(),45, 45);
		painter.setFill(Color.BLACK);
		if(CurrentDirection() == direction.left)
			painter.fillRect(pos.X() + Width()/2, pos.Y(), Width()/2, Width());
		else if(CurrentDirection() == direction.right)
			painter.fillRect(pos.X(), pos.Y(), Width()/2, Width());
		if(CurrentDirection() == direction.up)
			painter.fillRect(pos.X(), pos.Y() + Width()/2, Width(), Width()/2);
		else if(CurrentDirection() == direction.down)
			painter.fillRect(pos.X(), pos.Y(), Width(), Width()/2);
		
		for (int i = bodyCoordinates.size() - 2; i >= 0; i--) {
			pos = bodyCoordinates.get(i);
			painter.fillRect(pos.X(), pos.Y(), Width(), Width());
		}
	}
	
	private int lenght, grow;
	public enum direction{up, down, left, right};
	private direction currentDirection, nextDirection;
	private List<PointD> bodyCoordinates;
	private double speed;
	private boolean dead = false;
	private Media eatSound, dieSound;
}
