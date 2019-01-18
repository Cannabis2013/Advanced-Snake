package BaseKit;

import java.awt.Dimension;
import java.awt.Point;

import javafx.stage.Stage;

public class myStage extends Stage {

	public void setSize(int h, int w)
	{
		setWidth((double) w);
		setHeight((double) h);
	}
	public Dimension size()
	{
		int width = (int) getWidth(),
				height = (int) getHeight();

		return new Dimension(width, height);
	}

	public double width()
	{
		return (int) getWidth();
	}

	public double height()
	{
		return (int) getHeight();
	}


	public void setPosition(double x,double y)
	{
		setX(x);
		setY(y);
	}
	public void setPosition(Point pos)
	{
		setX(pos.getX());
		setY(pos.getY());
	}
	public PointD position()
	{
		double X =  getX();
		double Y =  getY();

		return new PointD(X,Y);
	}

	public double X()
	{
		return getX();
	}

	public double Y()
	{
		return getY();
	}

}
