package com.lichunshang.android.scribblehopper;

import java.util.Random;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;


public abstract class BasePlatform{
	
	protected BaseScene scene;
	protected Rectangle sprite;
	protected PhysicsWorld physicsWorld;
	protected Body physicsBody;
	protected Random random;
	
	public static enum PlatformType{
		REGULAR,
		BOUNCE,
	}
	
	public BasePlatform(BaseScene scene, PhysicsWorld physicsWorld){
		this.scene = scene;
		this.physicsWorld = physicsWorld;
		this.random = new Random();
		
		createPlatform();
		createPhysics();
	}
	
	public float generatePosX(){
		final float LEFT_BOUND = ProjectConstants.GameScene.LEFT_RIGHT_MARGIN + sprite.getWidth() / 2;
		final float RIGHT_BOUND = scene.camera.getWidth() - ProjectConstants.GameScene.LEFT_RIGHT_MARGIN - sprite.getWidth() / 2;
		
		float posX = random.nextFloat() * (RIGHT_BOUND - LEFT_BOUND) + LEFT_BOUND;
		
		return posX;
	}
	
	public abstract void createPlatform();
	
	public abstract void createPhysics();
	
	public abstract PlatformType getType();
	
	public abstract void reset(float speed);
}