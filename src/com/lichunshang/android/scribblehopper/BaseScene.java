package com.lichunshang.android.scribblehopper;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class BaseScene extends Scene{
	
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	protected Engine engine;
	protected GameActivity activity;
	protected ResourcesManager resourcesManager;
	protected VertexBufferObjectManager vertexBufferObjectManager;
	protected BoundCamera camera;
	
	public BaseScene(){
		this.resourcesManager = ResourcesManager.getInstance();
		this.engine = this.resourcesManager.engine;
		this.activity = this.resourcesManager.activity;
		this.vertexBufferObjectManager = this.resourcesManager.vertexBufferObjectManager;
		this.camera = this.resourcesManager.camera;
		createScene();
	}
	
	//---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
	
	public abstract void createScene();
	
	public abstract void onBackKeyPressed();
	
	public abstract SceneManager.SceneType getSceneType();
	
	public abstract void disposeScene();
}