package com.lichunshang.android.scribblehopper;

import com.badlogic.gdx.math.Vector2;

public interface Const{
	
	public static final int COMMON_DELAY_SHORT = 100;
	public static final int COMMON_DELAY_LONG = 400;
	
	public interface Manager{
		public static final int AUDIO_FADE_CALC_RATE = 50;
		public static final float AUDIO_FADE_OUT_VOLUME_STOP_THRESHOLD = 0.005f;
	}
	
	public interface MenuScene{
		public static final int LOADING_TEXT_ANIME_PERIOD = 500;
		public static final int LOADING_PERIOD = 2000;
		public static final float BUTTON_ALPHA = 0.87f;
		public static final float BUTTON_TEXT_SCALE = 1.4f;
		public static final int BOUNCE_ANIME_PERIOD = 500;
		public static final int UNSTABLE_ANIME_PERIOD = 1500;
		public static final int UNSTABLE_ANIME_DELAY = 800;
	}
	
	//general game scene settings
	public interface GameScene{
		public static final int TEXT_UPDATE_DURATION = 300;
		public static final float LEFT_RIGHT_MARGIN = 20; //margin size in pixel
		public static final float BORDER_DENSITY = 0;
		public static final float BORDER_ELASTICITY = 0.5f;
		public static final float BORDER_FRICTION = 0;
		public static final float TOP_BORDER_ELASTICITY = 0.1f;
		public static final int TOP_BORDER_HEALTH_DECREMENT = 4;
		public static final float SPEED_TO_SCORE_RATIO = 0.01f;
		public static final int SCORE_UPDATE_PERIOD = 100;
		
		public interface HUD{
			public static final int LEFT_RIGHT_MARGIN = 10;
			public static final int HEALTH_BOTTOM_MARGIN = 15;
			public static final int PAUSE_BOTTOM_MARGIN = 10;
			public static final int SCORE_RIGHT_MARGIN = 35;
		}
	}
	
	//general player settings
	public interface Player{
		
		public static final int MAX_HEALTH = 10;
		public static final int DIE_360_ROTATE_DURATION = 800;
		public static final int NUM_FLASH_WHEN_HURT = 8;
		public static final float FLASH_PERIOD_WHEN_HURT = 90;
		
		//control properties
		public static final float ACCELERATE_MULTIPLY_FACTOR = 17f;
		public static final float DEACCELERATE_MULTIPLY_FACTOR = 30f;
		public static final float ACCELEROMETER_MULTIPLY_FACTOR = 3.75f;
		public static final float ACCELEROMETER_MULTIPLY_FACTOR_LINEAR_VELOCITY = 4.5f;
		
		//physics properties
		public static final float DENSITY = 0;
		public static final float ELASTICITY = 0;
		public static final float FRICTION = 0;
		
		//vertices in pixels
		public static final Vector2[] bodyVerticesPixels = {
			new Vector2(1, 60f),
			new Vector2(-1, 60f),
			new Vector2(-1, -62.5f),
			new Vector2(1, -62.5f),
		};
		
		//vertices in Meter-Kilogram-Seconds units (Box2D default units)
		public static final Vector2[] bodyVerticesMKS = {
			new Vector2(bodyVerticesPixels[0].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[0].y / Physics.PIXEL_TO_METER_RATIO),
			new Vector2(bodyVerticesPixels[1].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[1].y / Physics.PIXEL_TO_METER_RATIO),
			new Vector2(bodyVerticesPixels[2].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
			new Vector2(bodyVerticesPixels[3].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
		};
		
		//the speed where animation switches
		public static final float IDLE_SWITCH_VELOCITY = 1f;
		public static final float WALK_SWITCH_VELOCITY = 6.5f;
		
		//Animation sprite tile index
		public static final int IDLE_INDEX_START = 0;
		public static final int IDLE_INDEX_END = 3;
		public static final int LAND_INDEX_START = 4;
		public static final int LAND_INDEX_END = 7;
		public static final int RUN_INDEX_START = 9;
		public static final int RUN_INDEX_END = 13;
		public static final int WALK_INDEX_START = 14;
		public static final int WALK_INDEX_END = 19;
		
		//Animation speed in milliseconds
		public static final long[] IDLE_ANIME_SPEED = {160, 160, 160, 160};
		public static final long[] LAND_ANIME_SPEED = {35, 35, 35, 35};
		public static final long[] RUN_ANIME_SPEED = {68, 68, 68, 68, 68};
		public static final long[] WALK_ANIME_SPEED = {90, 90, 90, 90, 90, 90};
		
		public static final int RUN_SOUND_EFFECT_SPEED = 360;
		public static final int WALK_SOUND_EFFECT_SPEED = 485;
		
		public static final float LAND_SPEED_REDUCE_FACTOR = 1f;
		
		//time in milliseconds to disable animation to prevent staggering
		public static final int ANIME_DISABLE_TIME_SHORT = 20;
		public static final int ANIME_DISABLE_TIME_LONG = 90;
	}
	
	//general physics settings
	public interface Physics{
		public static final int REFRESH_RATE = 60;
		public static final float GRAVITY = 50;
		public static final float PIXEL_TO_METER_RATIO = 32;
	}
	
	public interface Plaform{
		
		public static final float MIN_SPAWN_TIME = 1100f;
		public static final float MAX_SPAWN_TIME = 2700f;
		public static final float INITIAL_SPAWN_TIME = (MIN_SPAWN_TIME + MAX_SPAWN_TIME) / 2;
		public static final float INITIAL_SPEED = 5f;
		public static final int HEALTH_INCREMENT = 1;
		// This is to prevent the user from seeing a disabled platform when setVisible gets out of place with the update thread
		public static final int SPAWN_DISPLACEMENT = 50; 
		public static final float PLAYER_HORIZONTAL_VELOCITY_NO_LAND = Player.WALK_SWITCH_VELOCITY;
		
		//since the coordinates of collisions used in comparison might be slightly off
		public static final float COLLISION_CHECK_TOLERANCE = 0.15f;
		
		public interface Regular{
			public static final float DENSITY = 0;
			public static final float ELASTICITY = 0;
			public static final float FRICTION = 0;
			
			//vertices in pixels
			public static final Vector2[] bodyVerticesPixels = {
				new Vector2(172, 20f),
				new Vector2(-172, 20f),
				new Vector2(-172, 19.9f),
				new Vector2(172, 19.9f),
			};
			
			//vertices in Meter-Kilogram-Seconds units (Box2D default units)
			public static final Vector2[] bodyVerticesMKS = {
				new Vector2(bodyVerticesPixels[0].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[0].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[1].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[1].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[2].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[3].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
			};
		}
		
		public interface Bounce extends Regular{
			public static final float ELASTICITY = 0.70f;
			public static final float PLAYER_VELOCITY_NO_BOUNCE = 3.5f; //no player bounce under certain y velocity
			public static final float PLAYER_VELOCITY_NO_LAND = 4.5f; //no land animation under certain y velocity
			public static final long[] SHORT_FRAME_DURATION = {10, 10, 10, 25, 25, 25};
			public static final int[] SHORT_FRAMES = {1, 2, 3, 2, 1, 0};
			public static final long[] LONG_FRAME_DURATION = {10, 10, 10, 10, 10, 25, 25, 25, 25, 25};
			public static final int[] LONG_FRAMES = {1, 2, 3, 4, 5, 4, 3, 2, 1, 0};
			
			//vertices in pixels
			public static final Vector2[] bodyVerticesPixels = {
				new Vector2(172, 26f),
				new Vector2(-172, 26f),
				new Vector2(-172, 25.9f),
				new Vector2(172, 25.9f),
			};
			
			//vertices in Meter-Kilogram-Seconds units (Box2D default units)
			public static final Vector2[] bodyVerticesMKS = {
				new Vector2(bodyVerticesPixels[0].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[0].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[1].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[1].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[2].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[3].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
			};
		}
		
		public interface ConveyorLeft extends Regular{
			public static final float DISPLACEMENT_RATE = 5.5f;
			public static final long ANIME_SPEED = 110;
			
			public static final Vector2[] bodyVerticesPixels = {
				new Vector2(168, 21f),
				new Vector2(-168, 21f),
				new Vector2(-168, 20.9f),
				new Vector2(168, 20.9f),
			};
			
			//vertices in Meter-Kilogram-Seconds units (Box2D default units)
			public static final Vector2[] bodyVerticesMKS = {
				new Vector2(bodyVerticesPixels[0].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[0].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[1].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[1].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[2].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[3].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
			};
		}
		
		public interface ConveyorRight extends Regular{
			public static final float DISPLACEMENT_RATE = 5.5f;
			public static final long ANIME_SPEED = 110;
			
			public static final Vector2[] bodyVerticesPixels = ConveyorLeft.bodyVerticesPixels;
			//vertices in Meter-Kilogram-Seconds units (Box2D default units)
			public static final Vector2[] bodyVerticesMKS = ConveyorLeft.bodyVerticesMKS;
		}

		public interface Unstable extends Regular{
			public static final float COLLAPSE_TIME = 180;
			public static final long ANIME_SPEED = 60;
			public static final int NUM_FRAMES = 5;
			public static final long TOTAL_ANIME_PERIOD = ANIME_SPEED * NUM_FRAMES;
			public static final float UNSTABLE_SPAWN_DISPLACEMENT = 14; //spawn a bit lower because this platform has tall sprites
			
			public static final Vector2[] bodyVerticesPixels = {
				new Vector2(172, 72f),
				new Vector2(-172, 72f),
				new Vector2(-172, 71.9f),
				new Vector2(172, 71.9f),
			};
			
			//vertices in Meter-Kilogram-Seconds units (Box2D default units)
			public static final Vector2[] bodyVerticesMKS = {
				new Vector2(bodyVerticesPixels[0].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[0].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[1].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[1].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[2].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[3].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
			};
		}
		
		public interface Spike extends Regular{
			public static final int HEALTH_DECREMENT = 4;
			
			public static final Vector2[] bodyVerticesPixels = {
				new Vector2(172, 3f),
				new Vector2(-172, 3f),
				new Vector2(-172, 2.9f),
				new Vector2(172, 2.9f),
			};
			
			//vertices in Meter-Kilogram-Seconds units (Box2D default units)
			public static final Vector2[] bodyVerticesMKS = {
				new Vector2(bodyVerticesPixels[0].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[0].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[1].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[1].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[2].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
				new Vector2(bodyVerticesPixels[3].x / Physics.PIXEL_TO_METER_RATIO, bodyVerticesPixels[2].y / Physics.PIXEL_TO_METER_RATIO),
			};
		}
	}
}