package morethread.bank;

public class PsersonA extends Thread {

	Bank bank;

	public PsersonA(Bank bank) {
		this.bank = bank;
	}

	public void run() {

		while (Bank.money >= 100) {
			bank.counter(100);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
