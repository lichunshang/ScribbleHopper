package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class UnstablePlatform extends BasePlatform{
	
	public FadeOutModifier collapseFadeOutModifier = new FadeOutModifier(Const.Plaform.Unstable.TOTAL_ANIME_PERIOD / 1000f);
	
	public UnstablePlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPlatform(){
		this.sprite = new AnimatedSprite(0, 0, scene.getResourcesManager().unstablePlatformTextureRegion, scene.getVertexBufferObjectManager());
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Unstable.DENSITY, Const.Plaform.Unstable.ELASTICITY, Const.Plaform.Unstable.FRICTION);
		physicsBody = PhysicsFactory.createPolygonBody(physicsWorld, sprite, Const.Plaform.Unstable.bodyVerticesMKS, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.UNSTABLE;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return physicsBody.getPosition().y + sprite.getHeight() / 2f / Const.Physics.PIXEL_TO_METER_RATIO;
	}
	
	@Override
	public void reset(){
		super.reset();
		setPosition(generatePosX(),  0 - sprite.getHeight() / 2 - Const.Plaform.Unstable.UNSTABLE_SPAWN_DISPLACEMENT - Const.Plaform.SPAWN_DISPLACEMENT);
		sprite.setCurrentTileIndex(0);
		sprite.setAlpha(1);
	}
	
	public void startCollpseTimer(){
		scene.registerUpdateHandler(new TimerHandler(Const.Plaform.Unstable.COLLAPSE_TIME / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.unregisterUpdateHandler(pTimerHandler);
				collapse();
			}
		}));
	}
	
	public void collapse(){
		sprite.animate(Const.Plaform.Unstable.ANIME_SPEED, false);
		setPhysicsBodySensor(true);
		sprite.registerEntityModifier(collapseFadeOutModifier);
		scene.registerUpdateHandler(new TimerHandler(Const.Plaform.Unstable.TOTAL_ANIME_PERIOD / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.unregisterUpdateHandler(pTimerHandler);
				sprite.unregisterEntityModifier(collapseFadeOutModifier);
				collapseFadeOutModifier.reset();
				recyclePlatform();
			}
		}));
	}
}