package com.lichunshang.android.scribblehopper.scene;

import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.lichunshang.android.scribblehopper.R;
import com.lichunshang.android.scribblehopper.manager.DataManager;


public class MainMenuScoreSubMenu extends BaseMainMenuSubMenu{
	
	private Text highScore, lastScore, totalGamesPlayed, averageGameScore, totalPlayTime, totalPlatformsLanded,
	highScoreVal, lastScoreVal, totalGamesPlayedVal, averageGameScoreVal, totalPlayTimeVal, totalPlatformsLandedVal;
	

	public MainMenuScoreSubMenu(MainMenuScene menuScene) {
		super(menuScene);
	}

	@Override
	public void onCreateSubMenu() {
		highScore = new Text(0, 0, resourcesManager.font_70, parentScene.getGameActivity().getString(R.string.high_score_text), vertexBufferObjectManager);
		lastScore = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.scores_last_score), vertexBufferObjectManager);
		totalGamesPlayed = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.scores_total_games_played), vertexBufferObjectManager);
		averageGameScore = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.scores_average_game_score), vertexBufferObjectManager);
		totalPlayTime = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.scores_total_play_time), vertexBufferObjectManager);
		totalPlatformsLanded = new Text(0, 0, resourcesManager.font_40, parentScene.getGameActivity().getString(R.string.scores_total_platforms_landed), vertexBufferObjectManager);
		
		highScoreVal = new Text(0, 0, resourcesManager.font_70, "01234567890123456789", vertexBufferObjectManager);
		lastScoreVal = new Text(0, 0, resourcesManager.font_40, "01234567890123456789", vertexBufferObjectManager);
		totalGamesPlayedVal = new Text(0, 0, resourcesManager.font_40, "01234567890123456789", vertexBufferObjectManager);
		averageGameScoreVal = new Text(0, 0, resourcesManager.font_40, "01234567890123456789", vertexBufferObjectManager);
		totalPlayTimeVal = new Text(0, 0, resourcesManager.font_40, "01234567890123456789", vertexBufferObjectManager);
		totalPlatformsLandedVal = new Text(0, 0, resourcesManager.font_40, "01234567890123456789", vertexBufferObjectManager);
		
		highScore.setAnchorCenter(0, 0);
		lastScore.setAnchorCenter(0, 0);
		totalGamesPlayed.setAnchorCenter(0, 0);
		averageGameScore.setAnchorCenter(0, 0);
		totalPlayTime.setAnchorCenter(0, 0);
		totalPlatformsLanded.setAnchorCenter(0, 0);
		
		highScoreVal.setAnchorCenter(0, 0);
		lastScoreVal.setAnchorCenter(0, 0);
		totalGamesPlayedVal.setAnchorCenter(0, 0);
		averageGameScoreVal.setAnchorCenter(0, 0);
		totalPlayTimeVal.setAnchorCenter(0, 0);
		totalPlatformsLandedVal.setAnchorCenter(0, 0);
		
		highScore.setColor(Color.BLACK);
		lastScore.setColor(Color.BLACK);
		totalGamesPlayed.setColor(Color.BLACK);
		averageGameScore.setColor(Color.BLACK);
		totalPlayTime.setColor(Color.BLACK);
		totalPlatformsLanded.setColor(Color.BLACK);
		
		highScoreVal.setColor(Color.BLACK);
		lastScoreVal.setColor(Color.BLACK);
		totalGamesPlayedVal.setColor(Color.BLACK);
		averageGameScoreVal.setColor(Color.BLACK);
		totalPlayTimeVal.setColor(Color.BLACK);
		totalPlatformsLandedVal.setColor(Color.BLACK);
		
		final int OFFSET = 30;
		highScore.setPosition(camera.getWidth() * 0.1f, camera.getHeight() * 0.5f);
		lastScore.setPosition(highScore.getX(), highScore.getY() - lastScore.getHeight() - OFFSET);
		totalGamesPlayed.setPosition(highScore.getX(), lastScore.getY() - totalGamesPlayed.getHeight() - OFFSET);
		averageGameScore.setPosition(highScore.getX(), totalGamesPlayed.getY() - averageGameScore.getHeight() - OFFSET);
		totalPlayTime.setPosition(highScore.getX(), averageGameScore.getY() - totalPlayTime.getHeight() - OFFSET);
		totalPlatformsLanded.setPosition(highScore.getX(), totalPlayTime.getY() - totalPlatformsLanded.getHeight() - OFFSET);
		
		setValueTextPosition();
		
		attachChild(highScore);
		attachChild(lastScore);
		attachChild(totalGamesPlayed);
		attachChild(averageGameScore);
		attachChild(totalPlayTime);
		attachChild(totalPlatformsLanded);
		
		attachChild(highScoreVal);
		attachChild(lastScoreVal);
		attachChild(totalGamesPlayedVal);
		attachChild(averageGameScoreVal);
		attachChild(totalPlayTimeVal);
		attachChild(totalPlatformsLandedVal);
	}
	
	public void setValueTextPosition(){
		final float RIGHT_MARGIN = camera.getWidth() * 0.9f;
		highScoreVal.setPosition(RIGHT_MARGIN - highScoreVal.getWidth(), highScore.getY());
		lastScoreVal.setPosition(RIGHT_MARGIN - lastScoreVal.getWidth(), lastScore.getY());
		totalGamesPlayedVal.setPosition(RIGHT_MARGIN - totalGamesPlayedVal.getWidth(), totalGamesPlayed.getY());
		averageGameScoreVal.setPosition(RIGHT_MARGIN - averageGameScoreVal.getWidth(), averageGameScore.getY());
		totalPlayTimeVal.setPosition(RIGHT_MARGIN - totalPlayTimeVal.getWidth(), totalPlayTime.getY());
		totalPlatformsLandedVal.setPosition(RIGHT_MARGIN - totalPlatformsLandedVal.getWidth(), totalPlatformsLanded.getY());
	}
	
	public void setValues(){
		DataManager dataManager = DataManager.getInstance();
		
		highScoreVal.setText(String.valueOf(dataManager.getHighScore()));
		lastScoreVal.setText(String.valueOf(dataManager.getLastScore()));
		totalGamesPlayedVal.setText(String.valueOf(dataManager.getTotalGamesPlayed()));
		averageGameScoreVal.setText(String.valueOf(dataManager.getAverageGameScore()));
		totalPlayTimeVal.setText(timeMilliToVerbose(dataManager.getTotalPlayTime()));
		totalPlatformsLandedVal.setText(String.valueOf(dataManager.getTotalPlatformsLanded()));
		
		setValueTextPosition();
	}
	
	public String timeMilliToVerbose(long milliseconds){
		long hours = milliseconds / 3600000;
		milliseconds = milliseconds % 3600000;
		long minutes = milliseconds / 60000;
		milliseconds = milliseconds % 60000;
		long seconds = milliseconds / 1000;
		
		return String.valueOf(hours) + " hr " + String.valueOf(minutes) + " min " + String.valueOf(seconds) + " sec";
	}
	
}