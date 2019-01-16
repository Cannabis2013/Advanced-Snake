package MainKit;

import BaseKit.View;

public class PaintWorker extends Worker {

	public PaintWorker(View parent) {
		Parent = parent;
	}
	
	@Override
	public void run() {
		while(!stopThread && !stopAllThreads)
		{
			Parent.draw();
			try {
				sleep(pollResolution);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean stopThread = false;
	private int pollResolution = 10;
	private View Parent;
}
