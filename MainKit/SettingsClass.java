package MainKit;

import javafx.scene.paint.Color;

public final class SettingsClass {

	/*
	 * This section relates to the grid dimensions
	 */
	
	public static int rows = 30;
	public static int columns = 40;
	
	/*
	 * Coloring section
	 *  - Level and border colors
	 *  - ScoreBoard Colors
	 */
	
	public static Color backgroundColor = Color.BLACK;
	public static Color borderColor = Color.BROWN;
	public static Color LevelBackgroundColor = Color.BLACK;
	public static Color gridColor = Color.WHITE;
	public static Color textColor = Color.WHITE;
	
	public static Color ScoreBoardBackground = Color.WHITE;
	public static Color ScoreBoardBorder = Color.BROWN;
	public static Color ScoreBoardHeadline = Color.BLACK;
	public static Color ScoreBoardText = Color.BLACK;
	
	/*
	 * LevelObject related
	 * 	- Margin sizes
	 * 	- Border width
	 */
	
	public static double vMarginTop = 50;
	public static double vMarginBottom = 50;
	public static double LevelBorderWidth = 20;
}
