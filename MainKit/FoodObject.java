package MainKit;

import BaseKit.View;
import BaseKit.PointD;
import javafx.scene.canvas.GraphicsContext;

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
		GraphicsContext gC = Parent().getPainter();
		gC.setFill(bodyColor);
		
		gC.fillRect(Pos.X(), Pos.Y(), BlockSize(), BlockSize());
	}
	
	private int growAmount = 1;
	private PointD Pos;
	private int point;
}
