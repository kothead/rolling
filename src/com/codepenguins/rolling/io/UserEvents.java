package com.codepenguins.rolling.io;

import org.lwjgl.input.Keyboard;

public class UserEvents {
	private static final int buttonCount = 6;
	private static boolean[] keyDownedOld = new boolean[buttonCount];
	private static boolean[] keyDowned = new boolean[buttonCount];
	private static boolean[] keyPressed = new boolean[buttonCount];
	
	
	public static boolean[] getkeyDowned() {
		refreshKeyDowned();
		return keyDowned;
	}
	
	public static boolean[] getKeyPressed() {
		refreshKeyDowned();
		for (int i = 0; i < buttonCount; i++) {
			keyPressed[i] = (keyDowned[i] && !keyDownedOld[i]);
			keyDownedOld[i] = keyDowned[i]; 
		}
		return keyPressed;
	}
	
	private static void refreshKeyDowned() {
		keyDowned[0] = Keyboard.isKeyDown(Keyboard.KEY_UP);
		keyDowned[1] = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		keyDowned[2] = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
		keyDowned[3] = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		keyDowned[4] = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
		keyDowned[5] = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		keyDowned[6] = Keyboard.isKeyDown(Keyboard.KEY_W);
		keyDowned[7] = Keyboard.isKeyDown(Keyboard.KEY_A);
		keyDowned[8] = Keyboard.isKeyDown(Keyboard.KEY_S);
		keyDowned[9] = Keyboard.isKeyDown(Keyboard.KEY_D);
	}
}
