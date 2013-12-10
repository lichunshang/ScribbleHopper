package com.lichunshang.android.scribblehopper.platforms;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;


public abstract class BasePlatform{
	
	protected GameScene scene;
	protected AnimatedSprite sprite;
	protected PhysicsWorld physicsWorld;
	protected Body physicsBody;
	protected Random random = new Random();
	protected PlatformPool pool;
	protected boolean recycled;
	
	public static enum PlatformType{
		REGULAR,
		BOUNCE,
		CONVEYOR_LEFT,
		CONVEYOR_RIGHT,
		UNSTABLE,
		SPIKE,
	}
	
	public BasePlatform(GameScene scene){
		this.scene = scene;
		this.physicsWorld = scene.getPhysicsWorld();
		this.pool = scene.getPlatformPool();
		
		createPlatform(); //to create a sprite
		
		float posX = generatePosX();
		this.sprite.setPosition(posX, 0 - sprite.getHeight() / 2);
		this.recycled = false;
		scene.attachPlaform(sprite);
		
		createPhysics();
	}
	
	private void createPhysics(){
		createPhysicsBody();
		physicsBody.setUserData(this);
		setSpeed(scene.getPlatformSpeed());
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				super.onUpdate(pSecondsElapsed);
				if (!recycled && sprite.getY() > (scene.getCamera().getHeight() + sprite.getHeight() / 2)){
					recyclePlatform();
				}
				setSpeed(scene.getPlatformSpeed());
				BasePlatform.this.onUpdate();
			}
		});
	}
	
	private float generatePosX(){
		final float LEFT_BOUND = Const.GameScene.LEFT_RIGHT_MARGIN + sprite.getWidth() / 2;
		final float RIGHT_BOUND = scene.getCamera().getWidth() - Const.GameScene.LEFT_RIGHT_MARGIN - sprite.getWidth() / 2;
		
		float posX = random.nextFloat() * (RIGHT_BOUND - LEFT_BOUND) + LEFT_BOUND;
		
		return posX;
	}
	
	public AnimatedSprite getSprite(){
		return sprite;
	}
	
	public Body getPhyiscsBody(){
		return physicsBody;
	}
	
	public void reset(){
		recycled = false;
		physicsBody.setActive(true);
		setPhysicsBodySensor(false);
		this.physicsBody.setTransform(generatePosX() / Const.Physics.PIXEL_TO_METER_RATIO, 0, 0);
		this.sprite.setIgnoreUpdate(false);
		setSpeed(scene.getPlatformSpeed());
		
		scene.registerUpdateHandler(new TimerHandler(Const.COMMON_DELAY_SHORT / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.unregisterUpdateHandler(pTimerHandler);
				BasePlatform.this.sprite.setVisible(true);
			}
		}));
	}
	
	public void disable(){
		physicsBody.setActive(false);
		this.sprite.setIgnoreUpdate(true);
		this.sprite.setVisible(false);
		setPhysicsBodySensor(true);
	}
	
	public void setSpeed(float speed){
		this.physicsBody.setLinearVelocity(0, speed);
	}
	
	public void setPosition(float posX, float posY){
		this.physicsBody.setTransform(posX / Const.Physics.PIXEL_TO_METER_RATIO, posY / Const.Physics.PIXEL_TO_METER_RATIO, 0);
	}
	
	public void recyclePlatform(){
		pool.recyclePlatform(BasePlatform.this);
		recycled = true;
	}
	
	public void setPhysicsBodySensor(boolean isSensor){
		physicsBody.getFixtureList().get(0).setSensor(isSensor);
	}
	
	public boolean isRecycled(){
		return recycled;
	}
	
	/**
	 * Assumes that the body is exactly in the center, override this method
	 * if this assumption is not true for a platform
	 * @return the platforms's body top position
	 */
	public abstract float getBodyTopYMKS();
	
	public abstract void createPlatform();
	
	public abstract PlatformType getType();
	
	public abstract void onUpdate();
	
	public abstract void createPhysicsBody();
}