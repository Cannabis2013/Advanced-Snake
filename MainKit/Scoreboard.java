package MainKit;



import java.util.Arrays;
import java.util.List;

import BaseKit.View;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Scoreboard extends View{
	public Scoreboard(MainView parent) {
		super(parent);
		borderColor = Color.BROWN;
		fillColor = Color.GRAY;
		fillTextColor = Color.BLACK;
		keyboardShortcuts = Arrays.asList("CTRL + Q = Quit", 
				"R = Reset", 
				"Left arrow key = Move left", 
				"Right arrow key = Move right", 
				"Up arrow key = Move up", 
				"Down arrow key = Move down");
		
		setWidth(50);
		setHeight(100);
		setX(0);
		setY(0);
		
		Snake = null;
	}
	
	public void setBorderWidth(double w)
	{
		borderWidth = w;
	}
	
	public void setRoundedCorners(double val)
	{
		borderRadius = val;
	}
	
	private double ContentWidth()
	{
		return Width() - 2*borderWidth;
	}
	
	private double ContentHeight()
	{
		return Height() - 2*borderWidth;
	}
	
	private double ContentX()
	{
		return X() + borderWidth;
	}
	
	private double ContentY()
	{
		return Y() + borderWidth;
	}
	
	/*
	 * Draw section
	 */
	@Override
	public void draw() {
		GraphicsContext painter = P.getPainter();
		painter.setFill(borderColor);
		painter.fillRoundRect(X(), Y(), Width(), Height(), borderRadius, borderRadius);
		painter.setFill(fillColor);
		painter.fillRect(ContentX(), ContentY(), ContentWidth(), ContentHeight());
		
		// Draw headline
		painter.setFill(Color.BLACK);
		Font txtHeading = new Font(48);
		painter.setFont(txtHeading);
		
		double x = ContentX() + 7.5,
				y = ContentY() + 48;
		
		painter.fillText("Scoreboard", x, y);
		
		painter.setLineWidth(5);
		
		y += 24;
		// Draw line seperator
		painter.strokeLine(ContentX() + 2.5, y, ContentX() + ContentWidth() - 2.5, y);
		painter.setLineWidth(1);
		
		
		
		Font txtNormal = new Font(24);
		painter.setFont(txtNormal);
		
		Snake = (SnakeObject) P.Child("Snake");
		String txt = String.format("Player scorer: %d ", Snake.Lenght() - 1);
		
		y += 32;
		
		painter.fillText(txt, x, y);
		
		y += 32;
		
		painter.setLineWidth(3);
		painter.strokeLine(ContentX() + 2.5, y, ContentX() + ContentWidth() - 2.5, y);
		painter.setLineWidth(1);
		
		y += 32;
		txtHeading = new Font(28);
		
		painter.setFont(txtHeading);
		painter.fillText("Keyboard shortcuts", x, y);
		
		txtNormal = new Font(16);
		
		painter.setFont(txtNormal);
		y += 32;
		
		for (double i = 0, n = y; i < keyboardShortcuts.size(); i++, n += 32)
			painter.fillText(keyboardShortcuts.get((int) i), x, n);
	}
	
	private double borderWidth = 1, borderRadius = 0;
	private Color borderColor, fillColor, fillTextColor;
	private SnakeObject Snake;
	private List<String> keyboardShortcuts;

}
