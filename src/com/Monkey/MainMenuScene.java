package com.Monkey;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.menus.CCMenuItemToggle;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.types.CGPoint;

import com.Monkey.common.Global;
import com.Monkey.common.Macros;

public class MainMenuScene extends CCLayer{
	
	enum TAG{
	    BACK_TAG,
	    START_BUTTON_TAG,
	    SCORE_BUTTON_TAG,
	    MAIN_MENU_TAG,
	};

	int m_nDelayTime;
	    
    CCMenu m_mainMenu;
    CCMenuItemImage m_pStartButton;
    CCMenuItemImage m_pScoreButton;
    CCMenuItemImage m_pGameCenterButton;
    CCMenuItemToggle m_pSoundToggle;
	    
	public MainMenuScene(){
		CCTextureCache.sharedTextureCache().removeAllTextures();
        m_nDelayTime = 0;

        CCSprite m_backgroundSp = CCSprite.sprite("bg_menu.jpg");
        Macros.LOCATE_NODE(this, m_backgroundSp, Macros.m_ptCenter);
        m_backgroundSp.setTag(TAG.BACK_TAG.ordinal());
        
        this.loadResource();
        this.schedule("runActions", 0.5f);
	}
	
	private void loadResource(){
		
	    m_pStartButton = CCMenuItemImage.item("btn_start_nml.png",
	    							"btn_start_act.png" , this, "startGame");
	    m_pScoreButton = CCMenuItemImage.item("btn_score_nml.png",
	    							"btn_score_act.png" , this ,"showScore");
	    m_pGameCenterButton = CCMenuItemImage.item("btn_game_ct2.png",
	    							"btn_game_ct1.png" /*, this, "enterGameCenter"*/);
		m_pSoundToggle = CCMenuItemToggle.item(this ,"setSoundState",
								CCMenuItemImage.item("Sound_on.png", "Sound_on.png"),
								CCMenuItemImage.item("Sound_off.png", "Sound_off.png"));
		if (Global.g_bSoundState == Global.Sound.SOUND_ON) {
			m_pSoundToggle.setSelectedIndex(0);
		}else{
			m_pSoundToggle.setSelectedIndex(1);
		}

		Macros.CORRECT_SCALE(m_pStartButton);
	    Macros.CORRECT_SCALE(m_pScoreButton);
	    m_pGameCenterButton.setScale(0f);
		m_pSoundToggle.setScale(0f);
		
		Macros.POSITION_NODE(m_pStartButton, 680, 130);
		Macros.POSITION_NODE(m_pScoreButton, 680, 70);
		Macros.POSITION_NODE(m_pGameCenterButton, 340, 20);
		Macros.POSITION_NODE(m_pSoundToggle, 390, 20);
		
		m_mainMenu = CCMenu.menu(m_pStartButton, m_pScoreButton,
				 m_pGameCenterButton, m_pSoundToggle);
		m_mainMenu.setPosition(0, 0);
		this.addChild(m_mainMenu, 1, TAG.MAIN_MENU_TAG.ordinal());
	}
	
	public void startGame(Object sender){
//		Macros.REPLACE_LAYER_WITH_TRANS(this, new SelectScene(), CCCrossFadeTransition.class, 0.2f);
		Macros.REPLACE_LAYER(this, new SelectScene());
	}

	public void showScore(Object sender){
//		Macros.REPLACE_LAYER_WITH_TRANS(this, new ScoreScene(), CCCrossFadeTransition.class, 0.2f);
		Macros.REPLACE_LAYER(this, new ScoreScene());
	}
/*
	public void enterGameCenter(Object sender){
		
	}
*/
	public void setSoundState(Object sender){
	    if(Global.g_bSoundState == Global.Sound.SOUND_ON){
			m_pSoundToggle.setSelectedIndex(1);
	    	Global.g_bSoundState = Global.Sound.SOUND_OFF;
	    }else{
			m_pSoundToggle.setSelectedIndex(0);
	    	Global.g_bSoundState = Global.Sound.SOUND_ON;
	    }
	}

	public void runActions(float dt){
		this.unschedule("runActions");

		m_pStartButton.runAction(CCSequence.actions(
					CCMoveTo.action(1.0f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(320, 130))),
					CCMoveTo.action(0.6f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(380, 130))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(330, 130))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(370, 130))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(340, 130))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(350, 130)))));
		
		m_pScoreButton.runAction(CCSequence.actions( 
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(450, 70))),
					CCMoveTo.action(1.0f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(320, 70))),
					CCMoveTo.action(0.6f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(380, 70))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(330, 70))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(370, 70))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(340, 70))),
					CCMoveTo.action(0.2f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(350, 70)))));
		
		m_pGameCenterButton.runAction(CCSequence.actions(
					CCScaleTo.action(2.0f, 0f),
					CCScaleTo.action(1.0f, Macros.m_rScaleX)));
		
		m_pSoundToggle.runAction(CCSequence.actions(
				CCScaleTo.action(2.0f, 0f),
				CCScaleTo.action(1.0f, Macros.m_rScaleX)));
	}
	
}