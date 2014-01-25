package com.codepenguins.rolling.model;

import com.codepenguins.rolling.io.UserEvents;

public class Player extends GameObject {

	private static final int TEXTURE_ID = 5;
	private static final int MAX_SPEED = 20;
	private static final int MAX_ANGLE_SPEED = 10;
	private static final float INC_CONTROL = 0.5f;
	private static final float DEFAULT_VA = 1;
	private static final float G = 0.1f;
	
	private float angle;
	private float vx;
	private float vy;
	private float va;
	
	public Player() {
		setCurrentTexture(TEXTURE_ID);
	}
	
	@Override
	public void process(long tick) {
		processControl();
		vy += G;
		validateV();
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public float getVX() {
		return vx;
	}
	
	public float getVY() {
		return vy;
	}
	
	private void processControl() {
		boolean[] keyDown = UserEvents.getkeyDowned();
		if (keyDown[0]) {
			vy -= INC_CONTROL;
		}
		if (keyDown[1]) {
			vy += INC_CONTROL;
		}
		if (keyDown[2]) {
			vx -= INC_CONTROL;
		}
		if (keyDown[3]) {
			vx += INC_CONTROL;
		}
	}
	
	private void validateV() {
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
