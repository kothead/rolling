package com.codepenguins.rolling.model;

public class PlayerKiller extends GameObject {

	private static final int ANIM_SPEED = 100;
	
	private int animCounter;
	
	@Override
	public void process(long tick) {
		animCounter += tick;
		if (animCounter > ANIM_SPEED) {
			setNextFrame();
			animCounter = 0;
		}
		
	}

}
