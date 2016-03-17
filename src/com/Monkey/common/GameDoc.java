package com.Monkey.common;

import org.cocos2d.nodes.CCDirector;


/*
 * In this class we are going to model a "Game Director" that controls the flow of game.
 * This class is singleton.
 */
public class GameDoc{
	
	public static enum GameState{
		STATE_NON_PLAYING,
		STATE_BEFORE_PLAYING,
		STATE_PLAYING,
		STATE_PAUSED,
		STATE_GAMEOVER
	}

    /** declare informations. */
    public boolean[] isDontShow = new boolean[13];
    public boolean[] isSuccess = new boolean[13];
    public int m_nScore;

	/** game state */
	public GameState m_GameState;
	
	public static boolean	m_bSoundMute = false;
	public static boolean	m_bEffectMute = false;

	public int m_nLevel = 1;

	public GameDoc(){
		m_nLevel = 1;
		m_nScore = 0;
    	m_GameState = GameState.STATE_NON_PLAYING;
    }
    
	private static GameDoc _sharedDoc = null;
    public static GameDoc sharedDoc() {
    	if( _sharedDoc == null ){
    		_sharedDoc = new GameDoc();
    	}
        return _sharedDoc;
    }

    /** initialize / uninitialize resource */
    public void initialize(){
    	GameSetting.initialize();
    	SoundManager.initialize();
	}
    
    public static void uninitialize(){
        SoundManager.release();
	}
    
	/** control game level */
	public void startGame(){
    	m_GameState = GameState.STATE_PLAYING;
	}
	
	public void pauseGame(){
	    CCDirector.sharedDirector().pause();
    	m_GameState = GameState.STATE_PAUSED;
	}
	
	public void resetGame(){
		m_nLevel = 1;
		m_nScore = 0;
	    CCDirector.sharedDirector().resume();
		m_GameState = GameState.STATE_PAUSED;
	}
	
	public void resumeGame(){
	    CCDirector.sharedDirector().resume();
    	m_GameState = GameState.STATE_PLAYING;
	}
	
	public void levelUp(){
		m_nLevel++;
    	m_GameState = GameState.STATE_PLAYING;
	}
	
	public void gameOver(){
    	m_GameState = GameState.STATE_GAMEOVER;
	}
}

