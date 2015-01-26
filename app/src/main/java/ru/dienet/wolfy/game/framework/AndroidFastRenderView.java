package ru.dienet.wolfy.game.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable  {

	private AndroidGame androidGame;
	private Bitmap frameBuffer;

	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	public AndroidFastRenderView( AndroidGame androidGame, Bitmap frameBuffer ) {
		super( androidGame );
		this.androidGame = androidGame;
		this.frameBuffer = frameBuffer;
		this.holder = getHolder();
	}

	public void resume(){
		running = true;
		renderThread = new Thread( this );
		renderThread.start();
	}

	public void pause(){
		running = false;
		while ( true ){
			try{
				renderThread.join();
				break;
			} catch ( InterruptedException ignored ) {

			}
		}
	}

	@Override
	public void run() {
		Rect dstRect = new Rect();
		Canvas canvas;
		long startTime = System.nanoTime();
		float deltaTime;
		while ( running ){
			if( !holder.getSurface().isValid() ){
				continue;
			}
			deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
			startTime = System.nanoTime();
			if (deltaTime > 3.15){
				deltaTime = (float) 3.15;
			}
			androidGame.getCurrentScreen().update( deltaTime );
			androidGame.getCurrentScreen().paint( deltaTime );

			canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap( frameBuffer, null, dstRect,null );
			holder.unlockCanvasAndPost( canvas );
		}
	}
}
