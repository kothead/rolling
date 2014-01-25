package com.codepenguins.rolling.model;

public abstract class GameObject {
	// first dimension - textures
	// 0 - id of texture
	// 1 - count of frames in texture
	private static int[][] frames;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int textureId;
	private int frameCount;
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

	/**
	 * sets current texture
	 * @param id in frames array of class
	 */
	public void setCurrentTexture(int id) {
		textureId = frames[id][0];
		frameCount = frames[id][1];
		frameId = 0;
	}
	
	public int getTextureId() {
		return textureId;
	}
	
	public int getFrameCount() {
		return frameCount;
	}
	
	public int getFrameId() {
		return frameId;
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
