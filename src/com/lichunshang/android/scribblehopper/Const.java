package com.lichunshang.android.scribblehopper;


public interface Const{
	
	//general game scene settings
	public class GameScene{
		public static final float LEFT_RIGHT_MARGIN = 20; //margin size in pixel
		public static final float BORDER_DENSITY = 0;
		public static final float BORDER_ELASTICITY = 0.4f;
		public static final float BORDER_FRICTION = 0;
	}
	
	//general player settings
	public class Player{
		
		public static final float MAX_SPEED_ALLOWED_WHEN_ACCELERATE = 25f;
		public static final float ACCELERATE_MULTIPLY_FACTOR = 7.5f;
		public static final float DEACCELERATE_MULTIPLY_FACTOR = 13.5f;
		public static final float ACCELEROMETER_MULTIPLY_FACTOR = 3f;
		
		public static final float DENSITY = 0;
		public static final float ELASTICITY = 0;
		public static final float FRICTION = 0;
		
		//the speed where animation switches
		public static final float IDLE_SWITCH_VELOCITY = 1f;
		public static final float WALK_SWITCH_VELOCITY = 8.3f;
		
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
		public static final long[] LAND_ANIME_SPEED = {40, 40, 40, 40};
		public static final long[] RUN_ANIME_SPEED = {70, 70, 70, 70, 70};
		public static final long[] WALK_ANIME_SPEED = {90, 90, 90, 90, 90, 90} ;
		
		//run animation effect offset, used to reduce staggering
		public static final float RUN_ANIME_OFFSET = 1.5f;
		//time in milliseconds to disable animation to prevent staggering
		public static final int ANIME_DISABLE_TIME_SHORT = 25;
		public static final int ANIME_DISABLE_TIME_LONG = 71;
	}
	
	//general physics settings
	public class Physics{
		public static final int REFRESH_RATE = 60;
		public static final float GRAVITY = 40;
		public static final float PIXEL_TO_METER_RATIO = 32;
	}
	
	public class Plaform{
		
		public static final float INITIAL_SPAWN_DISTANCE = 350f;
		public static final float MIN_SPAWN_DISTANCE = 250f;
		public static final float MAX_SPAWN_DISTANCE = 420f;
		public static final float INITIAL_SPEED = 4.5f;
		
		public class Regular{
			public static final float DENSITY = 0;
			public static final float ELASTICITY = 0;
			public static final float FRICTION = 0;
		}
	}

}