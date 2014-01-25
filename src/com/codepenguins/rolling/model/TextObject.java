package com.codepenguins.rolling.model;

public class TextObject {
	
	private static int[] fonts;
	private int x;
	private int y;
	private String text;
	private int fontId;
	private int color;
	
	public static void setFonts(int[] ids) {
		fonts = ids;
	}
	
	public TextObject(int x, int y, String text, int color, int fontId) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		this.fontId = fontId;
	}
	
	public int getX() {
		 return x;
	}
	
	public int getY() {
		return y;
	}

	public String getText() {
		return text;
	}
	
	public int getFontId() {
		return fontId;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		
	}
}
