package com.codepenguins.rolling.model;

public class FinalScene extends Scene {

	private final int BACKGROUND = 0x333333;
	private final int COLOR = 0xFFFFFF;
	private final int SELECTED_COLOR = 0x33DD33;
	private final int FONT_ID = 0;
	
	private final String START_GAME = "START";
	private final String EXIT_GAME = "EXIT";
	
	@Override
	public void processScene(long tick) {
		super.processScene(tick);
		int x = 100;
		int y0 = 100;
		addTextObject(new TextObject(x, y0, START_GAME, SELECTED_COLOR, FONT_ID));
		setBackgroundColor(BACKGROUND);
	}
}
