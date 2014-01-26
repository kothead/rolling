package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;

public class FinalScene extends Scene {

	private final int BACKGROUND = 0x333333;
	private final int COLOR = 0xFFFFFF;
	private final int SELECTED_COLOR = 0x33DD33;
	private final int FONT_ID = 0;
	
	private final String WIN = "You won!";
	private final String LOSE = "You lost!";
	private String text = "";
	private int color;
	
	public FinalScene(boolean hasWon) {
		if (hasWon) {
			text = WIN;
			color = SELECTED_COLOR;
		} else {
			text = LOSE;
			color = COLOR;
		}
	}
	
	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		int x = Game.WIDTH / 2;
		int y = Game.HEIGHT / 2;
		addTextObject(new TextObject(x, y, text, color, FONT_ID));
		setBackgroundColor(BACKGROUND);
	}
}
