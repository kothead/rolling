package com.codepenguins.rolling.model;

import java.util.Random;

import com.codepenguins.rolling.Game;

public class GameScene extends Scene {

	private static final float CLOUD_PROBABILITY = 0.5f;
	private static final float PLANE_PROBABILITY = 0.1f;
	private static final float SCENE_MULTIPLIER = 2;
	
	private Player player;

	public GameScene() {
		player = new Player();
		addGameObject(player);
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
	}
	
	public void generateCloud() {
		
	}
	
	public void generatePlane() {
		
	}
	
	public void collectOutObjects() {
		
	}
	
}
