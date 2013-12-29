package com.lichunshang.android.scribblehopper.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.lichunshang.android.scribblehopper.GameActivity;
import com.lichunshang.android.scribblehopper.manager.ResourcesManager;


public abstract class BaseSubScene extends Scene{
	protected BaseScene parentScene;
	protected Camera camera;
	protected VertexBufferObjectManager vertexBufferObjectManager;
	protected SpriteBackground background;
	protected ResourcesManager resourcesManager;
	protected GameActivity activity;
	protected Engine engine;
	
	public BaseSubScene(BaseScene parentScene){
		this.parentScene = parentScene;
		this.camera = parentScene.getCamera();
		this.vertexBufferObjectManager = parentScene.getVertexBufferObjectManager();
		this.resourcesManager = parentScene.getResourcesManager();
		this.activity = parentScene.getGameActivity();
		this.engine = parentScene.getEngine();
		createScene();
	}
	
	public abstract void createScene();
	
	public abstract void attachScene();
	
	public abstract void detachScene();
	
	public abstract void onBackKeyPressed();
}