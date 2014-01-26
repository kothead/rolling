package com.codepenguins.rolling.io;

import com.codepenguins.rolling.model.GameObject;
import com.codepenguins.rolling.Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Render {
	private static Textures Tex;
	private ArrayList<TrueTypeFont> fonts;
	private float[] clearColor = new float[4];
	
	
	/* =============
	 * Init
	 */
	
	public Render(int width, int height, String title) {
		createWindow(width, height, title);
		setStartGLSettings(width, height);
		fonts = new ArrayList<TrueTypeFont>();
		Tex = new Textures();
	}
	
	
	/* =============
	 * Public
	 */
	
	public void setBackgroundColor(int color) {
		clearColor = colorIntToFloat(color);
		glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
	}
	
	public int initTexture(String fileName) {
		int number = Tex.getNumberOfTexture(fileName);
		if (number == -1) {
			return Tex.loadTexture(fileName);
		}
		return number;
	}
	
	public void drawBackground(float alpha) {
		glDisable(GL_TEXTURE_2D);
		glColor4f(clearColor[0], clearColor[1], clearColor[2], alpha);
		glBegin(GL_QUADS);
			glVertex3f(0, 0, -1);
			glVertex3f(0, Game.HEIGHT, -1);
			glVertex3f(Game.WIDTH, Game.HEIGHT, -1);
			glVertex3f(Game.WIDTH, 0, -1);
		glEnd();
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_TEXTURE_2D);
	}
	
    public void drawObject(GameObject obj) {
        Tex.getTexture(obj.getTextureId()).bind();
        if (false) {
                glBegin(GL_QUADS);
                glTexCoord2f(((float)obj.getFrameId()) / obj.getFrameCount(), 0);
                glVertex3f(obj.getX(), obj.getY(), -1);
                glTexCoord2f(((float)obj.getFrameId()) / obj.getFrameCount(), 1);
                glVertex3f(obj.getX(), obj.getY()+obj.getHeight(), -1);
                glTexCoord2f((obj.getFrameId() + 1.0f) / obj.getFrameCount(), 1);
                glVertex3f(obj.getX()+obj.getWidth(), obj.getY()+obj.getHeight(), -1);
                glTexCoord2f((obj.getFrameId() + 1.0f) / obj.getFrameCount(), 0);
                glVertex3f(obj.getX()+obj.getWidth(), obj.getY(), -1);
                glEnd();
        } else {
                glBegin(GL_QUADS);
                glTexCoord2f((obj.getFrameId() + 1.0f) / obj.getFrameCount(), 0);
                glVertex3f(obj.getX(), obj.getY(), -1);
                glTexCoord2f((obj.getFrameId() + 1.0f) / obj.getFrameCount(), 1);
                glVertex3f(obj.getX(), obj.getY()+obj.getHeight(), -1);
                glTexCoord2f(((float)obj.getFrameId()) / obj.getFrameCount(), 1);
                glVertex3f(obj.getX()+obj.getWidth(), obj.getY()+obj.getHeight(), -1);
                glTexCoord2f(((float)obj.getFrameId()) / obj.getFrameCount(), 0);
                glVertex3f(obj.getX()+obj.getWidth(), obj.getY(), -1);
                glEnd();
        }
    }
	
	public void drawText(int id, float x, float y, String text, int color) {
		Color.white.bind();
		float[] rgba = colorIntToFloat(color);
		Color clr = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
		fonts.get(id).drawString(x, y, text, clr);
	}
	
	public void update(float alpha) {
		Display.update();
		Display.sync(60);
		startNewFrame(alpha);
	}
	
	public void setCameraAngle(float angle) {
		glPushMatrix();
		float x = Game.WIDTH / 2;
		float y = Game.HEIGHT / 2 - 32;
		glTranslatef(x, y, 0);
		glRotatef(angle, 0.0f, 0.0f, 1.0f);
		glTranslatef(-x, -y, 0);
	}
	
	public int initNewFont(String fontName, int size, int style) {
		fonts.add(new TrueTypeFont(new Font(fontName, style, size), true));
		return fonts.size() - 1;
	}
	
	public boolean isClosing() {
		return Display.isCloseRequested();
	}
	
	public void setNullRotate() {
		glPopMatrix();
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
		
		startNewFrame(1);
	}

	private void startNewFrame(float alpha) {
//		glClear(GL_COLOR_BUFFER_BIT);
		drawBackground(alpha);
	}
	
	private float[] colorIntToFloat(long color) {
		float[] colors = new float[4];
		colors[3] = 1.0f;
		colors[2] = (color % 256) / 256f;
		colors[1] = ((color / 256) % 256) / 256f;
		colors[0] = ((color / 65536) % 256) / 256f; 
		return colors;
	}
}
