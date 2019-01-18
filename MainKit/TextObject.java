package MainKit;

import BaseKit.View;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextObject extends View {

	public TextObject(View parent,String txt, double x ,double y,int pointSize,Color textColor, double maxWidth) {
		super(parent);
		text = txt;
		xPos = x;
		yPos = y;
		txtColor = textColor;
		mWidth = maxWidth;
		pSize = pointSize;
	}
	@Override
	public void draw() {
		painter.setFill(txtColor);
		painter.setFont(new Font(pSize));
		painter.fillText(text, xPos, yPos,mWidth);
	}
	private int pSize;
	private double xPos,yPos, mWidth;
	private String text;
	private Color txtColor;
}
