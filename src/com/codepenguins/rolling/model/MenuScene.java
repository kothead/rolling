package com.codepenguins.rolling.model;

import java.util.List;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;
import com.codepenguins.rolling.io.Sound;

public class MenuScene extends Scene {

	private final int BACKGROUND = 0x333333;
	private final int COLOR = 0xFFFFFF;
	private final int SELECTED_COLOR = 0x33DD33;
	private final int FONT_ID = 0;
	private final int FONT_ID_CREDIT = 1;
	
	private final String START_GAME = "START";
	private final String EXIT_GAME = "EXIT";
	private final String ROLLING = "ROLLING";
	private final String CREDITS = "by EADan, Kate & st_";
	private final String TARGET = "(just get 500 miles)";
	private final String[] MUSIC = {"music - quake3 ost", 
			"Front Line Assembly (http://www.mindphaser.com/)", 
			"Sonic Mayhem (http://www.sonicmayhem.com/)"};
	
	private Sound soundtrack;
	
	int selectedIndex = 0;
	int buttonsNum = 2;

	int upIndex = 0;
	int downIndex = 1;
	int returnIndex = 4;
	int escapeIndex = 5;

	public MenuScene() {
		int x = getXcoord();
		int y = getYcoord(0);
		addTextObject(new TextObject(x, y, START_GAME, SELECTED_COLOR, FONT_ID));
		y = getYcoord(1);
		addTextObject(new TextObject(x, y, EXIT_GAME, COLOR, FONT_ID));
		x = getTitleXcoord();
		y = getTitleYcoord();
		addTextObject(new TextObject(x, y, ROLLING, COLOR, FONT_ID));
		addTextObject(new TextObject(x, y + 25, TARGET, SELECTED_COLOR, FONT_ID_CREDIT));
		
		addTextObject(new TextObject(0.44f * Game.WIDTH, 0.9f * Game.HEIGHT, CREDITS, 
				COLOR, FONT_ID_CREDIT));
		
		for (int i = 0; i < MUSIC.length; i++) {
			addTextObject(new TextObject(0.6f * Game.WIDTH, 0.05f * Game.HEIGHT + (12 * i), MUSIC[i], 
					COLOR, FONT_ID_CREDIT));
		}
		setBackgroundColor(BACKGROUND);
		soundtrack = new Sound("res/intro.wav");
	}

	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		boolean[] keyPressed = UserEvents.getKeyPressed();
		if (keyPressed[upIndex]) {
			selectedIndex = Math.max(0, selectedIndex - 1);
			List<TextObject> objects = getTextObjects();
			for (TextObject tObj : objects) {
				if (objects.indexOf(tObj) == selectedIndex) 
					tObj.setColor(SELECTED_COLOR);
				else
					tObj.setColor(COLOR);
			}
		} else if (keyPressed[downIndex]) {
			selectedIndex = Math.min(selectedIndex + 1, buttonsNum - 1);
			List<TextObject> objects = getTextObjects();
			for (TextObject tObj : objects) {
				if (objects.indexOf(tObj) == selectedIndex) 
					tObj.setColor(SELECTED_COLOR);
				else
					tObj.setColor(COLOR);
			}
		} else if (keyPressed[returnIndex]) {
			if (selectedIndex == 0) {
				Game.initGameScene();
				soundtrack.stopPlaying();
			} else if (selectedIndex == 1)
				Game.setGameOver();
		} else if (keyPressed[escapeIndex]) {
			Game.setGameOver();
		}
	}
	
	private int getXcoord() {
		int x = (int) (Game.WIDTH * 0.15f);
		return x;
	}
	
	private int getYcoord(int position) {
		int y = (int) (Game.HEIGHT * 0.7f + position * (Game.FONT_SIZE_LARGE + 20));
		return y;
	}
	
	private int getTitleXcoord() {
		int x = (int) (Game.WIDTH * 0.45f);
		return x;
	}
	
	private int getTitleYcoord() {
		int y = (int) (Game.HEIGHT * 0.3f);
		return y;
	}

}
