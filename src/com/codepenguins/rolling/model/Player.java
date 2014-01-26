package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;

public class Player extends GameObject {

	public static final int MAX_SPEED = 20;
	public static final float SPRITE_THRESHOLD = 0.3f;
	
	private static final int TEXTURE_ID = 7;
	private static final int MIN_SPEED = 0;
	private static final int MAX_ANGLE_SPEED = 10;
	private static final float INC_CONTROL = 0.5f;
	private static final float G = 0.1f;
	private static final float ROTATE_ACCELERATION = 1.0f;
	
	private static final int ANIM_SPEED = 100;
	private static final int RECOVERY_SPEED = 500;
	
	private float vx;
	private float vy;
	private float va;
	private int animCounter;
	private int recoveryCounter;
	private boolean hit;
	
	public Player() {
		setCurrentTexture(TEXTURE_ID);
	}
	
	@Override
	public void process(long tick) {
		processControl();
		vy += G;
		va *= ROTATE_ACCELERATION;
		validateV();
		
		setPlayerAngle(getPlayerAngle() + va);
		setPlayerSpeed(Math.abs(vx)+Math.abs(vy));
		
		animCounter += Game.TARGET_TICK;
		if (animCounter > ANIM_SPEED) {
			setNextFrame();
			animCounter = 0;
		}
		
		if (hit) {
			if (recoveryCounter > RECOVERY_SPEED) {
				hit = false;
				recoveryCounter = 0;
			} else {
				recoveryCounter += Game.TARGET_TICK;
			}
		}
	}
	
	public float getVX() {
		return vx;
	}
	
	public float getVY() {
		return vy;
	}
	
	public float getVA() {
		return va;
	}
	
	public void setVX(float vx) {
		this.vx = vx;
	}
	
	public void setVY(float vy) {
		this.vy = vy;
	}
	
	public void setVA(float va) {
		this.va = va;
	}

	public boolean getHit() {
		return hit;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	private void processControl() {
		boolean[] keyDown = UserEvents.getkeyDowned();
		if (keyDown[0]) {
			if (vy - INC_CONTROL >= MIN_SPEED) {
				vy -= INC_CONTROL;
			}
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
		if (va > MAX_SPEED) {
			va = MAX_ANGLE_SPEED;
		}
		if (va < - MAX_SPEED) {
			va = - MAX_ANGLE_SPEED;
		}
	}
}
