package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;
import com.lichunshang.android.scribblehopper.SceneManager;

public class MainMenuHelpSubScene extends BaseSubScene implements IOnMenuItemClickListener{

	private MenuScene menuScene;
	private final int MENU_MENU = 0;

	public MainMenuHelpSubScene(MainMenuScene menuScene) {
		super(menuScene);
	}

	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		createHelp();
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
	
	public void createHelp(){
		
		Sprite regularPlatform = new Sprite(0, 0, resourcesManager.regularPlaformTextureRegion, vertexBufferObjectManager);
		Sprite spikePlatform = new Sprite(0, 0, resourcesManager.spikePlaformTextureRegion, vertexBufferObjectManager);
		AnimatedSprite bouncePlatform = new AnimatedSprite(0, 0, resourcesManager.bouncePlatformTextureRegion, vertexBufferObjectManager);
		AnimatedSprite conveyorPlatform = new AnimatedSprite(0, 0, resourcesManager.conveyorPlatformTextureRegion, vertexBufferObjectManager);
		AnimatedSprite unstablePlatform = new AnimatedSprite(0, 0, resourcesManager.unstablePlatformTextureRegion, vertexBufferObjectManager);
		
		regularPlatform.setAnchorCenter(0, 0);
		spikePlatform.setAnchorCenter(0, 0);
		bouncePlatform.setAnchorCenter(0, 0);
		conveyorPlatform.setAnchorCenter(0, 0);
		unstablePlatform.setAnchorCenter(0, 0);
		
		regularPlatform.setPosition(camera.getWidth() * 0.02f, camera.getHeight() * 0.445f);
		spikePlatform.setPosition(regularPlatform.getX(), regularPlatform.getY() - 81);
		bouncePlatform.setPosition(regularPlatform.getX(), spikePlatform.getY() - 88);
		conveyorPlatform.setPosition(regularPlatform.getX(), bouncePlatform.getY() - 81);
		unstablePlatform.setPosition(regularPlatform.getX() - 68, conveyorPlatform.getY() - 189);
		
		Text instruction = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction), vertexBufferObjectManager);
		Text instructionRegular = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_regular), vertexBufferObjectManager);
		Text instructionSpike = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_spike), vertexBufferObjectManager);
		Text instructionBounce = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_bounce), vertexBufferObjectManager);
		Text instructionConveyor = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_conveyor), vertexBufferObjectManager);
		Text instructionUnstable = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_unstable), vertexBufferObjectManager);
		
		instruction.setPosition(camera.getWidth() / 2, camera.getHeight() * 0.55f);
		
		instructionRegular.setAnchorCenter(0, 0);
		instructionSpike.setAnchorCenter(0, 0);
		instructionBounce.setAnchorCenter(0, 0);
		instructionConveyor.setAnchorCenter(0, 0);
		instructionUnstable.setAnchorCenter(0, 0);
		
		instructionRegular.setPosition(regularPlatform.getX() + regularPlatform.getWidth() + 20, regularPlatform.getY());
		instructionSpike.setPosition(instructionRegular.getX(), instructionRegular.getY() - 85);
		instructionBounce.setPosition(instructionRegular.getX(), instructionSpike.getY() - 85);
		instructionConveyor.setPosition(instructionRegular.getX(), instructionBounce.getY() - 85);
		instructionUnstable.setPosition(instructionRegular.getX(), instructionConveyor.getY() - 85);
		
		attachChild(instruction);
		attachChild(instructionRegular);
		attachChild(instructionSpike);
		attachChild(instructionBounce);
		attachChild(instructionConveyor);
		attachChild(instructionUnstable);
		attachChild(regularPlatform);
		attachChild(spikePlatform);
		attachChild(bouncePlatform);
		attachChild(conveyorPlatform);
		attachChild(unstablePlatform);
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