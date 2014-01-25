package com.codepenguins.rolling.model;

public class GameScene extends Scene {

	private Player player;

	public GameScene() {
		player = new Player();
		addGameObject(player);
	}
}
