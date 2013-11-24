package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class SpikePlatform extends BasePlatform{
	
	public SpikePlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void onUpdate(){

	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 330, 40, scene.getVertexBufferObjectManager());
		this.sprite.setColor(0.3f, 0.3f, 0.3f);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Spike.DENSITY, Const.Plaform.Spike.ELASTICITY, Const.Plaform.Spike.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.SPIKE;
	}
	
	@Override
	public float getBodyTopYMKS(){
		return physicsBody.getPosition().y + sprite.getHeight() / 2f / Const.Physics.PIXEL_TO_METER_RATIO;
	}
}