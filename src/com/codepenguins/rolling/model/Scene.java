package com.codepenguins.rolling.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	private List<GameObject> objects;
	private List<TextObject> textObjects;
	private int bgColor;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
		textObjects = new ArrayList<TextObject>();
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
	
	public List<TextObject> getTextObjects() {
		return textObjects;
	}
	
	public void setBackgroundColor(int color) {
		bgColor = color;
	}
	
	public int getBackgroundColor() {
		return bgColor;
	}
	
	public void processScene(long tick) {
		for (GameObject object: objects) {
			object.process(tick);
		}
	}
}
