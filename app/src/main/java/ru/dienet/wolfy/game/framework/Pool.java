package ru.dienet.wolfy.game.framework;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
	public interface PoolObjectsFactory<T>{
		public T createObject();
	}
	private final List<T> freeObjects;
	private final PoolObjectsFactory<T> poolObjectsFactory;
	private final int maxSize;

	public Pool( PoolObjectsFactory<T> poolObjectsFactory, int maxSize ) {
		this.freeObjects = new ArrayList<T>( maxSize );
		this.poolObjectsFactory = poolObjectsFactory;
		this.maxSize = maxSize;
	}

	public T newObject(){
		T object = null;
		int freeObjectsSize = freeObjects.size();
		if( freeObjectsSize == 0 ){
			object = poolObjectsFactory.createObject();
		}else {
			object = freeObjects.remove(freeObjectsSize - 1 );
		}
		return object;
	}

	public void free( T object ){
		if(freeObjects.size() < maxSize){
			freeObjects.add( object );
		}
	}
}
