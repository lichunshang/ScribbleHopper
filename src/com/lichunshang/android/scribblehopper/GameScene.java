package com.lichunshang.android.scribblehopper;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class GameScene extends BaseScene {
	
	private HUD gameHUD;
	private Text scoreText;
	private SpriteBackground background;
	
	public void createScene(){
		createBackground();
		createHUD();
	}
	
	private void createHUD(){
		gameHUD = new HUD();
		scoreText = new Text(20, 0, resourcesManager.font, "00000000", vertexBufferObjectManager);
		scoreText.setAnchorCenter(0, 0);
		scoreText.setText("0");
		gameHUD.attachChild(scoreText);
		camera.setHUD(gameHUD);
	}
	
	private void createBackground(){
		background = new SpriteBackground(new Sprite(768 / 2, 1280 / 2, resourcesManager.gameBackgroundRegion, vertexBufferObjectManager));
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