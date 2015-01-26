package ru.dienet.wolfy.game.framework;

import android.graphics.Bitmap;

import ru.dienet.wolfy.game.framework.interfaces.Graphics.ImageFormat;
import ru.dienet.wolfy.game.framework.interfaces.Image;

public class AndroidImage implements Image {

	private Bitmap bitmap;
	private ImageFormat format;

	public AndroidImage( Bitmap bitmap, ImageFormat format ) {
		this.bitmap = bitmap;
		this.format = format;
	}

	public Bitmap getBitmap(){
		return bitmap;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public ImageFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}
}
