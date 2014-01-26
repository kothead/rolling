package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;

public abstract class Player extends GameObject {

	public static final int MAX_SPEED = 20;
	public static final float SPRITE_THRESHOLD = 0.3f;
	
	private static final int[] spritesFlow = {0, 1, 2, 3, 2, 1, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1};
	private static final int TEXTURE_ID = 7;
	protected static final int MIN_SPEED = 0;
	private static final int MAX_ANGLE_SPEED = 10;
	protected static final float INC_CONTROL = 0.5f;
	private static final float G = 0.1f;
	private static final float ROTATE_ACCELERATION = 0.95f;
	
	private static final int ANIM_SPEED = 100;
	private static final int RECOVERY_SPEED = 500;
	
	protected float vx;
	protected float vy;
	private float va;
	private int animCounter;
	private int recoveryCounter;
	private int spriteCounter;
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
		
		setX(getX() + vx);
		setY(getY() + vy);
	}
	
	@Override
	public void setNextFrame() {
		spriteCounter++;
		if (spriteCounter >= spritesFlow.length) { 
			spriteCounter = 0;
		}
		frameId = spritesFlow[spriteCounter];
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
	
	protected abstract void processControl();
	
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
