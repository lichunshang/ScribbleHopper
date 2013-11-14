package com.lichunshang.android.scribblehopper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener{
	
	public float[] accelerometer = {0f, 0f, 0f};
	
	public float getAccelerometerX(){
		return accelerometer[0];
	}
	
	public float getAccelerometerY(){
		return accelerometer[1];
	}
	
	public float getAccelerometerZ(){
		return accelerometer[2];
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy){
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event){
		synchronized (this) {
			int eventType = event.sensor.getType();
			
			if (eventType == Sensor.TYPE_ACCELEROMETER){
				accelerometer[0] = event.values[0];
				accelerometer[1] = event.values[1];
				accelerometer[2] = event.values[2];
			}
		}
	}
	
	
	
}