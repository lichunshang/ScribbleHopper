package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;

import com.lichunshang.android.scribblehopper.scenes.GameScene;


public class PlatformPool extends MultiPool<BasePlatform>{
	
	public GameScene gameScene;
	public PhysicsWorld physicsWorld;
	
	public PlatformPool(GameScene gameScene){
		super();
		this.gameScene = gameScene;
		this.physicsWorld = gameScene.getPhysicsWorld();
		
		this.registerPool(BasePlatform.PlatformType.REGULAR.ordinal(), new RegularPlatformPool());
		this.registerPool(BasePlatform.PlatformType.BOUNCE.ordinal(), new BouncePlatformPool());
		this.registerPool(BasePlatform.PlatformType.CONVEYOR_LEFT.ordinal(), new ConveyorLeftPlatformPool());
		this.registerPool(BasePlatform.PlatformType.CONVEYOR_RIGHT.ordinal(), new ConveyorRightPlatformPool());
		this.registerPool(BasePlatform.PlatformType.UNSTABLE.ordinal(), new UnstablePlatformPool());
		this.registerPool(BasePlatform.PlatformType.SPIKE.ordinal(), new SpikePlatformPool());
	}
	
	public BasePlatform initPlatform(BasePlatform.PlatformType type){
		return this.obtainPoolItem(type.ordinal());
	}
	
	public void recyclePlatform(BasePlatform platform){
		this.recyclePoolItem(platform.getType().ordinal(), platform);
	}

	// -------------------------------------------
	// Type Specific Platform Pools
	// -------------------------------------------
	
	public class RegularPlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform onAllocatePoolItem(){
			return new RegularPlatform(gameScene);
		}
	}
	
	public class BouncePlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform onAllocatePoolItem(){
			return new BouncePlatform(gameScene);
		}
	}
	
	public class ConveyorLeftPlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform onAllocatePoolItem(){
			return new ConveyorLeftPlatform(gameScene);
		}
	}
	
	public class ConveyorRightPlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform onAllocatePoolItem(){
			return new ConveyorRightPlatform(gameScene);
		}
	}
	
	public class UnstablePlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform onAllocatePoolItem(){
			return new UnstablePlatform(gameScene);
		}
	}
	
	public class SpikePlatformPool extends AbstractPlaformPool{
		@Override
		protected BasePlatform onAllocatePoolItem(){
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
		
		@Override
		protected abstract BasePlatform onAllocatePoolItem();
		
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