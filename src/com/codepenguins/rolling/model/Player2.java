package com.codepenguins.rolling.model;

import com.codepenguins.rolling.io.UserEvents;

public class Player2 extends Player {
	
	@Override
	protected void processControl() {
		boolean[] keyDown = UserEvents.getkeyDowned();
		if (keyDown[0]) {
			if (vy - INC_CONTROL >= MIN_SPEED) {
				vy -= INC_CONTROL;
			}
		}
		if (keyDown[1]) {
			vy += INC_CONTROL;
		}
		if (keyDown[2]) {
			vx -= INC_CONTROL;
		}
		if (keyDown[3]) {
			vx += INC_CONTROL;
		}
	}
	
}
