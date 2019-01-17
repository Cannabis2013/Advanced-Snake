package MainKit;

import java.io.File;

import BaseKit.Object;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundController extends Object {
	public SoundController() {
		Media music = new Media(new File("Music\\music.wav").toURI().toString());
		volume = 0.5;
		backgroundPlayer = new MediaPlayer(music);
		backgroundPlayer.setVolume(volume);
		backgroundPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				backgroundPlayer.seek(Duration.ZERO);
			}
		});
	}
	
	public void playMusic()
	{
		backgroundPlayer.play();
	}
	
	public void stopMusic()
	{
		backgroundPlayer.stop();
	}
	
	public static void playSoundEffect(String path)
	{
		Media sound = new Media(new File(path).toURI().toString());
		MediaPlayer player = new MediaPlayer(sound);
		player.setVolume(volume);
		player.play();
	}
	private MediaPlayer backgroundPlayer;
	private static double volume;
}
