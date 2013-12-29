package com.lichunshang.android.scribblehopper.scene;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.lichunshang.android.scribblehopper.Const;
import com.lichunshang.android.scribblehopper.R;
import com.lichunshang.android.scribblehopper.manager.DataManager;


public class MainMenuOptionSubMenu extends BaseMainMenuSubMenu{
	
	public MainMenuOptionSubMenu(MainMenuScene menuScene) {
		super(menuScene);
	}

	@Override
	public void onCreateSubMenu() {
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);

		AnimatedSprite clearHistoryButton = new AnimatedSprite(0, 0, resourcesManager.buttonTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
				if (pSceneTouchEvent.isActionDown()){
					setCurrentTileIndex(1);
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