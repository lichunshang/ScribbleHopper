package com.lichunshang.android.scribblehopper.scenes;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.SceneManager;
import com.lichunshang.android.scribblehopper.SensorListener;
import com.lichunshang.android.scribblehopper.game.GameContactListener;
import com.lichunshang.android.scribblehopper.game.GameHUD;
import com.lichunshang.android.scribblehopper.game.Player;
import com.lichunshang.android.scribblehopper.platforms.BasePlatform;
import com.lichunshang.android.scribblehopper.platforms.PlatformPool;

public class GameScene extends BaseScene {
	
	private SensorListener sensorListener;
	private Random random = new Random();
	private PhysicsWorld physicsWorld;
	
	private SpriteBackground background;
	private Player player;
	private float score = 0;
	
	private PlatformPool platformPool;
	private double platformSpeed = Const.Plaform.INITIAL_SPEED;
	private BasePlatform lastSpawnedPlatform;
	private TimerHandler platformSpawnTimer, textUpdateTimer;
	
	private BaseSubScene currentSubScene = null;
	private PlayerDieSubScene playerDieScene;
	private GamePauseSubScene pauseScene;
	private boolean paused = false;
	
	private Entity plaformLayer, backgroundLayer, playerLayer, HUDLayer;
	private GameHUD gameHUD;
	
	public void createScene(){
		createLayers();
		createHUD();
		createPhysics();
		initializeSensors();
		createGameElements();
		createUpdateHandlers();
		createSubScenes();
	}
	
	// ==============================================
	// GAME LOOP METHOD
	// ==============================================
	public void onUpdate(){
		
		//check if the player is alive
		if (!player.isAlive() && currentSubScene == null){
			player.setPhysicsBodySensor(true);
			player.animateDie();
			currentSubScene = playerDieScene;
			playerDieScene.attachScene();
			playerDieScene.setScoreText((int) score);
		}
		if (player.isAlive()){
			score += platformSpeed * Const.GameScene.SPEED_TO_SCORE_RATIO;
		}

	}
	
	private void createGameElements(){
		player = new Player(camera.getWidth() / 2, camera.getHeight() / 3, this, physicsWorld);
		platformPool = new PlatformPool(this);
		lastSpawnedPlatform = platformPool.initPlatform(BasePlatform.PlatformType.REGULAR);
		lastSpawnedPlatform.setPosition(camera.getWidth() / 2, 0);
		
		//create left and right border so the player does not get out
		Rectangle leftBorder = new Rectangle(-1, camera.getHeight() / 2, 2, camera.getHeight(), vertexBufferObjectManager);
		Rectangle rightBorder = new Rectangle(1 + camera.getWidth(), camera.getHeight() / 2, 2, camera.getHeight(), vertexBufferObjectManager);
		FixtureDef borderFixtureDef = PhysicsFactory.createFixtureDef(Const.GameScene.BORDER_DENSITY, Const.GameScene.BORDER_ELASTICITY, Const.GameScene.BORDER_FRICTION);
		PhysicsFactory.createBoxBody(physicsWorld, leftBorder, BodyType.StaticBody, borderFixtureDef);
		PhysicsFactory.createBoxBody(physicsWorld, rightBorder, BodyType.StaticBody, borderFixtureDef);
		
		//top spike sensor to detect reduce damage
		Rectangle topBorder = new Rectangle(camera.getWidth() / 2, camera.getHeight() - 5, camera.getWidth(), 10, vertexBufferObjectManager);
		FixtureDef topBorderFixtureDef = PhysicsFactory.createFixtureDef(Const.GameScene.BORDER_DENSITY, Const.GameScene.TOP_BORDER_ELASTICITY, Const.GameScene.BORDER_FRICTION);
		Body topBorderPhysicsBody = PhysicsFactory.createBoxBody(physicsWorld, topBorder, BodyType.StaticBody, topBorderFixtureDef);
		topBorderPhysicsBody.setUserData(new TopBorder()); 
		
		Sprite spike = new Sprite(0, 0, resourcesManager.spikeTextureRegion, vertexBufferObjectManager);
		spike.setPosition(camera.getWidth() / 2, camera.getHeight() - spike.getHeight() / 2);
		attachChild(spike);
	}
	
	private void createHUD(){
		gameHUD = new GameHUD(this, HUDLayer);
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
		
//		DebugRenderer debug = new DebugRenderer(physicsWorld, getVertexBufferObjectManager());
//		this.attachChild(debug);
	}
	
	private void createUpdateHandlers(){
		platformSpawnTimer = new TimerHandler(Const.Plaform.INITIAL_SPAWN_TIME / 1000f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				onPlatformSpawnTimerPass();
			}
		});
		
		textUpdateTimer = new TimerHandler(Const.GameScene.SCORE_UPDATE_PERIOD / 1000f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				gameHUD.updateScoreText((int) score);
			}
		});
		
		registerUpdateHandler(platformSpawnTimer);
		registerUpdateHandler(textUpdateTimer);
	}
	
	private void initializeSensors(){
		sensorListener = new SensorListener();
		SensorManager sensorManager = (SensorManager) activity.getSystemService(BaseGameActivity.SENSOR_SERVICE);
		sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
	}
	
	private void createLayers(){
		background = new SpriteBackground(new Sprite(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.backgroundTextureRegion, vertexBufferObjectManager));
		setBackground(background);
		
		backgroundLayer = new Entity();
		playerLayer = new Entity();
		plaformLayer = new Entity();
		HUDLayer = new Entity();
		attachChild(backgroundLayer);
		attachChild(plaformLayer);
		attachChild(playerLayer);
		attachChild(HUDLayer);
	}
	
	private void createSubScenes(){
		playerDieScene = new PlayerDieSubScene(this);
		pauseScene = new GamePauseSubScene(this);
	}
	
	public void onBackKeyPressed(){
		if (currentSubScene == playerDieScene){
			playAgain();
		}
		else if (paused){
			unPauseGame();
		}
		else{
			pauseGame();
		}
	}
	
	public void disposeScene(){
		camera.setHUD(null);
	}
	
	public void attachPlaform(Entity PlaformSprite){
		plaformLayer.attachChild(PlaformSprite);
	}
	
	public void attachPlayer(AnimatedSprite player){
		playerLayer.attachChild(player);
	}
	
	public void resetScene(){

		platformPool.recycleAllActivePlaform();
		lastSpawnedPlatform = platformPool.initPlatform(BasePlatform.PlatformType.REGULAR);
		lastSpawnedPlatform.setPosition(camera.getWidth() / 2, 0);
		platformSpawnTimer.reset();
		
		player.reset();
		score = 0;
		
		if (paused){
			unPauseGame();
		}
		
		this.registerUpdateHandler(new TimerHandler(Const.COMMON_DELAY_SHORT / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				engine.unregisterUpdateHandler(pTimerHandler);
				currentSubScene = null;
			}
		}));
	}
	
	public BasePlatform.PlatformType getNextPlatformType(){

		int randomNum = random.nextInt(6);
		if (randomNum == 0){
			return BasePlatform.PlatformType.REGULAR;
		}
		else if (randomNum == 1){
			return BasePlatform.PlatformType.UNSTABLE;
		}
		else if (randomNum == 2){
			return BasePlatform.PlatformType.CONVEYOR_LEFT;
		}
		else if (randomNum == 3){
			return BasePlatform.PlatformType.CONVEYOR_RIGHT;
		}
		else if (randomNum == 4){
			return BasePlatform.PlatformType.BOUNCE;
		}
		else {
			return BasePlatform.PlatformType.SPIKE;
		}
	}
	
	public void onPlatformSpawnTimerPass(){
		platformSpawnTimer.setTimerSeconds(getNextPlatformSpawnTime() / 1000f);
		platformSpawnTimer.reset();
		lastSpawnedPlatform = platformPool.initPlatform(getNextPlatformType());
	}
	
	public float getNextPlatformSpawnTime(){
		return random.nextFloat() * (Const.Plaform.MAX_SPAWN_TIME - Const.Plaform.MIN_SPAWN_TIME) + Const.Plaform.MIN_SPAWN_TIME;
	}
	
	public void onHealthChanged(){
		gameHUD.updateHealth(player.getHealth());
	}
	
	public void pauseGame(){
		pauseScene.attachScene();
		paused = true;
		currentSubScene = pauseScene;
		setIgnoreUpdate(true);
	}
	
	public void unPauseGame(){
		currentSubScene = null;
		pauseScene.detachScene();
		paused = false;
		setIgnoreUpdate(false);
	}
	
	public void playAgain(){
		currentSubScene = null;
		playerDieScene.detachScene();
		resetScene();
	}
	
	// -----------------------------------------------
	// Getter and Setters
	// -----------------------------------------------

	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_GAME;
	}
	
	public float getPlatformSpeed(){
		return (float) platformSpeed;
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
	
	public int getScore(){
		return (int) score;
	}
	
	public BaseSubScene getCurrentSubScene(){
		return currentSubScene;
	}
	
	public boolean isPaused(){
		return paused;
	}

	// -----------------------------------------------
	// GameScene classes
	// -----------------------------------------------

	public class TopBorder{}
}