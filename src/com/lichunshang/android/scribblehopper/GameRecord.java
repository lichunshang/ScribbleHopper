package com.lichunshang.android.scribblehopper;


public class GameRecord {

	// ------------ Constants -------------------

	public static final String DELIMITOR = "__DELIM__";
	public static final String DEATH_BY_FALL = "FALL";
	public static final String DEATH_BY_SPIKE = "SPIKE";
	public static final String NULL = "null";

	// ------------ Record Data ------------------
	public long timeStarted = 0, timeEnded = 0, elapsedTime = 0;
	public DeathReason reasonDied = null;
	public int numTopSpikeContact = 0, numSpikePlatformLanded = 0,
			numBouncePlatformLanded = 0, numConveyorLeftPlatformLanded = 0,
			numConveyorRightPlatformLanded = 0, numRegularPlatformLanded = 0,
			numUnstablePlatformLanded = 0;

	public GameRecord() {
		
	}

	public GameRecord(String recordLine) {
		setRecord(recordLine);
	}
	
	/**
	 * Each record is a line in the file that saves the user data. Each item
	 * is delimited by the constant DELIMITOR.
	 * 
	 * As of now the columns are in the following order:
	 * 1.  Game Start Time Stamp (timeStarted)
	 * 2.  Game End Time Stamp (timeEnded)
	 * 3.  How the Player Died (reasonDied)
	 * 4.  Number of Contacts with Spike at the Top (numTopSpikeContact)
	 * 5.  Number of Times Landed on Spike Platform (numSpikePlatformLanded)
	 * 6.  Number of Times Landed on Bounce Platform (numBouncePlatformLanded)
	 * 7.  Number of Times Landed on Conveyor Left Platform (numConveyorLeftPlatformLanded)
	 * 8.  Number of Times Landed on Conveyor Right Platform (numConveyorRightPlatformLanded)
	 * 9.  Number of Times Landed on Regular Platform (numRegularPlatformLanded)
	 * 10. Number of Times Landed on Unstable Platform (numUnstablePlatformLanded)
	 * 11. The amount of time the game elapsed in milliseconds
	 * 
	 * New columns can be added in the end and be backwards compatible as 
	 * the old order is kept and the record lines without the new columns
	 * are handled
	 * 
	 * @param recordLine
	 */
	public void setRecord(String recordLine) {
		String[] splitStr = recordLine.split(DELIMITOR);
		
		timeStarted = Long.parseLong(splitStr[0]);
		timeEnded = Long.parseLong(splitStr[1]);
		
		String reasonDiedStr = splitStr[2];
		if (reasonDiedStr.matches(DEATH_BY_FALL))
			reasonDied = DeathReason.FALL;
		else if (reasonDiedStr.matches(DEATH_BY_SPIKE))
			reasonDied = DeathReason.SPIKE;
		else
			reasonDied = null;
		
		numTopSpikeContact = Integer.parseInt(splitStr[3]);
		numSpikePlatformLanded = Integer.parseInt(splitStr[4]);
		numBouncePlatformLanded = Integer.parseInt(splitStr[5]);
		numConveyorLeftPlatformLanded = Integer.parseInt(splitStr[6]);
		numConveyorRightPlatformLanded = Integer.parseInt(splitStr[7]);
		numRegularPlatformLanded = Integer.parseInt(splitStr[8]);
		numUnstablePlatformLanded = Integer.parseInt(splitStr[9]);
		elapsedTime = Long.parseLong(splitStr[10]);
		
	}
	
	public String getRecordLine(){
		String str = "";
		
		str += String.valueOf(timeStarted) + DELIMITOR;
		str += String.valueOf(timeEnded) + DELIMITOR;
		
		if (reasonDied == DeathReason.FALL)
			str += DEATH_BY_FALL + DELIMITOR;
		else if (reasonDied == DeathReason.SPIKE)
			str += DEATH_BY_SPIKE + DELIMITOR;
		else 
			str += NULL + DELIMITOR;
		
		str += String.valueOf(numTopSpikeContact) + DELIMITOR;
		str += String.valueOf(numSpikePlatformLanded) + DELIMITOR;
		str += String.valueOf(numBouncePlatformLanded) + DELIMITOR;
		str += String.valueOf(numConveyorLeftPlatformLanded) + DELIMITOR;
		str += String.valueOf(numConveyorRightPlatformLanded) + DELIMITOR;
		str += String.valueOf(numRegularPlatformLanded) + DELIMITOR;
		str += String.valueOf(numUnstablePlatformLanded) + DELIMITOR;
		str += String.valueOf(elapsedTime) + DELIMITOR;
		
		return str;
	}

	public static enum DeathReason {
		SPIKE, FALL,
	}
}