package MainKit;

import java.util.Timer;
import java.util.TimerTask;

import BaseKit.Object;

public class OverLayController extends Object {

	public OverLayController(Object parent) {
		super(parent);
		
		
	}
	
	public void showText(String txt, long milliseconds)
	{
		Timer tm = new Timer();
		tm.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
			}
		}, milliseconds);
	}
	
	
}
