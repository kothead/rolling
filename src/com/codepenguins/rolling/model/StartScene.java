package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;

public class StartScene extends Scene {

	private Player player;
	private PlayerKiller killer;

	private final int FINAL_KICK_FRAME = 3;

	boolean startFalling = false;

	public StartScene() {
		player = new Player();
		player.setX(Game.WIDTH / 2);
		player.setY(Game.HEIGHT / 2);
		appendGameObject(player);

		killer = new PlayerKiller();
		killer.setX(100);
		killer.setY(100);
		appendGameObject(killer);
	}

	@Override
	public void processScene(long tick) {
		super.processScene(tick);

		if (killer.getFrameId() >= FINAL_KICK_FRAME){
			startFalling = true;
		}
		if (startFalling){
			player.setX(player.getX() + player.getVX());
			player.setY(player.getY() + player.getVY());
			if (player.getX() >= Game.WIDTH / 2 && player.getY() >= Game.HEIGHT / 2) {
				Game.initGameScene();
			}
		}
	}
}
