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
				if( inBounds( touchEvent, 0,0, 250,250 ) ){
					game.setScreen( new GameScreen( game ) );
				}
			}
		}
	}

	private boolean inBounds( TouchEvent touchEvent, int x, int y, int width, int height ) {
		return (touchEvent.x > x) && (touchEvent.x < x + width - 1) &&
				(touchEvent.y > y) && (touchEvent.y < y + height - 1);
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
	}
}
