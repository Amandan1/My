package morethread.food;

public class MainClass {
	public static void main(String[] args) {
		KFC kfc=new KFC();
		Customer c1=new Customer(kfc);
		Customer c2=new Customer(kfc);
		Customer c3=new Customer(kfc);
		Customer c4=new Customer(kfc);
		
		Waiter w1=new Waiter(kfc);
		Waiter w2=new Waiter(kfc);
		Waiter w3=new Waiter(kfc);
		
		w1.start();
		w2.start();
		w3.start();
		
		c1.start();
		c2.start();
		c3.start();
		c4.start();
	}
}
