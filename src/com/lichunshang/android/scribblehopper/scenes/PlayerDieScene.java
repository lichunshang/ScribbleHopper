package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;
import com.lichunshang.android.scribblehopper.SceneManager;

public class PlayerDieScene extends BaseSubScene implements IOnMenuItemClickListener{
	
	private Rectangle backgroundSprite;
	private Text scoreText;
	private Text highScoreText;
	private Text gameOverText;
	private MenuScene menuScene;
	private final int MENU_MENU = 0;
	private final int MENU_PLAY_AGAIN = 1;
	
	public PlayerDieScene(GameScene gameScene){
		super(gameScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		backgroundSprite = new Rectangle(camera.getWidth() / 2, camera.getHeight() / 2, camera.getWidth(), camera.getHeight(), vertexBufferObjectManager);
		backgroundSprite.setColor(0.9f, 0.9f, 0.9f);
		backgroundSprite.setAlpha(0.65f);
		attachChild(backgroundSprite);
		
		scoreText = new Text(camera.getWidth() / 2, camera.getHeight() * 2 / 3, resourcesManager.font_50, parentScene.getGameActivity().getString(R.string.score_text) + ": 0123456879", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		highScoreText = new Text(camera.getWidth() / 2, scoreText.getY() - scoreText.getHeight(), resourcesManager.font_50, parentScene.getGameActivity().getString(R.string.high_score_text) + ": 0123456879", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		
		gameOverText = new Text(camera.getWidth() / 2, camera.getHeight() * 4 / 5, resourcesManager.font_100, parentScene.getGameActivity().getString(R.string.game_over_text), new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		
		attachChild(gameOverText);
		attachChild(highScoreText);
		attachChild(scoreText);
		
		createMenu();
	}

	@Override
	public void attachScene() {
		parentScene.setChildScene(this);
		attached = true;
	}

	@Override
	public void detachScene() {
		parentScene.clearChildScene();
		((GameScene) parentScene).resetScene();
		attached = false;
	}

	@Override
	public void onBackKeyPressed() {
		detachScene();
	}
	
	public void createMenu(){
		menuScene = new MenuScene(parentScene.getCamera());
		
		AnimatedSpriteMenuItem menuMenuItem = new AnimatedSpriteMenuItem(MENU_MENU, parentScene.getResourcesManager().menuButtonTextureRegion, parentScene.getVertexBufferObjectManager()){
			@Override
			public void onSelected(){
				setCurrentTileIndex(1);
			}
			
			@Override
			public void onUnselected(){
				setCurrentTileIndex(0);
			}
		};
		AnimatedSpriteMenuItem playAgainMenuItem = new AnimatedSpriteMenuItem(MENU_PLAY_AGAIN, parentScene.getResourcesManager().menuButtonTextureRegion, parentScene.getVertexBufferObjectManager()){
			@Override
			public void onSelected(){
				setCurrentTileIndex(1);
			}
			
			@Override
			public void onUnselected(){
				setCurrentTileIndex(0);
			}
		};
		
		menuScene.addMenuItem(menuMenuItem);
		menuScene.addMenuItem(playAgainMenuItem);
		
		menuMenuItem.setPosition(parentScene.getCamera().getWidth() / 3, parentScene.getCamera().getHeight() / 2);
		playAgainMenuItem.setPosition(parentScene.getCamera().getWidth() * 2 / 3, menuMenuItem.getY() - menuMenuItem.getHeight());
		
		playAgainMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		
		Text resumeMenuItemText = new Text(0, 0, parentScene.getResourcesManager().font_70, parentScene.getGameActivity().getString(R.string.play_again_text), parentScene.getVertexBufferObjectManager());
		Text menuMenuItemText = new Text(0, 0, parentScene.getResourcesManager().font_70, parentScene.getGameActivity().getString(R.string.exit_to_menu_text), parentScene.getVertexBufferObjectManager()) ;
		resumeMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItemText.setScale(0.9f);
		resumeMenuItemText.setPosition(playAgainMenuItem.getWidth() / 2, playAgainMenuItem.getHeight() / 2);
		menuMenuItemText.setPosition(menuMenuItem.getWidth() / 2, menuMenuItem.getHeight() / 2);
		playAgainMenuItem.attachChild(resumeMenuItemText);
		menuMenuItem.attachChild(menuMenuItemText);

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuScene);
	}
	
	public void setScoreText(int score){
		scoreText.setText("Score: " + Integer.toString(score));
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		int menuItemId = pMenuItem.getID();
		
		if (menuItemId == MENU_MENU){
			parentScene.clearChildScene();
			attached = false;
			SceneManager.getInstance().loadMenuScene();
			return true;
		}
		else if (menuItemId == MENU_PLAY_AGAIN){
			detachScene();
			return true;
		}
		
		return false;
	}
}