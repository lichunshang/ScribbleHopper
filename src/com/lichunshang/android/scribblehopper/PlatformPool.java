package com.lichunshang.android.scribblehopper;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;


public class PlatformPool extends MultiPool<BasePlatform>{
	
	public BaseScene scene;
	
	public PlatformPool(BaseScene scene){
		super();
		this.registerPool(BasePlatform.PlatformType.REGULAR.ordinal(), new RegularPlatformPool());
	}
	
	public BasePlatform getPlatform(BasePlatform.PlatformType type){
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
			return null;
		}
		
		@Override
		protected void onHandleObtainItem(final BasePlatform platform){
			//TODO
		}
		
		@Override
		protected void onHandleRecycleItem(final BasePlatform platform){
			//TODO
		}
	}
}