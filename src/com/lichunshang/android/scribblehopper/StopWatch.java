package com.lichunshang.android.scribblehopper;

public class StopWatch {

	private long elapsedTime = 0, startTime = -1;
	private boolean stopped = true;
	
	public StopWatch(boolean start){
		if (start)
			start();
	}

	public void reset() {
		stop();
		elapsedTime = 0;
	}

	public void stop() {
		if (!stopped) {
			elapsedTime += System.nanoTime() - startTime;
			stopped = true;
		}
	}

	public void start() {
		if (stopped) {
			startTime = System.nanoTime();
			stopped = false;
		}
	}

	public long getElapsedTimeNano() {
		if (!stopped) {
			elapsedTime += System.nanoTime() - startTime;
			startTime = System.nanoTime();
		}
		return elapsedTime;
	}

	public long getElapsedTimeMilli() {
		return getElapsedTimeNano() / 1000000;
	}
}