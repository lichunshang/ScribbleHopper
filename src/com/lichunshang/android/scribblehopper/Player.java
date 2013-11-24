package com.lichunshang.android.scribblehopper;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.platforms.BasePlatform;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class Player{
	
	private AnimatedSprite sprite;
	private GameScene scene;
	
	private Body physicsBody;
	private PhysicsWorld physicsWorld;
	
	private AnimationState animationState = AnimationState.IDLE;
	private boolean canSwitchAnimation = true;
	
	private BasePlatform currentPlatform = null;
	private BasePlatform lastStayedPlatform = null;
	
	private int health = Const.Player.MAX_HEALTH;
	
	public Player(float posX, float posY, GameScene scene, PhysicsWorld physicsWorld){
		
		this.scene = scene;
		this.physicsWorld = physicsWorld;
		this.sprite = new AnimatedSprite(posX, posY, this.scene.getResourcesManager().gamePlayerTextureRegion, this.scene.getVertexBufferObjectManager());
		
		scene.attachChild(this.sprite);
		createPhysics();
		animateIdle();
	}
	
	// ------------------------------------------------
	// PLAYER UPDATE LOOP
	// ------------------------------------------------
	private void onUpdate(){
		float velocityX = physicsBody.getLinearVelocity().x;
		move(scene.getAccelerometerValue());
		
		updatePlatformEffect();
		
			if (Math.abs(velocityX) > Const.Player.IDLE_SWITCH_VELOCITY){
				
				sprite.setFlippedHorizontal(velocityX < 0); 
				if (canSwitchAnimation && Math.abs(velocityX) < Const.Player.WALK_SWITCH_VELOCITY && animationState != AnimationState.WALK){
					animateWalk();
				}
				else if (canSwitchAnimation && Math.abs(velocityX) > Const.Player.WALK_SWITCH_VELOCITY && animationState != AnimationState.RUN){
					animateRun();
				}
			}
			else {
				if (canSwitchAnimation && animationState != AnimationState.IDLE){
					animateIdle();
				}
			}
	}
	
	private void createPhysics(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Player.DENSITY, Const.Player.ELASTICITY, Const.Player.FRICTION);
		
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, Const.Player.bodyVerticesMKS, BodyType.DynamicBody, fixtureDef);
		physicsBody.setUserData(this);
		physicsBody.setBullet(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				super.onUpdate(pSecondsElapsed);
				Player.this.onUpdate();
			}
		});
	}

	private void move(float accelerometerVal){
		
		//check if the acceleration and velocity are the same sign
		if ((accelerometerVal < 0) != (physicsBody.getLinearVelocity().x < 0)){ // different sign
				this.physicsBody.applyForce(new Vector2(accelerometerVal * Const.Player.DEACCELERATE_MULTIPLY_FACTOR, 0),  new Vector2(0, 0));
		}
		else{// same  sign
			//limit maximum speed depends on the value of accelerometer
			if (Math.abs(physicsBody.getLinearVelocity().x) >  Math.abs(accelerometerVal * Const.Player.ACCELEROMETER_MULTIPLY_FACTOR)){ 
				physicsBody.setLinearVelocity(physicsBody.getLinearVelocity().x, physicsBody.getLinearVelocity().y);
			}
			else{
				this.physicsBody.applyForce(new Vector2(accelerometerVal * Const.Player.ACCELERATE_MULTIPLY_FACTOR, 0),  new Vector2(0, 0));
			}
		}
	}
	
	// ------------------------------------------
	// Platform Effects
	// ------------------------------------------
	
	private void updatePlatformEffect(){
		if (getCurrentPlatformType() == BasePlatform.PlatformType.CONVEYOR_LEFT){
			physicsBody.setTransform((sprite.getX() - Const.Plaform.ConveyorLeft.DISPLACEMENT_RATE) / Const.Physics.PIXEL_TO_METER_RATIO, physicsBody.getPosition().y, 0);
		}
		else if (getCurrentPlatformType() == BasePlatform.PlatformType.CONVEYOR_RIGHT){
			physicsBody.setTransform((sprite.getX() + Const.Plaform.ConveyorRight.DISPLACEMENT_RATE) / Const.Physics.PIXEL_TO_METER_RATIO, physicsBody.getPosition().y, 0);
		}
	}
	
	// -------------------------------------------------------------
	// ANIMATION
	// -------------------------------------------------------------
	public static enum AnimationState{
		IDLE, RUN, WALK, LAND
	}
	
	public void animateIdle(){
		stopAnimation();
		sprite.animate(Const.Player.IDLE_ANIME_SPEED, Const.Player.IDLE_INDEX_START, Const.Player.IDLE_INDEX_END, true);
		animationState = AnimationState.IDLE;
		pauseAnimationSwitch(Const.Player.ANIME_DISABLE_TIME_SHORT);
	}
	
	public void animateRun(){
		stopAnimation();
		sprite.animate(Const.Player.RUN_ANIME_SPEED, Const.Player.RUN_INDEX_START, Const.Player.RUN_INDEX_END, true);
		animationState = AnimationState.RUN;
		pauseAnimationSwitch(Const.Player.ANIME_DISABLE_TIME_LONG);
	}
	
	public void animateWalk(){
		stopAnimation();
		sprite.animate(Const.Player.WALK_ANIME_SPEED, Const.Player.WALK_INDEX_START, Const.Player.WALK_INDEX_END, true);
		animationState = AnimationState.WALK;
		pauseAnimationSwitch(Const.Player.ANIME_DISABLE_TIME_LONG);
	}
	
	public void animateLand(){
		int pauseTime = 0;
		for (int i = 0; i < Const.Player.LAND_ANIME_SPEED.length; i++){pauseTime += Const.Player.LAND_ANIME_SPEED[i];}
		animationState = AnimationState.LAND;
		
		stopAnimation();
		sprite.animate(Const.Player.LAND_ANIME_SPEED, Const.Player.LAND_INDEX_START, Const.Player.LAND_INDEX_END, false);
		pauseAnimationSwitch(pauseTime);
		physicsBody.setLinearVelocity(physicsBody.getLinearVelocity().x * Const.Player.LAND_SPEED_REDUCE_FACTOR, physicsBody.getLinearVelocity().y);
	}
	
	public void stopAnimation(){
		sprite.stopAnimation();
	}
	
	private void pauseAnimationSwitch(int milliseconds){
		canSwitchAnimation = false;
		scene.getEngine().registerUpdateHandler(new TimerHandler(milliseconds / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.getEngine().unregisterUpdateHandler(pTimerHandler);
				canSwitchAnimation = true;
			}
		}));
	}
	
	// ------------------------------------------
	// GETTER and SETTERS
	// ------------------------------------------
	
	public int getHealth(){
		return health;
	}
	
	public void increaseHealth(int inrement){
		health += inrement;
		if (health > Const.Player.MAX_HEALTH){
			health = Const.Player.MAX_HEALTH;
		}
	}
	
	public void decreaseHealth(int decrement){
		health -= decrement;
		if (health < 0){
			health = 0;
		}
	}
	
	public boolean isAlive(){
		return health > 0;
	}
	
	public AnimatedSprite getSprite(){
		return sprite;
	}
	
	public float getBodyBottomYMKS(){
		return physicsBody.getPosition().y + Const.Player.bodyVerticesMKS[Const.Player.bodyVerticesMKS.length - 1].y;
	}
	
	public Body getPhysicsBody(){
		return physicsBody;
	}
	
	public void setCurrentPlatform(BasePlatform platform){
		if (currentPlatform != null)
			lastStayedPlatform = currentPlatform;
		currentPlatform = platform;
	}
	
	public BasePlatform getCurrentPlaform(){
		return currentPlatform;
	}
	
	public BasePlatform.PlatformType getCurrentPlatformType(){
		if (currentPlatform == null){
			return null;
		}
		return currentPlatform.getType();
	}
	
	//should be called after the current platform has just been set
	public boolean isDifferentPlatform(){
		return currentPlatform != lastStayedPlatform;
	}
}