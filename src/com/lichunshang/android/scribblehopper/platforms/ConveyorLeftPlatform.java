package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class ConveyorLeftPlatform extends BasePlatform{
	
	public static final float DISPLACEMENT_RATE = -1 * Const.Plaform.ConveyorLeft.DISPLACEMENT_RATE;
	
	public ConveyorLeftPlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 330, 40, scene.getVertexBufferObjectManager());
		this.sprite.setColor(1, 0, 0);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.ConveyorLeft.DENSITY, Const.Plaform.ConveyorLeft.ELASTICITY, Const.Plaform.ConveyorLeft.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.CONVEYOR_LEFT;
	}
	
	@Override
	public void onUpdate(){

	}
}