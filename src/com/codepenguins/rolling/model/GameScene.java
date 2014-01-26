package com.codepenguins.rolling.model;

import java.util.List;

import sun.awt.geom.AreaOp.AddOp;
import sun.dc.pr.PathStroker;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.Sound;
import com.codepenguins.rolling.io.UserEvents;

public class GameScene extends Scene {

	private static final int BACKGROUND = 0xC0C0E0;
	private static final int UI_COLOR = 0xFFFFFF;
	
	private static final int UPPER_BOUND = 500;
	private static final int LOWER_BOUND = -100;
	private static final float CLOUD_PROBABILITY = 1f;
	private static final float PLANE_PROBABILITY = 0.1f;
	private static final float PLANE_PROB_INC = 0.001f;
	private static final float SCENE_MULTIPLIER = 2;
	private static final float PATH_MULTIPLIER = 100;
	
	private static final String KILOMETERS = "MILES";
	
	private TextObject pathText;
	//private UiObject[] hearts;
	private Player player;
	private float currentProb = PLANE_PROBABILITY;
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
		player = new Player();
		player.setX((Game.WIDTH - player.getWidth()) / 2);
		player.setY((Game.HEIGHT - player.getHeight()) / 2);
		appendGameObject(player);
	
		/*hearts = new UiObject[player.getLifes()];
		for (int i = 0; i < hearts.length; i++) {
			UiObject heart = new UiObject(8);
			heart.setY(20);
			heart.setX(Game.WIDTH - (heart.getWidth() + 10) * (i + 1));
			hearts[i] = heart;
			appendGameObject(heart);
		}*/
		
		pathText = new TextObject(20, 20, getPathLine(), UI_COLOR, 0);
		addTextObject(pathText);
	}

	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		updateObjectsPosition();
		if (!player.getHit()) {
			detectCollisions();
		}
		collectOutObjects();
		generateTrash();
		
		boolean[] keyPressed = UserEvents.getKeyPressed();
		if (keyPressed[Game.ESCAPE_INDEX]) {
			Game.initMenuScene();
		}
		
		pathText.setText(getPathLine());
		checkGameOver();
	}

	public void checkGameOver() {
		int miles = (int) (path / PATH_MULTIPLIER); 
		if (miles <= LOWER_BOUND) {
			Game.initFinalScene(false);
		} else if (miles >= UPPER_BOUND) {
			Game.initFinalScene(true);
		} else {
			currentProb += PLANE_PROB_INC;
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	private void updateObjectsPosition() {
		for (GameObject object: getObjects()) {
			if (!(object instanceof Player) && !(object instanceof UiObject)) {
				object.setX(object.getX() - player.getVX());
				object.setY(object.getY() - player.getVY());
			}
		}
		path += player.getVY();
	}

	private void detectCollisions() {
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
					player.setVA(10); // TODO
					player.setHit(true);
					/*if (player.getLifes() > 0) {
						deleteGameObject(hearts[player.getLifes()]);
					} else {
						Game.setGameOver();
					}*/
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

	private void generateTrash() {
		double cloudProb = Math.random();
		double calcProb = 1 + Math.max(Math.abs(player.getX()), Math.abs(player.getVY())) / Player.MAX_SPEED; 
		if (cloudProb < calcProb * CLOUD_PROBABILITY / Game.TARGET_FPS) {
			generateCloud();
		}
		double planeProb = Math.random();
		if (planeProb < calcProb * currentProb / Game.TARGET_FPS) {
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
		}/* else if (type == Plane.Type.MUNGH) {
			plane.setX(getRandomY());
			plane.setY(sceneBottom);
			appendGameObject(plane);
		}*/
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
