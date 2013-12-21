package com.lichunshang.android.scribblehopper;

import android.os.AsyncTask;


public abstract class AsynchronousTask extends AsyncTask<Void, Void, Void>{
	
	protected abstract void task();
	protected abstract void onFinished();

	@Override
	protected Void doInBackground(Void... params) {
		task();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result){
		onFinished();
	}
}