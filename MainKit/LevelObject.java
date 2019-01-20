package MainKit;

import BaseKit.View;
import BaseKit.PointD;
import javafx.scene.text.Font;

public class LevelObject extends View {
	
	public LevelObject(MainView parent) {
		super(parent);
		rows = SettingsClass.rows;
		columns = SettingsClass.columns;
		borderWidth = 20;
		xPos = 0;
		yPos = 0;
		verticalTopMargin = SettingsClass.vMarginTop;
		verticalBottomMargin = SettingsClass.vMarginBottom;
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
	
	public double BlockSize()
	{
		return gridHeight()/rows;
	}
	
	// Translate grid coordinates to global coordinates
	
	public double translateX(double x)
	{
		return  xPos + x*BlockSize() + SettingsClass.LevelBorderWidth;
	}
	
	public double translateY(double y)
	{
		return  yPos + SettingsClass.vMarginTop+ y*BlockSize() + SettingsClass.LevelBorderWidth;
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
	
	public int relativeX(double x)
	{
		return (int) PointD.round((x-(xPos+SettingsClass.LevelBorderWidth))/BlockSize(), 0);
	}
	
	public int relativeY(double y)
	{
		return (int) PointD.round((y-(yPos+SettingsClass.LevelBorderWidth + verticalTopMargin))/BlockSize(), 0);
	}
	
	public PointD relative(PointD pos)
	{
		double x = pos.X(), y = pos.Y(),
				 cx = relativeX(x),
				ry = relativeY(y);
		
		return new PointD(cx, ry);
	}
	
	public LevelObject() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Widget dimensions
	 */
	@Override
	public double Height()
	{
		return 2*SettingsClass.LevelBorderWidth + gridHeight() + SettingsClass.vMarginBottom + SettingsClass.vMarginTop;
	}
	
	@Override
	public double Width() {
		return gridWidth() + 2*SettingsClass.LevelBorderWidth;
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
	
	public double X()
	{
		return xPos;
	}
	
	public double Y()
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
		return Parent().Height() - 2*SettingsClass.LevelBorderWidth - SettingsClass.vMarginTop - SettingsClass.vMarginBottom;
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
		paint(mode, infoText);
	}
	
	private void paint(DisplayMode m, String text)
	{
		//Draw border
		painter.setFill(SettingsClass.borderColor);
		painter.fillRoundRect(translateX(0) - SettingsClass.LevelBorderWidth,
				translateY(0) - SettingsClass.LevelBorderWidth, 
				columns*BlockSize() + SettingsClass.LevelBorderWidth*2, 
				gridHeight() + SettingsClass.LevelBorderWidth*2,30,30);
		
		painter.setFill(SettingsClass.LevelBackgroundColor);
		painter.fillRect(translateX(0), translateY(0), columns*BlockSize(), gridHeight());
		
		if(m == DisplayMode.showGitter)
		{
			painter.setLineWidth(1);
			for (int i = 0; i <= columns; i++)
				painter.strokeLine(translateX(i), translateY(0), translateX(i), lastColumn()+BlockSize());
			for (int i = 0; i <= rows; i++) {
				painter.strokeLine(translateX(0), translateY(i), lastRow() + BlockSize(), translateY(i));
			}			
		}
		if(text != null)
		{
			double pointSize = 128;
			painter.setFont(new Font(pointSize));
			painter.setFill(SettingsClass.textColor);
			painter.fillText(text, translateX(0), Parent().Height()/2, Parent().Width());			
		}
	}
	
	enum DisplayMode {showGitter, noGitter};
	private DisplayMode mode = DisplayMode.strongGitter;
	private double verticalTopMargin,verticalBottomMargin;
	private int rows, columns;
	private double xPos, yPos;
	private double borderWidth;
	private String infoText = null;
}
