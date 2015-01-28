package ru.dienet.wolfy.game.game;

import ru.dienet.wolfy.game.framework.interfaces.Input;

public class Utils {

	public static final int HEIGHT = 480;
	public static final int WIDTH = 480;
	public static final int HALF_HEIGHT = (int)(HEIGHT * .5);
	public static final int HALF_WIDTH = (int)(WIDTH * .5);

	public static boolean inBounds( Input.TouchEvent touchEvent, int x, int y, int width, int height ) {
		return (touchEvent.x > x) && (touchEvent.x < x + width - 1) &&
				(touchEvent.y > y) && (touchEvent.y < y + height - 1);
	}
}
