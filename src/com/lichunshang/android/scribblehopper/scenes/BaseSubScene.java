package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.lichunshang.android.scribblehopper.ResourcesManager;


public abstract class BaseSubScene extends Scene{
	protected BaseScene parentScene;
	protected Camera camera;
	protected VertexBufferObjectManager vertexBufferObjectManager;
	protected SpriteBackground background;
	protected ResourcesManager resourcesManager;
	protected boolean attached = false;
	
	public BaseSubScene(BaseScene parentScene){
		this.parentScene = parentScene;
		this.camera = parentScene.getCamera();
		this.vertexBufferObjectManager = parentScene.getVertexBufferObjectManager();
		this.resourcesManager = parentScene.getResourcesManager();
		createScene();
	}
	
	public abstract void createScene();
	
	public abstract void attachScene();
	
	public abstract void detachScene();
	
	public abstract void onBackKeyPressed();
}