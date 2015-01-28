package ru.dienet.wolfy.game.shooter;

import android.graphics.Paint;

import ru.dienet.wolfy.game.framework.Screen;
import ru.dienet.wolfy.game.framework.interfaces.Game;

public class ShooterScreen extends Screen {
	enum GameState{
		Ready, Running, Paused, GameOver
	}

	GameState gameState = GameState.Ready;
	Paint paint;

	protected ShooterScreen( Game game ) {
		super( game );
	}

	@Override
	public void update( float deltaTime ) {

	}

	@Override
	public void paint( float deltaTime ) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}
