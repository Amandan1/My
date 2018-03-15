package thread;

public class TestRunnable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DoSomething d1=new DoSomething("小翔");
		DoSomething d2=new DoSomething("羊羽");
		
		Thread t1=new Thread(d1);
		Thread t2=new Thread(d2);
		t1.start();
		t2.start();
	}

}
