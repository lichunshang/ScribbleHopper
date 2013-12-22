package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;

public class MainMenuLoadingSubScene extends BaseSubScene{
	
	private Rectangle backgroundSprite;
	private Text loadingText;
	private TimerHandler loadingTextTimer;
	
	public MainMenuLoadingSubScene(MainMenuScene menuScene){
		super(menuScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		backgroundSprite = new Rectangle(camera.getWidth() / 2, camera.getHeight() / 2, camera.getWidth(), camera.getHeight(), vertexBufferObjectManager);
		backgroundSprite.setColor(0.9f, 0.9f, 0.9f);
		backgroundSprite.setAlpha(0.65f);
		attachChild(backgroundSprite);
		loadingText = new Text(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.font_50, "Loading.......", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		loadingText.setText("Loading");
		attachChild(loadingText);
		
		loadingTextTimer = new TimerHandler(Const.MenuScene.LOADING_TEXT_ANIME_PERIOD / 1000f, true, new ITimerCallback() {
			private int numDots = 0;
			private String[] dots = {"", ".", "..", "..."};
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				loadingText.setText(parentScene.getGameActivity().getString(R.string.loading_text) + dots[numDots]);
				numDots++;
				if (numDots == 3)
					numDots = 0;
			}
		});
		parentScene.registerUpdateHandler(loadingTextTimer);
	}

	@Override
	public void attachScene() {
		parentScene.attachChild(this);
	}

	@Override
	public void detachScene() {
		parentScene.getEngine().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				parentScene.detachChild(MainMenuLoadingSubScene.this);
				parentScene.unregisterUpdateHandler(loadingTextTimer);
			}
		});
	}

	@Override
	public void onBackKeyPressed() {

	}
}