
package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;


public class GamePauseScene extends BaseSubScene{
	
	private Rectangle backgroundSprite;
	private Text pauseText;
	
	public GamePauseScene(GameScene gameScene){
		super(gameScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		backgroundSprite = new Rectangle(camera.getWidth() / 2, camera.getHeight() / 2, camera.getWidth(), camera.getHeight(), vertexBufferObjectManager);
		backgroundSprite.setColor(0.5f, 0.5f, 0.5f);
		backgroundSprite.setAlpha(0.65f);
		attachChild(backgroundSprite);
		pauseText = new Text(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.font, "Paused", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		attachChild(pauseText);
	}

	@Override
	public void attachScene() {
		parentScene.attachChild(this);
		attached = true;
		((GameScene) parentScene).setIgnoreUpdate(true);
	}

	@Override
	public void detachScene() {
		parentScene.getEngine().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				parentScene.detachChild(GamePauseScene.this);
				attached = false;
				((GameScene) parentScene).setIgnoreUpdate(false);
			}
		});
	}

	@Override
	public void onBackKeyPressed() {
		detachScene();
	}
	
}