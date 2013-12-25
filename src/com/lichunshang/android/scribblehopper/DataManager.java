package com.lichunshang.android.scribblehopper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.andengine.util.debug.Debug;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class DataManager{
	
	// ---------------- Singleton Instance ----------------
	private static final DataManager INSTANCE = new DataManager();
	
	// ---------------- Constants ----------------
	private static final String HIGH_SCORE_KEY = "HIGH_SCORE_KEY";
	private static final int DEFAULT_HIGH_SCORE = 0;
	private static final String GAME_RECORDS_FILE_NAME = "RECORDS.shdata";
	
	public GameActivity activity;
	private SharedPreferences sharedPreferences; 
	private File gameRecordFile;
	private ArrayList<GameRecord> gameRecordsList = new ArrayList<GameRecord>();

	// ---------------- High Scores ----------------
	public void saveHighScore(int score){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(HIGH_SCORE_KEY, score);
		editor.commit();
	}
	
	public int getHighScore(){
		return sharedPreferences.getInt(HIGH_SCORE_KEY, DEFAULT_HIGH_SCORE);
	}
	
	public void clearHighScore(){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(HIGH_SCORE_KEY, DEFAULT_HIGH_SCORE);
		editor.commit();
	}
	
	// ---------------- Game Records ----------------
	
	/**
	 * You should load previous records before doing anything else with records
	 * in this class
	 */
	public void loadRecord(){
		try{
			BufferedReader fileReader = new BufferedReader(new FileReader(gameRecordFile));
			String line = fileReader.readLine();
			
			while (line != null && !line.trim().matches("")){
				line = line.trim();
				gameRecordsList.add(new GameRecord(line));
				line = fileReader.readLine();
			}
			
			fileReader.close();
		}
		catch(FileNotFoundException e){
			Debug.e(e);
		}
		catch (IOException e) {
			Debug.e(e);
		}
	}
	
	public void saveRecord(GameRecord record){
		String recordLine = record.getRecordLine();
		
		try{
			FileWriter fileWriter = new FileWriter(gameRecordFile, true);
			fileWriter.write(recordLine + "\n");
			fileWriter.close();
			
			gameRecordsList.add(record);
		}
		catch(IOException e){
			Debug.e(e);
		}
	}
	
	public void clearGameRecords(){
		try{
			FileWriter fileWriter = new FileWriter(gameRecordFile, true);
			fileWriter.write("");
			fileWriter.close();
			
			gameRecordsList.clear();
		}
		catch(IOException e){
			Debug.e(e);
		}
	}
	
	public void debugRecords(){
		for (GameRecord records : gameRecordsList){
			Log.w("checkstuff", "Record Debug:" + records.getRecordLine().replaceAll(GameRecord.DELIMITOR, ","));
		}
	}
	
	// --------------------------------------------------------------
	// SINGLETON METHODS
	// --------------------------------------------------------------
	/**
	 * Should be called before using other non-static methods in this class
	 */
	public static void prepareManager(GameActivity activity){
		getInstance().activity = activity;
		INSTANCE.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
		INSTANCE.gameRecordFile = new File(activity.getFilesDir(), GAME_RECORDS_FILE_NAME);
		
		if (!INSTANCE.gameRecordFile.exists()){
			try{
				INSTANCE.gameRecordFile.createNewFile();
			}
			catch(IOException e){
				Debug.e(e);
			}
		}
	}
	
	public static DataManager getInstance(){
		return INSTANCE;
	}
}