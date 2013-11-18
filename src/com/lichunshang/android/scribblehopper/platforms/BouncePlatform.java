package com.lichunshang.android.scribblehopper.platforms;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class BouncePlatform extends BasePlatform{
	
	public static final float ELASTICITY = Const.Plaform.Bounce.ELASTICITY;
	private static float DEFAULT_RESTITUION;
	
	public BouncePlatform(GameScene scene){
		super(scene);
		DEFAULT_RESTITUION = physicsBody.getFixtureList().get(0).getRestitution();
	}
	
	@Override
	public void createPlatform(){
		this.sprite = new Rectangle(0, 0, 330, 40, scene.getVertexBufferObjectManager());
		this.sprite.setColor(0, 1, 0);
	}
	
	@Override
	public void createPhysicsBody(){
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(Const.Plaform.Bounce.DENSITY, Const.Plaform.Bounce.ELASTICITY, Const.Plaform.Bounce.FRICTION);
		physicsBody = PhysicsFactory.createBoxBody(physicsWorld, sprite, BodyType.KinematicBody, fixtureDef);
	}
	
	@Override
	public PlatformType getType(){
		return PlatformType.BOUNCE;
	}
	
	@Override
	public void onUpdate(){

	}
	
	public void disabledElasticity(){
		physicsBody.getFixtureList().get(0).setRestitution(0);
	}
	
	public void resetElasticity(){
		physicsBody.getFixtureList().get(0).setRestitution(DEFAULT_RESTITUION);
	}
}