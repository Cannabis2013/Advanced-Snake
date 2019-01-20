package MainKit;

import BaseKit.Object;
import BaseKit.View;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundController extends Object {
	public SoundController(View parent) {
		super(parent);
		setObjectName("SoundController");
		backgroundPlayer = null;
	}
	
	
	public void playRepeatet(String path)
	{
		Media music = new Media(path);
		volume = 0.5;
		backgroundPlayer = new MediaPlayer(music);
		backgroundPlayer.setVolume(volume);
		backgroundPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				backgroundPlayer.seek(Duration.ZERO);
			}
		});
		backgroundPlayer.play();
	}
	
	public void stopRepeatet()
	{
		backgroundPlayer.stop();
	}
	
	public void playSoundEffect(String path)
	{
		Media sound = new Media(path);
		fxPlayer = new MediaPlayer(sound);
		fxPlayer.setVolume(volume/2);
		fxPlayer.play();
	}
	private MediaPlayer backgroundPlayer,fxPlayer;
	private static double volume;
}
