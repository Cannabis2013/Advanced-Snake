package Workers;

import BaseKit.View;

public class PaintWorker extends Worker {

	public PaintWorker(View parent) {
		super(parent);
	}
	
	@Override
	public void run() {
		synchronized (Parent) {
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
		
	}
}
