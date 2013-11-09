package com.lichunshang.android.scribblehopper;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RegularPlatform extends BasePlatform{
	
	public RegularPlatform(BaseScene scene, PhysicsWorld physicsWorld){
		super(scene, physicsWorld);
		this.physicsWorld = physicsWorld;
		this.scene = scene;
		
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 500, 30, scene.vertexBufferObjectManager);
		
		float posX = generatePosX();
		this.sprite.setPosition(posX, 0 - sprite.getHeight() / 2);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.REGULAR;
	}
	
	@Override
	public void createPhysics(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(ProjectConstants.Plaform.Regular.DENSITY, ProjectConstants.Plaform.Regular.ELASTICITY, ProjectConstants.Plaform.Regular.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				super.onUpdate(pSecondsElapsed);
				//TODO
				
			}
		});
	}
	
	@Override
	public void reset(float speed){
		
	}
}