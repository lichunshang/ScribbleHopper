package com.lichunshang.android.scribblehopper;

import java.security.PublicKey;

import org.andengine.entity.sprite.AnimatedSprite;
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
				new Vector2(Const.Player.FIXTURE_VERTICE_RATIO[0][0] * sprite.getWidth(), Const.Player.FIXTURE_VERTICE_RATIO[0][1] * sprite.getHeight()),
				new Vector2(Const.Player.FIXTURE_VERTICE_RATIO[1][0] * sprite.getWidth(), Const.Player.FIXTURE_VERTICE_RATIO[1][1] * sprite.getHeight()),
				new Vector2(Const.Player.FIXTURE_VERTICE_RATIO[2][0] * sprite.getWidth(), Const.Player.FIXTURE_VERTICE_RATIO[2][1] * sprite.getHeight()),
		};
		
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, vertices, BodyType.DynamicBody, fixtureDef);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				Player.this.onUpdate();
				super.onUpdate(pSecondsElapsed);
			}
		});
	}
	
	public void move(float accelerometerVal){
		float runOffset = 0;
		float walkOffset = 0;
		
		if (animationState == AnimationState.RUN){
			runOffset = Const.Player.RUN_ANIME_OFFSET;
			if (accelerometerVal < 0)
				runOffset *= -1;
		}
		if (animationState == AnimationState.WALK){
			walkOffset = Const.Player.WALK_ANIME_OFFSET;
			if (accelerometerVal < 0)
				walkOffset *= -1;
		}
		
		this.physicsBody.setLinearVelocity(accelerometerVal * Const.Player.ACCELEROMETER_MULTIPLY_FACTOR + runOffset + walkOffset, physicsBody.getLinearVelocity().y);
	}

	//UPDATE LOOP
	public void onUpdate(){
		
		float velocityX = physicsBody.getLinearVelocity().x;
		float velocityY = physicsBody.getLinearVelocity().y;
		
		if (Math.abs(velocityX) > Const.Player.IDLE_SWITCH_VELOCITY){
			
			sprite.setFlippedHorizontal(velocityX < 0); 
			
			if (Math.abs(velocityX) < Const.Player.WALK_SWITCH_VELOCITY && animationState != AnimationState.WALK){
				animationState = AnimationState.WALK;
				stopAnimation();
				animateWalk();
			}
			else if (Math.abs(velocityX) > Const.Player.WALK_SWITCH_VELOCITY && animationState == AnimationState.WALK){
				animationState = AnimationState.RUN;
				stopAnimation();
				animateRun();
			}
		}
		else {
			if (animationState != AnimationState.IDLE){
				animationState = AnimationState.IDLE;
				stopAnimation();
				animateIdle();
			}
		}
	}
	
	// -------------------------------------------------------------
	// ANIMATION
	// -------------------------------------------------------------
	
	public void animateIdle(){
		sprite.animate(Const.Player.IDLE_ANIME_SPEED, Const.Player.IDLE_INDEX_START, Const.Player.IDLE_INDEX_END, true);
	}
	
	public void animateLand(){
		sprite.animate(Const.Player.LAND_ANIME_SPEED, Const.Player.LAND_INDEX_START, Const.Player.LAND_INDEX_END, true);
	}
	
	public void animateRun(){
		sprite.animate(Const.Player.RUN_ANIME_SPEED, Const.Player.RUN_INDEX_START, Const.Player.RUN_INDEX_END, true);
	}
	
	public void animateWalk(){
		sprite.animate(Const.Player.WALK_ANIME_SPEED, Const.Player.WALK_INDEX_START, Const.Player.WALK_INDEX_END, true);
	}
	
	public void stopAnimation(){
		sprite.stopAnimation();
	}
	
	public static enum AnimationState{
		IDLE, RUN, WALK, LAND
	}
}