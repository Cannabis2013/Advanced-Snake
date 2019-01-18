package Workers;

import BaseKit.View;

public class Worker extends Thread {
	
	protected static boolean stopAllThreads = false;
	protected boolean stopThread = false;
	protected int pollResolution = 10;
	protected View Parent;
	private Thread t;
	
	public Worker(View parent) {
		Parent = parent;
	}
	public Worker()
	{}
	
	public void setPollRate(int rate)
	{
		if(rate > 1000)
			throw new IllegalArgumentException();
		pollResolution = 1000/rate;
	}
	
	public int PollRate()
	{
		return 1000/pollResolution;
	}
	
	@Override
	public synchronized void start() {
		if(t == null)
		{
			t = new Thread(this);
			t.start();
		}
	}
	
	public void Stop()
	{
		stopThread = true;
	}
	
	public static void StopAll()
	{
		stopAllThreads = true;
	}
}
