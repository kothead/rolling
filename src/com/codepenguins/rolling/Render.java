package com.codepenguins.rolling;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import com.codepenguins.rolling.model.GameObject;

public class Render {
	public static Textures Tex;
	
	/* =============
	 * Init
	 */
	public Render(int width, int height, String title){
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/* =============
	 * Public:
	 *   void setBackgroundColor(int color);
	 *   int initTexture(String fileName);
	 */
	public void setBackgroundColor(int color) {
		float[] colors = colorIntToFloat(color);
		glClearColor(colors[0], colors[1], colors[2], colors[3]);
	}
	public int initTexture(String fileName) {
		int number = Tex.getNumberOfTexture(fileName);
		if (number == -1) {
			return Tex.loadTexture(fileName);
		}
		return number;
	}
	public void drawObject(GameObject object) {
		// TODO
	}
	public void drawText(String text, int x, int y, int color) {
		// TODO
	}
	public void update() {
		// TODO
	}
	public void setCameraAngle(float angle) {
		// TODO
	}
	public boolean[] getEvents() {
		// TODO
		return new boolean[6];
	}
	
	/* =============
	 * Private:
	 *   float[] colorIntToFloat(int color);
	 */
	private float[] colorIntToFloat(int color) {
		float[] colors = new float[4];
		colors[0] = (color % 256) / 256f;
		colors[1] = (color / 256 % 256) / 256f;
		colors[2] = (color / 65536 % 256) / 256f;
		colors[3] = (color / 16777216) / 256f;
		return colors;
	}
}
