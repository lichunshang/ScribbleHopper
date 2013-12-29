package com.lichunshang.android.scribblehopper;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import com.lichunshang.android.scribblehopper.manager.AudioManager;
import com.lichunshang.android.scribblehopper.manager.DataManager;
import com.lichunshang.android.scribblehopper.manager.ResourcesManager;
import com.lichunshang.android.scribblehopper.manager.SceneManager;


public class GameActivity extends BaseGameActivity {
	
	private Camera camera;
	//private ResourcesManager resourcesManager;
	public final int CAMERA_WIDTH = 768;
	public final int CAMERA_HEIGHT = 1280;

	@Override
	public EngineOptions onCreateEngineOptions(){
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		//EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions){
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException{
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		DataManager.prepareManager(this);
		AudioManager.prepareManager(mEngine);
		//resourcesManager = ResourcesManager.getInstance();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback  pOnCreateResourcesCallback) throws IOException{
		mEngine.registerUpdateHandler(new FPSLogger());
		SceneManager.getInstance().createSplashScene(pOnCreateResourcesCallback);
	}
	
	@Override
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback populateSceneCallback) throws IOException{
		
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadInitialResources();
				SceneManager.getInstance().createMenuScene();
				SceneManager.getInstance().setMenuScene();
			}
		}));
		populateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	        System.exit(0);	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK){
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}
}
