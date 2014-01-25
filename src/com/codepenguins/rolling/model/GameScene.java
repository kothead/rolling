package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;

public class GameScene extends Scene {

	private static final float CLOUD_PROBABILITY = Game.TARGET_FPS;
	private static final float PLANE_PROBABILITY = 30;
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
		player.setX(Game.WIDTH / 2);
		player.setY(Game.HEIGHT / 2);
		appendGameObject(player);
	}

	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		for (GameObject object: getObjects()) {
			if (!(object instanceof Player)) {
				object.setX(object.getX() - player.getVX());
				object.setY(object.getY() - player.getVY());
			}
		}
		
		double cloudProb = Math.random();
		if (cloudProb < CLOUD_PROBABILITY / Game.TARGET_FPS) {
			generateCloud();
		}

		double planeProb = Math.random();
		if (planeProb < PLANE_PROBABILITY / Game.TARGET_FPS) {
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
		prependGameObject(cloud);
	}

	public void generatePlane() {
		int typesCount = Plane.getTypesCount();
		int typeIndex = (int) (Math.random() * (typesCount));
		Plane.Type type = Plane.Type.PLANE;//Plane.Type.values()[typeIndex];
		boolean right = Math.random() > 0.5;
		Plane plane = new Plane(type, right);
		if (type == Plane.Type.PLANE || type == Plane.Type.DUCK
				|| type == Plane.Type.ZEPPELIN) {
			plane.setX(right ? sceneRight: sceneLeft);
			plane.setY(getRandomY());
			appendGameObject(plane);
		} else if (type == Plane.Type.MUNGH) {
			plane.setX(getRandomY());
			plane.setY(sceneBottom);
			appendGameObject(plane);
		}
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
