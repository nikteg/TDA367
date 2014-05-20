package edu.chalmers.sankoss.java;

public class SankossScreenManager {
	public enum Screen{
		MAIN_MENU,
	    LOBBY,
	    WAITING,
	    PLACEMENT,
	    GAME,
	    CREDITS;
	}
	
	private static SankossScreenManager instance = null;
	
	private SankossScreenManager(){
		if(instance == null){
			instance = new SankossScreenManager();
		}
	}
	public SankossScreenManager getInstance(){
		return instance;
	}
}
