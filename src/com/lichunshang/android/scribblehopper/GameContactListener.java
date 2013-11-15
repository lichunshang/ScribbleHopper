package com.lichunshang.android.scribblehopper;

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
//		Log.w("ABC", "hello1");
//    	if (checkContact(BasePlatform.class, Player.class, contact)){
//    		
//
//    	}
		gameScene.getPlayer().animateLand();
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
	
	private boolean checkContact(Class classA, Class classB, Contact contact){
		Log.w("ABC", "abc1");
		final Object objectA = contact.getFixtureA().getUserData();
		final Object objectB = contact.getFixtureB().getUserData();
		Log.w("ABC", "abc2");

		if ((classA.isInstance(objectA) && classA.isInstance(objectB)) || (classA.isInstance(objectB) && classA.isInstance(objectA)))
			return true;
		return false;
	}
	
//	private Player getPlayerObject(Contact contact){
//		final Object objectA = contact.getFixtureA().getUserData();
//		final Object objectB = contact.getFixtureB().getUserData();
//		
//		if (objectA instanceof Player){
//			return (Player) objectA;
//		}
//		if (objectB instanceof Player){
//			return (Player) objectB;
//		}
//		return null;
//	}
}