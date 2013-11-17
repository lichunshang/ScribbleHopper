package com.lichunshang.android.scribblehopper;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

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
	
	public void createScene(){
		createBackground();
		createHUD();
		createPhysics();
		initializeSensors();
		createGameElements();
	}
	
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
	
	private void createGameElements(){
		player = new Player(camera.getWidth() / 2, camera.getHeight() / 2, this, physicsWorld);
		platformPool = new PlatformPool(this);
		lastSpawnedPlatform = platformPool.initPlatform(BasePlatform.PlatformType.REGULAR);
		lastSpawnedPlatform.setPosition(camera.getWidth() / 2, lastSpawnedPlatform.getSprite().getY());
		
		
		//create left and right border so the player does not get out
		Rectangle leftBorder = new Rectangle(-5, camera.getHeight() / 2, 10, camera.getHeight(), vertexBufferObjectManager);
		Rectangle rightBorder = new Rectangle(5 + camera.getWidth(), camera.getHeight() / 2, 10, camera.getHeight(), vertexBufferObjectManager);
		FixtureDef borderFixtureDef = PhysicsFactory.createFixtureDef(Const.GameScene.BORDER_DENSITY, Const.GameScene.BORDER_ELASTICITY, Const.GameScene.BORDER_FRICTION);
		PhysicsFactory.createBoxBody(physicsWorld, leftBorder, BodyType.StaticBody, borderFixtureDef);
		PhysicsFactory.createBoxBody(physicsWorld, rightBorder, BodyType.StaticBody, borderFixtureDef);
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
	
	private void initializeSensors(){
		sensorListener = new SensorListener();
		SensorManager sensorManager = (SensorManager) activity.getSystemService(BaseGameActivity.SENSOR_SERVICE);
		sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
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