package com.codepenguins.rolling.model;

import java.util.Random;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;

public class GameScene extends Scene {

	private static final float CLOUD_PROBABILITY = Game.TARGET_FPS;
	private static final float PLANE_PROBABILITY = 0.5f;
	private static final float SCENE_MULTIPLIER = 2;
	
	
	private Player player;
	private int sceneLeft;
	private int sceneTop;
	private int sceneRight;
	private int sceneBottom;
	
	public GameScene() {
		float multiplier = SCENE_MULTIPLIER / 2;
		sceneLeft = (int) (0 - Game.WIDTH / multiplier);
		sceneRight = (int) (Game.WIDTH + Game.WIDTH / multiplier);
		sceneTop = (int) (0 - Game.HEIGHT / multiplier);
		sceneBottom = (int) (Game.HEIGHT + Game.HEIGHT / multiplier);
		
		player = new Player();
		appendGameObject(player);
	}
	
	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		
		double cloudProb = Math.random();
		if (cloudProb < CLOUD_PROBABILITY / Game.TARGET_FPS) {
			generateCloud();
		}
		
		double planeProb = Math.random();
		if (cloudProb < PLANE_PROBABILITY / Game.TARGET_FPS) {
			generatePlane();
		}
		boolean[] keyPressed = UserEvents.getKeyPressed();
		if (keyPressed[Game.ESCAPE_INDEX]) {
			Game.initMenuScene();
		}
	}
	
	public void generateCloud() {
		boolean right = Math.random() > 0.5;
		Cloud cloud = new Cloud(right);
		cloud.setX(right ? sceneRight - 200: sceneLeft + 200);
		cloud.setY(getRandomY());
		System.out.println(cloud.getX() + " " + cloud.getY());
		appendGameObject(cloud);
	}
	
	public void generatePlane() {
		
	}
	
	public void collectOutObjects() {
		
	}
	
	public int getRandomX() {
		return (int) (Math.random() * Game.WIDTH * SCENE_MULTIPLIER + sceneLeft);
	}
	
	public int getRandomY() {
		return (int) (Math.random() * Game.HEIGHT * SCENE_MULTIPLIER + sceneTop);
	}
	
}
