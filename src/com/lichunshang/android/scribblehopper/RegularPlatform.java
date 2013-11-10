package com.lichunshang.android.scribblehopper;

import org.andengine.entity.primitive.Rectangle;

public class RegularPlatform extends BasePlatform{
	
	public RegularPlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 500, 30, scene.vertexBufferObjectManager);
	}
	
	@Override
	public void setBodyUserData(){
		physicsBody.setUserData(this);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.REGULAR;
	}
	
	@Override
	public void onUpdate(){
		
	}
}