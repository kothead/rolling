package com.codepenguins.rolling;

import com.codepenguins.rolling.io.Render;
import com.codepenguins.rolling.model.Cloud;
import com.codepenguins.rolling.model.GameObject;
import com.codepenguins.rolling.model.GameScene;
import com.codepenguins.rolling.model.MenuScene;
import com.codepenguins.rolling.model.Plane;
import com.codepenguins.rolling.model.Scene;
import com.codepenguins.rolling.model.TextObject;

public class Game {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final int TARGET_FPS = 20;
	public static final int FONT_SIZE_LARGE = 24;
	public static final int TARGET_TICK = 1000 / TARGET_FPS;
	
	private static final String TITLE = "Rolling";
	
	private static Render render;
	private static boolean running;
	private static long prevTime;
	private static Scene scene;
	
	public static void main(String[] args) {
		running = true;
		render = new Render(WIDTH, HEIGHT, TITLE);
		initTextures();
		initMenuScene();
		render.setBackgroundColor(scene.getBackgroundColor());
		prevTime = System.currentTimeMillis();
		while (running) {
			scene.processScene(prevTime);
			for (GameObject obj: scene.getObjects()) {
				render.drawObject(obj);
			}
			for (TextObject tObj: scene.getTextObjects()) {
				render.drawText(tObj.getFontId(), tObj.getX(), tObj.getY(), tObj.getText(), tObj.getColor());
			}
			
			render.update();
			if (render.isClosing()) {
				running = false;
			}
			try {
				long sleep = calcSleep();
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void initMenuScene() {
		scene = new MenuScene();
	}
	
	public static void initGameScene() {
		scene = new GameScene();
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
				{render.initTexture("res/Dirizhabl.png"), 1},
				{render.initTexture("res/duck.png"), 2},
				{render.initTexture("res/Munhgauzen.png"), 1},
				{render.initTexture("res/tiltrotor.png"), 2}, 
		});
		
		TextObject.setFonts(new int[] {
				render.initNewFont("arial", FONT_SIZE_LARGE, 0),
				render.initNewFont("comic sans ms", 18, 0)
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
