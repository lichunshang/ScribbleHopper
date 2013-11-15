package com.lichunshang.android.scribblehopper;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;

public class GameScene extends BaseScene {
	
	private SensorListener sensorListener;
	private Random random = new Random();
	
	private PhysicsWorld physicsWorld;
	
	private HUD gameHUD;
	private Text scoreText;
	private SpriteBackground background;
	private Player player;
	
	private PlatformPool platformPool;
	private float platformSpeed = Const.Plaform.INITIAL_SPEED;
	private BasePlatform lastSpawnedPlatform;
	private float nextPlatformDistance = Const.Plaform.INITIAL_SPAWN_DISTANCE;
	
	// ==============================================
	// GAME LOOP METHOD
	// ==============================================
	public void onUpdate(){
		
		if (lastSpawnedPlatform.getSprite().getY() > nextPlatformDistance){
			nextPlatformDistance = random.nextFloat() * (Const.Plaform.MAX_SPAWN_DISTANCE - Const.Plaform.MIN_SPAWN_DISTANCE) + Const.Plaform.MIN_SPAWN_DISTANCE;
			BasePlatform newPlatform = platformPool.initPlatform(BasePlatform.PlatformType.REGULAR);
			lastSpawnedPlatform = newPlatform;
		}
		
		player.moveWithAppliedForce(getAccelerometerValue());
	}
	
	public void createScene(){
		
		platformSpeed = 3f;
		
		createBackground();
		createHUD();
		createPhysics();
		
		//sensor
		sensorListener = new SensorListener();
		SensorManager sensorManager = (SensorManager) activity.getSystemService(BaseGameActivity.SENSOR_SERVICE);
		sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		
		//game objects
		player = new Player(camera.getWidth() / 2, camera.getHeight() / 2, this, physicsWorld);
		platformPool = new PlatformPool(this);
		lastSpawnedPlatform = platformPool.initPlatform(BasePlatform.PlatformType.REGULAR);
		

	}
	
	private void createHUD(){
		gameHUD = new HUD();
		scoreText = new Text(20, 0, resourcesManager.font, "00000000", vertexBufferObjectManager);
		scoreText.setAnchorCenter(0, 0);
		scoreText.setText("0");
		gameHUD.attachChild(scoreText);
		camera.setHUD(gameHUD);
	}
	
	private void createPhysics(){
		physicsWorld = new FixedStepPhysicsWorld(Const.Physics.REFRESH_RATE, new Vector2(0, -1 * Const.Physics.GRAVITY), false){
			@Override
			public void onUpdate(final float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				GameScene.this.onUpdate();
			}
		};
		physicsWorld.setContactListener(new GameContactListener(this));
		registerUpdateHandler(physicsWorld);
		
		DebugRenderer debug = new DebugRenderer(physicsWorld, getVertexBufferObjectManager());
		this.attachChild(debug);
	}
	
	private void createBackground(){
		background = new SpriteBackground(new Sprite(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.gameBackgroundTextureRegion, vertexBufferObjectManager));
		setBackground(background);
	}
	
	public void onBackKeyPressed(){
		SceneManager.getInstance().loadMenuScene();
	}
	
	public void disposeScene(){
		//TODO
	}
	
	// -----------------------------------------------
	// Getter and Setters
	// -----------------------------------------------

	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_GAME;
	}
	
	public float getPlatformSpeed(){
		return platformSpeed;
	}
	
	public float getAccelerometerValue(){
		return sensorListener.getAccelerometerX() * -1;
	}
	
	public PhysicsWorld getPhysicsWorld(){
		return physicsWorld;
	}
	
	public PlatformPool getPlatformPool(){
		return platformPool;
	}
	
	public Player getPlayer(){
		return player;
	}
	
}