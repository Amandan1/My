package thread;

public class TestThread extends Thread {

	public TestThread(String name){
		super(name);
	}
	public void run(){
		for(int i=0;i<5;i++){
			for(long k=0;k<1000;k++){
				System.out.println(this.getName()+":"+Thread.currentThread().getName()+"----"+i);
			}
		}
	}
	
	public static void main(String[] args) {
		Thread t1=new TestThread("智取");
		Thread t2=new TestThread("小翔");
		t1.start();
		t2.start();
		
	}
}
