package com.codepenguins.rolling.model;

import java.util.List;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;

public class MenuScene extends Scene {

	private final int BACKGROUND = 0x00FF00;
	private final int COLOR = 0xFFFF00;
	private final int SELECTED_COLOR = 0xFF0000;
	private final int FONT_ID = 0;
	
	private final String START_GAME = "Start";
	private final String EXIT_GAME = "Exit";
	
	int selectedIndex = 0;
	int buttonsNum = 3;

	int upIndex = 0;
	int downIndex = 1;
	int returnIndex = 4;
	int escapeIndex = 5;

	public MenuScene() {
		int x = getXcoord();
		int y0 = getYcoord(0);
		addTextObject(new TextObject(x, y0, START_GAME, COLOR, FONT_ID));
		int y1 = getYcoord(0);
		addTextObject(new TextObject(x, y1, EXIT_GAME, COLOR, FONT_ID));
		setBackgroundColor(BACKGROUND);
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
//			TextObject currentTextObject = getTextObjectById(selectedIndex);
//			currentTextObject.setColor(SELECTED_COLOR);
		} else if (keyPressed[downIndex]) {
			selectedIndex = Math.min(selectedIndex + 1, buttonsNum);
			List<TextObject> objects = getTextObjects();
			for (TextObject tObj : objects) {
				if (objects.indexOf(tObj) == selectedIndex) 
					tObj.setColor(SELECTED_COLOR);
				else
					tObj.setColor(COLOR);
			}
		} else if (keyPressed[returnIndex]) {
			Game.initGameScene();
		} else if (keyPressed[escapeIndex]) {
			Game.setGameOver();
		}
	}
	
	private int getXcoord() {
		int x = (int) (Game.WIDTH * 0.7f);
		return x;
	}
	
	private int getYcoord(int position) {
		int y = (int) (Game.HEIGHT * 0.3f + position * (Game.FONT_SIZE_LARGE + 20));
		return y;
	}

}
