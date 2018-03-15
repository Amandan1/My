package morethread.food;

public class Customer extends Thread {

	KFC kfc;

	public Customer(KFC kfc) {
		this.kfc = kfc;
	}
	@Override
	public void run() {
		int size=(int)(Math.random()*5);
		while(true){
			kfc.consu(size);
		}
	}
}
