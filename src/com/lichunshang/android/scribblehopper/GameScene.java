package com.lichunshang.android.scribblehopper;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;

public class GameScene extends BaseScene {
	
	private HUD gameHUD;
	private Text scoreText;
	private SpriteBackground background;
	private PhysicsWorld physicsWorld;
	private Player player;
	
	public void createScene(){
		createBackground();
		createHUD();
		createPhysics();
		
		player = new Player(camera.getWidth() / 2, camera.getHeight() / 2, this, physicsWorld);
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
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -1 * ProjectConstants.Physics.GRAVITY), false);
		//physicsWorld.setContactListener(pListener)
		registerUpdateHandler(physicsWorld);
	}
	
	private void createBackground(){
		background = new SpriteBackground(new Sprite(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.gameBackgroundTextureRegion, vertexBufferObjectManager));
		setBackground(background);
	}
	
	public void onBackKeyPressed(){
		SceneManager.getInstance().loadMenuScene();
	}
	
	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_GAME;
	}
	
	public void disposeScene(){
		//TODO
	}
}