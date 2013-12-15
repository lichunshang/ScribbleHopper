
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


public class GamePauseScene extends BaseSubScene implements IOnMenuItemClickListener{
	
	private Rectangle backgroundSprite;
	private Text pauseText;
	private MenuScene menuScene;
	private final int MENU_MENU = 0;
	private final int MENU_RESUME = 1;
	
	public GamePauseScene(GameScene gameScene){
		super(gameScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		backgroundSprite = new Rectangle(camera.getWidth() / 2, camera.getHeight() / 2, camera.getWidth(), camera.getHeight(), vertexBufferObjectManager);
		backgroundSprite.setColor(0.9f, 0.9f, 0.9f);
		backgroundSprite.setAlpha(0.65f);
		attachChild(backgroundSprite);
		pauseText = new Text(camera.getWidth() / 2, camera.getHeight() * 2 / 3, resourcesManager.font_100, parentScene.getGameActivity().getString(R.string.paused_text), new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		attachChild(pauseText);
		
		createMenu();
	}

	@Override
	public void attachScene() {
		parentScene.setChildScene(this);
		attached = true;
		((GameScene) parentScene).setIgnoreUpdate(true);
	}

	@Override
	public void detachScene() {
		parentScene.clearChildScene();
		attached = false;
		((GameScene) parentScene).setIgnoreUpdate(false);
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
		AnimatedSpriteMenuItem resumeMenuItem = new AnimatedSpriteMenuItem(MENU_RESUME, parentScene.getResourcesManager().menuButtonTextureRegion, parentScene.getVertexBufferObjectManager()){
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
		menuScene.addMenuItem(resumeMenuItem);
		
		menuMenuItem.setPosition(parentScene.getCamera().getWidth() / 3, parentScene.getCamera().getHeight() / 2);
		resumeMenuItem.setPosition(parentScene.getCamera().getWidth() * 2 / 3, menuMenuItem.getY() - menuMenuItem.getHeight());
		
		resumeMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		
		Text resumeMenuItemText = new Text(0, 0, parentScene.getResourcesManager().font_70, parentScene.getGameActivity().getString(R.string.resume_text), parentScene.getVertexBufferObjectManager());
		Text menuMenuItemText = new Text(0, 0, parentScene.getResourcesManager().font_70, parentScene.getGameActivity().getString(R.string.exit_to_menu_text), parentScene.getVertexBufferObjectManager()) ;
		resumeMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItemText.setScale(0.9f);
		resumeMenuItemText.setPosition(resumeMenuItem.getWidth() / 2, resumeMenuItem.getHeight() / 2);
		menuMenuItemText.setPosition(menuMenuItem.getWidth() / 2, menuMenuItem.getHeight() / 2);
		resumeMenuItem.attachChild(resumeMenuItemText);
		menuMenuItem.attachChild(menuMenuItemText);

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuScene);
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
		else if (menuItemId == MENU_RESUME){
			((GameScene) parentScene).unPauseGame();
			return true;
		}
		
		return false;
	}
}