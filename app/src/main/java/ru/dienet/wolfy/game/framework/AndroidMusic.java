package ru.dienet.wolfy.game.framework;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

import ru.dienet.wolfy.game.framework.interfaces.Music;

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener {
	private MediaPlayer mediaPlayer;
	private boolean isPrepared = false;

	public AndroidMusic( AssetFileDescriptor assetFileDescriptor ) {
		mediaPlayer = new MediaPlayer();
		try{
			mediaPlayer.setDataSource( assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength() );
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
		} catch ( IOException e ) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void play() {
		if(mediaPlayer.isPlaying()){
			return;
		}
		try{
			synchronized (this){
				if(!isPrepared){
					mediaPlayer.prepare();
				}
				mediaPlayer.start();
			}
		} catch ( IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		if(mediaPlayer.isPlaying()){
			mediaPlayer.stop();
			synchronized (this){
				isPrepared = false;
			}
		}
	}

	@Override
	public void pause() {
		if(mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
	}

	@Override
	public void setLooping( boolean looping ) {
		mediaPlayer.setLooping( looping );
	}

	@Override
	public void setVolume( float volume ) {
		mediaPlayer.setVolume( volume,volume );
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !isPrepared;
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		if( mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
		mediaPlayer.release();
	}

	@Override
	public void seekBegin() {
		mediaPlayer.seekTo( 0 );
	}

	@Override
	public void onCompletion( MediaPlayer mp ) {
		synchronized (this){
			isPrepared = false;
		}
	}

	@Override
	public void onSeekComplete( MediaPlayer mp ) {

	}

	@Override
	public void onPrepared( MediaPlayer mp ) {
		synchronized (this){
			isPrepared = true;
		}
	}

	@Override
	public void onVideoSizeChanged( MediaPlayer mp, int width, int height ) {

	}
}
