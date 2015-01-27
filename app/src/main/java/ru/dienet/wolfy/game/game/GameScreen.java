package ru.dienet.wolfy.game.game;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import ru.dienet.wolfy.game.framework.Screen;
import ru.dienet.wolfy.game.framework.interfaces.Game;
import ru.dienet.wolfy.game.framework.interfaces.Graphics;

import static ru.dienet.wolfy.game.framework.interfaces.Input.TouchEvent;

public class GameScreen extends Screen {

	enum GameState{
		Ready, Running, Paused, GameOver
	}

	GameState gameState = GameState.Ready;
	Paint paint;

	int livesLeft = 1;


	public GameScreen( Game game ) {
		super(game);

		//ToDo: init game objects

		paint = new Paint(  );
		paint.setTextSize( 30 );
		paint.setTextAlign( Paint.Align.CENTER );
		paint.setAntiAlias( true );
		paint.setColor( Color.WHITE );
	}

	@Override
	public void update( float deltaTime ) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		if (gameState == GameState.Ready)
			updateReady(touchEvents);
		if (gameState == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (gameState == GameState.Paused)
			updatePaused(touchEvents);
		if (gameState == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady( List<TouchEvent> touchEvents ) {
		if(touchEvents.size() > 0){
			gameState = GameState.Running;
		}
	}

	private void updateRunning( List<TouchEvent> touchEvents, float deltaTime ) {
		handleInput(touchEvents);
		checkEvents();
		callGameObjectsUpdates();
	}

	private void callGameObjectsUpdates() {

	}

	private void checkEvents() {
		if (livesLeft == 0) {
			gameState = GameState.GameOver;
		}
	}

	private void handleInput( List<TouchEvent> touchEvents ) {
		int length = touchEvents.size();
		TouchEvent touchEvent;
		for ( int i = 0; i < length; i++ ) {
			touchEvent = touchEvents.get( i );
			if(touchEvent.type == TouchEvent.TOUCH_DOWN){
				touchDown( touchEvent );
			}else if (touchEvent.type == TouchEvent.TOUCH_UP){
				touchUp(touchEvent);
			}
		}
	}

	private void touchDown( TouchEvent touchEvent ) {
		if (touchEvent.x < 640) {
			// Move left.
		}

		else if (touchEvent.x > 640) {
			// Move right.
		}
	}

	private void touchUp( TouchEvent touchEvent ) {
		if (touchEvent.x < 640) {
			// Stop moving left.
		}

		else if (touchEvent.x > 640) {
			// Stop moving right. }
		}
	}

	private void updateGameOver( List<TouchEvent> touchEvents ) {
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent touchEvent = touchEvents.get(i);
			if (touchEvent.type == TouchEvent.TOUCH_UP) {
				if (touchEvent.x > 300 && touchEvent.x < 980 && touchEvent.y > 100
						&& touchEvent.y < 500) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	private void nullify() {
		// Set all variables to null. It will be recreating in the constructor.
		paint = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void updatePaused( List<TouchEvent> touchEvents ) {
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

			}
		}
	}

	@Override
	public void paint( float deltaTime ) {
		Graphics graphics = game.getGraphics();

		// First draw the game elements.

		// Example:
		// graphics.drawImage(Assets.background, 0, 0);
		// graphics.drawImage(Assets.character, characterX, characterY);

		// Secondly, draw the UI above the game elements.
		if (gameState == GameState.Ready)
			drawReadyUI(graphics);
		if (gameState == GameState.Running)
			drawRunningUI(graphics);
		if (gameState == GameState.Paused)
			drawPausedUI(graphics);
		if (gameState == GameState.GameOver)
			drawGameOverUI(graphics);
	}

	private void drawGameOverUI( Graphics graphics ) {
		graphics.drawRect(0, 0, 1281, 801, Color.BLACK);
		graphics.drawString("GAME OVER.", 640, 300, paint);
	}

	private void drawPausedUI( Graphics graphics ) {
		// Darken the entire screen, can display the Paused screen.
		graphics.drawARGB(155, 0, 0, 0);
	}

	private void drawRunningUI( Graphics graphics ) {
		
	}

	private void drawReadyUI( Graphics graphics ) {
		graphics.drawARGB(155, 0, 0, 0);
		graphics.drawString("Tap each side of the screen to move in that direction.",
				640, 300, paint);
	}

	@Override
	public void pause() {
		if (gameState == GameState.Running)
			gameState = GameState.Paused;
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}
}
