package com.Monkey;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import com.Monkey.common.Macros;


public class Splash extends CCLayer{

	final float LOG_DELAY_TIME = 0.2f;
	final float MONKEY_ONE_APPEAR_TIME = 8f;
	final float REPLACE_TIME = 18f;

	enum TAG_TYPE{
	    BACK_BG_TAG,
	    SPRITE_TAG,
	};
	
	enum SPRITE_TYPE{
	    MONKEY_SPRITE,
	    FIRST_SPRITE,
	};
	
    int m_nDelayTime;
    
    CCSprite m_pMonkey;
    CCSprite m_pRollerMonkey;
    CCSprite m_pSkateBoardMonkey;
    CCSprite m_pMotorMonkey;
    
    CCSprite m_firstFlySprite;
    
    public Splash(){
        m_nDelayTime = 0;
        
        CCSprite bgSp = CCSprite.sprite("bg_wait.png");
        Macros.LOCATE_NODE(this, bgSp, Macros.m_ptCenter);
        bgSp.setTag(TAG_TYPE.BACK_BG_TAG.ordinal());

        this.loadSprite();
        this.schedule("runActionScene", LOG_DELAY_TIME);
            
    }

    private void loadSprite(){
        m_pMonkey = CCSprite.sprite("log_change.png");
        Macros.LOCATE_NODE(this, m_pMonkey, 680, 160);
    }

    private void runActionSprite(SPRITE_TYPE nType){
        if(nType == SPRITE_TYPE.MONKEY_SPRITE)
        {
        	m_pMonkey.runAction(CCMoveTo.action(2.0f, Macros.LOGICAL_TO_REAL(CGPoint.ccp(-200, 160))));
        }
        return;
    }
    
    public void runActionScene(float dt){
        if(m_nDelayTime == MONKEY_ONE_APPEAR_TIME)
        {
            this.runActionSprite(SPRITE_TYPE.MONKEY_SPRITE);
        }
        
        if(m_nDelayTime == REPLACE_TIME)
        {
        	this.unschedule("runActionScene");
        	this.goMainMenu();
        }
        m_nDelayTime ++;
    }

    private void goMainMenu(){
//    	Macros.REPLACE_LAYER_WITH_TRANSITION_FADE(this, new MainMenuScene(), 1.0f);
    	Macros.REPLACE_LAYER_WITH_FADE(this, new MainMenuScene(), 1.0f, ccColor3B.ccWHITE);
//    	Macros.REPLACE_LAYER(this, new MainMenuScene());
    }
}
