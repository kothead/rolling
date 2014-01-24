package com.codepenguins.rolling;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Textures {
	private ArrayList<ExtrTexture> textures;
	
	/* =============
	 * Init
	 */
	public Textures() {
		textures = new ArrayList<ExtrTexture>();
	}
	
	/* =============
	 * Public:
	 *   void loadTexture(String fileName);
	 *   Texture getTexture(int number);
	 *   String getTextureAddress(int number);
	 *   int getNumberOfTexture(String fileName);
	 */
	public int loadTexture(String fileName) {
		String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
		ExtrTexture tex = new ExtrTexture();
		tex.fileName = fileName;
		try {
			tex.texture = TextureLoader.getTexture(fileExt, new FileInputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textures.add(tex);
		return textures.size() - 1;
	}
	public Texture getTexture(int number) {
		return textures.get(number).texture;
	}
	public String getTextureAddress(int number) {
		return textures.get(number).fileName;
	}
	public int getNumberOfTexture(String fileName) {
		int number = -1;
		int count = textures.size();
		for (int i = 0; i < count; i++)
			if (getTextureAddress(i) == fileName) {
				number = i;
				break;
			}
		return number;
	}
	
	/* =============
	 * Private
	 */
	private class ExtrTexture {
		public Texture texture;
		public String fileName;
	}
}
