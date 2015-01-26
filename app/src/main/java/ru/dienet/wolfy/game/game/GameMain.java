package ru.dienet.wolfy.game.game;

import ru.dienet.wolfy.game.framework.AndroidGame;
import ru.dienet.wolfy.game.framework.Screen;

public class GameMain extends AndroidGame {
	@Override
	public Screen getInitScreen() {
		return new LoadingScreen(this);
	}
}
