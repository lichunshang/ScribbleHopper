package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class ConveyorRightPlatform extends BasePlatform{
	
	public static final float DISPLACEMENT_RATE = Const.Plaform.ConveyorRight.DISPLACEMENT_RATE;
	
	public ConveyorRightPlatform(GameScene scene){
		super(scene);
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 330, 40, scene.getVertexBufferObjectManager());
		this.sprite.setColor(0.5f, 0, 0);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.ConveyorRight.DENSITY, Const.Plaform.ConveyorRight.ELASTICITY, Const.Plaform.ConveyorRight.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.CONVEYOR_RIGHT;
	}
	
	@Override
	public void onUpdate(){

	}
}