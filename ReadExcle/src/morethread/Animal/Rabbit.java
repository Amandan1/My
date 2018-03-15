package morethread.Animal;

public class Rabbit extends Animal {

	@Override
	public void runing() {
		double dis=0.5;
		
		length-=dis;//跑完后距离减少
		
		if(length<=0){
			System.out.println("兔子胜利了");
			if(calltoback!=null){
				calltoback.win();
			}
		}
		System.out.println("兔子跑了"+dis+"米,距离终点还有"+(int)length+"米");
		if(length%2==0){
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public Rabbit() {
		setName("兔子");
	}
	
	
}
