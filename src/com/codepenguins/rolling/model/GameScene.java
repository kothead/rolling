package com.codepenguins.rolling.model;

import java.util.List;

import sun.awt.geom.AreaOp.AddOp;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.Sound;
import com.codepenguins.rolling.io.UserEvents;

public class GameScene extends Scene {

	private static final int BACKGROUND = 0xC0C0E0;
	private static final int UI_COLOR = 0xFFFFFF;
	
	private static final float CLOUD_PROBABILITY = 1f;
	private static final float PLANE_PROBABILITY = 0.5f;
	private static final float SCENE_MULTIPLIER = 2;
	private static final float PATH_MULTIPLIER = 100;
	
	private static final String KILOMETERS = "MILES";
	
	private TextObject pathText;
	private Player player1, player2;
	private int path;
	private int sceneLeft;
	private int sceneTop;
	private int sceneRight;
	private int sceneBottom;

	public GameScene() {
		setBackgroundColor(BACKGROUND);
		float multiplier = SCENE_MULTIPLIER / 2;
		sceneLeft = (int) (0 - Game.WIDTH / multiplier);
		sceneRight = (int) (Game.WIDTH + Game.WIDTH / multiplier);
		sceneTop = (int) (0 - Game.HEIGHT / multiplier);
		sceneBottom = (int) (Game.HEIGHT + Game.HEIGHT / multiplier);
		new Sound("res/game.wav");
		player1 = new Player1();
		player1.setX((Game.WIDTH - player1.getWidth()) / 4);
		player1.setY((Game.HEIGHT - player1.getHeight()) / 2);
		
		player2 = new Player2();
		player2.setX((Game.WIDTH - player1.getWidth()) / 4 * 3);
		player2.setY((Game.WIDTH - player1.getWidth()) / 2);
		
		appendGameObject(player1);
		appendGameObject(player2);
	
		pathText = new TextObject(20, 20, getPathLine(), UI_COLOR, 0);
		addTextObject(pathText);
	}

	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		if (!player1.getHit()) {
			detectCollisions(player1);
		}
		if (!player2.getHit()) {
			detectCollisions(player2);
		}
		collectOutObjects();
		generateTrash(player1);
		generateTrash(player2);
		
		boolean[] keyPressed = UserEvents.getKeyPressed();
		if (keyPressed[Game.ESCAPE_INDEX]) {
			Game.initMenuScene();
		}
		
		pathText.setText(getPathLine());
	}

	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	private void detectCollisions(Player player) {
		float x1 = player.getX() + player.getWidth() * Player.SPRITE_THRESHOLD;
		float x2 = player.getX() + player.getWidth() * (1 - Player.SPRITE_THRESHOLD);
		float y1 = player.getY() + player.getWidth() * Player.SPRITE_THRESHOLD;
		float y2 = player.getY() + player.getHeight() * (1 - Player.SPRITE_THRESHOLD);
		
		for (GameObject object: getObjects()) {
			if (object instanceof Plane) {
				Plane plane = (Plane) object;
				float ox1 = plane.getX();
				float ox2 = plane.getX() + plane.getWidth();
				float oy1 = plane.getY();
				float oy2 = plane.getY() + plane.getHeight();
				
				if (ox2 >= x1 && ox1 <= x2 && oy2 >= y1 && oy1 <= y2) {
					player.setVX(-player.getVX() + plane.getVX());
					player.setVY(-player.getVY() + plane.getVY());
					player.setVA(10);
					player.setHit(true);
					return;
				}
			}
		}
	}
	
	private void collectOutObjects() {
		List<GameObject> objects = getObjects();
		for (int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			if (object.getX() < sceneLeft || object.getX() > sceneRight 
					|| object.getY() < sceneTop || object.getY() > sceneBottom) {
				objects.remove(i);
				i--;
			}
		}
	}

	private void generateTrash(Player player) {
		double cloudProb = Math.random();
		double calcProb = 1 + Math.max(Math.abs(player.getX()), Math.abs(player.getVY())) / Player.MAX_SPEED; 
		if (cloudProb < calcProb * CLOUD_PROBABILITY / Game.TARGET_FPS) {
			generateCloud();
		}
		double planeProb = Math.random();
		if (planeProb < calcProb * PLANE_PROBABILITY / Game.TARGET_FPS) {
			generatePlane();
		}
	}
	
	private void generateCloud() {
		boolean right = Math.random() > 0.5;
		Cloud cloud = new Cloud(right);
		cloud.setY(getRandomY(-Game.HEIGHT, sceneBottom));
		if (cloud.getY() > Game.HEIGHT || cloud.getY() + cloud.getHeight() < 0) {
			cloud.setX(getRandomX());
		} else {
			cloud.setX(right ? sceneRight : sceneLeft);
		}
		prependGameObject(cloud);
	}

	private void generatePlane() {
		int typesCount = Plane.getTypesCount();
		int typeIndex = (int) (Math.random() * (typesCount));
		Plane.Type type = Plane.Type.values()[typeIndex];
		boolean right = Math.random() > 0.5;
		Plane plane = new Plane(type, right);
		if (type == Plane.Type.PLANE || type == Plane.Type.DUCK
				|| type == Plane.Type.ZEPPELIN) {
			plane.setY(getRandomY());
			if (plane.getY() > Game.HEIGHT) {
				plane.setX(getRandomX());
			} else {
				plane.setX(right ? sceneRight: sceneLeft);
			}
			appendGameObject(plane);
		} else if (type == Plane.Type.MUNGH) {
			plane.setX(getRandomY());
			plane.setY(sceneBottom);
			appendGameObject(plane);
		}
	}

	private int getRandomX() {
		return (int) (Math.random() * Game.WIDTH * SCENE_MULTIPLIER + sceneLeft);
	}

	private int getRandomY() {
		return getRandomY(0, sceneBottom);
	}
	
	private int getRandomY(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private String getPathLine() {
		return String.format("%s: %d", KILOMETERS, (int) (path / PATH_MULTIPLIER));
	}
}
