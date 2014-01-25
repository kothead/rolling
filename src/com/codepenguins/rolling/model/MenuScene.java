package com.codepenguins.rolling.model;

import java.util.List;

import com.codepenguins.rolling.Game;
import com.codepenguins.rolling.io.UserEvents;

public class MenuScene extends Scene {

	private final int BACKGROUND = 0xFF00FF00;
	private final int COLOR = 0xFFFF00FF;
	private final int SELECTED_COLOR = 0xFF0000FF;
	private final int FONT_ID = 0;
	
	int selectedIndex = 0;
	int buttonsNum = 3;

	int upIndex = 0;
	int downIndex = 1;
	int returnIndex = 4;
	int escapeIndex = 5;

	public MenuScene() {
		int x = getXcoord();
		addTextObject(new TextObject(x, 20, "text", COLOR, FONT_ID));
		addTextObject(new TextObject(x, 50, "text", COLOR, FONT_ID));
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
			TextObject currentTextObject = getTextObjectById(selectedIndex);
			currentTextObject.setColor(SELECTED_COLOR);
		} else if (keyPressed[returnIndex]) {
			Game.initGameScene();
		} else if (keyPressed[escapeIndex]) {
			//TODO close
		}
	}
	
	private int getXcoord() {
		int x = (int) (Game.WIDTH * 0.7f);
		return x;
	}

}
