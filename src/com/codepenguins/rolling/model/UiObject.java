package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;

public class UiObject extends GameObject {

	private static final int ANIM_SPEED = 500;

	private int animCounter;
	
	public UiObject(int textureId) {
		setCurrentTexture(textureId);
	}
	
	@Override
	public void setNextFrame() {
		frameId = (int) (Math.random() * frameCount);
	}
	
	@Override
	public void process(long tick) {
		animCounter += Game.TARGET_TICK;
		if (animCounter > ANIM_SPEED) {
			setNextFrame();
			animCounter = 0;
		}
	}

}
