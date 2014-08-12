package com.lichunshang.android.scribblehopper.manager;

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

import com.lichunshang.android.scribblehopper.GameActivity;
import com.lichunshang.android.scribblehopper.GameRecord;

public class DataManager{
	
	// ---------------- Singleton Instance ----------------
	private static final DataManager INSTANCE = new DataManager();
	
	// ---------------- Constants ----------------
	private static final String HIGH_SCORE_KEY = "HIGH_SCORE_KEY";
	private static final int DEFAULT_HIGH_SCORE = 0;
	private static final String SOUND_EFFECT_ENABLED_KEY = "SOUND_EFFECT_ENABLED_KEY";
	private static final String MUSIC_ENABLE_KEY = "MUSIC_ENABLE_KEY";
	private static final boolean DEFAULT_SOUND_EFFECT_ENABLED = true;
	private static final boolean DEFAULT_MUSIC_ENABLED = false;
	
	
	private static final String GAME_RECORDS_FILE_NAME = "RECORDS.shdata";
	
	// ---------------- Fields ----------------
	public GameActivity activity;
	private SharedPreferences sharedPreferences; 
	private File gameRecordFile;
	private ArrayList<GameRecord> gameRecordsList = new ArrayList<GameRecord>();

	// ----------------------------------------------
	// HIGH SCORES
	// ----------------------------------------------
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
	
	// ----------------------------------------------
	// GAME SETTINGS
	// ----------------------------------------------
	
	public void saveSoundEffectEnabled(boolean enabled){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SOUND_EFFECT_ENABLED_KEY, enabled);
		editor.commit();
	}
	
	public boolean getSoundEffectEnabled(){
		return sharedPreferences.getBoolean(SOUND_EFFECT_ENABLED_KEY, DEFAULT_SOUND_EFFECT_ENABLED);
	}
	
	public void saveMusicEnabled(boolean enabled){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(MUSIC_ENABLE_KEY, enabled);
		editor.commit();
	}
	
	public boolean getMusicEnabled(){
		return sharedPreferences.getBoolean(MUSIC_ENABLE_KEY, DEFAULT_MUSIC_ENABLED);
	}
	
	// ----------------------------------------------
	// GAME RECORDS
	// ----------------------------------------------
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
			FileWriter fileWriter = new FileWriter(gameRecordFile, false);
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
	
	public void clearGameHistory(){
		clearGameRecords();
		clearHighScore();
	}
	
	// -------- Getters of Processed Information ---------
	
	public int getTotalGamesPlayed(){
		return gameRecordsList.size();
	}

	public int getAverageGameScore(){
		
		if (getTotalGamesPlayed() == 0){
			return 0;
		}
		
		int totalScore = 0;
		
		for (GameRecord record : gameRecordsList){
			totalScore += record.score;
		}
		
		return totalScore / getTotalGamesPlayed();
	}
	
	/**
	 * 
	 * @return Total play time in milliseconds
	 */
	public long getTotalPlayTime(){
		long playTime = 0;
		for (GameRecord record : gameRecordsList){
			playTime += record.elapsedTime;
		}
		return playTime;
	}
	
	public int getLastScore(){
		if (gameRecordsList.isEmpty())
			return 0;
		return gameRecordsList.get(gameRecordsList.size() - 1).score;
	}
	
	
	public int getTotalPlatformsLanded(){
		int totalNum = 0;
		
		for (GameRecord record : gameRecordsList){
			totalNum += record.numBouncePlatformLanded;
			totalNum += record.numConveyorLeftPlatformLanded;
			totalNum += record.numConveyorRightPlatformLanded;
			totalNum += record.numRegularPlatformLanded;
			totalNum += record.numSpikePlatformLanded;
			totalNum += record.numUnstablePlatformLanded;
		}
		return totalNum;
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