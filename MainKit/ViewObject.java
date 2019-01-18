package MainKit;

import BaseKit.View;
import javafx.scene.paint.Color;

public abstract class ViewObject extends View {
	
	public ViewObject() {
	}
	
	public ViewObject(View parent) {
		super(parent);
	}
	
	public void setColor(Color color)
	{
		bodyColor = color;
	}
	
	public double Height()
	{
		return height;
	}
	
	public void setHeight(double h)
	{
		height = h;
	}
	
	public double Width()
	{
		return width;
	}
	
	public void setWidth(double w)
	{
		width = w;
	}
	
	public double BlockSize()
	{
		return Width();
	}

	protected Color bodyColor;
	private double width, height;
}
