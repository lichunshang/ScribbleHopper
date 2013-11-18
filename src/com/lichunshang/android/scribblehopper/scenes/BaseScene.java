package com.lichunshang.android.scribblehopper.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.lichunshang.android.scribblehopper.GameActivity;
import com.lichunshang.android.scribblehopper.ResourcesManager;
import com.lichunshang.android.scribblehopper.SceneManager;


public abstract class BaseScene extends Scene{
	
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	protected Engine engine;
	protected GameActivity activity;
	protected ResourcesManager resourcesManager;
	protected VertexBufferObjectManager vertexBufferObjectManager;
	protected Camera camera;
	
	public BaseScene(){
		this.resourcesManager = ResourcesManager.getInstance();
		this.engine = this.resourcesManager.engine;
		this.activity = this.resourcesManager.activity;
		this.vertexBufferObjectManager = this.resourcesManager.vertexBufferObjectManager;
		this.camera = this.resourcesManager.camera;
		createScene();
	}
	
	public Engine getEngine(){
		return this.engine;
	}
	
	public GameActivity getGameActivity(){
		return this.activity;
	}
	
	public ResourcesManager getResourcesManager(){
		return this.resourcesManager;
	}
	
	public VertexBufferObjectManager getVertexBufferObjectManager(){
		return this.vertexBufferObjectManager;
	}
	
	public Camera getCamera(){
		return this.camera;
	}
	
	//---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
	
	public abstract void createScene();
	
	public abstract void onBackKeyPressed();
	
	public abstract SceneManager.SceneType getSceneType();
	
	public abstract void disposeScene();
}