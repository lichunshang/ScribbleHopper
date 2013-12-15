package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;

public class MainMenuLoadingScene extends BaseSubScene{
	
	private Rectangle backgroundSprite;
	private Text loadingText;
	private TimerHandler loadingTextTimer;
	
	public MainMenuLoadingScene(MainMenuScene menuScene){
		super(menuScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		backgroundSprite = new Rectangle(camera.getWidth() / 2, camera.getHeight() / 2, camera.getWidth(), camera.getHeight(), vertexBufferObjectManager);
		backgroundSprite.setColor(0.5f, 0.5f, 0.5f);
		backgroundSprite.setAlpha(0.65f);
		attachChild(backgroundSprite);
		loadingText = new Text(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.font_50, "Loading.......", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		loadingText.setText("Loading");
		attachChild(loadingText);
		
		loadingTextTimer = new TimerHandler(Const.MenuScene.LOADING_TEXT_ANIME_PERIOD / 1000f, true, new ITimerCallback() {
			private int numDots = 1;
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (numDots == 0){
					loadingText.setText(parentScene.getGameActivity().getString(R.string.loading_text));
				}
				else if (numDots == 1){
					loadingText.setText(parentScene.getGameActivity().getString(R.string.loading_text) + ".");
				}
				else if (numDots == 2){
					loadingText.setText(parentScene.getGameActivity().getString(R.string.loading_text) + "..");
				}
				else if (numDots == 3){
					loadingText.setText(parentScene.getGameActivity().getString(R.string.loading_text) + "...");
					numDots = 0;
				}
				numDots++;
			}
		});
		parentScene.registerUpdateHandler(loadingTextTimer);
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
				parentScene.detachChild(MainMenuLoadingScene.this);
				attached = false;
			}
		});
	}

	@Override
	public void onBackKeyPressed() {

	}
}