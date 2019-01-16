package MainKit;

import BaseKit.View;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public abstract class ViewObject extends View {
	
	public ViewObject() {
		player = null;
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
	
	protected void playSound(Media sound)
	{
		player = new MediaPlayer(sound);
		player.play();
	}

	public abstract double BlockSize();
	
	protected Color bodyColor;
	private double width, height;
	private MediaPlayer player;
}
