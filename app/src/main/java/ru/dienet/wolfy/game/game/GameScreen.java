package ru.dienet.wolfy.game.game;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ru.dienet.wolfy.game.framework.Screen;
import ru.dienet.wolfy.game.framework.interfaces.Game;
import ru.dienet.wolfy.game.framework.interfaces.Graphics;
import ru.dienet.wolfy.game.framework.interfaces.Image;
import ru.dienet.wolfy.game.game.gameObjects.Animation;
import ru.dienet.wolfy.game.game.gameObjects.Background;
import ru.dienet.wolfy.game.game.gameObjects.Heliboy;
import ru.dienet.wolfy.game.game.gameObjects.Projectile;
import ru.dienet.wolfy.game.game.gameObjects.Robot;
import ru.dienet.wolfy.game.game.gameObjects.Tile;

import static ru.dienet.wolfy.game.framework.interfaces.Input.TouchEvent;

public class GameScreen extends Screen {

	enum GameState{
		Ready, Running, Paused, GameOver
	}

	GameState gameState = GameState.Ready;
	Paint paint;

	int livesLeft = 1;


	private static Background bg1, bg2;
	private static Robot robot;
	public static Heliboy hb, hb2;

	private Image currentSprite, character, character2, character3, heliboy,
			heliboy2, heliboy3, heliboy4, heliboy5;
	private Animation anim, hanim;

	private ArrayList tilearray = new ArrayList();
	private Paint paint2;

	public GameScreen( Game game ) {
		super(game);

		//ToDo: init game objects
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);

		character = Assets.character;
		character2 = Assets.character2;
		character3 = Assets.character3;

		heliboy = Assets.heliboy;
		heliboy2 = Assets.heliboy2;
		heliboy3 = Assets.heliboy3;
		heliboy4 = Assets.heliboy4;
		heliboy5 = Assets.heliboy5;

		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
		loadMap();

		paint = new Paint(  );
		paint.setTextSize( 30 );
		paint.setTextAlign( Paint.Align.CENTER );
		paint.setAntiAlias( true );
		paint.setColor( Color.WHITE );

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.GREEN);
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
		robot.update();
		if (robot.isJumped()) {
			currentSprite = Assets.characterJump;
		} else if (robot.isJumped() == false && robot.isDucked() == false) {
			currentSprite = anim.getImage();
		}

		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			if (p.isVisible() == true) {
				p.update();
			} else {
				projectiles.remove(i);
			}
		}

		updateTiles();
		hb.update();
		hb2.update();
		bg1.update();
		bg2.update();
		animate();

		if (robot.getCenterY() > 500) {
			gameState = GameState.GameOver;
		}
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
			}else if (touchEvent.type == TouchEvent.TOUCH_DRAGGED){
				touchDrag(touchEvent);
			}
		}
	}

	static int lastdrag;
	private void touchDrag( TouchEvent touchEvent ) {

		if(touchEvent.x < Utils.HALF_WIDTH){
			if(touchEvent.y > Utils.HALF_HEIGHT){

			}
		}
	}

	private void touchDown( TouchEvent touchEvent ) {
		if (Utils.inBounds( touchEvent, 0, 285, 65, 65 )) {
			robot.jump();
			currentSprite = anim.getImage();
			robot.setDucked(false);
		}

		else if (Utils.inBounds( touchEvent, 0, 350, 65, 65 )) {

			if (robot.isDucked() == false && robot.isJumped() == false
					&& robot.isReadyToFire()) {
				robot.shoot();
			}
		}

		else if (Utils.inBounds( touchEvent, 0, 415, 65, 65 )
				&& robot.isJumped() == false) {
			currentSprite = Assets.characterDown;
			robot.setDucked(true);
			robot.setSpeedX(0);

		}

		if (touchEvent.x > 400) {
			// Move right.
			robot.moveRight();
			robot.setMovingRight(true);

		}
	}

	private void touchUp( TouchEvent touchEvent ) {

		if (Utils.inBounds( touchEvent, 0, 415, 65, 65 )) {
			currentSprite = anim.getImage();
			robot.setDucked(false);
		}

		if (Utils.inBounds( touchEvent, 0, 0, 35, 35 )) {
			pause();
		}

		if (touchEvent.x > 400) {
			// Move right.
			robot.stopRight();
		}
	}

	private void updateGameOver( List<TouchEvent> touchEvents ) {
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent touchEvent = touchEvents.get(i);
			if (touchEvent.type == TouchEvent.TOUCH_DOWN) {
				if (Utils.inBounds( touchEvent, 0, 0, 800, 480 )) {
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

		bg1 = null;
		bg2 = null;
		robot = null;
		hb = null;
		hb2 = null;
		currentSprite = null;
		character = null;
		character2 = null;
		character3 = null;
		heliboy = null;
		heliboy2 = null;
		heliboy3 = null;
		heliboy4 = null;
		heliboy5 = null;
		anim = null;
		hanim = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void updatePaused( List<TouchEvent> touchEvents ) {
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (Utils.inBounds( event, 0, 0, 800, 240 )) {
					if (!Utils.inBounds( event, 0, 0, 35, 35 )) {
						resume();
					}
				}

				if (Utils.inBounds( event, 0, 240, 800, 240 )) {
					nullify();
					goToMenu();
				}
			}
		}
	}

	@Override
	public void paint( float deltaTime ) {
		Graphics graphics = game.getGraphics();

		// First draw the game elements.

		graphics.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		graphics.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
		paintTiles(graphics);

		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			graphics.drawRect(p.getX(), p.getY(), 10, 5, Color.YELLOW);
		}
		// First draw the game elements.

		graphics.drawImage(currentSprite, robot.getCenterX() - 61,
				robot.getCenterY() - 63);
		graphics.drawImage(hanim.getImage(), hb.getCenterX() - 48,
				hb.getCenterY() - 48);
		graphics.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48);
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

	private void paintTiles(Graphics graphics) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile tile = (Tile) tilearray.get(i);
			if (tile.type != 0) {
				graphics.drawImage( tile.getTileImage(), tile.getTileX(), tile.getTileY() );
			}
		}
	}

	public void animate() {
		anim.update(10);
		hanim.update(50);
	}

	private void drawGameOverUI( Graphics graphics ) {
		graphics.drawRect(0, 0, 1281, 801, Color.BLACK);
		graphics.drawString( "GAME OVER.", 400, 240, paint2 );
		graphics.drawString( "Tap to return.", 400, 290, paint );
	}

	private void drawPausedUI( Graphics graphics ) {
		// Darken the entire screen, can display the Paused screen.
		graphics.drawARGB(155, 0, 0, 0);

		graphics.drawString( "Resume", 400, 165, paint2 );
		graphics.drawString( "Menu", 400, 360, paint2 );
	}

	private void drawRunningUI( Graphics graphics ) {
		graphics.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
		graphics.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
		graphics.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
		graphics.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);
	}

	private void drawReadyUI( Graphics graphics ) {
		graphics.drawARGB(155, 0, 0, 0);
		graphics.drawString("Tap to Start.", 400, 240, paint);
	}

	@Override
	public void pause() {
		if (gameState == GameState.Running)
			gameState = GameState.Paused;
	}

	@Override
	public void resume() {
		if (gameState == GameState.Paused)
			gameState = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		game.setScreen(new MainMenuScreen(game));
	}

	public static Background getBg1() {
		// TODO Auto-generated method stub
		return bg1;
	}

	public static Background getBg2() {
		// TODO Auto-generated method stub
		return bg2;
	}

	public static Robot getRobot() {
		// TODO Auto-generated method stub
		return robot;
	}

	private void loadMap() {
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		Scanner scanner = new Scanner(GameMain.map);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			// no more lines to read
			if (line == null) {
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}

	}


	private void updateTiles() {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile tile = (Tile) tilearray.get(i);
			tile.update();
		}

	}

}
