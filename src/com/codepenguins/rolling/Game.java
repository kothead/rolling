package com.codepenguins.rolling;

import java.util.List;
import java.util.ArrayList;

import com.codepenguins.rolling.io.Render;
import com.codepenguins.rolling.model.FinalScene;
import com.codepenguins.rolling.model.GameObject;
import com.codepenguins.rolling.model.GameScene;
import com.codepenguins.rolling.model.MenuScene;
import com.codepenguins.rolling.model.Player;
import com.codepenguins.rolling.model.Scene;
import com.codepenguins.rolling.model.TextObject;
import com.codepenguins.rolling.model.UiObject;

public class Game {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final int TARGET_FPS = 60;
	public static final int FONT_SIZE_LARGE = 24;
	public static final int TARGET_TICK = 1000 / TARGET_FPS;
	public static final int UP_INDEX = 0;
	public static final int DOWN_INDEX = 1;
	public static final int RIGHT_INDEX = 3;
	public static final int LEFT_INDEX = 2;
	public static final int RETURN_INDEX = 4;
	public static final int GAME_TICK = 10;
	public static final int ESCAPE_INDEX = 5;
	
	private static final String TITLE = "Rolling";
	
	private static Render render;
	private static volatile boolean running;
	private static long prevTime;
	private static long tick;
	private static Scene scene;
	
	public static void main(String[] args) {
		running = true;
		render = new Render(WIDTH, HEIGHT, TITLE);
		initTextures();
		initMenuScene();
		prevTime = System.currentTimeMillis();
		
		while (running) {
			scene.processScene(tick);
			
			float camAngle = 0;
			float alpha = 1.0f;
			
			if (scene instanceof GameScene) {
				GameScene gameScene = (GameScene) scene;
				GameObject player = gameScene.getPlayer();
				camAngle = player.getPlayerAngle();
				alpha = 1 - player.getPlayerSpeed() / 50; 
			}
			
			render.setCameraAngle(camAngle);
			
			List<GameObject> noRotate = new ArrayList<GameObject>();
			for (GameObject obj: scene.getObjects()) {
				if (!(obj instanceof Player) && !(obj instanceof UiObject)) {
					render.drawObject(obj);
				} else {
					noRotate.add(obj);
				}
			}
			
			render.setNullRotate();
			//if (player != null) render.drawObject(player);
			for (GameObject obj: noRotate) {
				render.drawObject(obj);
			}
			
			for (TextObject tObj: scene.getTextObjects()) {
				render.drawText(tObj.getFontId(), tObj.getX(), tObj.getY(), tObj.getText(), tObj.getColor());
			}
			
			
			render.update(alpha);
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
	
	public static void initFinalScene(boolean hasWon) {
		scene = new FinalScene(hasWon);
		render.setBackgroundColor(scene.getBackgroundColor());
	}
	
	public static void initMenuScene() {
		scene = new MenuScene();
		render.setBackgroundColor(scene.getBackgroundColor());
	}
	
	public static void initGameScene() {
		scene = new GameScene();
		render.setBackgroundColor(scene.getBackgroundColor());
	}
	
	public static void setGameOver() {
		running = false;
	}
	
	public static boolean isRunning() {
		return running;
	}
	
	private static void initTextures() {
		GameObject.setFrames(new int[][] {
				{render.initTexture("res/cloud1.png"), 1, 197, 57},
				{render.initTexture("res/cloud2.png"), 1, 122, 45},
				{render.initTexture("res/cloud3.png"), 1, 141, 47},
				{render.initTexture("res/Dirizhabl.png"), 1, 200, 95},
				{render.initTexture("res/duck.png"), 2, 64, 64},
				{render.initTexture("res/Munhgauzen.png"), 1, 58, 85},
				{render.initTexture("res/tiltrotor.png"), 2, 128, 57}, 
				{render.initTexture("res/char_big.png"), 6, 170, 172},
				{render.initTexture("res/heart.png"), 1, 30, 30}
		});
		
		TextObject.setFonts(new int[] {
				render.initNewFont("arial", FONT_SIZE_LARGE, 0),
				render.initNewFont("arial", 12, 0),
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
		tick = now - prevTime;
		prevTime = now;
		long sleep = millis - tick;
		if (sleep < 0) {
			return 0;
		}
		return sleep;
	}
}
