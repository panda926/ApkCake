package com.Monkey;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.util.Log;
import android.view.MotionEvent;

import com.Monkey.common.GameDoc;
import com.Monkey.common.GameDoc.GameState;
import com.Monkey.common.GameSetting;
import com.Monkey.common.Global;
import com.Monkey.common.Macros;
import com.Monkey.common.MathUtils;
import com.Monkey.common.SoundManager;
import com.Monkey.objects.Actor;
import com.Monkey.objects.Actor.TagWeapon;
import com.Monkey.objects.Ball;
import com.Monkey.objects.Monkey;
import com.Monkey.objects.Monkey.TagMonkeyName;

public class GameLayer extends CCLayer {

    // my Defines
    final float BLOOD_SPRITE_POS_X = (550 * Macros.m_fImageScaleX);
    final float BLOOD_LABEL_POS_X  = (580 * Macros.m_fImageScaleX);
    final float GAME_STATE_X       = (324 * Macros.m_fImageScaleX);
    final float GAME_STAGE_SPRITE_X= (70  * Macros.m_fImageScaleX);

    final float SCORE_LABEL_POS_Y  = (730 * Macros.m_fImageScaleY);
    final float SCORE_LABEL_POS_X  =  (723 * Macros.m_fImageScaleX);
    final float SCORE_NUMBER_POS_X =  (795 * Macros.m_fImageScaleX);
    final float PAUSE_POS_X        =  (980 * Macros.m_fImageScaleX);

    final float BALLSTARTING_VELOCITY_X =  (800 * Macros.m_fImageScaleX);
    final float BALLSTARTING_VELOCITY_Y =  (350 * Macros.m_fImageScaleY);

    final float GENERATE_CAKE_MIN_Y =  (200 * Macros.m_fImageScaleY);
    final float GENERATE_CAKE_MAX_Y =  (400 * Macros.m_fImageScaleY);
    final float GENERATE_CAKE_DELTA =  (150 * Macros.m_fImageScaleY);

    final float CAKE_WIDTH  =  (50 * Macros.m_fImageScaleX);
    final float CAKE_HEIGHT =  (55 * Macros.m_fImageScaleY);

    final int STATE_COUNT    = 50;
    final int MONKEY_BLOOD   = 5;

    final float END_MONKEY_1_X     =  (162 * Macros.m_fImageScaleX);
    final float END_MONKEY_1_Y     =  (630 * Macros.m_fImageScaleY);
    final float END_MONKEY_2_X     =  (1010 * Macros.m_fImageScaleX);
    final float END_MONKEY_2_Y     =  (190 * Macros.m_fImageScaleY);
    final float END_MONKEY_3_X     =  (1370 * Macros.m_fImageScaleX);
    final float END_MONKEY_3_Y     =  (200 * Macros.m_fImageScaleY);

    final float END_MONKEY_ACCEL_X =  (442 * Macros.m_fImageScaleX);
    final float END_MONKEY_ACCEL_Y =  (234 * Macros.m_fImageScaleY);
    
    ArrayList<CCSprite> backgrounds = new ArrayList<CCSprite>();
    ArrayList<Ball> myBalls = new ArrayList<Ball>();
    ArrayList<Actor> foods = new ArrayList<Actor>();
    ArrayList<Actor> endTargets = new ArrayList<Actor>();
    
    public ArrayList<ArrayList<Actor>> groupTargets = new ArrayList<ArrayList<Actor>>();
    public ArrayList<CCSprite> updateWeapon = new ArrayList<CCSprite>();
    public ArrayList<CCSprite> expresions = new ArrayList<CCSprite>();
    
    public Monkey player;
    
    public Actor fallWood;
    public boolean stageSuccess;
   
    int stageNum;
    
    CCSprite cursorMonkey = new CCSprite();
    CCSprite seekbar = new CCSprite();
    CCLabelAtlas scoreLabel;
    CCLabelAtlas bloodLabel;
    CCSprite bloodSprite = new CCSprite();
    CCSprite[] stageSprite = new CCSprite[3];
    
    float startPosGameState;
    float endPosGameState;
    
    CCMenuItemImage m_resumeButton;
    CCMenuItemImage m_mainMenuButton;
    CCMenuItemImage m_pNextLevelButton;
    CCMenuItemImage m_pPlayAgainButton;
    
    CCSprite m_pauseSprite;
    CCSprite scoreSprite;
    
    CCMenuItemImage m_pause;
    
    CCSprite endBackSky;
    CCSprite endHill1;
    CCSprite endHill2;
    CCSprite endCake;
    CCSprite endMonkey;
    
    CCSprite heart;
    
    boolean isExpressedEndCake;
    
    CCSprite  m_gameOver;
    
    public boolean m_bCongratulationStage;
    boolean m_bCongratulateState;
    boolean m_bGameSuccessState;
    
    public int countObject;
    public int foodCount;
    public int ballCount;
    public int expresionCount;

    int backLength = 0;

    CGPoint completeVelocity = CGPoint.zero();
    float   completeSum_X = 0.0f;
    
	///////////////////////////////////////////////////////////////////////
	////////// on "init" you need to initialize your instance /////////////
	///////////////////////////////////////////////////////////////////////

	public GameLayer()
	{
		CCTextureCache.sharedTextureCache().removeAllTextures();
		Global.sharedGlobal().initAnimation();
    	if(!Global.g_momkeyMom){
    		Global.sharedGlobal().initPlayerAnimation();
    	}else{
    		Global.sharedGlobal().initPlayerMomAnimation();
    	}

		GameDoc.sharedDoc().m_GameState = GameState.STATE_PLAYING;
        this.isTouchEnabled_ = true;
    	
        m_bCongratulationStage = false;
        m_bCongratulateState = false; 
        m_bGameSuccessState = false;

        backgrounds = new ArrayList<CCSprite>();
        endTargets = new ArrayList<Actor>();

        stageNum = 0;
        isExpressedEndCake = false;	
        if (Global.g_levelNumber == 1) {
            Global.g_velocityBack = Global.VELOCITY_START1_X;
        }
        else
        {
            Global.g_velocityBack = Global.VELOCITY_START2_X;
        }
        
        heart = CCSprite.sprite(Global.IMG_HEART[0]);
        Macros.CORRECT_SCALE(heart);
        heart.setVisible(false);
        this.addChild(heart, 3);
        
        this.initExpresions();
        this.initBackground();
        this.initGroupTargets();
        this.initFoods();
        this.initBalls();
        this.initUpdateWeapon();
        this.initMonkey();
        this.initObserver();
        this.stageIconVisible();
        this.loadPauseResource();

        m_gameOver = CCSprite.sprite("gameover.png");
        Macros.CORRECT_SCALE(m_gameOver);
        m_gameOver.setPosition(Macros.m_szWindow.width / 2, Macros.m_szWindow.height / 2);
        this.addChild(m_gameOver, 10);
        m_gameOver.setVisible(false);
        CCTextureCache.sharedTextureCache().removeTexture(m_gameOver.getTexture());

        this.schedule("doStep");
        
        if (Global.g_bSoundState == Global.Sound.SOUND_ON){
        	SoundManager.sd_background.start();
        }else{
        	SoundManager.sharedSoundManager().pauseMusic();
        }
	}

    public void initBackground(){
    	CCSprite back = null; 
    	CCTexture2D paddleTexture = null;
    
    	switch (Global.g_levelNumber) {
        case 1:
            for (int num = 0; num < 3; num ++) {
                paddleTexture = CCTextureCache.sharedTextureCache().addImage(Global.IMG_STAGE_1[stageNum]);
                back = CCSprite.sprite(paddleTexture);
                back.setPosition(
                		Macros.m_szWindow.width / 2 + Macros.m_szWindow.width * num - num,
                		Macros.m_szWindow.height / 2);
                back.setScaleX(Macros.m_rScaleX * (int) Math.pow(-1, num));
                back.setScaleY( Macros.m_rScaleY );
                backgrounds.add(back);
                CCTextureCache.sharedTextureCache().removeTexture(paddleTexture);
            }
            break;
        case 2:
            for (int num = 0; num < 3; num ++) {
                if (stageNum == 1)
                {
                    paddleTexture = CCTextureCache.sharedTextureCache().addImage(Global.IMG_STAGE_2_2[num]);
                }
                else
                {
                    paddleTexture = CCTextureCache.sharedTextureCache().addImage(Global.IMG_STAGE_2[stageNum]);
                }
                back = CCSprite.sprite(paddleTexture);
                back.setPosition(Macros.m_szWindow.width / 2 + Macros.m_szWindow.width * num - num,
                				 Macros.m_szWindow.height / 2);
                back.setScaleX( Macros.m_rScaleX * (int) Math.pow(-1, num));
                back.setScaleY( Macros.m_rScaleY );
                backgrounds.add(back);
                CCTextureCache.sharedTextureCache().removeTexture(paddleTexture);
            }
            break;
    	}
    
    	backLength = backgrounds.size();
	
    	for ( CCSprite back1 : backgrounds)
    		this.addChild(back1, -1);
    }

    public void initMonkey(){
    	player = Monkey.mksprite(TagMonkeyName.MonkeyName_roll);
	    player.bloodNum = MONKEY_BLOOD;
		this.addChild(player ,1);
    }
    
    public void onPause(Object sender){
    	SoundManager.sharedSoundManager().pauseMusic();
	    CCDirector.sharedDirector().pause();
	    this.showPauseMenu();
    }
    
    public void initObserver(){

    	seekbar = CCSprite.sprite("seekbar.png");
	    seekbar.setScaleX(Macros.m_rScaleX);
	    seekbar.setScaleY(Macros.m_rScaleY);
	    seekbar.setPosition(GAME_STATE_X, SCORE_LABEL_POS_Y);
	    this.addChild(seekbar, 5);
	    CCTextureCache.sharedTextureCache().removeTexture(seekbar.getTexture());
	    
	    startPosGameState = GAME_STATE_X - seekbar.getContentSize().width / 2 * Macros.m_rScaleX;
	    endPosGameState = GAME_STATE_X + seekbar.getContentSize().width / 2 * Macros.m_rScaleY;
	
	    if (Global.g_momkeyMom == false)
	    {
	        cursorMonkey = CCSprite.sprite("monkeyhead_01.png");
	    }
	    else
	    {
	        cursorMonkey = CCSprite.sprite("monkeyhead_02.png");
	    }
	    cursorMonkey.setScaleX(Macros.m_rScaleX);
	    cursorMonkey.setScaleY(Macros.m_rScaleY);
	    cursorMonkey.setPosition(startPosGameState, SCORE_LABEL_POS_Y);
	    this.addChild(cursorMonkey, 5);
	    CCTextureCache.sharedTextureCache().removeTexture(cursorMonkey.getTexture());
	    
	    scoreSprite = CCSprite.sprite("score.png");
	    scoreSprite.setScaleX(Macros.m_rScaleX);
	    scoreSprite.setScaleY(Macros.m_rScaleY);
	    scoreSprite.setPosition(SCORE_LABEL_POS_X, SCORE_LABEL_POS_Y);
	    this.addChild(scoreSprite, 5);
	    CCTextureCache.sharedTextureCache().removeTexture(scoreSprite.getTexture());
	    
	    stageSprite[0] = CCSprite.sprite(Global.IMG_STAGE_ICON[0 + 3 * (Global.g_levelNumber - 1)]);
	    stageSprite[0].setScaleX(Macros.m_rScaleX);
	    stageSprite[0].setScaleY(Macros.m_rScaleY);
	    stageSprite[0].setPosition(GAME_STAGE_SPRITE_X, SCORE_LABEL_POS_Y);
	    this.addChild(stageSprite[0], 5);
	    CCTextureCache.sharedTextureCache().removeTexture(stageSprite[0].getTexture());
	    
	    stageSprite[1] = CCSprite.sprite(Global.IMG_STAGE_ICON[1 + 3 * (Global.g_levelNumber - 1)]);
	    stageSprite[1].setScaleX(Macros.m_rScaleX);
	    stageSprite[1].setScaleY(Macros.m_rScaleY);
	    stageSprite[1].setPosition(GAME_STAGE_SPRITE_X, SCORE_LABEL_POS_Y);
	    this.addChild(stageSprite[1], 5);
	    CCTextureCache.sharedTextureCache().removeTexture(stageSprite[1].getTexture());
	
	    stageSprite[2] = CCSprite.sprite(Global.IMG_STAGE_ICON[2 + 3 * (Global.g_levelNumber - 1)]);
	    stageSprite[2].setScaleX(Macros.m_rScaleX);
	    stageSprite[2].setScaleY(Macros.m_rScaleY);
	    stageSprite[2].setPosition(GAME_STAGE_SPRITE_X, SCORE_LABEL_POS_Y);
	    this.addChild(stageSprite[2], 5);
	    CCTextureCache.sharedTextureCache().removeTexture(stageSprite[2].getTexture());
	
	    bloodSprite = CCSprite.sprite("bloodSprite.png");
	    bloodSprite.setScaleX(Macros.m_rScaleX);
	    bloodSprite.setScaleY(Macros.m_rScaleY);
	    bloodSprite.setPosition(BLOOD_SPRITE_POS_X, SCORE_LABEL_POS_Y);
	    this.addChild(bloodSprite, 5);
	    CCTextureCache.sharedTextureCache().removeTexture(bloodSprite.getTexture());
	    
	    bloodLabel = CCLabelAtlas.label("0123456789", "nmb_score.png",
	    		30*480/1024, 38*320/768, '0');
	    String string = String.format("%d", player.bloodNum);
	    bloodLabel.setScaleX(Macros.m_rScaleX);
		bloodLabel.setScaleY(Macros.m_rScaleY);
		bloodLabel.setString(string);
	    bloodLabel.setPosition(BLOOD_LABEL_POS_X,
	    		SCORE_LABEL_POS_Y - bloodLabel.getContentSize().height * Macros.m_rScaleY / 2);
	    this.addChild(bloodLabel, 5);
	    
	    player.gameScore = 0;
	    scoreLabel = CCLabelAtlas.label("0123456789","nmb_score.png"
	    		,30*480/1024, 38*320/768,'0');
	    string = String.format("%d", player.gameScore);
	    scoreLabel.setScaleX(Macros.m_rScaleX);
		scoreLabel.setScaleY(Macros.m_rScaleY);
		scoreLabel.setString(string);
	    scoreLabel.setPosition(SCORE_NUMBER_POS_X,
	    		SCORE_LABEL_POS_Y - scoreLabel.getContentSize().height * Macros.m_rScaleY / 2);
	    this.addChild(scoreLabel, 5);
	
	    m_pause = CCMenuItemImage.item("btn_pause.png", "btn_pause.png", this, "onPause");
	    m_pause.setScaleX(Macros.m_rScaleX);
	    m_pause.setScaleY(Macros.m_rScaleY);
	    m_pause.setPosition(PAUSE_POS_X, SCORE_LABEL_POS_Y);
	    
	    CCMenu menu = CCMenu.menu(m_pause);
	    menu.setPosition(0,0);
	    this.addChild(menu, 5);
	}
    
    public void resetHeart(){
	    heart.stopAllActions();
	    heart.setVisible(false);
	    Macros.CORRECT_SCALE(heart);
	}
    
    int threshold = 10000;
    public void updateScore(){
    	String string = String.format("%d", player.gameScore);
    	scoreLabel.setString(string);
	    
	    if (player.gameScore>=threshold){
	    	threshold += 10000;
	    	player.bloodNum++;
	    	String string1 = String.format("%d", player.bloodNum);
	    	bloodLabel.setString(string1);
	    	SoundManager.sharedSoundManager().playEffect(R.raw.sd_bonus);
	    	heart.setVisible(true);
	    	heart.setPosition(player.getPosition());
	    	// HEART
	    	CCAnimation ani_heart = CCAnimation.animation("Animation34", 0.1f);
		    for (int num = 0; Global.IMG_HEART[num] != null; num ++) {
				String filename = Global.IMG_HEART[num];
				ani_heart.addFrame(filename);
			}
	    	heart.runAction(CCRepeatForever.action(CCAnimate.action(ani_heart, false)));
	    	heart.runAction(CCMoveTo.action(2.0f, bloodLabel.getPosition()));
	    	heart.runAction(CCSequence.actions(CCScaleBy.action(2.0f, 0.3f, 0.3f), 
	    					CCCallFunc.action(this, "resetHeart")));
	    }
	}

    public void loadPauseResource(){
	    m_pauseSprite = CCSprite.sprite("bg_pause.png");
	    m_pauseSprite.setScaleX(Macros.m_rScaleX);
	    m_pauseSprite.setScaleY(Macros.m_rScaleY);
	    m_pauseSprite.setPosition(Macros.m_szWindow.width/2.0f, Macros.m_szWindow.height/2.0f);
	    this.addChild(m_pauseSprite, 10);
	    m_pauseSprite.setVisible(false);
	    CCTextureCache.sharedTextureCache().removeTexture(m_pauseSprite.getTexture());
	    
	    m_resumeButton = CCMenuItemImage.item("btn_resume_nml.png", "btn_resume_act.png", 
	                                          this, "resumeGame");
	    m_resumeButton.setScaleX(Macros.m_rScaleX);
	    m_resumeButton.setScaleY(Macros.m_rScaleY);
	    m_resumeButton.setPosition(Macros.m_szWindow.width/2.0f,
	    		Macros.m_szWindow.height/2.0f + 60 * Macros.m_fImageScaleY);
	    m_resumeButton.setVisible(false);
	    m_mainMenuButton = CCMenuItemImage.item("btn_menu_nml.png", "btn_menu_act.png"
	                                            ,this , "goMainMenu");
	    m_mainMenuButton.setScaleX(Macros.m_rScaleX);
	    m_mainMenuButton.setScaleY(Macros.m_rScaleY);
	    m_mainMenuButton.setPosition(Macros.m_szWindow.width/2.0f,
	    		Macros.m_szWindow.height/2.0f - 60 * Macros.m_fImageScaleY);
	    m_mainMenuButton.setVisible(false);
	    
	    m_pNextLevelButton = CCMenuItemImage.item("btn_nextlevel_nml.png", "btn_nextlevel_act.png"
	                                               ,this, "goNextLevel");
	    m_pNextLevelButton.setScaleX(Macros.m_rScaleX);
	    m_pNextLevelButton.setScaleY(Macros.m_rScaleY);
	    m_pNextLevelButton.setPosition(Macros.m_szWindow.width/2.0f,
	    		Macros.m_szWindow.height/2.0f + 60 * Macros.m_fImageScaleY);
	    m_pNextLevelButton.setVisible(false);
	    
	    m_pPlayAgainButton = CCMenuItemImage.item("btn_playagain_nml.png" ,"btn_playagain_act.png"
	                                                       ,this, "playAgain");
	    m_pPlayAgainButton.setScaleX(Macros.m_rScaleX);
	    m_pPlayAgainButton.setScaleY(Macros.m_rScaleY);
	    m_pPlayAgainButton.setPosition(Macros.m_szWindow.width/2.0f,
	    		Macros.m_szWindow.height/2.0f - 60 * Macros.m_fImageScaleY);
	    m_pPlayAgainButton.setVisible(false);
	    
	    CCMenu menu = CCMenu.menu(m_resumeButton, m_mainMenuButton, m_pNextLevelButton, m_pPlayAgainButton);
	    menu.setPosition(0, 0);
	    this.addChild(menu, 11);
	 }

	///// BUTTON_EVENT_PART
    public void playAgain(Object sender){
    	GameDoc.sharedDoc().m_GameState = GameState.STATE_PLAYING;
	    CCDirector.sharedDirector().resume();
	    if (player.bloodNum <= 0) {
	        player.bloodNum = this.MONKEY_BLOOD;
	    }
	    else
	    {
	        if(Global.g_levelNumber == 2 && m_bGameSuccessState == false)
	            Global.g_levelNumber = 1;
	        else if(m_bGameSuccessState)
	            Global.g_levelNumber = 2;
	    }
	
	    Macros.REPLACE_LAYER(this, new GameLayer());
//		Macros.REPLACE_LAYER_WITH_TRANS(this, new GameLayer(), CCCrossFadeTransition.class, 1.0f);
	}
    
    public void resumeGame(Object sender){
    	GameDoc.sharedDoc().m_GameState = GameState.STATE_PLAYING;
	    if(Global.g_bSoundState == Global.Sound.SOUND_ON)
	    {
	    	SoundManager.sd_background.start();
	        player.soundPlay(player.name);
	    }
	    CCDirector.sharedDirector().resume();
	    this.hiddenPauseMenu();
	}
    
    public void goMainMenu(Object sender){
    	GameDoc.sharedDoc().m_GameState = GameState.STATE_NON_PLAYING;
	    this.hiddenPauseMenu();
	    CCDirector.sharedDirector().resume();
	    Macros.REPLACE_LAYER(this, new MainMenuScene());
//	    Macros.REPLACE_LAYER_WITH_TRANS(this, new MainMenuScene(), CCCrossFadeTransition.class, 1.0f);
	}
    
    public void showPauseMenu(){
    	
	    m_pauseSprite.setVisible(true);
	    if(m_bCongratulateState && !m_bGameSuccessState)
	    {
	        m_pPlayAgainButton.setVisible(true);
	        m_pNextLevelButton.setVisible(true);
	        m_resumeButton.setIsEnabled(false);
	        m_mainMenuButton.setIsEnabled(false);
	    }
	    else if(m_bGameSuccessState && m_bCongratulationStage)
	    {
	        m_pPlayAgainButton.setVisible(true);
	        m_mainMenuButton.setVisible(true);
	        m_mainMenuButton.setPosition(Macros.m_szWindow.width/2.0f,
	        		Macros.m_szWindow.height/2.0f + 60 * Macros.m_fImageScaleY);
	        m_resumeButton.setIsEnabled(false);
	        m_pNextLevelButton.setIsEnabled(false);
	    }
	    else if (m_bGameSuccessState == false 
	    		&& m_bCongratulationStage == false && (player.bloodNum <= 0))
	    {
	        m_pPlayAgainButton.setVisible(true);
	        m_mainMenuButton.setVisible(true);
	        m_mainMenuButton.setPosition(Macros.m_szWindow.width/2.0f,
	        		Macros.m_szWindow.height/2.0f + 60 * Macros.m_fImageScaleY);
	        m_resumeButton.setIsEnabled(false);
	        m_pNextLevelButton.setIsEnabled(false);
	    }
	    else
	    {
	        m_mainMenuButton.setVisible(true);
	        m_resumeButton.setVisible(true);
	        m_pNextLevelButton.setIsEnabled(false);
	        m_pPlayAgainButton.setIsEnabled(false);
	    }
	}
    
	public void hiddenPauseMenu()
	{
	    m_pPlayAgainButton.setVisible(false);
	    m_resumeButton.setVisible(false);
	    m_pauseSprite.setVisible(false);
	    m_mainMenuButton.setVisible(false);
	    m_pNextLevelButton.setVisible(false);
	    m_pNextLevelButton.setIsEnabled(true);
	    m_pPlayAgainButton.setIsEnabled(true);
	    m_mainMenuButton.setIsEnabled(true);
	    m_resumeButton.setIsEnabled(true);
	}
	
	public void stageIconVisible()
	{
	    stageSprite[0].setVisible(false);
	    stageSprite[1].setVisible(false);
	    stageSprite[2].setVisible(false);
	    stageSprite[stageNum].setVisible(true);
	}
	
	public void goNextLevel(Object sender)
	{
		GameDoc.sharedDoc().m_GameState = GameState.STATE_PLAYING;
	    CCDirector.sharedDirector().resume();
	    Macros.REPLACE_LAYER(this, new GameLayer());
//	    Macros.REPLACE_LAYER_WITH_TRANS(this, new GameLayer(), CCCrossFadeTransition.class, 1.0f);
	}

	public Actor initActorWithType(int ntype){
		Actor actor = null;
		switch (ntype) {
			case 0:
				actor = new Actor("log_down.png", TagWeapon.level01_log_down);
				break;
			case 1:
				actor = new Actor("snake01.png", TagWeapon.level01_snake);
				break;
			case 2:
				actor = new Actor("stone.png", TagWeapon.level01_stone);
				break;
	        case 3:
	            actor = new Actor("thorn_01.png", TagWeapon.level01_thorn);
				break;
			case 4:
				actor = new Actor("log_up.png", TagWeapon.level01_log_up);
				break;
			case 5:
				actor = new Actor("spider01.png", TagWeapon.level01_spider);
				break;
	        case 6:
				actor = new Actor("Bird_right0.png", TagWeapon.level01_bird);
	            break;
	        case 7:
				actor = new Actor("bird_1_0.png", TagWeapon.level02_bird);
	            break;
	        case 8:
				actor = new Actor("balloon_01.png", TagWeapon.level02_ballon_01);
	            break;
	        case 9:
				actor = new Actor("balloon_02.png", TagWeapon.level02_ballon_02);
	            break;
			case 10:
				actor = new Actor("car.png", TagWeapon.level02_car);
				break;
			case 11:
				actor = new Actor("hole.png", TagWeapon.level02_hole);
				break;
			case 12:
				actor = new Actor("stop.png", TagWeapon.level02_stop);
				break;
	        case 13:
	            actor = new Actor("stopCar.png", TagWeapon.level02_stopCar);
				break;
	        case 14:
	            actor = new Actor("thorn_02.png", TagWeapon.level02_thorn);
				break;
	        case 15:
				actor = new Actor("banana_01.png", TagWeapon.banana);
	            break;
	        case 16:
				actor = new Actor("bananaJet_01.png", TagWeapon.banana_jet);
	            break;
	        case 17:
	            actor = new Actor("cake_01.png", TagWeapon.cake);
	            break;
	        case 18:
	            actor = new Actor("cloud_01.png", TagWeapon.level02_cloud01);
				break;
	        case 19:
	            actor = new Actor("cloud_02.png", TagWeapon.level02_cloud02);
				break;
	        case 20:
	            actor = new Actor("cloud_03.png", TagWeapon.level02_cloud03);
				break;
			default:
				break;
		}
		
	    actor.initPosition();
	    
	    if (actor != null)
	    {
	        if (ntype == Actor.TagWeapon.level02_cloud01.ordinal() 
	        		|| ntype == Actor.TagWeapon.level02_cloud02.ordinal() 
	        		|| ntype == Actor.TagWeapon.level02_cloud03.ordinal())
	        {
	            this.addChild(actor, -1);
	        }
	        else
	            this.addChild(actor, 1);
	    }
	    
	    return actor;
	}

	public void initGroupTargets()
	{
	    countObject = Global.COUNT_GERNERAL_TARGET_LEVEL1;
	    if (Global.g_levelNumber == 1)
	        countObject = Global.COUNT_GERNERAL_TARGET_LEVEL1;
	    else
	        countObject = Global.COUNT_GERNERAL_TARGET_LEVEL2;

		groupTargets.ensureCapacity(countObject);

	    for (int i = 0; i < countObject; i ++) {
	        ArrayList<Actor> targetsPerObject = new ArrayList<Actor>();
	        targetsPerObject.ensureCapacity(Global.DEFAULT_COUNT_PER_GERNERAL);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            Actor actor = null;
	            if (Global.g_levelNumber == 1) {
	                actor = this.initActorWithType(i);
	            }
	            else if (Global.g_levelNumber == 2) {
	                actor = this.initActorWithType(i + Global.COUNT_GERNERAL_TARGET_LEVEL1);
	            }
	            
	            Log.i("Actor.visible", String.valueOf(actor.getVisible()));
	            
	            actor.isActive = false;
	            targetsPerObject.add(actor);
	        }

	        groupTargets.add(targetsPerObject);
	    }
	    
	}
	
	public void releaseGroupTargets()
	{
		Actor actor = new Actor();
	    for (int i = 0; i < countObject; i ++) {
	        ArrayList<Actor> targetsPerObject = new ArrayList<Actor>();
	        targetsPerObject = groupTargets.get(i);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            actor = targetsPerObject.get(tarNum);
	            actor.stopAllActions();
	            actor.removeFromParentAndCleanup(true);
	            
	            CCTexture2D tex = actor.getTexture();
	            CCTextureCache.sharedTextureCache().removeTexture(tex);
	        }
	        targetsPerObject.clear();
	    }
	    
	    CCTextureCache.sharedTextureCache().removeTexture("spider01.png");
	    CCTextureCache.sharedTextureCache().removeTexture("Bird_right0.png");
	    CCTextureCache.sharedTextureCache().removeTexture("snake01.png");
	    CCTextureCache.sharedTextureCache().removeTexture("bird_1_0.png");
	}
	
	public void initFoods()
	{
	    foodCount = Global.COUNT_BANANA * 2 + Global.COUNT_CAKE + 2;
	    foods.ensureCapacity(foodCount);
	    for (int i = 0; i < Global.COUNT_BANANA; i ++) {
	        Actor actor = this.initActorWithType(Actor.TagWeapon.banana.ordinal());
	        actor.isActive = false;
	        foods.add(actor);
	        
	        actor = this.initActorWithType(Actor.TagWeapon.banana_jet.ordinal());
	        actor.isActive = false;
	        foods.add(actor);
	    }
	    
	    for (int i = 0; i < Global.COUNT_CAKE; i ++) {
	        Actor actor = this.initActorWithType(Actor.TagWeapon.cake.ordinal());
	        actor.isActive = false;
	        actor.setVisible(false);
	        foods.add(actor);
	    }
	    
	    if (Global.g_levelNumber == 1)
	    {
	        Actor actor = new Actor("cave.png", Actor.TagWeapon.level01_cave);
	        Actor actor1 = new Actor("cave_01.png", Actor.TagWeapon.level01_cave_01);
	        
	        if (actor != null)
	        {
	            foods.add(actor);
	            this.addChild(actor, 0);
	            actor.isActive = false;
	            actor.setVisible(false);
	        }
	        
	        if (actor1 != null)
	        {
	            foods.add(actor1);
	            this.addChild(actor1, 2);
	            actor1.isActive = false;
	            actor1.setVisible(false);
	        }
	        
	    }
	    else
	    {
	        Actor actor = new Actor("tunnel_01.png", Actor.TagWeapon.level02_tunnel_01);
	        Actor actor1 = new Actor("tunnel_02.png", Actor.TagWeapon.level02_tunnel_02);
	        
	        if (actor != null)
	        {
	            foods.add(actor);
	            this.addChild(actor, 0);
	            actor.isActive = false;
	            actor.setVisible(false);
	            
	        }
	        
	        if (actor1 != null)
	        {
	            foods.add(actor1);
	            this.addChild(actor1, 2);
	            actor1.isActive = false;
	            actor1.setVisible(false);
	        }
	    }
	}
	
	public void releaseFoods()
	{
	    for (int i = 0; i < foodCount; i ++) {
	        Actor actor = foods.get(i);
	        actor.stopAllActions();
	        actor.removeFromParentAndCleanup(true);
	        
	        CCTexture2D tex = actor.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex);
	    }
	    
	    foods.clear();
	    
	    CCTextureCache.sharedTextureCache().removeTexture("banana_01.png");
	    CCTextureCache.sharedTextureCache().removeTexture("bananaJet_01.png");
	    CCTextureCache.sharedTextureCache().removeTexture("cake_01.png");
	}
	
	public void initBalls()
	{
	    ballCount = 50;
	    myBalls.ensureCapacity(ballCount);
	    
	    for (int i = 0; i < ballCount; i ++) {
	        Ball ball = new Ball("guning_01.png");
	        ball.setIsActive(false);
	        this.addChild(ball, 1);
	        myBalls.add(ball);
	    }
	}
	
	public void releaseBalls()
	{
	    for (int n = myBalls.size()- 1; n >= 0; n --) {
	        Ball ball = myBalls.get(n);
	        ball.removeFromParentAndCleanup(true);
	        CCTexture2D tex = ball.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex);
	    }
	    myBalls.clear();
	}

	public void addBallsTimer(boolean bType){
		if(bType){
			this.schedule("addBallToBalls", 0.1f);
		} else {
			if(SoundManager.sd_heavy_machin_gun.isPlaying())
				SoundManager.sd_heavy_machin_gun.pause();
			this.unschedule("addBallToBalls");
		}
	}
	
	public void initUpdateWeapon()
	{
	    updateWeapon.ensureCapacity(4);
	    for (int i = 0; i < 4; i ++) {
	        CCSprite aniSprite = CCSprite.sprite(Global.IMG_CHANGE[i]);
	        aniSprite.setScaleX(Macros.m_rScaleX);
	        aniSprite.setScaleY(Macros.m_rScaleY);
	        this.addChild(aniSprite, 2);
	        aniSprite.setVisible(false);
	        updateWeapon.add(aniSprite);
	    }
	}
	
	public void releaseUpdateWeapon()
	{
	    for (int i = 0; i < 4; i ++) {
	        CCSprite aniSprite = updateWeapon.get(i);
	        aniSprite.stopAllActions();
	        aniSprite.removeFromParentAndCleanup(true);
	        
	        CCTexture2D tex = aniSprite.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex);
	    }
	  updateWeapon.clear();
	}
	
	public void initExpresions()
	{
	    expresionCount = 10;
	    expresions.ensureCapacity(expresionCount);
	    
	    for (int i = 0; i < expresionCount;  i ++) {
	        CCSprite sprite = CCSprite.sprite("explosion_01.png");
	        sprite.setScaleX(Macros.m_rScaleX);
	        sprite.setScaleY(Macros.m_rScaleY);
	        this.addChild(sprite, 1);
	        sprite.setVisible(false);
	        expresions.add(sprite);
	    }
	}
	
	public void releaseExpresions()
	{
	    for (int i = 0; i < expresionCount; i ++) {
	        CCSprite sprite = expresions.get(i);
	        sprite.stopAllActions();
	        sprite.removeFromParentAndCleanup(true);
	        
	        CCTexture2D tex = sprite.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex);
	
	    }
	    expresions.clear();
	}
	
	public void scanEndedExpresion()
	{
	    for (int i = 0; i < expresionCount; i ++) {
	        CCSprite sprite =expresions.get(i);
	        if (sprite.getOpacity() <= 10) {
	            sprite.setVisible(false);
	            sprite.setOpacity(255);
	        }
	    }
	}

	public void addCakes()
	{
	    int col = MathUtils.random(9);
	
	    if (col > 5)
	        col = 1;
	
	    int row = MathUtils.random(6);
	    
	    if (col == 1)
	    {
	        row = row + 5;
	        int randPosY = MathUtils.random(9);
	        float posY = (GENERATE_CAKE_DELTA * randPosY / 10.0f) + GENERATE_CAKE_MIN_Y;
	        float posX = Actor.GENERATE_POS_X;
	        
	        for (int i = 0; i < row; i ++) {
	            for (int j = 0; j < foodCount; j ++) {
	                Actor actor = foods.get(j);
	                if (actor.type == Actor.TagWeapon.cake) {
	                    if (actor.isActive == false) {
	                        actor.setPosition(posX , posY);
	                        actor.isActive = true;
	                        actor.setVisible(true);
	                        actor.runActor();
	                        break;
	                    }
	                }
	            }
	            posX += CAKE_WIDTH;
	        }
	        
	        row = MathUtils.random(6);
	        row = row + 3;
	        randPosY = MathUtils.random(9);
	        posY = (GENERATE_CAKE_DELTA * randPosY / 10.0f) + GENERATE_CAKE_MIN_Y + GENERATE_CAKE_DELTA * 2;
	        
	        int tempStartX = (int) posX;
	        for (int cols = 0; cols < 2; cols ++)
	        {
	            for (int j = 0; j < row; j ++) {
	                for (int i = 0; i < foodCount; i ++) {
	                    Actor actor = foods.get(i);
	                    
	                    if (actor.type == Actor.TagWeapon.cake) {
	                        if (actor.isActive == false) {
	                            actor.setPosition(posX , posY);
	                            actor.isActive = true;
	                            actor.setVisible(true);
	                            actor.runActor();
	                            break;
	                        }
	                    }
	                }
	                
	                posX += CAKE_WIDTH;
	            }
	            posX = tempStartX;
	            posY += CAKE_HEIGHT;
	        }
	    }
	    else
	    {
	        row = row + 3;
	        int randPosY = MathUtils.random(9);
	        float posY = (GENERATE_CAKE_DELTA * randPosY / 10f) + GENERATE_CAKE_MIN_Y;
	        float posX = Actor.GENERATE_POS_X;
	        
	        for (int cols = 0; cols < 2; cols ++)
	        {
	            for (int j = 0; j < row; j ++) {
	                for (int i = 0; i < foodCount; i ++) {
	                    Actor actor = foods.get(i);
	                    if (actor.type == Actor.TagWeapon.cake) {
	                        if (actor.isActive == false) {
	                            actor.setPosition(posX , posY);
	                            actor.isActive = true;
	                            actor.setVisible(true);
	                            actor.runActor();
	                            break;
	                        }
	                    }
	                }
	                posX += CAKE_WIDTH;
	            }
	            if (col == 0) {
	                posX = Actor.GENERATE_POS_X;
	            }
	            posY += CAKE_HEIGHT;
	        }
	        
	        row = MathUtils.random(6);
	        row = row + 5;
	        randPosY = MathUtils.random(9);
	        posY = (GENERATE_CAKE_DELTA * randPosY / 10f) + GENERATE_CAKE_MIN_Y + GENERATE_CAKE_DELTA * 2;
	        
	        for (int j = 0; j < row; j ++) {
	            for (int i = 0; i < foodCount; i ++) {
	                Actor actor = foods.get(i);
	                if (actor.type == Actor.TagWeapon.cake) {
	                    if (actor.isActive == false) {
	                        actor.setPosition(posX , posY);
	                        actor.isActive = true;
	                        actor.setVisible(true);
	                        actor.runActor();
	                        break;
	                    }
	                }
	            }
	            posX += CAKE_WIDTH;
	        }
	    }
	}
	
	public void updateGameState()
	{
	    float percent = (float)(backLength + STATE_COUNT * stageNum) / (float)(STATE_COUNT * 3);
	    float posX = startPosGameState + (endPosGameState - startPosGameState) * percent;
	    cursorMonkey.setPosition(posX, SCORE_LABEL_POS_Y );
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event)
	{
	    if (this.stageSuccess == true)
	    {
	        return super.ccTouchesBegan(event);
	    }
	    if (this.player.isJumping == true ) {
	        return super.ccTouchesBegan(event);				
	    }
	    
	  this.player.runJump();
	  
	  return super.ccTouchesBegan(event);
	}
	
	public void activateActorWithType(int ntype)
	{
		ArrayList<Actor> targetsPerObject = new ArrayList<Actor>();
	    if (Global.g_levelNumber == 1) {
	        targetsPerObject = groupTargets.get(ntype);
	    }   
	    else if (Global.g_levelNumber == 2)
	    {
	        targetsPerObject = groupTargets.get(ntype - Global.COUNT_GERNERAL_TARGET_LEVEL1);
	    }
	    
	    for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	        Actor actor = targetsPerObject.get(tarNum);
	        
	        if (actor.isActive == false) {
	            actor.isActive = true;
	            actor.setVisible(true);

	            Log.i("Actor.visible", String.valueOf(actor.getVisible()));

	            actor.runActor();
	            break;
	        }
	    }
	}

	public void activateFoodWithType(Actor.TagWeapon type)
	{
		for (int i = 0; i < foodCount; i ++) {
	        Actor actor = foods.get(i);
	        if (actor.type == type) {
	            if (actor.isActive == false) {
	                actor.isActive = true;
	                actor.setVisible(true);
	                actor.runActor();
	                break;
	            }
	        }
	    }
	}
	
	float generateTimeCount = 0.0f;
    float thresoldTimeCount = 3.0f;

    public void generateTarget(float delta){

	    generateTimeCount += delta;
	    
	    if (generateTimeCount >= thresoldTimeCount) {
	        generateTimeCount = 0.0f;
	        
	        float thresholdLimit = MathUtils.random(9);
	        if (Global.g_levelNumber == 1) {
	            if (stageNum == 0)
	            {
	                thresoldTimeCount = 2.0f + 0.5f * ( thresholdLimit / 10f);
	            }
	            else if (stageNum == 1)
	            {
	                thresoldTimeCount = 1.8f + 0.5f * ( thresholdLimit / 10f);
	            }
	            else if (stageNum == 2)
	            {
	                thresoldTimeCount = 1.6f + 0.3f * ( thresholdLimit / 10f);
	            }
	
	        }
	        else
	        {
	            if (stageNum == 0)
	            {
	                thresoldTimeCount = 1.7f + 0.5f * ( thresholdLimit / 10f);
	            }
	            else if (stageNum == 1)
	            {
	                thresoldTimeCount = 1.5f + 0.5f * ( thresholdLimit / 10f);
	            }
	            else if (stageNum == 2)
	            {
	                thresoldTimeCount = 1.4f + 0.3f * ( thresholdLimit / 10f);
	            }
	        }
	            
	        int flagTop = MathUtils.random(9);
	        
	        if (flagTop > 3)
	            flagTop = 1;
	        else
	            flagTop = 0;
	        
	        int bodyNum = MathUtils.random(2);
	        
	        if (Global.g_levelNumber == 1)
	        {
                if (flagTop == 0){//Top
	                    this.activateActorWithType(bodyNum + 4);
	            } else{
		            // Bottom
	                bodyNum = MathUtils.random(3);
		            this.activateActorWithType(bodyNum);
	            }
	        }
	        else // LEVEL 2
	        {
	            if (player.name == Monkey.TagMonkeyName.MonkeyName_moto)
	            {
	                if (flagTop == 0) // Top
	                {
	                    this.activateActorWithType(bodyNum + 7);
	                } else{
		                // Bottom
		                bodyNum = MathUtils.random(3);
		                if ( (stageNum == 2) && bodyNum == 0)
		                {
		                    bodyNum = 4;
		                }
		                    
		                this.activateActorWithType(bodyNum + 10);
	                }
	            }
	            else
	            {
	                if (flagTop == 0) // Top
	                {
	                    if (player.name == TagMonkeyName.MonkeyName_sboard 
	                    		&& player.getPosition().y > Macros.m_szWindow.height / 2) {
	                        this.activateActorWithType(9);
	                    }
	                    this.activateActorWithType(bodyNum + 7);
	                } 
	                else
	                {
	                    // Bottom
	                    bodyNum = MathUtils.random(3);
	                    if ( (stageNum == 2) && bodyNum == 0)
	                    {
	                        bodyNum = 4;
	                    }
	                    this.activateActorWithType(bodyNum + 10);
	                }
	            }
	        }
	    }
	}
	
	public boolean moveBackgrounds(float delta){

		float delta_X = Global.g_velocityBack * delta;
		CCSprite firstBack = backgrounds.get(0);
	    
	    if ( (firstBack.getPosition().x <= -Macros.m_szWindow.width / 2f) ) {
	        CCSprite lastBack = backgrounds.get(backgrounds.size()-1);
	        
	        backgrounds.add(firstBack);
	        backgrounds.remove(0);
	        
	        firstBack.setPosition( lastBack.getPosition().x + Macros.m_szWindow.width - 0.5f,
	        		firstBack.getPosition().y);
	        firstBack.setScaleX(firstBack.getScaleX()*(-1));
	        backLength ++;
	        this.updateGameState();
	        
	        if ( (backLength % 7) == 0)
	        {
	            this.activateFoodWithType(Actor.TagWeapon.banana);
	        }
	        
	        if ( (backLength % 15) == 0)
	        {
	            this.activateFoodWithType(Actor.TagWeapon.banana_jet);
	        }
	        
	        this.addCakes();
	        
	        if ((backLength == STATE_COUNT - 2) && ( !( (stageNum == 2) ) ))
	        {
	            Actor actor = null;
	            Actor actor1 = null;
	            
	            float temp = firstBack.getContentSize().width * firstBack.getScaleX()/2;
	            if (temp < 0)
	                temp *= -1;
	            
	            if (Global.g_levelNumber == 1)
	            {
	                for (int i = 0; i < foodCount; i ++) {
	                    Actor temp1 = foods.get(i);
	                    if (temp1.type == Actor.TagWeapon.level01_cave) {
	                        actor = temp1;
	                        continue;
	                    }
	                    else if (temp1.type == Actor.TagWeapon.level01_cave_01){
	                        actor1 = temp1;
	                        continue;
	                    }
	                }
	                actor.setPosition( Macros.m_szWindow.width * 3 - actor.rect().size.width / 2 - delta_X,
	                		Global.LAND_Y + actor.rect().size.height / 2);	
	                actor.isActive = true;
	                actor.setVisible(true);
	                
	                actor1.setPosition( Macros.m_szWindow.width * 3 - actor1.rect().size.width / 2 - delta_X,
	                		Global.LAND_Y + actor1.rect().size.height / 2 );
	                actor1.isActive = true;
	                actor1.setVisible(true);
	            }
	            else
	            {
	                for (int i = 0; i < foodCount; i ++) {
	                    Actor temp2 = foods.get(i);
	                    if (temp2.type == Actor.TagWeapon.level02_tunnel_01) {
	                        actor = temp2;
	                        continue;
	                    }
	                    else if (temp2.type == TagWeapon.level02_tunnel_02){
	                        actor1 = temp2;
	                        continue;
	                    }
	                }
	                actor.setPosition( Macros.m_szWindow.width * 3 - actor.rect().size.width / 2 - delta_X,
	                		35 * Macros.m_fImageScaleY + actor.rect().size.height / 2);	
	                actor.isActive = true;
	                actor.setVisible(true);
	
	                actor1.setPosition( Macros.m_szWindow.width * 3 - actor.rect().size.width / 2 - delta_X,
	                		35 * Macros.m_fImageScaleY +actor.rect().size.height / 2 );			
	                actor1.isActive = true;
	                actor1.setVisible(true);
	            }
	        }
	        if (backLength >= STATE_COUNT) {
	            return false;
	        }
	    }
	    
	    for (CCSprite back : backgrounds)
	    {
	        back.setPosition(back.getPosition().x + delta_X, back.getPosition().y);
	    }
	    return true;
	}

	public void congretulation()
	{
	    CCSprite congratulation = CCSprite.sprite("congratlation.png");
	    congratulation.setScaleX(Macros.m_rScaleX);
	    congratulation.setScaleY(Macros.m_rScaleY);
	    congratulation.setPosition(Macros.m_szWindow.width / 2, Macros.m_szWindow.height * 3 / 4);
	    this.addChild(congratulation, 5);
	    CCTextureCache.sharedTextureCache().removeTexture(congratulation.getTexture());

	    this.saveLevelInfo();
	    
	    if (Global.g_levelNumber == 1)
	    {
	        Global.g_levelNumber ++;
	        GameSetting.putValue("LEVEL_INFO", 1);
//		    Macros.REPLACE_LAYER_WITH_FADE(this, new GameLayer(), 2.0f, ccColor3B.ccBLACK);
	    }
	    else
	    {
	        m_bGameSuccessState = true;
//	        Macros.REPLACE_LAYER_WITH_FADE(this, new MainMenuScene(), 1.0f, ccColor3B.ccWHITE);
	    }
	    
	    m_bCongratulateState = true;
	    
	    this.showPauseMenu();
//	    Macros.REPLACE_LAYER_WITH_TRANS(this, new GameLayer(), CCCrossFadeTransition.class, 1.0f);
	}
	
	public void selMonkeyHoHo()
	{
		player.stopAllActions();
		if(!Global.g_momkeyMom){
			player.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_monkey_hoho.copy()));
		}else{
			player.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_monkey_mom_hoho.copy()));
		}
	
	    if (Global.g_momkeyMom == false)
	    {
	    	player.runAction(CCSequence.actions(
	                         CCMoveBy.action(0.5f, CGPoint.ccp(0, 220 * Macros.m_fImageScaleY)),
	                         CCCallFunc.action(this, "congretulation")));
	    }
	    else
	    {
	    	player.runAction(CCSequence.actions(
                      CCMoveBy.action(0.5f, CGPoint.ccp(0, 250 * Macros.m_fImageScaleY)),
                      CCCallFunc.action(this, "congretulation")));
	    }
	}
	
	public void monkeyFallDown(float delta){
	    
		if ( ((endCake.getPosition().y
				+ endCake.getContentSize().height * Macros.m_rScaleY / 2) >
			player.getPosition().y) && isExpressedEndCake == false)
	    {
	        isExpressedEndCake = true;
	        SoundManager.sharedSoundManager().playEffect(R.raw.sd_crush_cake);
	        
	        SoundManager.sharedSoundManager().pauseMusic();
	        SoundManager.sharedSoundManager().playMusic(R.raw.sd_crush_cake_after, false);
	        
	    	// CAKE EXPRESSION
			CCAnimation ani_cake_expression = CCAnimation.animation("Animation31", 0.1f);
		    for (int num = 0; Global.IMG_CAKE_EXP[num] != null; num ++) {
				String filename = Global.IMG_CAKE_EXP[num];
				ani_cake_expression.addFrame(filename);
			}
		    
	        endCake.runAction(CCAnimate.action(ani_cake_expression, false));
	    }

		player.setPosition(player.getPosition().x,
	    		player.getPosition().y - 700 * Macros.m_fImageScaleY * delta);
	    
	    if (player.getPosition().y < 0)
	    {
	        this.unschedule("monkeyFallDown");
	
	        player.setScaleX(2 * Macros.m_rScaleX);
	        player.setScaleY(2 * Macros.m_rScaleY);
	
	        this.selMonkeyHoHo();
	    }
	}
	
	public void monkeyFlay(float delta)
	{
	    if (endTargets.size() == 0)
	    {
	    	endCake.runAction(CCMoveTo.action(0.3f,
	    			CGPoint.ccp(Macros.m_szWindow.width / 2,
	    						endCake.getContentSize().height * Macros.m_rScaleY / 2)));
	        this.unschedule("monkeyFlay");
	        this.schedule("monkeyFallDown");
	    }
	    for (int n = endTargets.size()- 1; n >= 0; n --)
	    {
	        Actor actor = endTargets.get(n);
	        actor.setPosition(actor.getPosition().x - completeVelocity.x * delta,
	        		actor.getPosition().y);
	        
	        if (actor.rect().origin.x + actor.rect().size.width < 0)
	        {
	            CCTextureCache.sharedTextureCache().removeTexture(actor.getTexture());
	            actor.removeFromParentAndCleanup(true);
	            endTargets.remove(actor);
	        }
	    }
	    player.gameScore += 50;
	    String string = String.format("%d", player.gameScore);
	    scoreLabel.setString(string);
	    
	    player.setScaleX(player.getScaleX()*1.005f);
	    player.setScaleY(player.getScaleY()*1.005f);
	    
	    if (player.getScaleX() > 1.2f * Macros.m_rScaleX) player.setScaleX(1.2f * Macros.m_rScaleX);
	    if (player.getScaleY() > 1.2f * Macros.m_rScaleY) player.setScaleY(1.2f * Macros.m_rScaleY);
	    
	    player.setPosition(player.getPosition().x,
	    		player.getPosition().y - 10 * Macros.m_fImageScaleY * delta);
	    
	}
	
	public void completeStep(float delta)
	{
	    if (completeSum_X <= END_MONKEY_2_X)
	    {
	        completeVelocity.x += END_MONKEY_ACCEL_X * delta;
	        completeVelocity.y += END_MONKEY_ACCEL_Y * delta;
	        player.setPosition(player.getPosition().x, 
	        		player.getPosition().y - completeVelocity.y * delta);
	        
	        Log.i("player.position_",
	        		String.format("%f%f", player.getPosition().x, player.getPosition().y));
	        
	        endHill1.setPosition(endHill1.getPosition().x - completeVelocity.x * delta,
	        		endHill1.getPosition().y);
	        
	        Log.i("endHill1.position.y", String.format("%f", endHill1.getPosition().x));
	        
	        endHill2.setPosition(endHill2.getPosition().x - completeVelocity.x * delta,
	        		endHill2.getPosition().y);
	        completeSum_X += completeVelocity.x * delta;
	                
	    }
	    else if (completeSum_X >= END_MONKEY_2_X && completeSum_X <= END_MONKEY_3_X)
	    {
	        if (player.getRotation() > -30)
	        {
	            player.setRotation(player.getRotation()-10);
	        }
	        endHill1.setPosition(endHill1.getPosition().x - completeVelocity.x * delta, endHill1.getPosition().y);
	        endHill2.setPosition(endHill2.getPosition().x - completeVelocity.x * delta, endHill2.getPosition().y);
	        completeSum_X += completeVelocity.x * delta;
	        
	    }
	    else if (completeSum_X >= END_MONKEY_3_X && completeSum_X <= 3050 * Macros.m_fImageScaleX)
	    {
	        player.setPosition(player.getPosition().x, player.getPosition().y + completeVelocity.y * delta);
	        
	        if (player.getPosition().y >= (650 * Macros.m_fImageScaleY))
	        {
	            player.setPosition(player.getPosition().x, (650 * Macros.m_fImageScaleY));
	        }
	        endHill1.setPosition(endHill1.getPosition().x - completeVelocity.x * delta, endHill1.getPosition().y);
	        endHill2.setPosition(endHill2.getPosition().x - completeVelocity.x * delta, endHill2.getPosition().y);
	        completeSum_X += completeVelocity.x * delta;
	        
	
	    }
	    else if (completeSum_X > 3050 * Macros.m_fImageScaleX)
	    {
	      player.runAction(CCMoveTo.action(2.5f, 
	    		  CGPoint.ccp(Macros.m_szWindow.width / 2, player.getPosition().y)));
	        this.unschedule("completeStep");
	        this.schedule("monkeyFlay");
	    }
	    
	    for (int n = endTargets.size() - 1; n >= 0; n --)
	    {
	        Actor actor = endTargets.get(n);
	        if (actor.type == Actor.TagWeapon.level02_cloud01 
	        		|| actor.type == Actor.TagWeapon.level02_cloud02
	        		|| actor.type == Actor.TagWeapon.level02_cloud03)
	        {
	            actor.setPosition(actor.getPosition().x - completeVelocity.x * delta,
	            		actor.getPosition().y);
	        }
	        
	        if (actor.rect().origin.x + actor.rect().size.width < 0)
	        {
	            CCTextureCache.sharedTextureCache().removeTexture(actor.getTexture());
	            actor.removeFromParentAndCleanup(true);
	            endTargets.remove(actor);
	        }
	    }
	    
	    for (int i = 0; i < foodCount; i ++) {
	        Actor actor = foods.get(i);
	        if (actor.isActive == true) {
	            if (actor.type == Actor.TagWeapon.cake)
	            {
	                actor.setPosition(
	                		actor.getPosition().x - completeVelocity.x * delta,
	                		actor.getPosition().y);
	                if ( this.player.collidgePlayerWithActor(actor) == false )
	                {
	                    continue;
	                }
	            }
	        }
	    }
	}
	
	public void onPlayAfterCake()
	{
		SoundManager.sharedSoundManager().pauseMusic();
		SoundManager.sharedSoundManager().playMusic(R.raw.sd_end_cake_after);
	}
	
	public void startCompleteAnimation()
	{
		
	    m_pause.setVisible(false);
	    
	    player.setRotation(30f);
	    completeVelocity.set(0,0);
	    completeSum_X = END_MONKEY_1_X;
	    
	    if(Global.g_bSoundState == Global.Sound.SOUND_ON)
	    {
	    	this.runAction(CCSequence.actions(
	    			CCDelayTime.action(2.0f),
	    			CCCallFunc.action(this, "onPlayAfterCake")));
	    }
	    
	    m_bCongratulationStage = true;
	    this.schedule("completeStep");
	}
	
	public void addCloudInCompleteLevel(int ntype)
	{
		Actor actor = null;
		float height = Actor.GENERATE_SPRIDER_POS_Y;
	    
		switch(ntype) {
        case 18:
	        {
	            actor = new Actor("cloud_01.png", Actor.TagWeapon.level02_cloud01);
	            height = Actor.GENERATE_SPRIDER_POS_Y;
	            
	            int randheight = MathUtils.random(2);
	            
	            switch (randheight) {
	                case 0:
	                    height = 600 * Macros.m_fImageScaleY;
	                    break;
	                case 1:
	                    height = 650 * Macros.m_fImageScaleY;
	                    break;
	                case 2:
	                    height = 700 * Macros.m_fImageScaleY;
	                    break;
	                    
	                default:
	                    break;
	            }
	            break;
	
	        }
	        case 19:
	        {
	            actor = new Actor("cloud_02.png", Actor.TagWeapon.level02_cloud02);
	            height = Actor.GENERATE_SPRIDER_POS_Y;
	            int randheight = MathUtils.random(2);
	            switch (randheight) {
	                case 0:
	                    height = 600 * Macros.m_fImageScaleY;
	                    break;
	                case 1:
	                    height = 650 * Macros.m_fImageScaleY;
	                    break;
	                case 2:
	                    height = 700 * Macros.m_fImageScaleY;
	                    break;
	                    
	                default:
	                    break;
	            }
	            break;
	
	        }
	        case 20:
	        {
	            actor = new Actor("cloud_03.png", Actor.TagWeapon.level02_cloud03);
	            height = Actor.GENERATE_SPRIDER_POS_Y;
	            int randheight = MathUtils.random(2);
	            switch (randheight) {
	                case 0:
	                    height = 600 * Macros.m_fImageScaleY;
	                    break;
	                case 1:
	                    height = 650 * Macros.m_fImageScaleY;
	                    break;
	                case 2:
	                    height = 700 * Macros.m_fImageScaleY;
	                    break;
	                    
	                default:
	                    break;
	            }
	            break;
	        }
	        default:
	            break;
	    }
	    
	    actor.setPosition( Actor.GENERATE_POS_X, height);
	    if (actor != null)
	    {
	        if (ntype == Actor.TagWeapon.level02_cloud01.ordinal() 
	        		|| ntype == Actor.TagWeapon.level02_cloud02.ordinal() 
	        		|| ntype == Actor.TagWeapon.level02_cloud03.ordinal())
	        {
	            this.addChild(actor, 0);
	            endTargets.add(actor);
	        }
	    }
	    
	}

	public void addEndCakes()
	{
	    float posX = END_MONKEY_1_X;
	    float posY = END_MONKEY_1_Y + 30 * Macros.m_fImageScaleY;
	    float alpha = (END_MONKEY_2_Y - END_MONKEY_1_Y) / (END_MONKEY_2_X - END_MONKEY_1_X);
	    float deltaX = 50 * Macros.m_fImageScaleX;
	    
	    while (posX <= END_MONKEY_2_X)
	    {
	        posX += deltaX;
	        posY += deltaX * alpha;
	        
	        for (int i = 0; i < foodCount; i ++) {
	            Actor actor = foods.get(i);
	            if (actor.type == Actor.TagWeapon.cake) {
	                if (actor.isActive == false) {
	                    actor.setPosition(posX , posY);
	                    
	                    actor.isActive = true;
	                    actor.setVisible(true);
	                    actor.runActor();
	                    break;
	                }
	            }
	        }
	    }
	    
	    posX = END_MONKEY_3_X;
	    posY = END_MONKEY_3_Y;
	    alpha = (410 * Macros.m_fImageScaleY - END_MONKEY_3_Y) / ( (1024 + 777)* Macros.m_fImageScaleX - END_MONKEY_3_X);
	    
	    while (posX >= END_MONKEY_3_X && posX <= 1700 * Macros.m_fImageScaleX)
	    {
	        posX += deltaX;
	        posY += deltaX * alpha;
	        
	        for (int i = 0; i < foodCount; i ++) {
	            Actor actor = foods.get(i);
	            
	            if (actor.type == Actor.TagWeapon.cake) {
	                if (actor.isActive == false) {
	                    actor.setPosition(posX , posY);
	                    
	                    actor.isActive = true;
	                    actor.setVisible(true);
	                    actor.runActor();
	                    break;
	                }
	            }
	        }
	    }
	}
	
	public void completeLevel(){
		
	     for (int n = backgrounds.size()- 1; n >= 0; n--)
	     {
	    	 CCSprite back = backgrounds.get(n);
    	     CCTexture2D tex = back.getTexture();
	         CCTextureCache.sharedTextureCache().removeTexture(tex);
	            
	         back.removeFromParentAndCleanup(true);
	         backgrounds.remove(back);
	     }
	     
	      backgrounds.removeAll(backgrounds);
	      this.removeAllTargets(0);
	      this.removeAllTargets(1);
	      this.removeAllTargets(2);
	      
	      SoundManager.sharedSoundManager().pauseMusic();
	        
	        endBackSky = CCSprite.sprite("sky.png");
	        endBackSky.setScaleX(Macros.m_rScaleX);
	        endBackSky.setScaleY(Macros.m_rScaleY);
	        endBackSky.setPosition(Macros.m_szWindow.width / 2, Macros.m_szWindow.height / 2);
	        this.addChild(endBackSky, -1);
	        CCTextureCache.sharedTextureCache().removeTexture(endBackSky.getTexture());
	        
	        for (int i = 0; i < 10; i ++)
	        {
	            int cloudNum = MathUtils.random(2);
	            cloudNum = cloudNum + 18;
	            this.addCloudInCompleteLevel(cloudNum);
	        }
	        
	        float cloudPosX = 100 * Macros.m_fImageScaleX;
	        for (int n = endTargets.size()- 1; n >= 0; n --)
	        {
	            Actor actor = endTargets.get(n);
	            actor.setPosition(cloudPosX, actor.getPosition().y);
	            cloudPosX += 300 * Macros.m_fImageScaleX;
	        }
	
	        this.addEndCakes();
	        
	        endHill1 = CCSprite.sprite("high jump_01.png");
	        endHill1.setScaleX(Macros.m_rScaleX);
	        endHill1.setScaleY(Macros.m_rScaleY);
	        endHill1.setPosition(Macros.m_szWindow.width / 2,
	        		endHill1.getContentSize().height * Macros.m_rScaleY / 2);
	        this.addChild(endHill1, 0);
	        CCTextureCache.sharedTextureCache().removeTexture(endHill1.getTexture());
	        
	        endHill2 = CCSprite.sprite("high jump_02.png");
	        endHill2.setScaleX(Macros.m_rScaleX);
	        endHill2.setScaleY(Macros.m_rScaleY);
	        endHill2.setPosition(Macros.m_szWindow.width * 3 / 2,
	        		endHill1.getContentSize().height * Macros.m_rScaleY / 2);
	        this.addChild(endHill2, 0);
	        CCTextureCache.sharedTextureCache().removeTexture(endHill2.getTexture());
	        
	        endCake = CCSprite.sprite("endcake01.png");
	        endCake.setScaleX(Macros.m_rScaleX);
	        endCake.setScaleY(Macros.m_rScaleY);
	        endCake.setPosition(Macros.m_szWindow.width / 2,
	        		-endCake.getContentSize().height * Macros.m_rScaleY / 2);
	        this.addChild(endCake, 2);
	        CCTextureCache.sharedTextureCache().removeTexture(endCake.getTexture());
	        
	        player.isWillChange = true;
	        player.isJumping = false;
	        player.nameWillChange = Monkey.TagMonkeyName.MonkeyName_sboard;
	
	        player.changeMonkey();
	
	        player.setScaleX(Macros.m_rScaleX * 0.8f);
	        player.setScaleY(Macros.m_rScaleY * 0.8f);
	        player.setPosition(END_MONKEY_1_X,
	        		END_MONKEY_1_Y + player.rectMonkey().size.height / 2);
	        this.startCompleteAnimation();
	  	
	}
	
	public void woodTime(float delta)
	{
	    
	    if (fallWood != null)
	    {
	        float velocity = -500 * Macros.m_fImageScaleY * delta;
	        fallWood.setPosition(fallWood.getPosition().x, fallWood.getPosition().y + velocity);
	        
	        if (fallWood.getPosition().y <= Global.LAND_Y )
	        {
	            fallWood.setPosition(fallWood.getPosition().x, Global.LAND_Y);
	            this.unschedule("woodTime");
	            fallWood = null;
	        }
	
	        for (int j = 0; j < countObject; j ++) {
	            ArrayList<Actor> targetsPerObject = groupTargets.get(j);
	            
	            for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	                Actor actor;
	                actor = targetsPerObject.get(tarNum);
	                
	                if (actor.isActive == true) {
	                    if (actor.type == Actor.TagWeapon.level01_snake)
	                    {
	                        if (CGRect.intersects(fallWood.rect(), actor.rect()))
	                        {
	                            player.gameScore += actor.addScore;
	                            this.updateScore();
	                            
	                            for (int i = 0; i < expresionCount; i ++) {
	                                CCSprite sprite = expresions.get(i);
	                                if (sprite.getVisible() == false) {
	                                    sprite.setVisible(true);
	                                    sprite.setPosition(actor.getPosition());
	                                    CCSequence seq = CCSequence.actions(Global.sharedGlobal().ani_explosion.copy(),
	                                                     CCFadeOut.action(0.2f),
	                                                     CCCallFunc.action(this, "scanEndedExpresion"));
	                                  	sprite.runAction(seq);
	                                    break;
	                                }
	                            }
	                            
	                            actor.isActive = false;
	                            actor.setVisible(false);
	                            actor.initPosition(); 
	                            actor.unscheduleAllSelectors();
	                            actor.stopAllActions();
	                            
	                        }
	                    }
	                }
	            }
	        }
	
	    }
	}
	
	public void updateStage()
	{
	    stageNum += 1;
	    if (stageNum >= 3)
	    {
	        this.completeLevel();
	        this.unschedule("doStep");
	        return;
	    }
	    else
	    {
	        this.stageIconVisible();
	        this.restartGame();
	        this.schedule("doStep");
	    }
	}
	
	public void completeStage()
	{
	    this.unschedule("doStep");
	    this.updateStage();
	}
	
	public void actorMoveAndCollition(float delta)
	{
	    for (int i = 0; i < countObject; i ++) {
	        ArrayList<Actor> targetsPerObject = groupTargets.get(i);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            Actor actor;
	            actor = targetsPerObject.get(tarNum);
	            
	            if (actor.isActive == true) {
	                if (actor.move(delta) == false)
	                    continue;
	                
	                for (int n = myBalls.size()- 1; n >= 0; n --){
	                    Ball ball = myBalls.get(n);
	                    if (ball.isActive == true) {
	                        if (ball.collideWithActor(actor) == true)
	                            break;
	                    }
	                }
	            }
	        }
	    }
	
	    for (int i = 0; i < countObject; i ++) {
	        ArrayList<Actor> targetsPerObject = groupTargets.get(i);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            Actor actor;
	            actor = targetsPerObject.get(tarNum);
	            
	            if (actor.isActive == true) {
	                if ( this.player.collidgePlayerWithActor(actor) == true )
	                {
	                    return;
	                }
	            }
	        }
	    }
	}
	
	public void foodMoveAndCollition(float delta)
	{
	    for (int i = 0; i < foodCount; i ++) {
	        Actor actor = foods.get(i);
	        
	        if (actor.isActive == true) {
	            if (actor.move(delta) == false) {
	                continue;
	            }
	            
	            if ( this.player.collidgePlayerWithActor(actor) == true )
	            {
	                break;
	            }
	
	        }
	    }
	}
	
	public void doStep(float delta)
	{
	    for (int n = myBalls.size()- 1; n >= 0; n --) {
			Ball ball = myBalls.get(n);
	        if (ball.isActive == true) {
	          ball.move(Global.delta);
	        }
		}
	
	    if (player.isDelay == false)
	    {
	        if (player.rectMonkey().origin.x > Macros.m_szWindow.width)
	        {
	            this.completeStage();
	        }
	        
	        if (stageSuccess == false)
	        {
	            if (this.moveBackgrounds(Global.delta) == false)
	            {
	                stageSuccess = true;
	                player.isJumping = false;
	                player.isDelay = false;
	                this.removeChildByTag(1, true);
	                if (player.name == TagMonkeyName.MonkeyName_sboard && player.getScaleY() < 0)
	                {
	                    player.setScaleY(player.getScaleY()*(-1));
	                    player.setPosition(player.getPosition().x,
	                    		Global.LAND_Y + player.rectMonkey().size.height / 2);
	                }
	            }
	            else
	            {
	                Global.g_velocityBack += Global.VELOCITY_DELTA_X * Global.delta;
	                if (Global.g_levelNumber == 1)
	                {
	                    if (Global.g_velocityBack < Global.VELOCITY_LIMIT1)
	                        Global.g_velocityBack = Global.VELOCITY_LIMIT1;
	                }
	                else{
	                    if (Global.g_velocityBack < Global.VELOCITY_LIMIT2)
	                        Global.g_velocityBack = Global.VELOCITY_LIMIT2;
	                }
	                this.generateTarget(Global.delta);
	                this.actorMoveAndCollition(Global.delta);
	                this.foodMoveAndCollition(Global.delta);
	            }
	        }
	        else
	        {
	            float delta_X = Global.g_velocityBack * Global.delta;
	            player.setPosition(player.getPosition().x - delta_X,
	            		player.getPosition().y);
	        }
	    }
	}
	
	public void addBallToBalls(float delta)
	{
		if (player.isDelay == true) {
			if(SoundManager.sd_heavy_machin_gun.isPlaying())
				SoundManager.sd_heavy_machin_gun.pause();
			this.unschedule("addBallToBalls");
			return;
		}
	
	    int addNum = 1;
		for (int i = 0; i < ballCount ; i ++) {
	        Ball ball = myBalls.get(i);
	        if (ball.isActive == false) {
	            
	            if (addNum == 1) {
	                ball.velocity = CGPoint.ccp(BALLSTARTING_VELOCITY_X, BALLSTARTING_VELOCITY_Y);
	                ball.setPosition(CGPoint.ccpAdd(player.getPosition(),
	                		CGPoint.ccp(player.startBallPos.x * player.getScaleX(),
	                					player.startBallPos.y * player.getScaleY())));
	                addNum = 2;
	                ball.setIsActive(true);
	            }
	            else if (addNum == 2){
	                ball.velocity = CGPoint.ccp(BALLSTARTING_VELOCITY_X, BALLSTARTING_VELOCITY_Y / 2);
	                ball.setPosition(CGPoint.ccpAdd(player.getPosition(),
	                		CGPoint.ccp(player.startBallPos.x * player.getScaleX(),
	                					player.startBallPos.y * player.getScaleY())));
	                addNum = 3;
	                ball.setIsActive(true);
	            }
	            else if (addNum == 3){
	                ball.velocity = CGPoint.ccp(BALLSTARTING_VELOCITY_X, 0);
	                ball.setPosition(CGPoint.ccpAdd(player.getPosition(),
	                		CGPoint.ccp(player.startBallPos.x * player.getScaleX(),
	                					player.startBallPos.y * player.getScaleY())));
	                addNum = 4;
	                ball.setIsActive(true);
	                break;
	            }
	        }
	    }
	}
	
	public void popupGameOver()
	{
	    this.saveLevelInfo();

	    this.unscheduleAllSelectors();
	    this.stopAllActions();

	    m_pause.setVisible(false);
	    m_gameOver.setVisible(true);	    
	    
	    this.runAction(CCSequence.actions(CCDelayTime.action(1.0f),
	                   CCCallFunc.action(this, "onShowPauseMenuGameOver")));

	}
	
	public void onShowPauseMenuGameOver()
	{
	    m_gameOver.setVisible(false);
	    SoundManager.sharedSoundManager().pauseMusic();
	    CCDirector.sharedDirector().pause();
	    this.showPauseMenu();
	}
	
	public void restartGameOver()
	{
		GameDoc.sharedDoc().m_GameState = GameState.STATE_PLAYING;
	    for (int n = myBalls.size()- 1; n >= 0; n --) {
	        Ball ball = myBalls.get(n);
	        if (ball.isActive == true) {
	        	ball.setIsActive(false);
	        }
	    }
	    
	    for (int i = 0; i < countObject; i ++) {
	        ArrayList<Actor> targetsPerObject = groupTargets.get(i);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            Actor actor;
	            actor = targetsPerObject.get(tarNum);
	            
	            if (actor.isActive == true) {
	                actor.isActive = false;
	                actor.setVisible(false);
	                actor.initPosition(); 
	                actor.unscheduleAllSelectors();
	                actor.stopAllActions();
	            }
	        }
	    }
	    
	    for (int i = 0; i < foodCount; i ++) {
	        Actor actor = foods.get(i);
	        if (actor.isActive == true) {
	            actor.isActive = false;
	            actor.setVisible(false);
	            actor.initPosition(); 
	            actor.unscheduleAllSelectors();
	            actor.stopAllActions();
	        }
	    }
	    
	    for (int n = backgrounds.size() - 1; n >= 0; n--)
	    {
	        CCSprite back = backgrounds.get(n);
	        back.removeFromParentAndCleanup(true);
	        backgrounds.remove(back);
	    }
	    backgrounds.removeAll(backgrounds);
	    
	    player.bloodNum -= 1;
	    String string = String.format("%d", player.bloodNum);
	    bloodLabel.setString(string);
	    
	    if (player.bloodNum <= 0)
	    {
	    	SoundManager.sharedSoundManager().pauseMusic();
	        this.popupGameOver();
	    }
	    else
	    {
	        if (Global.g_levelNumber == 1)
	        {
	            if (stageNum == 0) {
	                Global.g_velocityBack = Global.VELOCITY_START1_X;
	            }
	        }
	        else
	        {
	            if (stageNum == 0)
	            {
	                Global.g_velocityBack = Global.VELOCITY_START2_X;
	            }
	        }
	    }
	    
	    this.initBackground();
	    this.updateScore();
	    this.updateGameState();
	    
	    player.setOpacity(255);
	    player.restartMonKeyOver();
	    
	    player.isDelay = false;
	    stageSuccess = false;
	    
	}
	
	public void restartGame()
	{    
	    GameDoc.sharedDoc().m_GameState = GameState.STATE_PLAYING;
	    for (int n = myBalls.size()- 1; n >= 0; n --) {
	        Ball ball = myBalls.get(n);
	        if (ball.isActive == true) {
	          ball.setIsActive(false);
	        }
	    }
	    
	    for (int foodNumber = 0; foodNumber < foodCount; foodNumber ++) {
	        Actor actor = foods.get(foodNumber);
	        if (actor.type == Actor.TagWeapon.level01_cave
	        		|| actor.type == Actor.TagWeapon.level01_cave_01
	        		|| actor.type == Actor.TagWeapon.level02_tunnel_01
	        		|| actor.type == Actor.TagWeapon.level02_tunnel_02 ) {
	        	
	            actor.isActive = false;
	            actor.setVisible(false);
	        }
	    }
	
	    for (int n = backgrounds.size()- 1; n >= 0; n--)
	    {
	        CCSprite back = backgrounds.get(n);
	        CCTexture2D tex = back.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex);
	        
	        back.removeFromParentAndCleanup(true);
	        backgrounds.remove(back);
	    }
	    backgrounds.removeAll(backgrounds);
	    
	    if(!SoundManager.sd_background.isPlaying()&& (Global.g_bSoundState == Global.Sound.SOUND_ON))
	    	SoundManager.sd_background.start();
	    this.initBackground();
	    this.updateScore();
	    this.updateGameState();
	
	    player.setOpacity(255);
	    player.restartMonkey();
	    player.isDelay = false;
	    stageSuccess = false;
	}
	
	public void removeAllTargets(int isgameEnd)
	{
	    for (int i = 0; i < countObject; i ++) {
	        ArrayList<Actor> targetsPerObject = groupTargets.get(i);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            Actor actor;
	            actor = targetsPerObject.get(tarNum);
	            
	            if (actor.isActive == true) {
	            
	                for (int j = 0; j < expresionCount; j ++) {
	                    CCSprite sprite = expresions.get(j);
	                    if (sprite.getVisible() == false) {
	                        sprite.setVisible(true);
	                        sprite.setPosition(actor.getPosition());
	                        CCSequence seq = CCSequence.actions(Global.sharedGlobal().ani_explosion.copy() ,
	                                         CCFadeOut.action(0.2f),
	                                         CCCallFunc.action(this, "scanEndedExpresion"));
	                      sprite.runAction(seq);
	                      break;
	                    }
	                }
	                
	                actor.isActive = false;
	                actor.setVisible(false);
	                actor.initPosition(); 
	                actor.unscheduleAllSelectors();
	                actor.stopAllActions();
	            }
	        }
	    }
	
	    if (isgameEnd == 2) {
	        return;
	    }
	    for (int i = 0; i < foodCount; i ++) {
	        Actor actor = foods.get(i);
	        if (actor.isActive == true) {
	            
	            if (isgameEnd == 1)
	            {
	                if (actor.type == Actor.TagWeapon.level01_cave || 
	                    actor.type == Actor.TagWeapon.level01_cave_01 ||
	                    actor.type == Actor.TagWeapon.level02_tunnel_01 ||
	                    actor.type == Actor.TagWeapon.level02_tunnel_02)
	                {
	                    continue;
	                }
	            }
	
	            actor.isActive = false;
	            actor.setVisible(false);
	            actor.initPosition(); 
	            actor.unscheduleAllSelectors();
	            actor.stopAllActions();
	        }
	    }
	}
	
	public void removeCache()
	{
	    this.removeSprite(stageSprite[0]);
	    this.removeSprite(stageSprite[1]);
	    this.removeSprite(stageSprite[2]);
	    
	    this.removeSprite(m_pauseSprite);
	    this.removeSprite((CCSprite)m_resumeButton.getNormalImage());
	    this.removeSprite((CCSprite)m_resumeButton.getSelectedImage());
	    m_resumeButton.removeFromParentAndCleanup(true);
	    
	    this.removeSprite((CCSprite)m_mainMenuButton.getNormalImage());
	    this.removeSprite((CCSprite)m_mainMenuButton.getSelectedImage());
	    m_mainMenuButton.removeFromParentAndCleanup(true);
	    
	    this.removeSprite((CCSprite)m_pNextLevelButton.getNormalImage());
	    this.removeSprite((CCSprite)m_pNextLevelButton.getSelectedImage());
	    m_pNextLevelButton.removeFromParentAndCleanup(true);
	    
	    this.removeSprite((CCSprite)m_pPlayAgainButton.getNormalImage());
	    this.removeSprite((CCSprite)m_pPlayAgainButton.getSelectedImage());
	    m_pPlayAgainButton.removeFromParentAndCleanup(true);
	    
	    CCTexture2D tex = scoreLabel.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    scoreLabel.removeFromParentAndCleanup(true);
	    
	    tex = bloodLabel.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    bloodLabel.removeFromParentAndCleanup(true);
	
	        
	    this.removeSprite((CCSprite)m_pause.getNormalImage());
	    this.removeSprite((CCSprite)m_pause.getSelectedImage());
	    m_pause.removeFromParentAndCleanup(true);
	    
	    this.removeSprite(cursorMonkey);
	    this.removeSprite(seekbar);
	    this.removeSprite(scoreSprite);
	    this.removeSprite(bloodSprite);
	    this.removeSprite(heart);
	    CCTextureCache.sharedTextureCache().removeTexture(Global.IMG_HEART[0]);
	    
	    this.releaseExpresions();
	    this.releaseUpdateWeapon();
	    this.releaseBalls();
	    this.releaseGroupTargets();
	    this.releaseFoods();
	
	    int endTargetsCount = endTargets.size();
	    for (int i = 0; i < endTargetsCount; i ++) {
	        Actor actor = endTargets.get(i);
	        actor.stopAllActions();
	        actor.removeFromParentAndCleanup(true);
	        CCTexture2D tex_ = actor.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex_);
	    }
	    endTargets.clear();
	    
	    for (int n = backgrounds.size()- 1; n >= 0; n--)
	    {
	        CCSprite back = backgrounds.get(n);
	        
	        CCTexture2D tex_ = back.getTexture();
	        CCTextureCache.sharedTextureCache().removeTexture(tex_);
	        
	        back.removeFromParentAndCleanup(true);
	        backgrounds.remove(back);
	    }
	    backgrounds.clear();
	
	    this.removeSprite(m_gameOver);
	}
	
	public void removeSprite(CCSprite sp)
	{
	    CCTexture2D tex = sp.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    sp.removeFromParentAndCleanup(true);
	}
	
	public void saveLevelInfo()
	{
		Global.g_nLevelCompleteInfo = 1;
	    int nPlayerCount = GameSetting.getIntValue("PLAYER_COUNT", 1);
	
	    String str;
	    
	    if (nPlayerCount >= 5) {
	    	GameSetting.putValue("PLAYER_COUNT", nPlayerCount);
	        str = String.format("Player%d", nPlayerCount);
	    }
	    else
	    {
	        str = String.format("Player%d", nPlayerCount);
	        GameSetting.putValue("PLAYER_COUNT", nPlayerCount+1);
	    }
	    
	    GameSetting.putValue(str, player.gameScore);
		GameSetting.putValue("LEVEL_VALUE", Global.g_levelNumber);
	}

	@Override
	public void onExit(){
		this.removeCache();
		super.onExit();
	}
}
