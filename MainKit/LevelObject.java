package MainKit;

import BaseKit.View;
import BaseKit.PointD;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LevelObject extends View {
	
	public LevelObject(MainView parent) {
		super(parent);
		rows = 0;
		columns = 0;
		borderWidth = 20;
		xPos = 0;
		yPos = 0;
		verticalTopMargin = 0;
		verticalBottomMargin = 0;
	}
	
	public void setHorizontalCenter()
	{
		
		xPos = (int) Parent().Width()*0.5 - 0.5*columns*BlockSize() - borderWidth;
	}
	public void setVerticalCenter()
	{
		yPos = Parent().Height()*0.5 - 0.5*rows*BlockSize() - borderWidth;
	}
	
	/*
	 * Border section
	 * 		- Get border width
	 * 		- Set border width
	 */
	
	public double BorderWidth()
	{
		return borderWidth;
	}
	
	public void setBorderWidth(double w)
	{
		borderWidth = w;
	}
	
	public double BlockSize()
	{
		return gridHeight()/rows;
	}
	
	// Translate grid coordinates to global coordinates
	
	public double translateX(double x)
	{
		return  xPos + x*BlockSize() + borderWidth;
	}
	
	public double translateY(double y)
	{
		return  yPos + verticalTopMargin + y*BlockSize() + borderWidth;
	}
	
	public PointD translate(double x, double y)
	{
		double tx = translateX(x),
				ty = translateY(y);
		
		return new PointD(tx, ty);
	}
	
	public PointD translate(PointD coords)
	{
		double x = coords.X(), y = coords.Y(),
				 tx = translateX(x),
				ty = translateY(y);
		
		return new PointD(tx, ty);
	}
	
	/*
	 * Widget dimensions
	 */
	@Override
	public double Height()
	{
		return 2*BorderWidth() + gridHeight() + verticalBottomMargin + verticalTopMargin;
	}
	
	@Override
	public double Width() {
		return gridWidth() + 2*borderWidth;
	}
	
	/*
	 * Boundaries section
	 * Get left/right/upper/lower boundaries
	 */
	
	public double LeftBound()
	{
		return translateX(0);
	}
	
	public double RightBound()
	{
		return translateX(0) + gridWidth() - BlockSize();
	}
	
	public double UpperBound()
	{
		return  translateY(0);
	}
	
	public double LowerBound()
	{
		return  translateY(0) + gridHeight();
	}
	
	/*
	 * Position section
	 * 		- Get position
	 * 		- Set position
	 */
	
	public double x()
	{
		return xPos;
	}
	
	public double y()
	{
		return yPos;
	}
	
	public void setX(double x)
	{
		xPos = x;
	}
	
	public void setY(double y)
	{
		yPos = y;
	}
	
	/*
	 * Offset object
	 */
	
	public void MoveObjectHorizontally(double dx)
	{
		xPos += dx;
	}
	
	public void moveObjectVertically(double dy)
	{
		yPos += dy;
	}
	
	public double lastColumn()
	{
		return translateY(0) + BlockSize()*(rowCount()-1);
	}
	
	public double lastRow()
	{
		return translateX(0) + (columns-1)*BlockSize();
	}
	
	/*
	 * Grid section
	 * 		-Get grid dimensions
	 * 		-Set grid dimensions
	 */
	
	public int rowCount()
	{
		return rows;
	}
	
	public int columnCount()
	{
		return columns;
	}
	
	public void setRows(int r)
	{
		rows = r;
	}
	
	public void setColumns(int c)
	{
		columns = c;
	}
	
	/*
	 * Grid dimensions
	 */
	
	public double gridWidth()
	{
		return columns*BlockSize();
	}
	
	private double gridHeight()
	{
		return Parent().Height() - 2*borderWidth - verticalTopMargin - verticalBottomMargin;
	}
	
	public void setverticalTopMargin(double vPad)
	{
		verticalTopMargin = vPad;
	}
	
	public double VerticalTopMargin()
	{
		return verticalTopMargin;
	}
	public void setVerticalBottomMargin(double vPad)
	{
		verticalBottomMargin = vPad;
	}
	
	public double VerticalBottomMargin()
	{
		return verticalBottomMargin;
	}
	
	
	/*
	 * Draw section
	 * Re-implements View.draw()
	 */
	
	public void draw()
	{
		GraphicsContext gC = P.getPainter();
		
		// Draw background color
		
		//Draw border
		gC.setFill(Color.BROWN);
		gC.fillRoundRect(translateX(0) - borderWidth, translateY(0) - borderWidth, columns*BlockSize() + borderWidth*2, gridHeight() + borderWidth*2,30,30);
		gC.setFill(Color.DARKGREEN);
		gC.fillRect(translateX(0), translateY(0), columns*BlockSize(), gridHeight());
		
		for (int i = 0; i <= columns; i++)
			gC.strokeLine(translateX(i), translateY(0), translateX(i), lastColumn()+BlockSize());
		for (int i = 0; i <= rows; i++) {
			gC.strokeLine(translateX(0), translateY(i), lastRow() + BlockSize(), translateY(i));
		}
	}
	
	enum DisplayMode {strongGitter, weakGitter, noGitter};
	private double verticalTopMargin,verticalBottomMargin;
	private int rows, columns;
	private double xPos, yPos;
	private double borderWidth;
}
