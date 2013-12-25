package com.lichunshang.android.scribblehopper.game;

import java.util.Hashtable;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.platforms.BasePlatform;
import com.lichunshang.android.scribblehopper.platforms.BasePlatform.PlatformType;
import com.lichunshang.android.scribblehopper.platforms.BouncePlatform;
import com.lichunshang.android.scribblehopper.platforms.UnstablePlatform;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class GameContactListener implements ContactListener{
	
	private GameScene gameScene;
	private Hashtable<BasePlatform.PlatformType, Integer> platformLandCounter = new Hashtable<BasePlatform.PlatformType, Integer>();
	private int numTopSpikeContact;
	
	public GameContactListener(GameScene gameScene){
		this.gameScene = gameScene;
		initCounters();
	}
	
	private void initCounters(){
		platformLandCounter.put(BasePlatform.PlatformType.BOUNCE, 0);
		platformLandCounter.put(BasePlatform.PlatformType.CONVEYOR_LEFT, 0);
		platformLandCounter.put(BasePlatform.PlatformType.CONVEYOR_RIGHT, 0);
		platformLandCounter.put(BasePlatform.PlatformType.REGULAR, 0);
		platformLandCounter.put(BasePlatform.PlatformType.SPIKE, 0);
		platformLandCounter.put(BasePlatform.PlatformType.UNSTABLE, 0);
		numTopSpikeContact = 0;
	}
	
	public void resetCounters(){
		initCounters();
	}
	
	public Hashtable<BasePlatform.PlatformType, Integer> getPlatformLandCounter(){
		return platformLandCounter;
	}
	
	public int getNumTopSpikeContact(){
		return numTopSpikeContact;
	}
	
	@Override
    public void preSolve(Contact contact, Manifold oldManifold){
		if (this.checkContact(BasePlatform.class, Player.class, contact)){
			
			Player player = gameScene.getPlayer();
			BasePlatform platform = getPlatformObject(contact);
			
			if (!platform.isRecycled() && player.isAlive() && player.getBodyBottomYMKS() < platform.getPhyiscsBody().getPosition().y){
				contact.setEnabled(false);
			}
		}
    }

	@Override
    public void beginContact (Contact contact){
		
		if (checkContact(GameScene.TopBorder.class, Player.class, contact)){
			Player player = gameScene.getPlayer();
			if (player.getCurrentPlaform() != null){
				player.getCurrentPlaform().setPhysicsBodySensor(true);
			}
			player.decreaseHealth(Const.GameScene.TOP_BORDER_HEALTH_DECREMENT);
			numTopSpikeContact++;
		}
		
    	if (checkContact(BasePlatform.class, Player.class, contact)){
    		BasePlatform platform = getPlatformObject(contact);
    		BasePlatform.PlatformType platformType = platform.getType();
    		Player player = gameScene.getPlayer();
    		boolean animatedLand = false;
    		
    		if (!platform.isRecycled() && player.isAlive() && player.getBodyBottomYMKS() >= platform.getPhyiscsBody().getPosition().y){
	    		
	    		if (platform.getType() == BasePlatform.PlatformType.REGULAR){
	    			player.setCurrentPlatform(platform);
	    			animatedLand = true;
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.BOUNCE){
	    			player.setCurrentPlatform(platform);
	    			if (Math.abs(player.getPhysicsBody().getLinearVelocity().y) < Const.Plaform.Bounce.PLAYER_VELOCITY_NO_BOUNCE){
	    				((BouncePlatform) platform).disabledElasticity();
	    			}
	    			if (Math.abs(player.getPhysicsBody().getLinearVelocity().y) > Const.Plaform.Bounce.PLAYER_VELOCITY_NO_LAND){
	    				((BouncePlatform) platform).animate(BouncePlatform.AnimationLength.LONG);
	    				animatedLand = true;
	    			}
	    			else{
	    				((BouncePlatform) platform).animate(BouncePlatform.AnimationLength.SHORT);
	    			}
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.CONVEYOR_LEFT){
	    			player.setCurrentPlatform(platform);
	    			animatedLand = true;
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.CONVEYOR_RIGHT){
	    			player.setCurrentPlatform(platform);
	    			animatedLand = true;
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.UNSTABLE){
	    			player.setCurrentPlatform(platform);
	    			animatedLand = true;
		    		((UnstablePlatform) platform).startCollpseTimer();
	    		}
	    		else if (platform.getType() == BasePlatform.PlatformType.SPIKE){
	    			player.setCurrentPlatform(platform);
	    			player.decreaseHealth(Const.Plaform.Spike.HEALTH_DECREMENT);
	    			animatedLand = true;
	    		}
	    		
	    		if (animatedLand && Math.abs(player.getPhysicsBody().getLinearVelocity().x) < Const.Plaform.PLAYER_HORIZONTAL_VELOCITY_NO_LAND)
	    			player.animateLand();
	    		
	    		if (platformType != PlatformType.SPIKE && player.isDifferentPlatform()){
	    			player.increaseHealth(Const.Plaform.HEALTH_INCREMENT);
	    		}
	    		
	    		//increase the counters of platform land
	    		if (player.isDifferentPlatform()){
	    			platformLandCounter.put(platformType, platformLandCounter.get(platformType) + 1);
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
			
			if (!platform.isRecycled() && platform.getType() == BasePlatform.PlatformType.BOUNCE){
				((BouncePlatform) platform).resetElasticity();
			}
		}
    }

	@Override
    public void postSolve(Contact contact, ContactImpulse impulse){
    	
    }
	
	private boolean checkContact(Class ClassA, Class ClassB, Contact contact){
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