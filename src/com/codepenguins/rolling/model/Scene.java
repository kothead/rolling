package com.codepenguins.rolling.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	private List<GameObject> objects;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
	
	public void processScene(long tick) {
		for (GameObject object: objects) {
			object.process(tick);
		}
	}
}
