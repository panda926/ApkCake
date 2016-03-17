package com.Monkey;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;

import com.Monkey.common.GameSetting;
import com.Monkey.common.Global;
import com.Monkey.common.Macros;

public class ScoreScene extends CCLayer {
	
    CCSprite m_backgroundSp;
    CCSprite m_scoreBoardSp;
    
    CCMenuItemImage m_backButton;
    CCLabel[] m_pScoreLabel;
    CCLabel[] m_pNameLabel;
    
    int m_nPlayerCount;
    int[] m_nScoreArray;

    public ScoreScene(){
    	
    	CCTextureCache.sharedTextureCache().removeAllTextures();
    	m_pScoreLabel = new CCLabel[Global.REGISTER_COUNT];
    	m_pNameLabel = new CCLabel[Global.REGISTER_COUNT];
    	m_nScoreArray = new int[Global.REGISTER_COUNT];
    	
    	this.loadResource();
    	this.loadScoreInfo();
    	this.showScore();
    	
    }
    
    private void loadResource(){
        m_backgroundSp = CCSprite.sprite("bg_change_stage.jpg");
        Macros.LOCATE_NODE(this, m_backgroundSp, Macros.m_ptCenter);
        
        m_scoreBoardSp = CCSprite.sprite("bg_score.png");
        Macros.LOCATE_NODE(this, m_scoreBoardSp, Macros.m_ptCenter);
        
        m_backButton = CCMenuItemImage.item("btn_back_nml.png", "btn_back_act.png",
        									this, "goMainMenu");
        Macros.CORRECT_SCALE(m_backButton);
        Macros.POSITION_NODE(m_backButton, 240, 20);
        
        CCMenu menu = CCMenu.menu(m_backButton);
        menu.setPosition(0, 0);  this.addChild(menu);
    }
    
    private void showScore(){
    	
        for (int i = 0; i < m_nPlayerCount-1; i ++) {
            String str = String.format("%d", m_nScoreArray[i]);
            m_pScoreLabel[i] = CCLabel.makeLabel( str, "Arial", 20);
            Macros.LOCATE_NODE(this, m_pScoreLabel[i], 290, 200-30*i);
            
            m_pNameLabel[i] = CCLabel.makeLabel( String.format("Player%d", i+1), "Arial", 20);
            Macros.LOCATE_NODE(this, m_pNameLabel[i], 190, 200-30*i);
        }
    }

    private void loadScoreInfo(){

    	m_nPlayerCount = GameSetting.getIntValue("PLAYER_COUNT", 1);
     
        for(int i = 0; i < m_nPlayerCount-1; i ++){
            String str = String.format("Player%d", i+1);
            m_nScoreArray[i] = GameSetting.getIntValue(str, 0);
        }
    }
    
    public void goMainMenu(Object sender){
    	Macros.REPLACE_LAYER(this, new MainMenuScene());
//    	Macros.REPLACE_LAYER_WITH_TRANS(this, new MainMenuScene(), CCCrossFadeTransition.class, 1.0f);
    }

}
