package ru.dienet.wolfy.game.framework;

import ru.dienet.wolfy.game.framework.interfaces.Game;

public abstract class Screen {
	protected final Game game;

	protected Screen( Game game ) {
		this.game = game;
	}

	public abstract void update( float deltaTime );

	public abstract void paint( float deltaTime );

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void backButton();
}
