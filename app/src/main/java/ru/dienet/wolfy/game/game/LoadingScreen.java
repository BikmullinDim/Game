package ru.dienet.wolfy.game.game;

import ru.dienet.wolfy.game.framework.Screen;
import ru.dienet.wolfy.game.framework.interfaces.Game;
import ru.dienet.wolfy.game.framework.interfaces.Graphics;
import ru.dienet.wolfy.game.framework.interfaces.Graphics.ImageFormat;

public class LoadingScreen extends Screen {

	protected LoadingScreen( Game game ) {
		super( game );
	}

	@Override
	public void update( float deltaTime ) {

		Graphics graphics = game.getGraphics();
		Assets.background = graphics.newImage("background.png", ImageFormat.RGB565);
		Assets.character = graphics.newImage("character.png", ImageFormat.ARGB4444);
		Assets.character2 = graphics.newImage("character2.png", ImageFormat.ARGB4444);
		Assets.character3  = graphics.newImage("character3.png", ImageFormat.ARGB4444);
		Assets.characterJump = graphics.newImage("jumped.png", ImageFormat.ARGB4444);
		Assets.characterDown = graphics.newImage("down.png", ImageFormat.ARGB4444);


		Assets.heliboy = graphics.newImage("heliboy.png", ImageFormat.ARGB4444);
		Assets.heliboy2 = graphics.newImage("heliboy2.png", ImageFormat.ARGB4444);
		Assets.heliboy3  = graphics.newImage("heliboy3.png", ImageFormat.ARGB4444);
		Assets.heliboy4  = graphics.newImage("heliboy4.png", ImageFormat.ARGB4444);
		Assets.heliboy5  = graphics.newImage("heliboy5.png", ImageFormat.ARGB4444);



		Assets.tiledirt = graphics.newImage("tiledirt.png", ImageFormat.RGB565);
		Assets.tilegrassTop = graphics.newImage("tilegrasstop.png", ImageFormat.RGB565);
		Assets.tilegrassBot = graphics.newImage("tilegrassbot.png", ImageFormat.RGB565);
		Assets.tilegrassLeft = graphics.newImage("tilegrassleft.png", ImageFormat.RGB565);
		Assets.tilegrassRight = graphics.newImage("tilegrassright.png", ImageFormat.RGB565);

		Assets.button = graphics.newImage("button.jpg", ImageFormat.RGB565);

		//This is how you would load a sound if you had one.
		//Assets.click = game.getAudio().createSound("explode.ogg");

		Assets.setMenu( graphics.newImage( "menu.png", Graphics.ImageFormat.RGB565 ) );
		Assets.setClick( game.getAudio().createSound( "shot.ogg" ) );
		//when assets loaded
		game.setScreen( new MainMenuScreen( game ) );
	}

	@Override
	public void paint( float deltaTime ) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
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
