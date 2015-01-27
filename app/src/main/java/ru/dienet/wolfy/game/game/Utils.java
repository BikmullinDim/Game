package ru.dienet.wolfy.game.game;

import ru.dienet.wolfy.game.framework.interfaces.Input;

public class Utils {
	public static boolean inBounds( Input.TouchEvent touchEvent, int x, int y, int width, int height ) {
		return (touchEvent.x > x) && (touchEvent.x < x + width - 1) &&
				(touchEvent.y > y) && (touchEvent.y < y + height - 1);
	}
}
