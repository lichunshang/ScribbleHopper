package com.lichunshang.android.scribblehopper.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;

public class MainMenuHelpSubMenu extends BaseMainMenuSubMenu{

	private Sprite regularPlatform, spikePlatform, bouncePlatform, conveyorPlatform, unstablePlatform;
	private TimerHandler bouncePlatformAnimeTimer, unstablePlatformAnimeTimer;
	public FadeOutModifier collapseFadeOutModifier;

	public MainMenuHelpSubMenu(MainMenuScene menuScene) {
		super(menuScene);
	}
	
	@Override
	public void onCreateSubMenu() {
		createHelp();
	}
	
	public void createHelp(){
		
		regularPlatform = new Sprite(0, 0, resourcesManager.regularPlaformTextureRegion, vertexBufferObjectManager);
		spikePlatform = new Sprite(0, 0, resourcesManager.spikePlaformTextureRegion, vertexBufferObjectManager);
		bouncePlatform = new AnimatedSprite(0, 0, resourcesManager.bouncePlatformTextureRegion, vertexBufferObjectManager);
		conveyorPlatform = new AnimatedSprite(0, 0, resourcesManager.conveyorPlatformTextureRegion, vertexBufferObjectManager);
		unstablePlatform = new AnimatedSprite(0, 0, resourcesManager.unstablePlatformTextureRegion, vertexBufferObjectManager);
		
        regularPlatform.setAnchorCenter(0, 0);
        spikePlatform.setAnchorCenter(0, 0);
        bouncePlatform.setAnchorCenter(0, 0);
        conveyorPlatform.setAnchorCenter(0, 0);
        unstablePlatform.setAnchorCenter(0, 0);
		
		regularPlatform.setPosition(camera.getWidth() * 0.03f, camera.getHeight() * 0.445f);
		spikePlatform.setPosition(regularPlatform.getX(), regularPlatform.getY() - 81);
		bouncePlatform.setPosition(regularPlatform.getX(), spikePlatform.getY() - 88);
		conveyorPlatform.setPosition(regularPlatform.getX(), bouncePlatform.getY() - 81);
		unstablePlatform.setPosition(regularPlatform.getX() - 68, conveyorPlatform.getY() - 189);
		
		
		((AnimatedSprite) conveyorPlatform).animate(Const.Plaform.ConveyorRight.ANIME_SPEED);
		conveyorPlatform.setFlippedHorizontal(true);
		
		bouncePlatformAnimeTimer = new TimerHandler(Const.MenuScene.BOUNCE_ANIME_PERIOD / 1000f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				((AnimatedSprite) bouncePlatform).animate(Const.Plaform.Bounce.LONG_FRAME_DURATION, Const.Plaform.Bounce.LONG_FRAMES, false);
			}
		});
		registerUpdateHandler(bouncePlatformAnimeTimer);
		
		collapseFadeOutModifier = new FadeOutModifier(Const.Plaform.Unstable.TOTAL_ANIME_PERIOD / 1000f);
		collapseFadeOutModifier.setAutoUnregisterWhenFinished(true);
		unstablePlatformAnimeTimer = new TimerHandler(Const.MenuScene.UNSTABLE_ANIME_PERIOD / 1000f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				((AnimatedSprite) unstablePlatform).setCurrentTileIndex(0);
				unstablePlatform.setAlpha(1);
				collapseFadeOutModifier.reset();
				
				registerUpdateHandler(new TimerHandler(Const.MenuScene.UNSTABLE_ANIME_DELAY / 1000f, false, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						unregisterUpdateHandler(pTimerHandler);
						unstablePlatform.registerEntityModifier(collapseFadeOutModifier);
						((AnimatedSprite) unstablePlatform).animate(Const.Plaform.Unstable.ANIME_SPEED, false);	
					}
				}));
			}
		});
		registerUpdateHandler(unstablePlatformAnimeTimer);
		
		
		
		Text instruction = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction), vertexBufferObjectManager);
		Text instructionRegular = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_regular), vertexBufferObjectManager);
		Text instructionSpike = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_spike), vertexBufferObjectManager);
		Text instructionBounce = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_bounce), vertexBufferObjectManager);
		Text instructionConveyor = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_conveyor), vertexBufferObjectManager);
		Text instructionUnstable = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.help_instruction_unstable), vertexBufferObjectManager);
		
		instruction.setPosition(camera.getWidth() / 2, camera.getHeight() * 0.55f);
		instruction.setColor(Color.BLACK);
		
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
		
		instructionRegular.setColor(Color.BLACK);
		instructionSpike.setColor(Color.BLACK);
		instructionBounce.setColor(Color.BLACK);
		instructionConveyor.setColor(Color.BLACK);
		instructionUnstable.setColor(Color.BLACK);
		
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
}