package MainKit;

import BaseKit.Object;

public class ObjectAnimator extends Worker {
	
	public ObjectAnimator(Object parent) {
		Parent = (GameController) parent;
		target = null;
	}
	
	public void setTarget(SnakeObject obj)
	{
		target = obj;
	}
	
	@Override
	public void run() {
		isBusy = true;
		while(!stopThread && !stopAllThreads)
		{
			
			double dt = target.BlockSize()/PollRate();
			Parent.moveObject(dt, dt);
			
			try {
				sleep(pollResolution);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isBusy = false;	
	}
	
	public boolean isWorking()
	{
		return isBusy;
	}
	GameController Parent;
	SnakeObject target;
	private boolean isBusy = false;
}
