package com.Monkey;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.menus.CCMenuItemToggle;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;

import com.Monkey.common.GameSetting;
import com.Monkey.common.Global;
import com.Monkey.common.Macros;

public class SelectScene extends CCLayer {
    
	CCSprite m_backgroundSp;
    
    CCMenuItemImage m_pbackButton;
    CCMenuItemImage m_pPlayButton;
    
    CCMenuItemToggle m_pYoungMonkey;
    CCMenuItemToggle m_pMumMonkey;
    
    CCMenuItemToggle m_pForestPlace;
    CCMenuItemToggle m_pCityPlace;
    
    public SelectScene(){
    	CCTextureCache.sharedTextureCache().removeAllTextures();
    	readLevelInfo();
        loadResource();
        setPlaceState();
    }

    private void loadResource(){
        m_backgroundSp = CCSprite.sprite("bg_change_stage.jpg");
        Macros.LOCATE_NODE(this, m_backgroundSp, Macros.m_ptCenter);
        
        m_pPlayButton = CCMenuItemImage.item("btn_play_nml.png", "btn_play_act.png",
        										this, "playGame");
        Macros.CORRECT_SCALE(m_pPlayButton);
        Macros.POSITION_NODE(m_pPlayButton, 360, 30);
        
        m_pbackButton = CCMenuItemImage.item("btn_back_nml.png", "btn_back_act.png",
        										this, "goMainMenu");
        Macros.CORRECT_SCALE(m_pbackButton);
        Macros.POSITION_NODE(m_pbackButton, 120, 30);
        
        m_pYoungMonkey = CCMenuItemToggle.item(this, "setYoungMonkey", 
        					CCMenuItemImage.item("change_monkey_blue.png", "change_monkey_blue.png"),
        					CCMenuItemImage.item("change_monkey_gray___.png", "change_monkey_gray___.png"));
        Macros.CORRECT_SCALE(m_pYoungMonkey);
        Macros.POSITION_NODE(m_pYoungMonkey, 120, 110);
        
        m_pMumMonkey = CCMenuItemToggle.item(this, "setMumMonkey",
                          	CCMenuItemImage.item("change_monkey_rad.png", "change_monkey_rad.png"),
                          	CCMenuItemImage.item("change_monkey_rad_gray.png", "change_monkey_rad_gray.png"));
        Macros.CORRECT_SCALE(m_pMumMonkey);
        Macros.POSITION_NODE(m_pMumMonkey, 360, 110);

        if( Global.g_momkeyMom == false)
        {
            m_pYoungMonkey.setSelectedIndex(0);
            m_pMumMonkey.setSelectedIndex(1);
        }
        else
        {
            m_pYoungMonkey.setSelectedIndex(1);
            m_pMumMonkey.setSelectedIndex(0);
        }
        
        m_pForestPlace = CCMenuItemToggle.item(this, "setForestPlace",
                        CCMenuItemImage.item("change_stage_forest.png", "change_stage_forest.png"),
                        CCMenuItemImage.item("change_stage_forest.png", "change_stage_forest.png"));
        Macros.CORRECT_SCALE(m_pForestPlace);
        Macros.POSITION_NODE(m_pForestPlace, 130, 220);
        
        m_pCityPlace = CCMenuItemToggle.item(this, "setCityPlace",
                          CCMenuItemImage.item("change_stage_city.png", "change_stage_city.png"),
                          CCMenuItemImage.item("change_stage_city_gray.png", "change_stage_city_gray.png"));
        Macros.CORRECT_SCALE(m_pCityPlace);
        Macros.POSITION_NODE(m_pCityPlace, 350, 220);
        
        CCMenu menu = CCMenu.menu(m_pPlayButton, m_pbackButton, m_pYoungMonkey,
        						  m_pMumMonkey, m_pForestPlace, m_pCityPlace);
        menu.setPosition(0, 0);
        this.addChild(menu, 2);
    }


    private void setPlaceState(){
    	if (Global.g_nLevelCompleteInfo == 0) {
            m_pCityPlace.setSelectedIndex(1);
        } else {
        	m_pCityPlace.setSelectedIndex(0);
        }
    	return;
    }


    public void setForestPlace(Object sender){
        Global.g_levelNumber = 1;
    }

    public void setCityPlace(Object sender){
        if (Global.g_nLevelCompleteInfo == 0) {
            m_pCityPlace.setSelectedIndex(1);
            return;
        }

        Global.g_levelNumber = 2;
        m_pCityPlace.setSelectedIndex(0);
    }
    
    public void playGame(Object sender){
    	Macros.REPLACE_LAYER(this, new GameLayer());
    }

    public void goMainMenu(Object sender){
    	Macros.REPLACE_LAYER(this, new MainMenuScene());
    }

    public void setYoungMonkey(Object sender){
        Global.g_momkeyMom = false;
        m_pYoungMonkey.setSelectedIndex(0);
        m_pMumMonkey.setSelectedIndex(1);
    }
    
    public void setMumMonkey(Object sender){
        Global.g_momkeyMom = true;
        m_pMumMonkey.setSelectedIndex(0);
        m_pYoungMonkey.setSelectedIndex(1);
    }
    
    private void readLevelInfo(){
        Global.g_nLevelCompleteInfo = GameSetting.getIntValue("LEVEL_INFO", 0);
    }

}
