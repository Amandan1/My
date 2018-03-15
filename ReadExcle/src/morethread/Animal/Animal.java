package morethread.Animal;

public abstract class Animal extends Thread {

	public double length=20;
	
	public abstract void runing();
	
	
	public void run() {
		super.run();
	synchronized (this) {
		while (length>0) {
			runing();
		}
	}
	}
	
	public static interface Calltoback{
		public void win();
	}
	
	public Calltoback calltoback;
	
}
