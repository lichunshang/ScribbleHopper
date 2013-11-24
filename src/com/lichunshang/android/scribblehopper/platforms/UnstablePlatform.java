package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class UnstablePlatform extends BasePlatform{
	
	public UnstablePlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 330, 40, scene.getVertexBufferObjectManager());
		this.sprite.setColor(0, 0.5f, 0);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Unstable.DENSITY, Const.Plaform.Unstable.ELASTICITY, Const.Plaform.Unstable.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.UNSTABLE;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return physicsBody.getPosition().y + sprite.getHeight() / 2f / Const.Physics.PIXEL_TO_METER_RATIO;
	}
	
	public void startCollpseTimer(){
		scene.getEngine().registerUpdateHandler(new TimerHandler(Const.Plaform.Unstable.COLLAPSE_TIME / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				scene.getEngine().unregisterUpdateHandler(pTimerHandler);
				collapse();
			}
		}));
	}
	
	public void collapse(){
		setPhysicsBodySensor(true);
		//TODO play animation then set invisible
		sprite.setVisible(false);
	}
}