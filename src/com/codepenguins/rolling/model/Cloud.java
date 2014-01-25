package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;

public class Cloud extends GameObject {

	private static final int MIN_SPEED = 10;
	private static final int MAX_SPEED = 20;
	private static final int TEXTURE_START = 0;
	private static final int TEXTURE_COUNT = 3;
	
	private float v;
	
	public Cloud(boolean right) {
		int speedDiff = MAX_SPEED - MIN_SPEED;
		v = (int) (Math.random() * speedDiff + MIN_SPEED);
		if (right) {
			v = -v;
		}
		int texture = (int) (Math.random() * TEXTURE_COUNT);
		setCurrentTexture(texture);
	}
	
	@Override
	public void process(long tick) {
		float relative = v * Game.GAME_TICK / Game.TARGET_TICK;
		setX(getX() + relative);
	}

}
