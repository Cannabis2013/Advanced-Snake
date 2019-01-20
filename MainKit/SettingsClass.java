package MainKit;

import java.util.List;

import com.sun.scenario.Settings;

import BaseKit.PointD;
import MainKit.LevelObject.DisplayMode;
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
	public static Color LevelBorderColor = Color.BROWN;
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
	public static DisplayMode levelDisplayMode = DisplayMode.noGitter;
	
	/*
	 * Snake properties
	 *  - Snake color
	 */
	
	public static Color SnakeColor = Color.GREEN;
	public static double SnakeSpeed = 10;
	
	public static void parseArguments(List<String> args)
	{
		for (int i = 0; i < args.size(); i++) {
			int next = i + 1;
			if(next > (args.size() - 1))
				return;
			String arg = args.get(i);
			String nextArg = args.get(next);
			if(arg.equals("--rows") && PointD.isPureDigit(nextArg))
				SettingsClass.rows = Integer.parseInt(nextArg);
			else if(arg.equals("--columns") && PointD.isPureDigit(nextArg))
				SettingsClass.rows = Integer.parseInt(nextArg);
			else if(arg.equals("--background-color"))
				SettingsClass.backgroundColor = SettingsClass.colorFromString(nextArg);
			else if(arg.equals("--snake-Color"))
				SettingsClass.SnakeColor = SettingsClass.colorFromString(nextArg);
			else if(arg.equals("--level-Color"))
				SettingsClass.LevelBackgroundColor = SettingsClass.colorFromString(nextArg);
			else if(arg.equals("--level-border"))
				SettingsClass.LevelBorderColor = SettingsClass.colorFromString(nextArg);
			else if(arg.equals("--scoreBoard-border"))
				SettingsClass.ScoreBoardBorder = SettingsClass.colorFromString(nextArg);
			else if(arg.equals("--text-color"))
				SettingsClass.textColor = SettingsClass.colorFromString(nextArg);	
			else if(arg.equals("--no-gitter"))
				SettingsClass.levelDisplayMode = DisplayMode.noGitter;
			else if(arg.equals("--strong-gitter"))
				SettingsClass.levelDisplayMode = DisplayMode.showGitter;
		}
	}
	private static Color colorFromString(String str)
	{
		if(str.equals("RED"))
			return Color.RED;
		else if(str.equals("BLUE"))
			return Color.BLUE;
		else if(str.equals("BROWN"))
			return Color.BROWN;
		else if(str.equals("BLACK"))
			return Color.BLACK;
		else if(str.equals("WHITE"))
			return Color.WHITE;
		else if(str.equals("GREEN"))
			return Color.GREEN;
		else
			return Color.BLACK;
	}
}
