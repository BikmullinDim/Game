package ru.dienet.wolfy.game.framework;

import android.view.View;

import java.util.List;

import ru.dienet.wolfy.game.framework.interfaces.Input;

public interface TouchHandler extends View.OnTouchListener{

	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<Input.TouchEvent> getTouchEvents();
}
