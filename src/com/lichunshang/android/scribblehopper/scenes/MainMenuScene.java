package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.SceneManager;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	
	private MainMenuLoadingScene mainMenuLoadingScene;
	
	public void createScene(){
		createBackground();
		createLoadingScene();
		
	}
	
	public void onBackKeyPressed(){
		System.exit(0);
	}
	
	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_MENU;
	}
	
	public void disposeScene(){
		
	}
	
	private void createBackground(){
		attachChild(new Sprite(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.menuBackgroundRegion, vertexBufferObjectManager){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
		});
	}
	
	private void createLoadingScene(){
		mainMenuLoadingScene = new MainMenuLoadingScene(this);
		mainMenuLoadingScene.attachScene();
		SceneManager.getInstance().createGameScene();
		
		registerUpdateHandler(new TimerHandler(Const.MenuScene.LOADING_PERIOD / 1000f, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				unregisterUpdateHandler(pTimerHandler);
				mainMenuLoadingScene.detachScene();
				createMenuChildScene();
			}
		}));
	}
	
	private void createMenuChildScene(){
		menuChildScene = new MenuScene(camera);
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.playRegion, vertexBufferObjectManager), 1.2f, 1);
		final IMenuItem optionsMenuItem  = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.optionsRegion, vertexBufferObjectManager), 1.2f, 1);
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		playMenuItem.setPosition(camera.getWidth() / 2, camera.getHeight() / 2);
		optionsMenuItem.setPosition(camera.getWidth() / 2, camera.getHeight() / 2 - 120);
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
		
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY){
		int menuItemId = pMenuItem.getID();
		
		if (menuItemId == MENU_PLAY){
			SceneManager.getInstance().loadGameScene();
			((GameScene) SceneManager.getInstance().getScene(SceneManager.SceneType.SCENE_GAME)).resetScene();
			return true;
		}
		else if (menuItemId == MENU_OPTIONS){
			return true;
		}
		return false;
	}
}