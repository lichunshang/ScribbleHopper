package com.lichunshang.android.scribblehopper.game;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.lichunshang.android.scribblehopper.scenes.GameScene;

public class GameHUD{
	
	private GameScene gameScene;
	private Entity HUDLayer;
	private Text scoreText;
	private Sprite[] heartsSprites;
	
	public GameHUD(GameScene gameScene, Entity HUDLayer){
		this.gameScene = gameScene;
		this.HUDLayer = HUDLayer;
		
		scoreText = new Text(20, 0, this.gameScene.getResourcesManager().font, "0123456789", this.gameScene.getVertexBufferObjectManager());
		scoreText.setAnchorCenter(0, 0);
		scoreText.setText("0");
		
		Sprite HUDBar = new Sprite(0, 0, this.gameScene.getResourcesManager().gameHUDBarTextureRegion, this.gameScene.getVertexBufferObjectManager());
		HUDBar.setPosition(this.gameScene.getCamera().getWidth() / 2, HUDBar.getHeight() / 2);
		
		this.HUDLayer.attachChild(HUDBar);
		this.HUDLayer.attachChild(scoreText);
		
//		heartsSprites = new Sprite[Const.Player.MAX_HEALTH];
//		
//		for (int i = 0; i < heartsSprites.length; i++){
//			heartsSprites[i] = new Sprite(0, 0, gameScene.getResourcesManager().gameHeartTextureRegion, gameScene.getVertexBufferObjectManager());
//			scoreText.setAnchorCenter(0, 0);
//			heartsSprites[i].setPosition(heartsSprites[i].getWidth() * i + 20, 0);
//		}
	}
	
	public void updateScoreText(int score){
		scoreText.setText(Integer.toString(score));
	}
}