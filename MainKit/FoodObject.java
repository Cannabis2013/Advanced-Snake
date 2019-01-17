package MainKit;

import BaseKit.View;
import BaseKit.PointD;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FoodObject extends ViewObject {

	public FoodObject(View parent, PointD pos, double Width) {
		super(parent);
		setWidth(Width);
		setHeight(Width);
		Pos = new PointD(pos.X(), pos.Y());
		point = 1;
	}
	
	public PointD Position()
	{
		return Pos;
	}
	
	public int getPoint()
	{
		return point;
	}
	
	public int GrowAmount()
	{
		return growAmount;
	}
	
	public void setGrowAmount(int amount)
	{
		growAmount = amount;
	}
	
	public void draw()
	{
		painter.setFill(bodyColor);
		painter.fillRect(Pos.X(), Pos.Y(), BlockSize(), BlockSize());
		painter.setFill(Color.BLACK);
	}
	
	private int growAmount = 1;
	private PointD Pos;
	private int point;
}
