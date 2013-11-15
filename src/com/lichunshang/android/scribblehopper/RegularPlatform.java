package com.lichunshang.android.scribblehopper;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RegularPlatform extends BasePlatform{
	
	public RegularPlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 300, 30, scene.vertexBufferObjectManager);
		this.sprite.setColor(0,0,0);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Regular.DENSITY, Const.Plaform.Regular.ELASTICITY, Const.Plaform.Regular.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.REGULAR;
	}
	
	@Override
	public void onUpdate(){

	}
}