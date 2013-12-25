package com.lichunshang.android.scribblehopper.manager;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.lichunshang.android.scribblehopper.scene.BaseScene;
import com.lichunshang.android.scribblehopper.scene.GameScene;
import com.lichunshang.android.scribblehopper.scene.MainMenuScene;
import com.lichunshang.android.scribblehopper.scene.SplashScene;

public class SceneManager{
	
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	private static final SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	
	
	
	//---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------	
	
	public void setScene(BaseScene scene){
		if (currentScene != null)
			currentScene.setIgnoreUpdate(true);
		
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
		
		if (currentScene != null)
			currentScene.setIgnoreUpdate(false);
	}
	
	public void setScene(SceneType sceneType){
		if (sceneType == SceneType.SCENE_MENU){
			setScene(menuScene);
		}
		else if (sceneType == SceneType.SCENE_GAME){
			setScene(gameScene);
		}
		else if (sceneType == SceneType.SCENE_SPLASH){
			setScene(splashScene);
		}
		else if (sceneType == SceneType.SCENE_LOADING){
			setScene(loadingScene);
		}
	}
	
	public enum SceneType{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
		SCENE_PLAYER_DIE,
		SCENE_PAUSE,
	}

	public BaseScene getScene(SceneType sceneType){
		if (sceneType == SceneType.SCENE_MENU){
			return menuScene;
		}
		else if (sceneType == SceneType.SCENE_GAME){
			return gameScene;
		}
		else if (sceneType == SceneType.SCENE_SPLASH){
			return splashScene;
		}
		else if (sceneType == SceneType.SCENE_LOADING){
			return loadingScene;
		}
		return null;
	}
	
	public static SceneManager getInstance(){
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType(){
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene(){
		return currentScene;
	}
	
	//---------------------------------------------
    // CREATE / KILL SCENES
    //---------------------------------------------	
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene= new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void disposeSplashScene(){
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void createMenuScene(){
		menuScene = new MainMenuScene();
	}
	
	public void createGameScene(){
		gameScene = new GameScene();
	}
	
	public void setGameScene(){
		setScene(gameScene);
	}
	
	public void setMenuScene(){
		setScene(menuScene);
	}
}