package com.lichunshang.android.scribblehopper.scene;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;
import com.lichunshang.android.scribblehopper.manager.AudioManager;
import com.lichunshang.android.scribblehopper.manager.DataManager;
import com.lichunshang.android.scribblehopper.util.OnOffRadioButton;
import com.lichunshang.android.scribblehopper.util.OnOffRadioButton.State;


public class MainMenuOptionSubMenu extends BaseMainMenuSubMenu{
	
	OnOffRadioButton musicOnOffRadioButton;
	
	public MainMenuOptionSubMenu(MainMenuScene menuScene) {
		super(menuScene);
	}

	@Override
	public void onCreateSubMenu() {
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		
		//---------------------- ON / OFF Settings ---------------------
		
		Text muteSoundEffectText = new Text(camera.getWidth() * 0.1f, camera.getHeight() * 0.5f, resourcesManager.font_50, parentScene.getGameActivity().getString(R.string.sound_effect), vertexBufferObjectManager);
		muteSoundEffectText.setAnchorCenter(0, 0);
		muteSoundEffectText.setColor(Color.BLACK);
		attachChild(muteSoundEffectText);
		
		musicOnOffRadioButton = new OnOffRadioButton(camera.getWidth() * 0.665f, muteSoundEffectText.getY(), resourcesManager.font_50, State.ON_SELECTED, vertexBufferObjectManager){
			@Override
			public void onOnSelected(){
				AudioManager.getInstance().setSoundEffectEnabled(true);
			}
			
			@Override
			public void onOffSelected(){
				AudioManager.getInstance().setSoundEffectEnabled(false);
			}
		};
		
		if (AudioManager.getInstance().isSoundEffectEnabled()){
			musicOnOffRadioButton.setState(State.ON_SELECTED);
		}
		else{
			musicOnOffRadioButton.setState(State.OFF_SELECTED);
		}
		
		musicOnOffRadioButton.registerTouchArea(this);
		musicOnOffRadioButton.attach(this);
		
		//------------------ clear history button -----------------------
		AnimatedSprite clearHistoryButton = new AnimatedSprite(0, 0, resourcesManager.buttonTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
				if (pSceneTouchEvent.isActionDown()){
					setCurrentTileIndex(1);
				}
				else if (pSceneTouchEvent.isActionCancel() || !this.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
					setCurrentTileIndex(0);
				}
				else if (pSceneTouchEvent.isActionUp()){
					setCurrentTileIndex(0);
					clearHistoryClicked();
				}
				return true;
			}
		};
		registerTouchArea(clearHistoryButton);

		clearHistoryButton.setPosition(parentScene.getCamera().getWidth() * 0.69f, parentScene.getCamera().getHeight() * 0.30f);
		clearHistoryButton.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		
		Text clearHistoryMenuItemText = new Text(0, 0, parentScene.getResourcesManager().font_50, parentScene.getGameActivity().getString(R.string.clear_history), parentScene.getVertexBufferObjectManager()) ;
		clearHistoryMenuItemText.setAlpha(Const.MenuScene.BUTTON_ALPHA);
		clearHistoryMenuItemText.setScale(0.93f);
		clearHistoryMenuItemText.setPosition(clearHistoryButton.getWidth() / 2, clearHistoryButton.getHeight() / 2);
		clearHistoryMenuItemText.setColor(Color.BLACK);
		clearHistoryButton.attachChild(clearHistoryMenuItemText);
		
		attachChild(clearHistoryButton);
	}

	public void clearHistoryClicked(){
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				AlertDialog dialog = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
				dialog.setTitle("Clear Game History");
				dialog.setMessage("Delete all game history which would also clear your high score?");
				
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DataManager.getInstance().clearGameHistory();
					}
				});
				dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				dialog.show();
			}
		});
	}
}