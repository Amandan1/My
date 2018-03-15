package morethread.tick;

public class MainClass {
	public static void main(String[] args) {
		Station s1=new Station("窗口1");
		Station s2=new Station("窗口2");
		Station s3=new Station("窗口3");
		Station s4=new Station("窗口4");
		Station s5=new Station("窗口5");
		Station s6=new Station("窗口6");
		Station s7=new Station("窗口7");
		
		s1.start();
		s2.start();
		s3.start();
		s4.start();
		s5.start();
		s6.start();
		s7.start();
		
	}

}
