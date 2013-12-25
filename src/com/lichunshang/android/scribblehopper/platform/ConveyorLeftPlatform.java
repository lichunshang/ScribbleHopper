package com.lichunshang.android.scribblehopper.platform;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scene.GameScene;

public class ConveyorLeftPlatform extends BasePlatform{
	
	public ConveyorLeftPlatform(GameScene scene){
		super(scene);
	}
	
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPlatform(){
		this.sprite = new AnimatedSprite(0, 0, scene.getResourcesManager().conveyorPlatformTextureRegion, scene.getVertexBufferObjectManager());
		this.sprite.animate(Const.Plaform.ConveyorLeft.ANIME_SPEED);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.ConveyorLeft.DENSITY, Const.Plaform.ConveyorLeft.ELASTICITY, Const.Plaform.ConveyorLeft.FRICTION);
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, Const.Plaform.ConveyorLeft.bodyVerticesMKS, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.CONVEYOR_LEFT;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return physicsBody.getPosition().y + sprite.getHeight() / 2f / Const.Physics.PIXEL_TO_METER_RATIO;
	}
}