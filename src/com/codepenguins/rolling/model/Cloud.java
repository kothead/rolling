package com.codepenguins.rolling.model;

import java.util.Random;

import com.codepenguins.rolling.Game;

public class Cloud extends GameObject {

	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 10;

	private float v;
	
	public Cloud(boolean right) {
		int speedDiff = MAX_SPEED - MIN_SPEED;
		v = (int) (Math.random() * speedDiff + MIN_SPEED);
		if (right) {
			v = -v;
		}
	}
	
	@Override
	public void process(long tick) {
		float relative = v * tick / Game.TARGET_TICK;
		setX(getX() + relative);
	}

}
