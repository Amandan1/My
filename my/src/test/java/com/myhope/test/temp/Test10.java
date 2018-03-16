package com.myhope.test.temp;

public class Test10 {
	public static void main(String args[]) {
		Thread t = new Thread() {
			public void run() {
				haha();
			}
		};
		t.run();
		System.out.print("heihei");
	}

	static void haha() {
		System.out.print("haha");
	}

}
