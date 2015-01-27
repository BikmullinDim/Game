package ru.dienet.wolfy.game.game;

import ru.dienet.wolfy.game.framework.interfaces.Image;
import ru.dienet.wolfy.game.framework.interfaces.Music;
import ru.dienet.wolfy.game.framework.interfaces.Sound;

public class Assets {

	private static Image menu;
	private static Sound click;

	public static Sound getClick() {
		return click;
	}

	public static void setClick( Sound click ) {
		Assets.click = click;
	}

	public static Image getMenu() {
		return menu;
	}

	public static void setMenu( Image menu ) {
		Assets.menu = menu;
	}

	public static Image splash, background, character, character2, character3, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
	public static Image tiledirt, tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, characterJump, characterDown;
	public static Image button;
	public static Music theme;

	public static void load(GameMain sampleGame) {
		theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}
}
