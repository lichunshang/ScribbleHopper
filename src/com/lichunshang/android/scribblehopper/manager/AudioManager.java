package com.lichunshang.android.scribblehopper.manager;

import java.util.Hashtable;

import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.lichunshang.android.scribblehopper.Const;


public class AudioManager{
	
	// ---------------- Singleton Instance ----------------
	private static final AudioManager INSTANCE = new AudioManager();
	
	// ---------------- Fields ----------------
	private Engine engine;
	private Hashtable<SoundEffect, Sound> soundEffects = new Hashtable<AudioManager.SoundEffect, Sound>();
	private Hashtable<MusicEffect, Music> musicEffects = new Hashtable<MusicEffect, Music>();
	private boolean soundEffectEnabled = true;
	
	public void setSoundEffectEnabled(boolean enabled){
		soundEffectEnabled = enabled;
		DataManager.getInstance().saveSoundEffectEnabled(soundEffectEnabled);
	}
	
	public boolean isSoundEffectEnabled(){
		return soundEffectEnabled;
	}
	
	public void playSoundEffect(SoundEffect soundEffect){
		playSoundEffect(soundEffect, false);
	}
	
	public void playSoundEffect(SoundEffect soundEffect, boolean loop){
		if (soundEffectEnabled){
			soundEffects.get(soundEffect).setLooping(loop);
			soundEffects.get(soundEffect).play();
		}
	}
	
	public void stopSoundEffect(SoundEffect soundEffect){
		soundEffects.get(soundEffect).stop();
	}
	
	public void setVolume(SoundEffect soundEffect, float volume){
		soundEffects.get(soundEffect).setVolume(volume);
	}
	
	public void fadeOutSoundEffect(final SoundEffect soundEffect, float speed){
		if (speed == 0){
			return;
		}
		if (speed >= 1f){
			stopSoundEffect(soundEffect);
		}
		
		final float SPEED =  1 - speed;
		final float ORIGINAL_VOLUME = soundEffects.get(soundEffect).getVolume();
		final Sound sound = soundEffects.get(soundEffect);
		
		engine.registerUpdateHandler(new TimerHandler(Const.Manager.AUDIO_FADE_CALC_RATE / 1000f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (soundEffects.get(soundEffect).getVolume() < Const.Manager.AUDIO_FADE_OUT_VOLUME_STOP_THRESHOLD){
					sound.stop();
					sound.setVolume(ORIGINAL_VOLUME);
					engine.unregisterUpdateHandler(pTimerHandler);
				}
				else{
					sound.setVolume(sound.getVolume() * SPEED);
				}
			}
		}));
	}
	
	public void playMusic(MusicEffect musicEffect){
		playMusic(musicEffect, false);
	}
	
	public void playMusic(MusicEffect musicEffect, boolean loop){
		musicEffects.get(musicEffect).setLooping(loop);
		musicEffects.get(musicEffect).play();
	}
	
	//must be called before using this class
	public void initResources(){
		ResourcesManager resourcesManager = ResourcesManager.getInstance();
		soundEffects.put(SoundEffect.PLATFORM_LAND, resourcesManager.platformLandSound);
		soundEffects.put(SoundEffect.PLATFORM_BOUNCE, resourcesManager.platformBounceSound);
		soundEffects.put(SoundEffect.PLAYER_HURT, resourcesManager.playerHurtSound);
		soundEffects.put(SoundEffect.PLATFORM_CRACK, resourcesManager.platformCrackSound);
		soundEffects.put(SoundEffect.PLAYER_DIE, resourcesManager.playerDieSound);
		soundEffects.put(SoundEffect.PLAYER_WALK0, resourcesManager.playerWalkSound0);
		soundEffects.put(SoundEffect.PLAYER_WALK1, resourcesManager.playerWalkSound1);
	}
	
	//must be called before using this class
	public void loadAudioSettings(){
		soundEffectEnabled = DataManager.getInstance().getSoundEffectEnabled();
	}
	
	public static enum SoundEffect{
		PLATFORM_LAND, 
		PLATFORM_BOUNCE, 
		PLAYER_HURT,
		PLATFORM_CRACK,
		PLAYER_DIE,
		PLAYER_WALK0,
		PLAYER_WALK1,
	}
	
	public static enum MusicEffect{
		
	}
	
	// --------------------------------------------------------------
	// SINGLETON METHODS
	// --------------------------------------------------------------
	/**
	 * Should be called before using other non-static methods in this class
	 */
	
	public static AudioManager getInstance(){
		return INSTANCE;
	}
	
	public static void prepareManager(Engine engine){
		getInstance().engine = engine;
	}
}