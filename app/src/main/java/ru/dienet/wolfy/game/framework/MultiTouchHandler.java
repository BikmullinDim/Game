package ru.dienet.wolfy.game.framework;

import android.view.MotionEvent;
import android.view.View;

import ru.dienet.wolfy.game.framework.interfaces.Input.TouchEvent;

public class MultiTouchHandler extends SingleTouchHandler {

	private static final int MAX_TOUCHPOINTS = 10;

	boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
	int[] touchX = new int[MAX_TOUCHPOINTS];
	int[] touchY = new int[MAX_TOUCHPOINTS];
	int[] id = new int[MAX_TOUCHPOINTS];


	public MultiTouchHandler( View view, float scaleX, float scaleY ) {
		super( view,  scaleX,  scaleY);
	}

	@Override
	public boolean onTouch( View v, MotionEvent event ) {
		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerCount = event.getPointerCount();
			for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
				if (i >= pointerCount) {
					isTouched[i] = false;
					id[i] = -1;
					continue;
				}
				int pointerId = event.getPointerId(i);
				if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
					// if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
					// point
					continue;
				}
				switch (action) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_POINTER_DOWN:
						addTouchEventToBuffer(TouchEvent.TOUCH_DOWN, pointerId, i, event);
						isTouched[i] = true;
						id[i] = pointerId;
						break;

					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
					case MotionEvent.ACTION_CANCEL:
						addTouchEventToBuffer( TouchEvent.TOUCH_UP, pointerId, i, event );
						isTouched[i] = false;
						id[i] = -1;
						break;

					case MotionEvent.ACTION_MOVE:
						addTouchEventToBuffer(TouchEvent.TOUCH_DRAGGED, pointerId, i, event);
						isTouched[i] = true;
						id[i] = pointerId;
						break;
				}
			}
			return true;
		}
	}

	private void addTouchEventToBuffer( int touchType, int pointerId, int i, MotionEvent event ) {
		TouchEvent touchEvent = touchEventPool.newObject();
		touchEvent.type = touchType;
		touchEvent.pointer = pointerId;
		touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
		touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
		touchEventsBuffer.add(touchEvent);
	}

	// returns the index for a given pointerId or -1 if no index.
	private int getIndex(int pointerId) {
		for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
			if (id[i] == pointerId) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isTouchDown( int pointer ) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS)
				return false;
			else
				return isTouched[index];
		}
	}

	@Override
	public int getTouchX( int pointer ) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}

	@Override
	public int getTouchY( int pointer ) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}
}
