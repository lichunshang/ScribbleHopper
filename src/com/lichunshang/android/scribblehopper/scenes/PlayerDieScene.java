package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

public class PlayerDieScene extends BaseSubScene{
	
	private Rectangle backgroundSprite;
	private Text scoreText;
	
	public PlayerDieScene(GameScene gameScene){
		super(gameScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		backgroundSprite = new Rectangle(camera.getWidth() / 2, camera.getHeight() / 2, camera.getWidth(), camera.getHeight(), vertexBufferObjectManager);
		backgroundSprite.setColor(0.5f, 0.5f, 0.5f);
		backgroundSprite.setAlpha(0.65f);
		attachChild(backgroundSprite);
		scoreText = new Text(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.font, "Score: 0123456879", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		attachChild(scoreText);
	}

	@Override
	public void attachScene() {
		parentScene.attachChild(this);
		attached = true;
	}

	@Override
	public void detachScene() {
		parentScene.getEngine().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				parentScene.detachChild(PlayerDieScene.this);
				((GameScene) parentScene).resetScene();
				attached = false;
			}
		});
	}

	@Override
	public void onBackKeyPressed() {
		detachScene();
	}
	
	public void setScoreText(int score){
		scoreText.setText("Score: " + Integer.toString(score));
	}
}