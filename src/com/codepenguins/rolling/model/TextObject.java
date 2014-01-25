package com.codepenguins.rolling.model;

public class TextObject {
	
	private static int[] fonts;
	private float x;
	private float y;
	private String text;
	private int fontId;
	private int color;
	
	public static void setFonts(int[] ids) {
		fonts = ids;
	}
	
	public TextObject(float x, float y, String text, int color, int fontId) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		this.fontId = fontId;
	}
	
	public float getX() {
		 return x;
	}
	
	public float getY() {
		return y;
	}

	public String getText() {
		return text;
	}
	
	public int getFontId() {
		return fonts[fontId];
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
