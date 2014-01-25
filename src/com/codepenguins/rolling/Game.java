package com.codepenguins.rolling;

import com.codepenguins.rolling.io.Render;
import com.codepenguins.rolling.model.Cloud;
import com.codepenguins.rolling.model.GameObject;
import com.codepenguins.rolling.model.MenuScene;
import com.codepenguins.rolling.model.Plane;
import com.codepenguins.rolling.model.Scene;
import com.codepenguins.rolling.model.TextObject;

public class Game {
	
	private static final int TARGET_FPS = 60;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final String TITLE = "rolling";
	
	private static Render render;
	private static boolean running;
	private static long prevTime;
	private static Scene scene;
	
	public static void main(String[] args) {
		running = true;
		render = new Render(WIDTH, HEIGHT, TITLE);
		initTextures();
		initMenuScene();
		
		prevTime = System.currentTimeMillis();
		while (running) {
			for (GameObject obj: scene.getObjects()) {
				render.drawObject(obj);
			}
			for (TextObject tObj: scene.getTextObjects()) {
				render.drawText(tObj.getText(), tObj.getX(), tObj.getY(), 
						tObj.getColor(), tObj.getFontId());
			}
			try {
				Thread.sleep(calcSleep());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void initMenuScene() {
		scene = new MenuScene();
	}
	
	public static void initGameScene() {
		// TODO: insert game scene
	}
	
	public static void setGameOver() {
		running = false;
	}
	
	private static void initTextures() {
		Cloud.setFrames(new int[][] {
				{render.initTexture("res/cloud1.png"), 1},
				{render.initTexture("res/cloud2.png"), 1}
		});
		
		Plane.setFrames(new int[][] {
				{render.initTexture("res/Dirizhable.png"), 1},
				{render.initTexture("res/duck.png"), 2},
				{render.initTexture("res/Munhgauzen.png"), 1},
				{render.initTexture("res/tiltrotor.png"), 2}, 
		});
	}
	
	/**
	 * calculates sleep time. Updates prevTime
	 * @return sleep time
	 */
	private static long calcSleep() {
		long millis = 1000 / TARGET_FPS;
		long now = System.currentTimeMillis();
		long dif = now - prevTime;
		prevTime = now;
		long sleep = millis - dif;
		if (sleep < 0) {
			return 0;
		}
		return sleep;
	}
}
