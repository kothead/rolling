package com.codepenguins.rolling.model;

public abstract class GameObject {
	// first dimension - textures
	// 0 - id of texture
	// 1 - count of frames in texture
	private static int[][] frames;
	
	private float x;
	private float y;
	private float width;
	private float height;
	private int textureId;
	private int frameCount;
	private int frameId;
	
	public static void setFrames(int[][] ids) {
		frames = ids;
	}
	
	public static int getTexturesCount() {
		return frames.length;
	}
	
	public abstract void process(long tick);
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
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
