package ru.dienet.wolfy.game.game;

import java.util.List;

import ru.dienet.wolfy.game.framework.Screen;
import ru.dienet.wolfy.game.framework.interfaces.Game;
import ru.dienet.wolfy.game.framework.interfaces.Graphics;

import static ru.dienet.wolfy.game.framework.interfaces.Input.TouchEvent;

public class MainMenuScreen extends Screen {
	public MainMenuScreen( Game game ) {
		super(game);
	}

	@Override
	public void update( float deltaTime ) {
		Graphics graphics = game.getGraphics();
		List<TouchEvent> touchEvents  = game.getInput().getTouchEvents();

		int length = touchEvents.size();
		TouchEvent touchEvent;
		for ( int i = 0; i < length; i++ ) {
			touchEvent = touchEvents.get( i );
			if (touchEvent.type == TouchEvent.TOUCH_UP ){
				if(Utils.inBounds( touchEvent, 50, 350, 250, 450 ) ){
					game.setScreen( new GameScreen( game ) );
				}
			}
		}
	}


	@Override
	public void paint( float deltaTime ) {
		Graphics graphics = game.getGraphics();
		graphics.drawImage( Assets.getMenu() ,0,0 );
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
		//Display "Exit Game?" dialog
		android.os.Process.killProcess( android.os.Process.myPid() );
	}
}
