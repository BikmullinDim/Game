package ru.dienet.wolfy.game.framework;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import ru.dienet.wolfy.game.framework.interfaces.Audio;
import ru.dienet.wolfy.game.framework.interfaces.FileIO;
import ru.dienet.wolfy.game.framework.interfaces.Game;
import ru.dienet.wolfy.game.framework.interfaces.Graphics;
import ru.dienet.wolfy.game.framework.interfaces.Input;

public abstract class AndroidGame extends Activity implements Game {

	private AndroidFastRenderView renderView;
	private Graphics graphics;
	private Audio audio;
	private Input input;
	private FileIO fileIO;
	private Screen screen;
	private PowerManager.WakeLock wakeLock;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		requestWindowFeature( Window.FEATURE_NO_TITLE );
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN );
		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

		Point windowSize = new Point();
		getWindow().getWindowManager().getDefaultDisplay().getSize( windowSize );
		/*int frameBufferWidth = isPortrait ? windowSize.x: windowSize.y;
		int frameBufferHeight = isPortrait ? windowSize.y: windowSize.x;*/
		int frameBufferWidth = isPortrait ? 800: 1280;
		int frameBufferHeight = isPortrait ? 1280: 800;

		float scaleY = ( float )frameBufferWidth/windowSize.y;
		float scaleX = ( float )frameBufferWidth/windowSize.x;

		Bitmap frameBuffer = Bitmap.createBitmap( frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565 );
		renderView = new AndroidFastRenderView( this, frameBuffer );
		graphics = new AndroidGraphics( getAssets(),frameBuffer );
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getInitScreen();

		setContentView( renderView );

		PowerManager powerManager = (PowerManager)getSystemService( Context.POWER_SERVICE );
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WolfyGame" );


	}

	@Override
	protected void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		if(isFinishing()){
			screen.dispose();
		}
	}

	@Override
	public Audio getAudio() {
		return audio;
	}

	@Override
	public Input getInput() {
		return input;
	}

	@Override
	public FileIO getFileIO() {
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		return graphics;
	}

	@Override
	public void setScreen( Screen screen ) {
		if(screen == null ){
			throw  new IllegalStateException( "screen must be not null!" );

		}
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update( 0 );
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen() {
		return screen;
	}

}
