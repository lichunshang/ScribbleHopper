package com.lichunshang.android.scribblehopper.platform;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;

import com.lichunshang.android.scribblehopper.scene.GameScene;


public class PlatformPool extends MultiPool<BasePlatform>{
	
	private GameScene gameScene;
	private LinkedList<BasePlatform> activePlaforms;
	
	public PlatformPool(GameScene gameScene){
		super();
		this.gameScene = gameScene;
		activePlaforms = new LinkedList<BasePlatform>();
		
		this.registerPool(BasePlatform.PlatformType.REGULAR.ordinal(), new RegularPlatformPool());
		this.registerPool(BasePlatform.PlatformType.BOUNCE.ordinal(), new BouncePlatformPool());
		this.registerPool(BasePlatform.PlatformType.CONVEYOR_LEFT.ordinal(), new ConveyorLeftPlatformPool());
		this.registerPool(BasePlatform.PlatformType.CONVEYOR_RIGHT.ordinal(), new ConveyorRightPlatformPool());
		this.registerPool(BasePlatform.PlatformType.UNSTABLE.ordinal(), new UnstablePlatformPool());
		this.registerPool(BasePlatform.PlatformType.SPIKE.ordinal(), new SpikePlatformPool());
	}
	
	public BasePlatform initPlatform(BasePlatform.PlatformType type){
		BasePlatform platform = this.obtainPoolItem(type.ordinal());
		activePlaforms.add(platform);
		return platform;
	}
	
	public void recyclePlatform(BasePlatform platform){
		//this is to accommodate the recycleAllActivePlaform method. When the platform 
		//is removed from the queue in recycleAllActivePlaform, it won't be removed again
		Iterator<BasePlatform> iterator = activePlaforms.iterator();
		while (iterator.hasNext()){
			if (iterator.next() == platform){
				iterator.remove();
				break;
			}
		}
		this.recyclePoolItem(platform.getType().ordinal(), platform);
	}
	
	public void recycleAllActivePlaform(){
		
		while (!activePlaforms.isEmpty()){
			BasePlatform platform = activePlaforms.poll();
			platform.recyclePlatform();
		}
	}

	// -------------------------------------------
	// Type Specific Platform Pools
	// -------------------------------------------
	
	public class RegularPlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform getNewPlaform(){
			return new RegularPlatform(gameScene);
		}
	}
	
	public class BouncePlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform getNewPlaform(){
			return new BouncePlatform(gameScene);
		}
	}
	
	public class ConveyorLeftPlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform getNewPlaform(){
			return new ConveyorLeftPlatform(gameScene);
		}
	}
	
	public class ConveyorRightPlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform getNewPlaform(){
			return new ConveyorRightPlatform(gameScene);
		}
	}
	
	public class UnstablePlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform getNewPlaform(){
			return new UnstablePlatform(gameScene);
		}
	}
	
	public class SpikePlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform getNewPlaform(){
			return new SpikePlatform(gameScene);
		}
	}
	
	//----------------------------------------------
	// Abstract Class to factor out common methods
	//----------------------------------------------
	public abstract class AbstractPlaformPool extends GenericPool<BasePlatform>{
		public AbstractPlaformPool(){
			super();
		}
		
		protected abstract BasePlatform getNewPlaform();
		
		@Override
		protected BasePlatform onAllocatePoolItem(){
			return getNewPlaform();
		};
		
		@Override
		protected void onHandleObtainItem(final BasePlatform platform){
			platform.reset();
		}
		
		@Override
		protected void onHandleRecycleItem(final BasePlatform platform){
			platform.disable();
		}
	}
}