package morethread.bank;

public class MainClass {
	public static void main(String[] args) {
		Bank bank=new Bank();
		
		PsersonA pA=new PsersonA(bank); 
		PsersonB pB=new PsersonB(bank); 
		
		pA.start();
		pB.start();
	}
}
