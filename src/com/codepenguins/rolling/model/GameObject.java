package com.codepenguins.rolling.model;

public abstract class GameObject {
	private static int[][] frames;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int textureId;
	private int frameCount;
	private int animationId;
	private int frameId;
	
	public static void setFrames(int[][] ids) {
		frames = ids;
	}
	
	public abstract void process(long tick);
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTextureId() {
		return textureId;
	}
	
	public int getFrameCount() {
		return frameCount;
	}
	
	public int getAnimationId() {
		return animationId;
	}
	
	public void setAnimationId(int id) {
		animationId = id;
		frameId = 0;
	}
	
	public int getFrameId() {
		return frames[animationId][frameId];
	}
	
	public void setNextFrame() {
		if (frameCount > 0) {
			if (frameId < frameCount) {
				frameId++;
			} else {
				frameId = 0;
			}
		}
	}
}
