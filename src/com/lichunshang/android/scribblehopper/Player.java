package com.lichunshang.android.scribblehopper;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Player{
	
	private AnimatedSprite sprite;
	private BaseScene scene;
	
	private Body physicsBody;
	private PhysicsWorld physicsWorld;
	
	private AnimationState animationState = AnimationState.IDLE;
	private boolean canSwitchAnimation = true;
	
	
	public Player(float posX, float posY, BaseScene scene, PhysicsWorld physicsWorld){
		
		this.scene = scene;
		this.physicsWorld = physicsWorld;
		this.sprite = new AnimatedSprite(posX, posY, this.scene.resourcesManager.gamePlayerTextureRegion, this.scene.vertexBufferObjectManager);
		
		scene.attachChild(this.sprite);
		createPhysics();
		animateIdle();
	}
	
	private void createPhysics(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Player.DENSITY, Const.Player.ELASTICITY, Const.Player.FRICTION);
		
		Vector2 [] vertices = {
				new Vector2(0f / Const.Physics.PIXEL_TO_METER_RATIO, -10f / Const.Physics.PIXEL_TO_METER_RATIO),
				new Vector2(-3f / Const.Physics.PIXEL_TO_METER_RATIO, -62.5f / Const.Physics.PIXEL_TO_METER_RATIO),
				new Vector2(3f / Const.Physics.PIXEL_TO_METER_RATIO, -62.5f / Const.Physics.PIXEL_TO_METER_RATIO),
		};
		
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, vertices, BodyType.DynamicBody, fixtureDef);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				super.onUpdate(pSecondsElapsed);
				Player.this.onUpdate();
			}
		});
	}
	
	public void moveWithLinearVelocity(float accelerometerVal){
		
		float runOffset = 0;
		
		if (animationState == AnimationState.RUN){
			runOffset = Const.Player.RUN_ANIME_OFFSET;
			if (accelerometerVal < 0)
				runOffset *= -1;
		}
		
		this.physicsBody.setLinearVelocity(accelerometerVal * Const.Player.ACCELEROMETER_MULTIPLY_FACTOR + runOffset, physicsBody.getLinearVelocity().y);
	}
	
	public void moveWithAppliedForce(float accelerometerVal){
		
		//check if the acceleration and velocity are the same sign
		if ((accelerometerVal < 0) != (physicsBody.getLinearVelocity().x < 0)){ // different sign
				this.physicsBody.applyForce(new Vector2(accelerometerVal * Const.Player.DEACCELERATE_MULTIPLY_FACTOR, 0),  new Vector2(0, 0));
		}
		else{// same  sign
			if (Math.abs(physicsBody.getLinearVelocity().x) >  Const.Player.MAX_SPEED_ALLOWED_WHEN_ACCELERATE){
				physicsBody.setLinearVelocity(physicsBody.getLinearVelocity().x, physicsBody.getLinearVelocity().y);
			}
			else{
				this.physicsBody.applyForce(new Vector2(accelerometerVal * Const.Player.ACCELERATE_MULTIPLY_FACTOR, 0),  new Vector2(0, 0));
			}
		}
	}

	//UPDATE LOOP
	public void onUpdate(){
		
		float velocityX = physicsBody.getLinearVelocity().x;
		
		if (canSwitchAnimation){
			if (Math.abs(velocityX) > Const.Player.IDLE_SWITCH_VELOCITY){
				
				sprite.setFlippedHorizontal(velocityX < 0); 
				
				if (Math.abs(velocityX) < Const.Player.WALK_SWITCH_VELOCITY && animationState != AnimationState.WALK){
					animationState = AnimationState.WALK;
					animateWalk();
				}
				else if (Math.abs(velocityX) > Const.Player.WALK_SWITCH_VELOCITY && animationState == AnimationState.WALK){
					animationState = AnimationState.RUN;
					animateRun();
				}
			}
			else {
				if (animationState != AnimationState.IDLE){
					animationState = AnimationState.IDLE;
					animateIdle();
				}
			}
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
		pauseAnimationSwitch(Const.Player.ANIME_DISABLE_TIME);
	}
	
	public void animateRun(){
		stopAnimation();
		sprite.animate(Const.Player.RUN_ANIME_SPEED, Const.Player.RUN_INDEX_START, Const.Player.RUN_INDEX_END, true);
		pauseAnimationSwitch(Const.Player.ANIME_DISABLE_TIME);
	}
	
	public void animateWalk(){
		stopAnimation();
		sprite.animate(Const.Player.WALK_ANIME_SPEED, Const.Player.WALK_INDEX_START, Const.Player.WALK_INDEX_END, true);
		pauseAnimationSwitch(Const.Player.ANIME_DISABLE_TIME);
	}
	
	public void animateLand(){
		stopAnimation();
		sprite.animate(Const.Player.LAND_ANIME_SPEED, Const.Player.LAND_INDEX_START, Const.Player.LAND_INDEX_END, false);
		canSwitchAnimation = false;
		int pauseTime = 0;
		for (int i = 0; i < Const.Player.LAND_ANIME_SPEED.length; i++){pauseTime += Const.Player.LAND_ANIME_SPEED[i];}
		physicsBody.setLinearVelocity(0, physicsBody.getLinearVelocity().y);
		scene.getEngine().registerUpdateHandler(new TimerHandler(pauseTime / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.getEngine().unregisterUpdateHandler(pTimerHandler);
				canSwitchAnimation = true;
				onUpdate();
			}
		}));
	}
	
	public void stopAnimation(){
		sprite.stopAnimation();
	}
	
	public void pauseAnimationSwitch(int milliseconds){
		canSwitchAnimation = false;
		scene.getEngine().registerUpdateHandler(new TimerHandler(milliseconds / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.getEngine().unregisterUpdateHandler(pTimerHandler);
				canSwitchAnimation = true;
			}
		}));
	}
	
}