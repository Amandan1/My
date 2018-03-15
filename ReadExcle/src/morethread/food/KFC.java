package morethread.food;

import java.awt.List;
import java.util.ArrayList;

public class KFC {

	String names[] = { "薯条", "板烧", "鸡翅", "汉堡" };
	static final int Max = 20;

	ArrayList<Food> foods = new ArrayList<Food>();

	public void prod(int index) {
		synchronized (this) {
			while (foods.size() > Max) {
				System.out.println("石材不够啊");
				this.notifyAll();

				try {
					String name = Thread.currentThread().getName();
					this.wait();
					System.out.println("生产者" + name);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			System.out.println("开始生产食物");
			for (int i = 0; i < index; i++) {
				Food food=new Food(names[(int)(Math.random()*4)]);
				foods.add(food);
				System.out.println("生产了"+food.getName()+foods.size());
			}
		}

	}
	public void consu(int index){
		synchronized (this) {
			while(foods.size()<index){
				System.out.println("食物不够了");
			this.notifyAll();
			try {
				String name=Thread.currentThread().getName();
				this.wait();
				System.out.println("消费者"+name);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			}
			System.out.println("开始消费");
			for(int i=0;i<index;i++){
				Food food=foods.remove(foods.size()-1);
				System.out.println("消费了一个"+food.getName()+foods.size());
			}
		}
	}
	
}
