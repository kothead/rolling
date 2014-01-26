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
	
	private final String START_GAME = "START";
	private final String EXIT_GAME = "EXIT";
	
	int selectedIndex = 0;
	int buttonsNum = 2;

	int upIndex = 0;
	int downIndex = 1;
	int returnIndex = 4;
	int escapeIndex = 5;

	public MenuScene() {
		Background back = new Background();
		prependGameObject(back);
		int x = getXcoord();
		int y0 = getYcoord(0);
		addTextObject(new TextObject(x, y0, START_GAME, SELECTED_COLOR, FONT_ID));
		int y1 = getYcoord(1);
		addTextObject(new TextObject(x, y1, EXIT_GAME, COLOR, FONT_ID));
		setBackgroundColor(BACKGROUND);
		new Sound("res/intro.wav");
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
			if (selectedIndex == 0)
				Game.initGameScene();
			else if (selectedIndex == 1)
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

}
