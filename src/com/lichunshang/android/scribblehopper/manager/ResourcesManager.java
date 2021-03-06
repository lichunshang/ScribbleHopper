package com.lichunshang.android.scribblehopper.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.lichunshang.android.scribblehopper.GameActivity;

public class ResourcesManager{
	
	//-----------------------------------------
	// VARIABLES
	//-----------------------------------------
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
	public GameActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vertexBufferObjectManager;
	
	//-----------------------------------------
	// TEXTRUEs & TEXTURE REGIONS
	//-----------------------------------------
	
	public ITextureRegion splashRegion;
	public BitmapTextureAtlas splashTextureAtlas;
	
	public Font font_40;
	public Font font_50;
	public Font font_70;
	public Font font_100;
	public Font font_120;

	private BuildableBitmapTextureAtlas initialGraphicsTextureAtlas;
	private BuildableBitmapTextureAtlas graphicsTextureAtlas0;
	
	public ITextureRegion backgroundTextureRegion;
	public ITextureRegion hudBarTextureRegion;
	public ITextureRegion spikeTextureRegion;
	public ITextureRegion heartTextureRegion;
	public ITextureRegion pauseTextureRegion;
	public ITiledTextureRegion playerTextureRegion;
	public ITiledTextureRegion regularPlaformTextureRegion;
	public ITiledTextureRegion spikePlaformTextureRegion;
	public ITiledTextureRegion bouncePlatformTextureRegion;
	public ITiledTextureRegion conveyorPlatformTextureRegion;
	public ITiledTextureRegion unstablePlatformTextureRegion;
	public ITiledTextureRegion buttonTextureRegion;
	
	public Music backgroundMusic;
	public Sound platformLandSound;
	public Sound platformBounceSound;
	public Sound platformCrackSound;
	public Sound playerDieSound;
	public Sound playerHurtSound;
	public Sound playerWalkSound0;
	public Sound playerWalkSound1;
	
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
	
	public void loadInitialResources(){
		FontFactory.setAssetBasePath("font/");
		final ITexture font_50_texture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture font_70_texture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture font_120_texture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font_50 = FontFactory.createFromAsset(activity.getFontManager(), font_50_texture, activity.getAssets(), "Sketchy.ttf", 50, true, Color.WHITE);
	    font_70 = FontFactory.createFromAsset(activity.getFontManager(), font_70_texture, activity.getAssets(), "Sketchy.ttf", 70, true, Color.WHITE);
		font_120 = FontFactory.createFromAsset(activity.getFontManager(), font_120_texture, activity.getAssets(), "Sketchy.ttf", 120, true, Color.WHITE);
	    font_50.load();
	    font_70.load();
	    font_120.load();
	    
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    initialGraphicsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 2048, TextureOptions.BILINEAR);
	    
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(initialGraphicsTextureAtlas, activity, "background.png");
		buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(initialGraphicsTextureAtlas, activity, "button.png", 1, 2);
		
		try{
			this.initialGraphicsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.initialGraphicsTextureAtlas.load();
		}
		catch (final TextureAtlasBuilderException e){
			Debug.e(e);
		}
	}
	
	public void loadRemainingResources(){
		loadFonts();
		loadGraphics();
		loadAudio();
	}
	
	public void loadFonts(){
		FontFactory.setAssetBasePath("font/");
		final ITexture font_40_texture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture font_100_texture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font_40 = FontFactory.createFromAsset(activity.getFontManager(), font_40_texture, activity.getAssets(), "Sketchy.ttf", 40, true, Color.WHITE);
	    font_100 = FontFactory.createFromAsset(activity.getFontManager(), font_100_texture, activity.getAssets(), "Sketchy.ttf", 100, true, Color.WHITE);
	    
	    font_40.load();
	    font_100.load();
	}
	
	public void loadGraphics(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		graphicsTextureAtlas0 =  new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		
		playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(graphicsTextureAtlas0, activity, "player.png", 5, 5);
		
		
		regularPlaformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(graphicsTextureAtlas0, activity, "regularPlatform.png", 1, 1);
		spikePlaformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(graphicsTextureAtlas0, activity, "spikePlatform.png", 1, 1);
		bouncePlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(graphicsTextureAtlas0, activity, "bouncePlatform.png", 1, 6);
		conveyorPlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(graphicsTextureAtlas0, activity, "conveyorPlatform.png", 1, 3);
		unstablePlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(graphicsTextureAtlas0, activity, "unstablePlatform.png", 1, 5);
		hudBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(graphicsTextureAtlas0, activity, "HUDBar.png");
		spikeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(graphicsTextureAtlas0, activity, "spike.png");
		heartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(graphicsTextureAtlas0, activity, "health.png");
		pauseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(graphicsTextureAtlas0, activity, "pause.png");
		
		try{
			this.graphicsTextureAtlas0.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.graphicsTextureAtlas0.load();
		}
		catch(final TextureAtlasBuilderException e){
			Debug.e(e);
		}
		
	}
	
	public void loadAudio(){
		try{
			backgroundMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "mfx/background.mp3");
			platformLandSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/platformLand.wav");
			platformBounceSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/platformBounce.wav");
			playerHurtSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/playerHurt.wav");
			platformCrackSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/platformCrack.wav");
			playerDieSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/playerDie.wav");
			playerWalkSound0 = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/playerWalk0.wav");
			playerWalkSound1 = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/playerWalk0.wav");
			
			backgroundMusic.setVolume(0.3f);
			platformBounceSound.setVolume(0.3f);
			platformLandSound.setVolume(3f);
			playerDieSound.setVolume(0.2f);
			
		}
		catch(IOException e){
			Debug.e(e);
		}
	}
	
	public void loadSplashScreen(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen(){
		splashTextureAtlas.unload();
		splashRegion = null;
	}
	
	/**
	 * @param engine
	 * @param activity
	 * @param camera
	 * @param vertexBufferObjectManager
	 * <br><br>
	 * We use this method at beginning of game loading, to prepare Resources Manager properly.
	 * setting all needed parameters, so we can latter access them from different classes (e.g. scenes)
	 */
	
	public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vertexBufferObjectManager){
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vertexBufferObjectManager = vertexBufferObjectManager;
	}
	
	public static ResourcesManager getInstance(){
		return INSTANCE;
	}
}