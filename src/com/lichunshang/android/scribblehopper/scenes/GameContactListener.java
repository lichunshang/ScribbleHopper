package com.lichunshang.android.scribblehopper.scenes;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.Player;
import com.lichunshang.android.scribblehopper.platforms.BasePlatform;
import com.lichunshang.android.scribblehopper.platforms.BouncePlatform;
import com.lichunshang.android.scribblehopper.platforms.UnstablePlatform;

public class GameContactListener implements ContactListener{
	
	private GameScene gameScene;
	
	public GameContactListener(GameScene gameScene){
		this.gameScene = gameScene;
	}
	
	@Override
    public void preSolve(Contact contact, Manifold oldManifold){
		if (this.checkContact(BasePlatform.class, Player.class, contact)){
			
			Player player = gameScene.getPlayer();
			BasePlatform platform = getPlatformObject(contact);
			
			if (player.getBodyBottomYMKS() <= platform.getPhyiscsBody().getPosition().y){
				contact.setEnabled(false);
			}
		}
    }

	@Override
    public void beginContact (Contact contact){
		
    	if (this.checkContact(BasePlatform.class, Player.class, contact)){
    		BasePlatform platform = getPlatformObject(contact);
    		Player player = gameScene.getPlayer();
    		
    		if (player.getBodyBottomYMKS() >= platform.getPhyiscsBody().getPosition().y){
	    		
	    		if (platform.getType() == BasePlatform.PlatformType.REGULAR){
	    			player.setCurrentPlatform(BasePlatform.PlatformType.REGULAR);
		    		player.animateLand();
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.BOUNCE){
	    			player.setCurrentPlatform(BasePlatform.PlatformType.BOUNCE);
	    			if (Math.abs(player.getPhysicsBody().getLinearVelocity().y) < Const.Plaform.Bounce.PLAYER_VELOCITY_NO_BOUNCE){
	    				((BouncePlatform) platform).disabledElasticity();
	    			}
	    			if (Math.abs(player.getPhysicsBody().getLinearVelocity().y) > Const.Plaform.Bounce.PLAYER_VELOCITY_NO_LAND){
	    				player.animateLand();
	    			}
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.CONVEYOR_LEFT){
	    			player.setCurrentPlatform(BasePlatform.PlatformType.CONVEYOR_LEFT);
		    		player.animateLand();
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.CONVEYOR_RIGHT){
	    			player.setCurrentPlatform(BasePlatform.PlatformType.CONVEYOR_RIGHT);
		    		player.animateLand();
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.UNSTABLE){
	    			player.setCurrentPlatform(BasePlatform.PlatformType.UNSTABLE);
		    		player.animateLand();
		    		((UnstablePlatform) platform).startCollpseTimer();
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.SPIKE){
	    			player.setCurrentPlatform(BasePlatform.PlatformType.UNSTABLE);
		    		player.animateLand();
	    		}
    		}
    		else{
    			player.setCurrentPlatform(null);
    		}
    	}
    }

	@Override
    public void endContact (Contact contact){
		contact.setEnabled(true);
		if (this.checkContact(BasePlatform.class, Player.class, contact)){
			Player player = gameScene.getPlayer();
			BasePlatform platform = getPlatformObject(contact);
			
			player.setCurrentPlatform(null);
			
			if (platform.getType() == BasePlatform.PlatformType.BOUNCE){
				((BouncePlatform) platform).resetElasticity();
			}
		}
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