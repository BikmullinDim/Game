package ru.dienet.wolfy.game.game;

import ru.dienet.wolfy.game.framework.Screen;
import ru.dienet.wolfy.game.framework.interfaces.Graphics;

public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen( GameMain game ) {
		super(game);
	}

	@Override
	public void update( float deltaTime ) {
		Graphics graphics = game.getGraphics();
		Assets.splash= graphics.newImage("splash.jpg", Graphics.ImageFormat.RGB565);
		game.setScreen(new LoadingScreen(game));
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
