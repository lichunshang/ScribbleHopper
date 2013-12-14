package com.lichunshang.android.scribblehopper;

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
	
	//----------------- Splash Scene -----------------
	public ITextureRegion splashRegion;
	public BitmapTextureAtlas splashTextureAtlas;
	
	//----------------- Menu Scene ------------------
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	public ITextureRegion menuBackgroundRegion;
	public ITextureRegion playRegion;
	public ITextureRegion optionsRegion;
	
	public Font font;
	
	//----------------- Game Scene ------------------
	public BuildableBitmapTextureAtlas gameTextureAtlas;

	public ITextureRegion gameBackgroundTextureRegion;
	public ITextureRegion gameHUDBarTextureRegion;
	public ITextureRegion gameSpikeTextureRegion;
	public ITextureRegion gameHeartTextureRegion;
	public ITiledTextureRegion gamePlayerTextureRegion;
	public ITiledTextureRegion regularPlaformTextureRegion;
	public ITiledTextureRegion spikePlaformTextureRegion;
	public ITiledTextureRegion bouncePlatformTextureRegion;
	public ITiledTextureRegion conveyorPlatformTextureRegion;
	public ITiledTextureRegion unstablePlatformTextureRegion;
	
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
	
	public void loadMenuResources(){
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}
	
	public void loadGameResource(){
		loadGameGraphic();
		loadGameFonts();
		loadGameAudio();
	}
	
	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		
		menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
		playRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
		optionsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
		gameBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "background.png");
		
		try{
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		}
		catch (final TextureAtlasBuilderException e){
			Debug.e(e);
		}
	}
	
	private void loadMenuFonts(){
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Sketchy.ttf", 50, true, Color.BLACK, 0, Color.BLACK);
	    font.load();
	}
	
	private void loadMenuAudio(){
		
	}
	
	private void loadGameGraphic(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		gameTextureAtlas =  new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		
		gamePlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "player.png", 5, 5);
		
		
		regularPlaformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "regularPlatform.png", 1, 1);
		spikePlaformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "spikePlatform.png", 1, 1);
		bouncePlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "bouncePlatform.png", 1, 6);
		conveyorPlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "conveyorPlatform.png", 1, 3);
		unstablePlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "unstablePlatform.png", 1, 5);
		gameHUDBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "HUDBar.png");
		gameSpikeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "spike.png");
		gameHeartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "heart.png");
		
		try{
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
		}
		catch(final TextureAtlasBuilderException e){
			Debug.e(e);
		}
		
	}
	
	private void loadGameFonts(){
		
	}
	
	private void loadGameAudio(){
		
	}
	
	public void loadSplashScreen(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
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