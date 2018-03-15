package morethread.tick;

public class Station  extends Thread{

	public Station(String name) {
		super(name);
	}
	//票数 20个
	static int tick=20;
	 
	//静态钥匙
	static Object o="aa";
	
	@Override
	public void run() {
		while (tick>0) {
			synchronized (o) {
				if(tick>0){
					System.out.println(getName()+"卖出去第"+tick+"张票");
					tick--;
				}else{
					System.out.println("票卖完了");
				}
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
