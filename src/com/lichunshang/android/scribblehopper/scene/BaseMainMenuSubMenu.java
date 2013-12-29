package com.lichunshang.android.scribblehopper.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.text.Text;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;

public abstract class BaseMainMenuSubMenu extends BaseSubScene implements IOnMenuItemClickListener{

	protected MenuScene menuScene;
	protected final int MENU_MENU = 99;

	public BaseMainMenuSubMenu(MainMenuScene menuScene) {
		super(menuScene);
		onCreateSubMenu();
	}
	
	public abstract void onCreateSubMenu();

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		createMenu();
	}

	@Override
	public void attachScene() {
		parentScene.setChildScene(this);
	}

	@Override
	public void detachScene() {
		parentScene.clearChildScene();
		((MainMenuScene) parentScene).loadMainMenu();
	}

	@Override
	public void onBackKeyPressed() {
		detachScene();
	}
	
	public void createMenu(){
		menuScene = new MenuScene(parentScene.getCamera());
		
		AnimatedSpriteMenuItem menuMenuItem = new AnimatedSpriteMenuItem(MENU_MENU, parentScene.getResourcesManager().buttonTextureRegion, parentScene.getVertexBufferObjectManager()){
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
		
		menuMenuItem.setPosition(parentScene.getCamera().getWidth() * 0.69f, parentScene.getCamera().getHeight() * 0.117f);
		menuMenuItem.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		
		Text menuMenuItemText = new Text(0, 0, parentScene.getResourcesManager().font_70, parentScene.getGameActivity().getString(R.string.menu_text), parentScene.getVertexBufferObjectManager()) ;
		menuMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		menuMenuItemText.setPosition(menuMenuItem.getWidth() / 2, menuMenuItem.getHeight() / 2);
		menuMenuItem.attachChild(menuMenuItemText);

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		int menuItemId = pMenuItem.getID();
		
		if (menuItemId == MENU_MENU){
			detachScene();
			return true;
		}
		return false;
	}
}