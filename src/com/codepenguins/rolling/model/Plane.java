package com.codepenguins.rolling.model;

import com.codepenguins.rolling.Game;

public class Plane extends GameObject {

	private static final int TYPES_COUNT = 4;
	enum Type { PLANE, ZEPPELIN, DUCK, MUNGH }

	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 10;
	
	private static final int PLANE_TEXTURE = 3;
	private static final int ZEPPELIN_TEXTURE = 0;
	private static final int DUCK_TEXTURE = 1;
	private static final int MUNGH_TEXTURE = 2;

	private float vX;
	private float vY;
	private Type type;

	public Plane(Type type, boolean right) {
		this.type = type;
		int speedDiff = MAX_SPEED - MIN_SPEED;
		vX = (int) (Math.random() * speedDiff + MIN_SPEED);
		vY = (int) (Math.random() * speedDiff + MIN_SPEED);
		if (right) {
			vX = -vX;
		}
		if (type == Type.PLANE) {
			setCurrentTexture(PLANE_TEXTURE);
		} else if (type == Type.DUCK) {
			vY = -vY;
			setCurrentTexture(DUCK_TEXTURE);
		} else if (type == Type.ZEPPELIN) {
			vY = 0;
			setCurrentTexture(ZEPPELIN_TEXTURE);
		} else if (type == Type.MUNGH) {
			vY = -vY;
			vX = 0;
			setCurrentTexture(MUNGH_TEXTURE);
		}
	}


	public static int getTypesCount() {
		return TYPES_COUNT;
	}

	@Override
	public void process(long tick) {
		float relativeX = vX * tick / Game.TARGET_TICK;
		float relativeY = vY * tick / Game.TARGET_TICK;
		setX(getX() + relativeX);
		setY(getY() + relativeY);
	}

}
