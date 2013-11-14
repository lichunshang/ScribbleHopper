package com.lichunshang.android.scribblehopper;


public interface Const{
	
	//general game scene settings
	public class GameScene{
		public static final float LEFT_RIGHT_MARGIN = 20; //margin size in pixel
	}
	
	//general player settings
	public class Player{
		
		public static final float ACCELEROMETER_MULTIPLY_FACTOR = 5;
		
		public static final float DENSITY = 0;
		public static final float ELASTICITY = 0;
		public static final float FRICTION = 0;
		
		//the speed where animation switches
		public static final float IDLE_SWITCH_VELOCITY = 1.2f;
		public static final float WALK_SWITCH_VELOCITY = 6.2f;
		//public static final float RUN_SWITCH_VELOCITY = 8f;
		
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
		public static final long[] LAND_ANIME_SPEED = {90, 90, 90, 90};
		public static final long[] RUN_ANIME_SPEED = {70, 70, 70, 70, 70};
		public static final long[] WALK_ANIME_SPEED = {90, 90, 90, 90, 90, 90} ;
		
		//run animation effect offset, used to reduce staggering
		public static final float WALK_ANIME_OFFSET = 0.2f;
		public static final float RUN_ANIME_OFFSET = 1f;
		
		//the vector for the fixture vertices presented as ratio
		//to the width and height of each frame
		//image = {717 x 828}, center = {358, 414}
		//vertices = {292, 264}, {247, 12}, {350, 12}
		public static final float [][] FIXTURE_VERTICE_RATIO = {
			{(-66f  / 717f) / Physics.PIXEL_TO_METER_RATIO, (-150f / 828f) / Physics.PIXEL_TO_METER_RATIO},
			{(-111f / 717f) / Physics.PIXEL_TO_METER_RATIO, (-402f / 828f) / Physics.PIXEL_TO_METER_RATIO},
			{(-8f   / 717f) / Physics.PIXEL_TO_METER_RATIO, (-402f / 828f) / Physics.PIXEL_TO_METER_RATIO},
		};
	}
	
	//general physics settings
	public class Physics{
		public static final float GRAVITY = 40;
		public static final float PIXEL_TO_METER_RATIO = 32;
	}
	
	public class Plaform{
		
		public static final float INITIAL_SPEED = 3f;
		
		public class Regular{
			public static final float DENSITY = 0;
			public static final float ELASTICITY = 0;
			public static final float FRICTION = 0;
		}
	}

}