package com.codepenguins.rolling.model;

public class GameScene extends Scene {

	private static final float CLOUD_PROBABILITY = 0.5f;
	private static final float PLANE_PROBABILITY = 0.1f;
	
	private Player player;

	public GameScene() {
		player = new Player();
		addGameObject(player);
	}
	
	public void generateCloud() {
		
	}
	
	public void generatePlane() {
		
	}
	
	public void collectOutObjects() {
		
	}
	
}
