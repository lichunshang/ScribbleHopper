package com.lichunshang.android.scribblehopper;

import java.util.Random;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;


public abstract class BasePlatform{
	
	protected GameScene scene;
	protected Rectangle sprite;
	protected PhysicsWorld physicsWorld;
	protected Body physicsBody;
	protected Random random = new Random();
	protected PlatformPool pool;
	protected boolean recycled;
	
	public static enum PlatformType{
		REGULAR,
		BOUNCE,
	}
	
	public BasePlatform(GameScene scene){
		this.scene = scene;
		this.physicsWorld = scene.getPhysicsWorld();
		this.pool = scene.getPlatformPool();
		
		createPlatform(); //to create a sprite
		
		float posX = generatePosX();
		this.sprite.setPosition(posX, 0 - sprite.getHeight() / 2);
		this.recycled = false;
		scene.attachChild(sprite);
		
		createPhysicsBody();
		createPhysics();
	}
	
	public void createPhysics(){
		physicsBody.setUserData(this);
		setSpeed(scene.getPlatformSpeed());
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				super.onUpdate(pSecondsElapsed);
				if (!recycled && sprite.getY() > (scene.camera.getHeight() + sprite.getHeight() / 2)){
					pool.recyclePlatform(BasePlatform.this);
					recycled = true;
				}
				setSpeed(scene.getPlatformSpeed());
				BasePlatform.this.onUpdate();
			}
		});
	}
	
	public float generatePosX(){
		final float LEFT_BOUND = Const.GameScene.LEFT_RIGHT_MARGIN + sprite.getWidth() / 2;
		final float RIGHT_BOUND = scene.camera.getWidth() - Const.GameScene.LEFT_RIGHT_MARGIN - sprite.getWidth() / 2;
		
		float posX = random.nextFloat() * (RIGHT_BOUND - LEFT_BOUND) + LEFT_BOUND;
		
		return posX;
	}
	
	public Rectangle getSprite(){
		return sprite;
	}
	
	public Body getPhyiscsBody(){
		return physicsBody;
	}
	
	public void reset(){
		recycled = false;
		this.sprite.setIgnoreUpdate(false);
		this.sprite.setVisible(true);
		setSpeed(scene.getPlatformSpeed());
		this.physicsBody.setTransform(generatePosX() / Const.Physics.PIXEL_TO_METER_RATIO, 0, 0);
	}
	
	public void disable(){
		setSpeed(0);
		this.sprite.setIgnoreUpdate(true);
		this.sprite.setVisible(false);
	}
	
	public void setSpeed(float speed){
		this.physicsBody.setLinearVelocity(0, speed);
	}
	
	public void setPosition(float posX, float posY){
		this.physicsBody.setTransform(posX / Const.Physics.PIXEL_TO_METER_RATIO, posY / Const.Physics.PIXEL_TO_METER_RATIO, 0);
	}
	
	public abstract void createPlatform();
	
	public abstract PlatformType getType();
	
	public abstract void onUpdate();
	
	public abstract void createPhysicsBody();
}