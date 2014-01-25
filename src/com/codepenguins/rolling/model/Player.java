package com.codepenguins.rolling.model;

import com.codepenguins.rolling.io.UserEvents;

public class Player extends GameObject {

	private static final int MAX_SPEED = 20;
	private static final int MAX_ANGLE_SPEED = 10;
	private static final int INC_CONTROL = 5;
	private static final int DEFAULT_VA = 1;
	
	private int angle;
	private float vx;
	private float vy;
	private float va;
	
	@Override
	public void process(long tick) {
		
	}
	
	public int getAngle() {
		return angle;
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	private void processControl() {
		boolean[] keyPressed = UserEvents.getKeyPressed();
		if (keyPressed[0]) {
			vy += INC_CONTROL;
		}
		if (keyPressed[1]) {
			vy -= INC_CONTROL;
		}
		if (keyPressed[2]) {
			vx += INC_CONTROL;
		}
		if (keyPressed[3]) {
			vy -= INC_CONTROL;
		}
		if (vx > MAX_SPEED) {
			vx = MAX_SPEED;
		}
		if (vx < - MAX_SPEED) {
			vx = - MAX_SPEED;
		}
		if (vy > MAX_SPEED) {
			vy = MAX_SPEED;
		}
		if (vy < - MAX_SPEED) {
			vy = - MAX_SPEED;
		}
	}
}
