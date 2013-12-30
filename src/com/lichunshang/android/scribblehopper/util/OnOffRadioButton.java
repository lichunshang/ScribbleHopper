package com.lichunshang.android.scribblehopper.util;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;


public class OnOffRadioButton{
	
	// ----------------- Static Constants --------------
	
	public static final Color SELECTED_COLOR = new Color(0.992f, 0.659f, 0.008f);
	public static final Color UNSELECTED_COLOR = new Color(0.6f, 0.6f, 0.6f);
	public static final String ON_TEXT = "ON";
	public static final String OFF_TEXT = "OFF";
	public static final String TEXT_SEPARATOR = "     ";
	
	public static enum State{
		ON_SELECTED, OFF_SELECTED
	}
	
	// -------------------------------------------
	// ------------------ CLASS ------------------
	// -------------------------------------------
	
	private Text onButtonText;
	private Text offButtonText;
	private Text textSeparator;
	private State state;
	private boolean attached = false;
	
	public OnOffRadioButton(float posX, float posY, Font font, State state, VertexBufferObjectManager vertexBufferObjectManager){
		
		onButtonText = new Text(0, 0, font, ON_TEXT, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
				if (pSceneTouchEvent.isActionDown()){
				}
				else if (pSceneTouchEvent.isActionCancel() || !this.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
				}
				else if (pSceneTouchEvent.isActionUp()){
					setState(State.ON_SELECTED);
					onOnSelected();
				}
				return true;
			}
		};
		
		offButtonText = new Text(0, 0, font, OFF_TEXT, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
				if (pSceneTouchEvent.isActionDown()){
				}
				else if (pSceneTouchEvent.isActionCancel() || !this.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
				}
				else if (pSceneTouchEvent.isActionUp()){
					setState(State.OFF_SELECTED);
					onOffSelected();
				}
				return true;
			}
		};
		
		textSeparator = new Text(0, 0, font, TEXT_SEPARATOR, vertexBufferObjectManager);
		
		onButtonText.setAnchorCenter(0, 0);
		offButtonText.setAnchorCenter(0, 0);
		textSeparator.setAnchorCenter(0, 0);
		
		setPosition(posX, posY);
		setState(state);
		
	}
	
	private void setColorState(State state){
		if (state == State.ON_SELECTED){
			onButtonText.setColor(SELECTED_COLOR);
			offButtonText.setColor(UNSELECTED_COLOR);
		}
		else{
			onButtonText.setColor(UNSELECTED_COLOR);
			offButtonText.setColor(SELECTED_COLOR);
		}
	}
	
	public void setState(State state){
		setColorState(state);
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
	
	public void setPosition(float posX, float posY){
		onButtonText.setPosition(posX, posY);
		textSeparator.setPosition(onButtonText.getX() + onButtonText.getWidth(), onButtonText.getY());
		offButtonText.setPosition(textSeparator.getX() + textSeparator.getWidth(), onButtonText.getY());
	}
	
	public boolean attachTo(Entity entity){
		if (attached)
			return false;
		entity.attachChild(onButtonText);
		entity.attachChild(offButtonText);
		entity.attachChild(textSeparator);
		return true;
	}
	
	public boolean detachFrom(Entity entity){
		if (!attached)
			return false;
		entity.detachChild(onButtonText);
		entity.detachChild(offButtonText);
		entity.detachChild(textSeparator);
		return true;
	}
	
	public void registerTouchArea(Scene scene){
		scene.registerTouchArea(onButtonText);
		scene.registerTouchArea(offButtonText);
	}
	
	public void onOnSelected(){
		
	}
	
	public void onOffSelected(){
		
	}
}