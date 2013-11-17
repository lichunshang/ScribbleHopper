package com.lichunshang.android.scribblehopper;

import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener{
	
	private GameScene gameScene;
	
	public GameContactListener(GameScene gameScene){
		this.gameScene = gameScene;
	}

	@Override
    public void beginContact (Contact contact){
    	if (this.checkContact(BasePlatform.class, Player.class, contact)){
    		gameScene.getPlayer().animateLand();
    	}
    }

	@Override
    public void endContact (Contact contact){
    	
    }

	@Override
    public void preSolve(Contact contact, Manifold oldManifold){
    }

	@Override
    public void postSolve(Contact contact, ContactImpulse impulse){
    	
    }
	
	public boolean checkContact(Class ClassA, Class ClassB, Contact contact){
		final Object objectA = contact.getFixtureA().getBody().getUserData();
		final Object objectB = contact.getFixtureB().getBody().getUserData();

		if ((ClassA.isInstance(objectA) && ClassB.isInstance(objectB)) || (ClassA.isInstance(objectB) && ClassB.isInstance(objectA)))
			return true;
		return false;
	}
	
	private BasePlatform getPlatformObject(Contact contact){
		final Object objectA = contact.getFixtureA().getBody().getUserData();
		final Object objectB = contact.getFixtureB().getBody().getUserData();
		
		if (objectA instanceof BasePlatform){
			return (BasePlatform) objectA;
		}
		if (objectB instanceof BasePlatform){
			return (BasePlatform) objectB;
		}
		return null;
	}
}