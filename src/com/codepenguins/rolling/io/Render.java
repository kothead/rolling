package com.codepenguins.rolling.io;

import com.codepenguins.rolling.model.GameObject;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Render {
	private static Textures Tex;
	private static float cameraAngle;
	private TrueTypeFont[] fonts;
	
	
	/* =============
	 * Init
	 */
	
	public Render(int width, int height, String title) {
		initFonts();
		createWindow(width, height, title);
		setStartGLSettings(width, height);
		Tex = new Textures();
		cameraAngle = 0;
	}
	
	
	/* =============
	 * Public
	 */
	
	public void setBackgroundColor(int color) {
		float[] rgba = colorIntToFloat(color);
		glClearColor(rgba[0], rgba[1], rgba[2], rgba[3]);
	}
	
	public int initTexture(String fileName) {
		int number = Tex.getNumberOfTexture(fileName);
		if (number == -1) {
			return Tex.loadTexture(fileName);
		}
		return number;
	}
	
	public void drawObject(GameObject obj) {
//		Tex.getTexture(obj.textureID).bind();
		glBegin(GL_QUADS);
//			glTexCoord2f(0, ((float)obj.frame)/obj.frameCount);
//			glVertex3f(obj.x-obj.width/2, obj.y-obj.height/2, -1);
//			glTexCoord2f(1, ((float)obj.frame)/obj.frameCount);
//			glVertex3f(obj.x+obj.width/2, obj.y-obj.height/2, -1);
//			glTexCoord2f(1, (obj.frame + 1.0f)/obj.frameCount);
//			glVertex3f(obj.x+obj.width/2, obj.y+obj.height/2, -1);
//			glTexCoord2f(0, (obj.frame + 1.0f)/obj.frameCount);
//			glVertex3f(obj.x-obj.width/2, obj.y+obj.height/2, -1);
		glEnd();
	}
	
	public void drawText(String text, int x, int y, int color, int number) {
		Color.white.bind();
		float[] rgba = colorIntToFloat(color);
		Color clr = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
		fonts[number].drawString(x, y, text, clr);
	}
	
	public void update() {
		glPopMatrix();
		Display.update();
		Display.sync(60);
		startNewFrame();
	}
	
	public void setCameraAngle(float angle) {
		cameraAngle = angle;
	}
	
	
	/* =============
	 * Private
	 */
	
	private void createWindow(int width, int height, String title) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	private void setStartGLSettings(int width, int height) {
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glShadeModel(GL_SMOOTH);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);  
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glViewport(0, 0, width, height);
		glMatrixMode(GL_MODELVIEW);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		startNewFrame();
	}

	private void startNewFrame() {
		glClear(GL_COLOR_BUFFER_BIT);
		glPushMatrix();
		glRotatef(cameraAngle, 0.0f, 0.0f, 1.0f);
	}
	
	private float[] colorIntToFloat(int color) {
		float[] colors = new float[4];
		colors[0] = (color % 256) / 256f;
		colors[1] = (color / 256 % 256) / 256f;
		colors[2] = (color / 65536 % 256) / 256f;
		colors[3] = (color / 16777216) / 256f;
		return colors;
	}
	
	private void initFonts() {
		fonts = new TrueTypeFont[2];
		fonts[0] = new TrueTypeFont(new Font("Arial", Font.BOLD, 24), true);
		fonts[1] = new TrueTypeFont(new Font("Comic Sans MS", Font.BOLD, 14), true);
	}
	
}
