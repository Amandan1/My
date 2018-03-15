package morethread.bank;

public class Bank {

	static int money=1000;
	public void counter(int money){
		Bank.money-=money;
		System.out.println("A取走了"+money+"元,还剩下"+Bank.money);
	}
	public void ATM(int money){
		Bank.money-=money;
		System.out.println("B取走了"+money+"元,还剩下"+Bank.money);
	}
}
