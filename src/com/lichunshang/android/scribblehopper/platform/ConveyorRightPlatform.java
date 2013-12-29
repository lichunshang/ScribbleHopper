package com.lichunshang.android.scribblehopper.platform;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scene.GameScene;

public class ConveyorRightPlatform extends BasePlatform{
	
	public ConveyorRightPlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPlatform(){
		this.sprite = new AnimatedSprite(0, 0, scene.getResourcesManager().conveyorPlatformTextureRegion, scene.getVertexBufferObjectManager());
		this.sprite.animate(Const.Plaform.ConveyorRight.ANIME_SPEED);
		this.sprite.setFlippedHorizontal(true);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.ConveyorRight.DENSITY, Const.Plaform.ConveyorRight.ELASTICITY, Const.Plaform.ConveyorRight.FRICTION);
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, Const.Plaform.ConveyorRight.bodyVerticesMKS, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.CONVEYOR_RIGHT;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return sprite.getY() / Const.Physics.PIXEL_TO_METER_RATIO + Const.Plaform.ConveyorRight.bodyVerticesMKS[0].y; 
	}

}