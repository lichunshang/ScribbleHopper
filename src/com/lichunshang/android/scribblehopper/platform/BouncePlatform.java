package com.lichunshang.android.scribblehopper.platform;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scene.GameScene;

public class BouncePlatform extends BasePlatform{
	
	private static float DEFAULT_RESTITUION;
	
	public BouncePlatform(GameScene scene){
		super(scene);
		DEFAULT_RESTITUION = physicsBody.getFixtureList().get(0).getRestitution();
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new AnimatedSprite(0, 0, scene.getResourcesManager().bouncePlatformTextureRegion, scene.getVertexBufferObjectManager());
	}
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Bounce.DENSITY, Const.Plaform.Bounce.ELASTICITY, Const.Plaform.Bounce.FRICTION);
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, Const.Plaform.Bounce.bodyVerticesMKS, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.BOUNCE;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return sprite.getY() / Const.Physics.PIXEL_TO_METER_RATIO + Const.Plaform.Bounce.bodyVerticesMKS[0].y; 
	}
	
	public void disabledElasticity(){
		physicsBody.getFixtureList().get(0).setRestitution(0);
	}
	
	public void resetElasticity(){
		physicsBody.getFixtureList().get(0).setRestitution(DEFAULT_RESTITUION);
	}
	
	public void animate(AnimationLength length){
		sprite.stopAnimation();
		if (length == AnimationLength.LONG){
			sprite.animate(Const.Plaform.Bounce.LONG_FRAME_DURATION, Const.Plaform.Bounce.LONG_FRAMES, false);
		}
		else if (length == AnimationLength.SHORT){
			sprite.animate(Const.Plaform.Bounce.SHORT_FRAME_DURATION, Const.Plaform.Bounce.SHORT_FRAMES, false);
		}
	}
	
	public static enum AnimationLength{
		LONG, SHORT
	}
}