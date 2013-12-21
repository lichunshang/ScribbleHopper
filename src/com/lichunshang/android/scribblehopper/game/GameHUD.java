package com.lichunshang.android.scribblehopper.game;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class GameHUD{
	
	private GameScene gameScene;
	private Entity HUDLayer;
	private Text scoreText;
	private Sprite[] heartsSprites;
	private Sprite pauseSprite;
	private int health = 0;
	
	public GameHUD(GameScene gameScene, Entity HUDLayer){
		this.gameScene = gameScene;
		this.HUDLayer = HUDLayer;
		
		pauseSprite = new Sprite(0, 0, this.gameScene.getResourcesManager().pauseTextureRegion, gameScene.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float floatpTouchAreaLocalX, final float pTouchAreaLocalY){
				if (GameHUD.this.gameScene.getCurrentSubScene() == null && pSceneTouchEvent.isActionUp()){
					GameHUD.this.gameScene.pauseGame();
				}
				return false;
			}
		};
		pauseSprite.setAnchorCenter(0, 0);
		pauseSprite.setPosition(gameScene.getCamera().getWidth() - pauseSprite.getWidth() - Const.GameScene.HUD.LEFT_RIGHT_MARGIN, Const.GameScene.HUD.PAUSE_BOTTOM_MARGIN);
		pauseSprite.setAnchorCenter(0, 0);
		this.gameScene.registerTouchArea(pauseSprite);
		
		scoreText = new Text(0, 0, this.gameScene.getResourcesManager().font_50, "0123456789", this.gameScene.getVertexBufferObjectManager());
		scoreText.setAnchorCenter(0, 0);
		scoreText.setText("0");
		scoreText.setPosition(gameScene.getCamera().getWidth() - scoreText.getWidth() - pauseSprite.getWidth() - Const.GameScene.HUD.SCORE_RIGHT_MARGIN, 0);
		
		Sprite HUDBar = new Sprite(0, 0, this.gameScene.getResourcesManager().hudBarTextureRegion, this.gameScene.getVertexBufferObjectManager());
		HUDBar.setPosition(this.gameScene.getCamera().getWidth() / 2, HUDBar.getHeight() / 2);
		
		this.HUDLayer.attachChild(HUDBar);
		this.HUDLayer.attachChild(scoreText);
		this.HUDLayer.attachChild(pauseSprite);
		
		heartsSprites = new Sprite[Const.Player.MAX_HEALTH];
		
		for (int i = 0; i < heartsSprites.length; i++){
			heartsSprites[i] = new Sprite(0, 0, gameScene.getResourcesManager().heartTextureRegion, gameScene.getVertexBufferObjectManager());
			heartsSprites[i].setAnchorCenter(0, 0);
			heartsSprites[i].setPosition(heartsSprites[i].getWidth() * i + Const.GameScene.HUD.LEFT_RIGHT_MARGIN + i * 2, Const.GameScene.HUD.HEALTH_BOTTOM_MARGIN);
			HUDLayer.attachChild(heartsSprites[i]);
		}
	}
	
	public void updateScoreText(int score){
		scoreText.setText(Integer.toString(score));
		scoreText.setPosition(gameScene.getCamera().getWidth() - scoreText.getWidth() - pauseSprite.getWidth() - Const.GameScene.HUD.SCORE_RIGHT_MARGIN, 0);
	}
	
	public void updateHealth(int health){
		if (health == this.health){
			return;
		}
		for (int i = 0; i < heartsSprites.length; i++){
			if (i + 1 <= health){
				heartsSprites[i].setVisible(true);
			}
			else{
				heartsSprites[i].setVisible(false);
			}
		}
		this.health = health;
	}
}