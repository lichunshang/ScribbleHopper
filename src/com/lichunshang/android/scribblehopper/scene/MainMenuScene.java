package com.lichunshang.android.scribblehopper.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;
import com.lichunshang.android.scribblehopper.manager.DataManager;
import com.lichunshang.android.scribblehopper.manager.SceneManager;
import com.lichunshang.android.scribblehopper.util.AsynchronousTask;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private BaseSubScene currentSubMenu = null, helpSubScene, scoresSubScene, optionSubScene;
	private final int MENU_PLAY = 0;
	private final int MENU_SCORE = 1;
	private final int MENU_HELP = 2;
	private final int MENU_OPTIONS = 3;
	
	private Text subMenuNameText;
	
	private MainMenuLoadingSubScene mainMenuLoadingScene;
	
	public void createScene(){
		createBackground();
		createLoadingScene();
	}
	
	public void onBackKeyPressed(){
		if (currentSubMenu != null){
			currentSubMenu.onBackKeyPressed();
		}
		else{
			System.exit(0);
		}
	}
	
	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_MENU;
	}
	
	public void disposeScene(){
		
	}
	
	private void createBackground(){
		Text title =  new Text(camera.getWidth() / 2, camera.getHeight() * 0.82f, resourcesManager.font_120, activity.getString(R.string.game_name), vertexBufferObjectManager);
		Text titleJust = new Text(camera.getWidth() / 2, camera.getHeight() * 0.92f, resourcesManager.font_70, activity.getString(R.string.game_name_just), vertexBufferObjectManager);
		this.subMenuNameText = new Text(camera.getWidth() * 0.75f, camera.getHeight() * 0.685f, resourcesManager.font_50, "abcdefghijklmnopqrstuvwxyz0123456879", vertexBufferObjectManager);
		this.subMenuNameText.setText("");
		this.subMenuNameText.setRotation(15f);
		title.setRotation(15f);
		attachChild(new Sprite(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.backgroundTextureRegion, vertexBufferObjectManager));
		attachChild(title);
		attachChild(titleJust);
		attachChild(subMenuNameText);
	}
	
	private void createLoadingScene(){
		mainMenuLoadingScene = new MainMenuLoadingSubScene(this);
		mainMenuLoadingScene.attachScene();
		
		AsynchronousTask asyncTask = new AsynchronousTask() {
			
			@Override
			protected void task() {
				resourcesManager.loadRemainingResources();
				SceneManager.getInstance().createGameScene();
				createSubScenes();
				DataManager.getInstance().loadRecord();
			}
			
			@Override
			protected void onFinished() {
				registerUpdateHandler(new TimerHandler(Const.MenuScene.LOADING_PERIOD / 1000f, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						unregisterUpdateHandler(pTimerHandler);
						mainMenuLoadingScene.detachScene();
						createMenuChildScene();
						createBackGroundElements();
						setChildScene(menuChildScene);
					}
				}));
			}
		};
		
		asyncTask.execute();
	}
	
	private void createBackGroundElements(){
		AnimatedSprite playerSprite = new AnimatedSprite(camera.getWidth() * 0.3f, camera.getHeight() * 0.71f, resourcesManager.playerTextureRegion, vertexBufferObjectManager);
		AnimatedSprite conveyorPlatform = new AnimatedSprite(playerSprite.getX(), playerSprite.getY() - 83, resourcesManager.conveyorPlatformTextureRegion, vertexBufferObjectManager);
		Sprite unstablePlatform = new AnimatedSprite(0, camera.getHeight() * 0.1f, resourcesManager.unstablePlatformTextureRegion, vertexBufferObjectManager);  
		Sprite bouncePlatfrom = new AnimatedSprite(camera.getWidth() * 0.85f, camera.getHeight() * 0.55f, resourcesManager.bouncePlatformTextureRegion, vertexBufferObjectManager);
		Sprite spikePlatform = new AnimatedSprite(camera.getWidth(), camera.getHeight() * 0.4f, resourcesManager.spikePlaformTextureRegion, vertexBufferObjectManager);
		Sprite hudBarSprite = new Sprite(0, 0, resourcesManager.hudBarTextureRegion, vertexBufferObjectManager);
		hudBarSprite.setAnchorCenter(0, 0);
		Sprite spike = new Sprite(0, 0, resourcesManager.spikeTextureRegion, vertexBufferObjectManager);
		spike.setPosition(0, camera.getHeight() - spike.getHeight());
		spike.setAnchorCenter(0, 0);
		
		playerSprite.animate(Const.Player.RUN_ANIME_SPEED, Const.Player.RUN_INDEX_START, Const.Player.RUN_INDEX_END, true);
		conveyorPlatform.animate(Const.Plaform.ConveyorRight.ANIME_SPEED);
		
		menuChildScene.attachChild(bouncePlatfrom);
		menuChildScene.attachChild(unstablePlatform);
		menuChildScene.attachChild(spikePlatform);
		
		attachChild(playerSprite);
		attachChild(conveyorPlatform);
		attachChild(spike);
		attachChild(hudBarSprite);
	}
	
	public void createSubScenes(){
		helpSubScene = new MainMenuHelpSubMenu(this);
		scoresSubScene = new MainMenuScoreSubMenu(this);
	}
	
	private void createMenuChildScene(){
		menuChildScene = new MenuScene(camera);
		
		AnimatedSpriteMenuItem playMenuItem = new AnimatedSpriteMenuItem(MENU_PLAY, resourcesManager.buttonTextureRegion, vertexBufferObjectManager){
			@Override
			public void onSelected(){
				setCurrentTileIndex(1);
			}
			
			@Override
			public void onUnselected(){
				setCurrentTileIndex(0);
			}
		};
		AnimatedSpriteMenuItem helpMenuItem = new AnimatedSpriteMenuItem(MENU_HELP, resourcesManager.buttonTextureRegion, vertexBufferObjectManager){
			@Override
			public void onSelected(){
				setCurrentTileIndex(1);
			}
			
			@Override
			public void onUnselected(){
				setCurrentTileIndex(0);
			}
		};
		
		AnimatedSpriteMenuItem scoreMenuItem = new AnimatedSpriteMenuItem(MENU_SCORE, resourcesManager.buttonTextureRegion, vertexBufferObjectManager){
			@Override
			public void onSelected(){
				setCurrentTileIndex(1);
			}
			
			@Override
			public void onUnselected(){
				setCurrentTileIndex(0);
			}
		};
		AnimatedSpriteMenuItem optionMenuItem = new AnimatedSpriteMenuItem(MENU_OPTIONS, resourcesManager.buttonTextureRegion, vertexBufferObjectManager){
			@Override
			public void onSelected(){
				setCurrentTileIndex(1);
			}
			
			@Override
			public void onUnselected(){
				setCurrentTileIndex(0);
			}
		};
		
		final int OFFSET = 38;
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(helpMenuItem);
		menuChildScene.addMenuItem(scoreMenuItem);
		menuChildScene.addMenuItem(optionMenuItem);
		
		playMenuItem.setPosition(camera.getWidth() * 3 / 10 + OFFSET, camera.getHeight() * 0.45f);
		helpMenuItem.setPosition(camera.getWidth() * 4 / 10 + OFFSET, playMenuItem.getY() - playMenuItem.getHeight());
		scoreMenuItem.setPosition(camera.getWidth() * 5 / 10 + OFFSET, helpMenuItem.getY() - helpMenuItem.getHeight());
		optionMenuItem.setPosition(camera.getWidth() * 6 / 10 + OFFSET, scoreMenuItem.getY() - scoreMenuItem.getHeight());
		
		playMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		helpMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		scoreMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		optionMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		
		Text playMenuItemText = new Text(playMenuItem.getWidth() / 2, playMenuItem.getHeight() / 2, resourcesManager.font_70, activity.getString(R.string.menu_play_text), vertexBufferObjectManager);
		Text helpMenuItemText = new Text(helpMenuItem.getWidth() / 2, helpMenuItem.getHeight() / 2, resourcesManager.font_70, activity.getString(R.string.menu_help_text), vertexBufferObjectManager);
		Text scoreMenuItemText = new Text(scoreMenuItem.getWidth() / 2, scoreMenuItem.getHeight() / 2, resourcesManager.font_70, activity.getString(R.string.menu_score_text), vertexBufferObjectManager);
		Text optionMenuItemText = new Text(optionMenuItem.getWidth() / 2, optionMenuItem.getHeight() / 2, resourcesManager.font_70, activity.getString(R.string.menu_options_text), vertexBufferObjectManager) ;
		
		helpMenuItemText.setScale(0.9f);
		
		playMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		helpMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		scoreMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		optionMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		
		playMenuItem.attachChild(playMenuItemText);
		helpMenuItem.attachChild(helpMenuItemText);
		scoreMenuItem.attachChild(scoreMenuItemText);
		optionMenuItem.attachChild(optionMenuItemText);

		menuChildScene.setBackgroundEnabled(false);

		menuChildScene.setOnMenuItemClickListener(this);
		
	}
	
	public void loadMainMenu(){
		setChildScene(menuChildScene);
		currentSubMenu = null;
		subMenuNameText.setText("");
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY){
		int menuItemId = pMenuItem.getID();
		
		if (menuItemId == MENU_PLAY){
			SceneManager.getInstance().setGameScene();
			((GameScene) SceneManager.getInstance().getScene(SceneManager.SceneType.SCENE_GAME)).resetScene();
			return true;
		}
		else if (menuItemId == MENU_HELP){
			currentSubMenu = helpSubScene;
			helpSubScene.attachScene();
			subMenuNameText.setText(activity.getString(R.string.menu_help_text));
			return true;
		}
		else if (menuItemId == MENU_SCORE){
			currentSubMenu = scoresSubScene;
			scoresSubScene.attachScene();
			((MainMenuScoreSubMenu) scoresSubScene).setValues();
			subMenuNameText.setText(activity.getString(R.string.menu_score_text));
			return true;
		}
		else if (menuItemId == MENU_OPTIONS){
			return true;
		}
		return false;
	}
}