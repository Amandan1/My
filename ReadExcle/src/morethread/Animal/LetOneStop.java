package morethread.Animal;

import morethread.Animal.Animal.Calltoback;

public class LetOneStop implements Calltoback {

	Animal animal;
	
	public LetOneStop(Animal animal) {
		this.animal=animal;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void win() {
		// TODO Auto-generated method stub
		animal.stop();
	}
}
