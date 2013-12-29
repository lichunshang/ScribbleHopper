package com.lichunshang.android.scribblehopper.platform;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scene.GameScene;

public class SpikePlatform extends BasePlatform{
	
	public SpikePlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPlatform(){
		this.sprite = new AnimatedSprite(0, 0, scene.getResourcesManager().spikePlaformTextureRegion, scene.getVertexBufferObjectManager());
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Spike.DENSITY, Const.Plaform.Spike.ELASTICITY, Const.Plaform.Spike.FRICTION);
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, Const.Plaform.Spike.bodyVerticesMKS, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.SPIKE;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return sprite.getY() / Const.Physics.PIXEL_TO_METER_RATIO + Const.Plaform.Spike.bodyVerticesMKS[0].y; 
	}
}