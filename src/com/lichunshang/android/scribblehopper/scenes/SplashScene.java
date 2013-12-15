package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.lichunshang.android.scribblehopper.SceneManager;


public class SplashScene extends BaseScene{
	
	private Sprite splash;
	
	@Override
	public void createScene(){
		setBackground(new Background(Color.WHITE));
		splash = new Sprite(0, 0, resourcesManager.splashRegion, vertexBufferObjectManager)
		{
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		
		splash.setPosition(camera.getWidth() / 2, camera.getHeight() / 2);
		attachChild(splash);
	}
	
	@Override
	public void onBackKeyPressed(){
		
	}
	
	@Override
	public SceneManager.SceneType getSceneType(){
		return SceneManager.SceneType.SCENE_SPLASH;
	}
	
	@Override
	public void disposeScene(){
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}
	
}