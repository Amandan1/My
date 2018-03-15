package thread;

public class DoSomething implements Runnable {

	private String username;
	
	public DoSomething(String username) {
		this.username=username;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++){
			for(long k=0;k<1000;k++){
				System.out.println(username+": "+i);
			}
		}
	}

}
