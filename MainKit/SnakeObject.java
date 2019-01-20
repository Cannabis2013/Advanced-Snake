package MainKit;

import java.util.ArrayList;
import java.util.List;
import BaseKit.PointD;
import BaseKit.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class SnakeObject extends ViewObject {
	public SnakeObject(View parent, int l, double pollRate) {
		super(parent);
		sController = (SoundController) Parent().Child("SoundController");
		bodyCoordinates = new ArrayList<PointD>();
		relativeCoordinates = new ArrayList<PointD>();
		currentDirection = direction.left;
		nextDirection = currentDirection;
		speed = SettingsClass.SnakeSpeed;
		PollRate = pollRate;
		setWidth(1);
		grow = 0;
		bodyColor = SettingsClass.SnakeColor;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void Kill()
	{
		dead = true;
		sController.playSoundEffect(dieSound);
	}
	
	public void eat(FoodObject obj)
	{
		grow += obj.GrowAmount();
		sController.playSoundEffect(eatSound);
		incrementSpeed(0.25);
	}
	
	// Position section
	
	public void setPosition(PointD pos)
	{
		double xPos = pos.X(), yPos = pos.Y(), dx = Speed()*BlockSize()/PollRate;
		for (double i = 2*BlockSize(); i >= 0; i -= dx)
		{
			bodyCoordinates.add(new PointD(xPos, yPos));
			xPos -= dx;
		}
		initializeRelativeList();
	}
	
	public PointD position()
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
	
	public boolean containsCoordinate(PointD pos, boolean fullLenght)
	{
		int I = 1, n = 2;
		if(fullLenght)
		{
			I = 0;
			n = 0;
		}
			
		LevelObject level = (LevelObject) Parent().Child("Level");
		PointD tempPos = level.relative(pos.copy());
		for (int i = I;i < relativeCoordinates.size() - n;i++) {
			PointD point = relativeCoordinates.get(i);
			if(tempPos.Equals(point))
				return true;
		}
		return false;
	}
	
	// Draw section
	
	public void draw()
	{
		painter.setFill(bodyColor);
		for (int i = bodyCoordinates.size() - 1; i >= 0; i--) {
			PointD pos = bodyCoordinates.get(i);
			painter.fillRoundRect(pos.X(), pos.Y(),Width() ,Width(), 45,45);
		}
		paintEyes();
	}
	
	private void paintEyes()
	{
		painter.setFill(Color.WHITE);
		if(CurrentDirection() == direction.left)
		{
			painter.fillArc(position().X(), position().Y() + Width()/2, Width()/4,Width()/4, 0, 360, ArcType.ROUND);
			painter.fillArc(position().X(), position().Y() + Width()/4, Width()/4,Width()/4, 0, 360, ArcType.ROUND);
		}
		else if(CurrentDirection() == direction.right)
		{
			painter.fillArc(position().X() + Width() - Width()/4, position().Y() + Width()/2, Width()/4,Width()/4, 0, 360, ArcType.ROUND);
			painter.fillArc(position().X() + Width() - Width()/4, position().Y() + Width()/4, Width()/4,Width()/4, 0, 360, ArcType.ROUND);
		}
		else if(currentDirection == direction.up)
		{
			painter.fillArc(position().X() + Width()/2, position().Y(), Width()/4 ,Width()/4, 0, 360, ArcType.ROUND);
			painter.fillArc(position().X() + Width()/4, position().Y(), Width()/4,Width()/4, 0, 360, ArcType.ROUND);
		}
		else if(currentDirection == direction.down)
		{
			painter.fillArc(position().X() + Width()/2, position().Y() + Width() - Width()/4, Width()/4 ,Width()/4, 0, 360, ArcType.ROUND);
			painter.fillArc(position().X() + Width()/4, position().Y() + Width() - Width()/4, Width()/4,Width()/4, 0, 360, ArcType.ROUND);
		}
	}
	private void initializeRelativeList()
	{
		LevelObject level = (LevelObject) Parent().Child("Level");
		relativeCoordinates.clear();
		PointD startPos = level.relative(position());
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
	private String eatSound = "SoundFx\\attack.wav", dieSound = "SoundFx\\death.wav";
	private SoundController sController;
}
