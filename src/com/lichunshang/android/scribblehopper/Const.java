package com.lichunshang.android.scribblehopper;

import com.badlogic.gdx.math.Vector2;

public interface Const{
	
	public static final int COMMON_DELAY_SHORT = 100;
	public static final int COMMON_DELAY_LONG = 400;
	
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
	}
	
	//general player settings
	public interface Player{
		
		public static final int MAX_HEALTH = 10;
		public static final int DIE_360_ROTATE_DURATION = 800;
		public static final int NUM_FLASH_WHEN_HURT = 8;
		public static final float FLASH_PERIOD_WHEN_HURT = 90;
		
		//control properties
		public static final float ACCELERATE_MULTIPLY_FACTOR = 6f;
		public static final float DEACCELERATE_MULTIPLY_FACTOR = 13f;
		public static final float ACCELEROMETER_MULTIPLY_FACTOR = 6.5f;
		
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
		public static final long[] LAND_ANIME_SPEED = {50, 50, 50, 50};
		public static final long[] RUN_ANIME_SPEED = {70, 70, 70, 70, 70};
		public static final long[] WALK_ANIME_SPEED = {90, 90, 90, 90, 90, 90};
		
		public static final float LAND_SPEED_REDUCE_FACTOR = 1f;
		
		//time in milliseconds to disable animation to prevent staggering
		public static final int ANIME_DISABLE_TIME_SHORT = 25;
		public static final int ANIME_DISABLE_TIME_LONG = 300;
	}
	
	//general physics settings
	public interface Physics{
		public static final int REFRESH_RATE = 60;
		public static final float GRAVITY = 45;
		public static final float PIXEL_TO_METER_RATIO = 32;
	}
	
	public interface Plaform{
		
		public static final float MIN_SPAWN_TIME = 1000f;
		public static final float MAX_SPAWN_TIME = 2000f;
		public static final float INITIAL_SPAWN_TIME = (MIN_SPAWN_TIME + MAX_SPAWN_TIME) / 2;
		public static final float INITIAL_SPEED = 5f;
		public static final int HEALTH_INCREMENT = 1;
		
		//since the coordinates of collisions used in comparison might be slightly off
		public static final float COLLISION_CHECK_TOLERANCE = 0.15f;
		
		public interface Regular{
			public static final float DENSITY = 0;
			public static final float ELASTICITY = 0;
			public static final float FRICTION = 0;
		}
		
		public interface Bounce extends Regular{
			public static final float ELASTICITY = 0.70f;
			public static final float PLAYER_VELOCITY_NO_BOUNCE = 3.5f; //no player bounce under certain y velocity
			public static final float PLAYER_VELOCITY_NO_LAND = 4.5f; //no land animation under certain y velocity
		}
		
		public interface ConveyorLeft extends Regular{
			public static final float DISPLACEMENT_RATE = 4f;
		}
		
		public interface ConveyorRight extends Regular{
			public static final float DISPLACEMENT_RATE = 4f;
		}

		public interface Unstable extends Regular{
			public static final float COLLAPSE_TIME = 200f;
		}
		
		public interface Spike extends Regular{
			public static final int HEALTH_DECREMENT = 4;
		}
	}
}