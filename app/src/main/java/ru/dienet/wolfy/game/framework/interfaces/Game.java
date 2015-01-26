package ru.dienet.wolfy.game.framework.interfaces;

import ru.dienet.wolfy.game.framework.Screen;

public interface Game {

	public Audio getAudio();
	public Input getInput();
	public FileIO getFileIO();
	public Graphics getGraphics();
	public void setScreen( Screen screen );
	public Screen getCurrentScreen();
	public Screen getInitScreen();

}
