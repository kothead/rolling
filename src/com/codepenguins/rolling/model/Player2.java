package com.codepenguins.rolling.model;

import com.codepenguins.rolling.io.UserEvents;

public class Player2 extends Player {
	
	@Override
	protected void processControl() {
		boolean[] keyDown = UserEvents.getkeyDowned();
		if (keyDown[6]) {
			if (vy - INC_CONTROL >= MIN_SPEED) {
				vy -= INC_CONTROL;
			}
		}
		if (keyDown[7]) {
			vy += INC_CONTROL;
		}
		if (keyDown[8]) {
			vx -= INC_CONTROL;
		}
		if (keyDown[9]) {
			vx += INC_CONTROL;
		}
	}
	
}
