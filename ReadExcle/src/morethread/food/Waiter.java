package morethread.food;

import java.util.Random;

public class Waiter extends Thread {

	KFC kfc;
	public Waiter(KFC kfc) {
		this.kfc=kfc;
	}
	
	@Override
	public void run() {
		int size=(int)(Math.random()*5)+5;
		while(true){
		}		
	}
}
