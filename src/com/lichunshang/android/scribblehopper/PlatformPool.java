package com.lichunshang.android.scribblehopper;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;


public class PlatformPool extends MultiPool<BasePlatform>{
	
	public GameScene gameScene;
	public PhysicsWorld physicsWorld;
	
	public PlatformPool(GameScene gameScene){
		super();
		this.gameScene = gameScene;
		this.physicsWorld = gameScene.getPhysicsWorld();
		
		this.registerPool(BasePlatform.PlatformType.REGULAR.ordinal(), new RegularPlatformPool());
	}
	
	public BasePlatform initPlatform(BasePlatform.PlatformType type){
		return this.obtainPoolItem(type.ordinal());
	}
	
	public void recyclePlatform(BasePlatform platform){
		this.recyclePoolItem(platform.getType().ordinal(), platform);
	}
	
	//-----------------------------------------
	// Regular Platform Pool
	//-----------------------------------------
	public class RegularPlatformPool extends GenericPool<BasePlatform>{
		
		public RegularPlatformPool(){
			super();
		}
		
		@Override
		protected BasePlatform onAllocatePoolItem(){
			//TODO
			return new RegularPlatform(gameScene);
		}
		
		@Override
		protected void onHandleObtainItem(final BasePlatform platform){
			platform.reset(gameScene.getPlatformSpeed());
		}
		
		@Override
		protected void onHandleRecycleItem(final BasePlatform platform){
			platform.disable();
		}
	}
}