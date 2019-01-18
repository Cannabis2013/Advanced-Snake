package MainKit;

import BaseKit.View;
import BaseKit.PointD;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class FoodObject extends ViewObject {

	public FoodObject(View parent, PointD pos, double Width) {
		super(parent);
		setWidth(Width);
		setHeight(Width);
		Pos = new PointD(pos.X(), pos.Y());
		point = 1;
		growAmount = BlockSize();
	}
	
	public PointD Position()
	{
		return Pos;
	}
	
	public int getPoint()
	{
		return point;
	}
	
	/*
	 * Properties
	 *  - Grow amount
	 *  - Speed incremental amount
	 */
	
	public double GrowAmount()
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
		painter.fillArc(Pos.X(),Pos.Y(), BlockSize(), BlockSize(), 0, 360, ArcType.ROUND);
		painter.setFill(Color.BLACK);
	}
	
	private double growAmount;
	private PointD Pos;
	private int point;
}
