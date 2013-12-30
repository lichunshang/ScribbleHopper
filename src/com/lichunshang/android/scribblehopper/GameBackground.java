package com.lichunshang.android.scribblehopper;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.lichunshang.android.scribblehopper.manager.ResourcesManager;


public class GameBackground{
	
	private final static int BACKGROUND_OFFSET = 15; //to avoid the seams of two sprites from showing
	
	private Sprite background1;
	private Sprite background2;
	private Camera camera;
	
	public GameBackground(Camera camera, VertexBufferObjectManager vertexBufferObjectManager){
		background1 = new Sprite(0, 0, ResourcesManager.getInstance().backgroundTextureRegion, vertexBufferObjectManager);
		background2 = new Sprite(0, 0, ResourcesManager.getInstance().backgroundTextureRegion, vertexBufferObjectManager);
		
		background1.setAnchorCenter(0, 0);
		background2.setAnchorCenter(0, 0);
		
		background1.setPosition(0, 0);
		background2.setPosition(0, 0 - background1.getHeight() + BACKGROUND_OFFSET);
		
		this.camera = camera;
	}
	
	//note speed is in MKS
	public void onUpdate(float speed){
		float amountToMove = (speed / Const.Physics.REFRESH_RATE) * Const.Physics.PIXEL_TO_METER_RATIO; 
		
		background1.setPosition(0, background1.getY() + amountToMove);
		background2.setPosition(0, background2.getY() + amountToMove);
		
		if (background1.getY() >= camera.getHeight()){
			background1.setPosition(0, background2.getY() - background2.getHeight() + BACKGROUND_OFFSET);
		}
		if (background2.getY() >= camera.getHeight()){
			background2.setPosition(0, background1.getY() - background1.getHeight() + BACKGROUND_OFFSET);
		}
	}
	
	public void attachTo(Entity entity){
		entity.attachChild(background1);
		entity.attachChild(background2);
	}
	
	public void detachFrom(Entity entity){
		entity.detachChild(background1);
		entity.detachChild(background2);
	}
}