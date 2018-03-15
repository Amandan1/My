package morethread.bank;

public class PsersonB extends Thread {

	Bank bank;

	public PsersonB(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void run() {
		while (Bank.money>=200) {
			bank.ATM(200);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
