package ru.dienet.wolfy.game.game;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ru.dienet.wolfy.game.R;
import ru.dienet.wolfy.game.framework.AndroidGame;
import ru.dienet.wolfy.game.framework.Screen;


public class GameMain extends AndroidGame {
	public static String map;
	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {
		if ( firstTimeCreate ) {
			Assets.load( this );
			firstTimeCreate = false;
		}

		InputStream is = getResources().openRawResource( R.raw.map1 );
		map = convertStreamToString( is );

		return new SplashLoadingScreen( this );
		//return new LoadingScreen(this);
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		getCurrentScreen().backButton();
	}

	private static String convertStreamToString( InputStream is ) {

		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( is ) );
		StringBuilder stringBuilder = new StringBuilder();

		String line = null;
		try {
			while ( (line = bufferedReader.readLine()) != null ) {
				stringBuilder.append( line ).append( "\n" );
			}
		} catch ( IOException e ) {
			Log.w( "LOG", e.getMessage() );
		} finally {
			try {
				is.close();
			} catch ( IOException e ) {
				Log.w( "LOG", e.getMessage() );
			}
		}
		return stringBuilder.toString();
	}

	@Override
	public void onResume() {
		super.onResume();
		Assets.theme.play();
	}

	@Override
	public void onPause() {
		super.onPause();
		Assets.theme.pause();
	}
}
