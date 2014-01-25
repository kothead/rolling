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
	
	public void addGameObject(GameObject gameObject) {
		objects.add(gameObject);
	}
	
	public void addTextObject(TextObject textObject) {
		textObjects.add(textObject);
	}
	
	public void deleteGameObject(GameObject gameObject) {
		objects.remove(gameObject);
	}
	
	public void deleteGameObject(int index) {
		objects.remove(index);
	}
	
	public void deleteTextObject(TextObject textObject) {
		textObjects.remove(textObject);
	}
	
	public void deleteTextObject(int index) {
		textObjects.remove(index);
	}
	
	public TextObject getTextObjectById(int index) {
		return textObjects.get(index);
	}
}
