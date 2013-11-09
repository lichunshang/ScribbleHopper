package com.lichunshang.android.scribblehopper;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Player{
	
	private AnimatedSprite sprite;
	private Body physicsBody;
	private PhysicsWorld physicsWorld;
	private BaseScene scene;
	
	public Player(float posX, float posY, BaseScene scene, PhysicsWorld physicsWorld){
		
		this.scene = scene;
		this.physicsWorld = physicsWorld;
		this.sprite = new AnimatedSprite(posX, posY, this.scene.resourcesManager.gamePlayerTextureRegion, this.scene.vertexBufferObjectManager);
		this.sprite.setScale(1.5f);
		
		scene.attachChild(this.sprite);
		createPhysics();
		
		
		//animation
		final long[] PLAYER_ANIMATE = new long[8];
		
		for (int i = 0; i < PLAYER_ANIMATE.length; i++){
			PLAYER_ANIMATE[i] = 70;
		}
		sprite.animate(PLAYER_ANIMATE, 0, 7, true);
		
	}
	
	private void createPhysics(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(ProjectConstants.Player.DENSITY, ProjectConstants.Player.ELASTICITY, ProjectConstants.Player.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.StaticBody, fixtureDef);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, physicsBody, true, false){
			@Override
			public void onUpdate(float pSecondsElapsed){
				super.onUpdate(pSecondsElapsed);
				//TODO
				
				move();
			}
		});
	}
	
	public void move(){
		
	}
	
}