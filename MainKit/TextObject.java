package MainKit;

import BaseKit.View;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextObject extends View {

	public TextObject(View parent,String txt, double x ,double y,int pointSize,Color textColor, fillMode Mode, double maxWidth) {
		super(parent);
		text = txt;
		xPos = x;
		yPos = y;
		txtColor = textColor;
		mWidth = maxWidth;
		pSize = pointSize;
		mode = Mode;
	}
	
	public void setBoxColor(Color color)
	{
		boxColor = color;
	}
	@Override
	public void draw() {
		if(mode == fillMode.boxedText)
		{
			Text txt = new Text(text);
			txt.setFont(new Font(pSize));
			double boxWidth = txt.getLayoutBounds().getWidth(), padding = 20;
			painter.setFill(boxColor);
			painter.fillRect(xPos - padding,yPos - pSize , boxWidth + 2*padding, pSize + padding);
		}
		painter.setFill(txtColor);
		painter.setFont(new Font(pSize));
		painter.fillText(text, xPos, yPos,mWidth);
	}
	public enum fillMode{cleanText, boxedText};
	public fillMode mode;
	private int pSize;
	private double xPos,yPos, mWidth;
	private String text;
	private Color txtColor, boxColor;
}
