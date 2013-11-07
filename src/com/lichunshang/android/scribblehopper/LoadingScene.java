package com.lichunshang.android.scribblehopper;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

public class LoadingScene extends BaseScene{
	
	@Override
	public void createScene(){
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.font, "Loading", vertexBufferObjectManager));
	}
	
	@Override
	public void onBackKeyPressed(){
		return;
	}
	
	@Override
	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_LOADING;
	}
	
	@Override
	public void disposeScene(){
		
	}
}