package ru.dienet.wolfy.game.framework;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ru.dienet.wolfy.game.framework.interfaces.Input;
import ru.dienet.wolfy.game.framework.interfaces.Input.TouchEvent;

public class SingleTouchHandler implements TouchHandler {

	private boolean isTouched;
	private int touchX;
	private int touchY;
	protected Pool<TouchEvent> touchEventPool;
	protected List<TouchEvent> touchEvents = new ArrayList<>();
	protected List<TouchEvent> touchEventsBuffer = new ArrayList<>();
	protected float scaleX;
	protected float scaleY;

	public SingleTouchHandler( View view, float scaleX, float scaleY ) {
		Pool.PoolObjectsFactory<TouchEvent> touchEventPoolObjectsFactory = new Pool.PoolObjectsFactory<TouchEvent>() {
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<>( touchEventPoolObjectsFactory, 100 );
		view.setOnTouchListener( this );
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public boolean isTouchDown( int pointer ) {
		synchronized (this){
			if(pointer == 0){
				return isTouched;
			}else {
				return false;
			}
		}
	}

	@Override
	public int getTouchX( int pointer ) {
		synchronized (this){
			return touchX;
		}
	}

	@Override
	public int getTouchY( int pointer ) {
		synchronized (this){
			return touchY;
		}
	}

	@Override
	public List<Input.TouchEvent> getTouchEvents() {
		synchronized (this) {
			int length = touchEvents.size();
				for ( int i = 0; i < length; i++ ) {
					touchEventPool.free(touchEvents.get( i )  );
				}
				touchEvents.clear();
				touchEvents.addAll( touchEventsBuffer );
				touchEventsBuffer.clear();
				return touchEvents;

		}
	}

	@Override
	public boolean onTouch( View v, MotionEvent event ) {
		synchronized (this){
			TouchEvent touchEvent = touchEventPool.newObject();
			switch ( event.getAction() ){
				case MotionEvent.ACTION_DOWN:{
					touchEvent.type = TouchEvent.TOUCH_DOWN;
					isTouched = true;
					break;
				}
				case MotionEvent.ACTION_MOVE:{
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					isTouched = true;
					break;
				}
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:{
					touchEvent.type = TouchEvent.TOUCH_UP;
					isTouched = false;
					break;
				}
			}
			touchEvent.x = touchX = (int)(event.getX() * scaleX);
			touchEvent.y = touchY = (int)(event.getY() * scaleY);
			touchEventsBuffer.add(touchEvent);

			return true;
		}
	}
}
