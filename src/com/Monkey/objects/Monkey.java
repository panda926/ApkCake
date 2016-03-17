package com.Monkey.objects;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import com.Monkey.GameLayer;
import com.Monkey.R;
import com.Monkey.common.Global;
import com.Monkey.common.Macros;
import com.Monkey.common.SoundManager;

public class Monkey extends CCSprite{

    final float START_BOLL_POS_X = 114;
    final float START_BOLL_POS_Y = 15;

    final float JUMP_VEL 	=  Macros.m_fImageScaleY*100;
    final float JUMP_MINUS 	=  Macros.m_fImageScaleY*(-150);

    final float AFTER_MOTO_X = -10*Macros.m_fImageScaleX;
    final float AFTER_MOTO_Y = 90*Macros.m_fImageScaleY;

	public static enum TagMonkeyName {
	    MonkeyName_roll,
	    MonkeyName_sboard,
		MonkeyName_moto,
		MonkeyName_jet
	};

	CCSprite afterMoto;

	public boolean isJumping;
    public boolean isDelay;
    public boolean isWillChange;
    
    public TagMonkeyName nameBeforeJet;
    public TagMonkeyName nameWillChange;
    public TagMonkeyName name;
    
    public CCAnimate animate_N;
    public CCAnimate animate_J_A;
    public CCAnimate animate_J_B;
    public CCAnimate animate_HOHO;
    
    public int gameScore;
    public int bloodNum;
    float timeLimit = 7f;
    boolean isBlink = false;
    boolean isBall;
    
    float timeCountJet = 0.0f;
    float timeCountMoto = 0.0f;
    float velocity_Y = 0;

    CCAction action_run = null;
    CCAction action_jump_A = null;
    CCAction action_jump_B = null;

    public CGPoint startBallPos;
    
    public Monkey(){
    	super.init();
    }
    
    public static Monkey mksprite(TagMonkeyName monkeyName){
    	return new Monkey(startImageName(monkeyName), monkeyName);
    }

    public Monkey(String filepath, TagMonkeyName monkeyName){
    	
    	super(filepath);
    	
    	CCTextureCache.sharedTextureCache().removeTexture(filepath);
    	Macros.CORRECT_SCALE(this);
        this.setPosition(300 * Macros.m_fImageScaleX,
        		Global.LAND_Y + this.rectMonkey().size.height / 2);
        afterMoto = CCSprite.sprite("aftermoto_01.png");
        afterMoto.setPosition(AFTER_MOTO_X,
        		AFTER_MOTO_Y);
        this.addChild(afterMoto ,0);

        this.startBallPos = CGPoint.ccp(START_BOLL_POS_X, START_BOLL_POS_Y);

        if (timeLimit < 3.0f)
        {
            timeLimit = 3.0f;
        }

        nameBeforeJet = monkeyName;
        this.name = monkeyName;
        this.nameWillChange = monkeyName;
        
        if (this.name == TagMonkeyName.MonkeyName_moto)
        {
            afterMoto.setVisible(true);
            
            if(Global.g_bSoundState == Global.Sound.SOUND_ON){
	        	if(!SoundManager.sd_background.isPlaying())
	        		SoundManager.sd_background.start();
            	SoundManager.sharedSoundManager().playMusic(R.raw.sd_motor);
            	if(!SoundManager.sd_heavy_machin_gun.isPlaying())
            		SoundManager.sd_heavy_machin_gun.start();
            }

            afterMoto.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_after_moto.copy()));
            this.schedule("onTimeMoto");
            
            GameLayer gamelayer = (GameLayer) this.parent_;
            isBall = true;
            gamelayer.addBallsTimer(isBall);
        }else{
            afterMoto.setVisible(false);
        }
        this.playerStart();
    }
    
	public CGRect rectMonkey(){
		CGSize s = CGSize.zero();
		if (this.getTexture() != null){
			s = this.getTexture().getContentSize();
			CGRect rt = CGRect.make(-s.width * this.scaleX_ / 2 + this.getPosition().x,
									 -s.height * this.scaleY_ / 2 + this.getPosition().y, 
									  s.width * this.scaleX_, 
									  s.height * this.scaleY_);
			return rt;
		}
		return CGRect.zero();
	}

	public CGRect realRectMonkey(){
	    CGSize s = CGSize.zero();
		if (this.getTexture() != null){
		    s = this.getTexture().getContentSize();
			CGRect rt = CGRect.make(-s.width * this.scaleX_ / 2 * 0.8f + this.getPosition().x,
								   -s.height * this.scaleY_ / 2 + this.getPosition().y, 
								   s.width * this.scaleX_ * 0.8f, 
								   s.height * this.scaleY_ * 0.5f);
			return rt;
		}
		return CGRect.zero();
	}

	public void soundPlay(TagMonkeyName playName){
		
	    if(Global.g_bSoundState == Global.Sound.SOUND_OFF)
	        return;
	    switch (playName) {
	        case MonkeyName_roll:
	        	if(!SoundManager.sd_background.isPlaying())
	        		SoundManager.sd_background.start();
	        	if(SoundManager.sd_heavy_machin_gun.isPlaying())
	        		SoundManager.sd_heavy_machin_gun.pause();
	        	SoundManager.sharedSoundManager().playMusic(R.raw.sd_roll);
	            break;
	        case MonkeyName_sboard:
	        	if(!SoundManager.sd_background.isPlaying())
	        		SoundManager.sd_background.start();
	        	if(SoundManager.sd_heavy_machin_gun.isPlaying())
	        		SoundManager.sd_heavy_machin_gun.pause();
	        	SoundManager.sharedSoundManager().playMusic(R.raw.sd_sboard);
	            break;
	        case MonkeyName_moto:
	        	if(!SoundManager.sd_background.isPlaying())
	        		SoundManager.sd_background.start();
	        	if(!SoundManager.sd_heavy_machin_gun.isPlaying())
	        		SoundManager.sd_heavy_machin_gun.start();
	        	SoundManager.sharedSoundManager().playMusic(R.raw.sd_motor);
	            break;
	        case MonkeyName_jet:
	        	if(SoundManager.sd_heavy_machin_gun.isPlaying())
	        		SoundManager.sd_heavy_machin_gun.pause();
	        	if(!SoundManager.sd_background.isPlaying())
	        		SoundManager.sd_background.start();
	        	SoundManager.sharedSoundManager().playMusic(R.raw.sd_jet);
	            break;
	        default:
	            break;
	    }
	}

	public void setAnimationPerName(){
	    if (Global.g_momkeyMom == false)
	    {
	        switch (name) {
	            case MonkeyName_moto:
	            {
	                animate_N = Global.sharedGlobal().ani_moto_N;
	                animate_J_A = Global.sharedGlobal().ani_moto_J_A;
	                animate_J_B = Global.sharedGlobal().ani_moto_J_B;
	                break;
	            }
	            case MonkeyName_roll:
	            {
	                animate_N = Global.sharedGlobal().ani_roll_N;
	                animate_J_A = Global.sharedGlobal().ani_roll_J_A;
	                animate_J_B = Global.sharedGlobal().ani_roll_J_B;
	                break;
	            }
	            case MonkeyName_sboard:
	            {
	                animate_N = Global.sharedGlobal().ani_sboard_N;
	                animate_J_A = Global.sharedGlobal().ani_sboard_J_A;
	                animate_J_B = Global.sharedGlobal().ani_sboard_J_B;
	                break;
	            }
	            case MonkeyName_jet:
	            {
	                animate_N = Global.sharedGlobal().ani_jet_N;
	                break;
	            }
	            default:
	                break;
	        }
	    }
	    else
	    {
	        switch (name) {
	            case MonkeyName_moto:
	            {
	                animate_N = Global.sharedGlobal().ani_moto_mom_N;
	                animate_J_A = Global.sharedGlobal().ani_moto_mom_J_A;
	                animate_J_B = Global.sharedGlobal().ani_moto_mom_J_B;
	                break;
	            }
	            case MonkeyName_roll:
	            {
	                animate_N = Global.sharedGlobal().ani_roll_mom_N;
	                animate_J_A = Global.sharedGlobal().ani_roll_mom_J_A;
	                animate_J_B = Global.sharedGlobal().ani_roll_mom_J_B;
	                break;
	            }
	            case MonkeyName_sboard:
	            {
	                animate_N = Global.sharedGlobal().ani_sboard_mom_N;
	                animate_J_A = Global.sharedGlobal().ani_sboard_mom_J_A;
	                animate_J_B = Global.sharedGlobal().ani_sboard_mom_J_B;
	                break;
	            }
	            case MonkeyName_jet:
	            {
	                animate_N = Global.sharedGlobal().ani_jet_mom_N;
	                break;
	            }
	            default:
	                break;
	        }
	    }
	}
	
	public void playerStart(){

		this.setAnimationPerName();
		this.soundPlay(name);
	    
	    action_run = this.runAction(CCRepeatForever.action(animate_N.copy()));
	    this.setPosition(Macros.m_fImageScaleX*300, Global.LAND_Y+this.rectMonkey().size.height/2);
	    this.setOpacity(255);
	    setVisible(true);
	}

	public static String startImageName(TagMonkeyName monkeyName){
	    String startImageName = null;
	    
	    if (Global.g_momkeyMom == false) {
	        switch (monkeyName) {
	            case MonkeyName_moto:
	                startImageName = "moto01.png";
	                break;
	            case MonkeyName_roll:
	                startImageName = "roll01.png";
	                break;
	            case MonkeyName_sboard:
	                startImageName = "sboard01.png";
	                break;
	            case MonkeyName_jet:
	                startImageName = "jet01.png";
	                break;
	                
	            default:
	                break;
	        }
	    }
	    else
	    {
	        switch (monkeyName) {
	            case MonkeyName_moto:
	                startImageName = "motoMom01.png";
	                break;
	            case MonkeyName_roll:
	                startImageName = "rollMom01.png";
	                break;
	            case MonkeyName_sboard:
	                startImageName = "sbordMom01.png";
	                break;
	            case MonkeyName_jet:
	                startImageName = "jetMom01.png";
	                break;
	                
	            default:
	                break;
	        }
	    }
	    return startImageName;
	}

	public void changeMonkeyAnimation(){
		
	    if (Global.g_bSoundState == Global.Sound.SOUND_ON){
	    	SoundManager.sharedSoundManager().playEffect(R.raw.sd_change_monkey);
	    }
	    
	    GameLayer gameLayer = (GameLayer)this.parent_;
	    CCSprite aniSprite = gameLayer.updateWeapon.get(nameWillChange.ordinal());
	    Macros.CORRECT_SCALE(aniSprite);
	    aniSprite.setPosition(aniSprite.getContentSize().width/2*Macros.m_fImageScaleX,
	    		Macros.m_fImageScaleY*200);
	    aniSprite.setVisible(true);
	    
	    aniSprite.runAction(CCMoveTo.action(1.5f,
	    		CGPoint.ccp(Macros.m_szWindow.width * 2, aniSprite.getPosition().y)));
	}
	
	public void restartMonKeyOver(){

		this.stopAllActions();
		this.setOpacity(255);
	    
	    GameLayer gamelayer = (GameLayer)this.parent_;
	    if (isBall == true) {
	        isBall = false;
	        gamelayer.addBallsTimer(isBall);
	    }
	    
	    String fileName = startImageName(TagMonkeyName.MonkeyName_roll);
	    
	    CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(fileName);
	    if( texture != null ) {
	        CGRect rect = CGRect.zero();
	        rect.size = texture.getContentSize();
	        this.setTexture(texture);
	        this.setTextureRect(rect);
	        CCTextureCache.sharedTextureCache().removeTexture(fileName);
	    }
	    
	    {
	        if (name == TagMonkeyName.MonkeyName_moto)
	        {
	            afterMoto.stopAllActions();
	            afterMoto.setVisible(false);
	        }

	        if (Global.g_momkeyMom == false)
	        {
	        	Macros.CORRECT_SCALE(this);
	        }

	        this.setPosition(300 * Macros.m_fImageScaleX,
	        		Global.LAND_Y + this.rectMonkey().size.height / 2);
	        nameBeforeJet = TagMonkeyName.MonkeyName_roll;
	        name = TagMonkeyName.MonkeyName_roll;
	        this.setAnimationPerName();
	        this.playerStart();
	        if (name == TagMonkeyName.MonkeyName_moto) {
	            afterMoto.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_after_moto));
	            afterMoto.setVisible(true);
	            if(!SoundManager.sd_heavy_machin_gun.isPlaying())
	            	SoundManager.sd_heavy_machin_gun.start();
	        }
	    }
	}
	
	public void restartMonkey(){
	    this.setPosition(300 * Macros.m_fImageScaleX,
	    		Global.LAND_Y + this.rectMonkey().size.height / 2f);
	}

	public void changeMonkey(){
		
	    GameLayer gamelayer = (GameLayer)this.parent_;
	    this.changeMonkeyAnimation();
	    
	    String fileName = startImageName(name);
	    CCTextureCache.sharedTextureCache().removeTexture(fileName);

	    if (name == TagMonkeyName.MonkeyName_jet) {
	        this.unschedule("onTimeJet");
	    }

	    if (name == TagMonkeyName.MonkeyName_moto)
	    {
	        this.unschedule("onTimeMoto");
	        if (isBall) {
	            isBall = false;
	            gamelayer.addBallsTimer(isBall);
		    }
	
		    afterMoto.stopAllActions();
		    afterMoto.setVisible(false);
	    
			if(SoundManager.sd_heavy_machin_gun.isPlaying())
				SoundManager.sd_heavy_machin_gun.pause();
			
	    }

	    SoundManager.sharedSoundManager().pauseMusic();
	    this.stopAllActions();
	    this.unscheduleAllSelectors();
	    
	    fileName = startImageName(nameWillChange);
	    CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(fileName);
	    
	    if( texture != null ) {
	        CGRect rect = CGRect.zero();
	        rect.size = texture.getContentSize();
	        this.setTexture(texture);
	        this.setTextureRect(rect);
	        CCTextureCache.sharedTextureCache().removeTexture(fileName);
	    }

	    {
	        isBlink = false;
	        if (Global.g_levelNumber == 1)
	        {
	            timeLimit = 7.0f * (Global.VELOCITY_START1_X / Global.g_velocityBack) + 10f;
	        }
	        else
	        {
	            timeLimit = 7.0f * (Global.VELOCITY_START2_X / Global.g_velocityBack) + 10f;
	        }
	        
	        if (timeLimit < 10.0f) {
	            timeLimit = 10.0f;
	        }
	        
	        Macros.CORRECT_SCALE(this);
	        
	        this.position_ = CGPoint.ccp(300 * Macros.m_fImageScaleX,
	        		Global.LAND_Y + this.rectMonkey().size.height / 2);
	        nameBeforeJet = name;
	        name = nameWillChange;
	        
	        this.setAnimationPerName();
	        this.playerStart();
	        
	        if (name == TagMonkeyName.MonkeyName_moto) {
	            afterMoto.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_after_moto.copy()));
	            afterMoto.setVisible(true);
	            if(!SoundManager.sd_heavy_machin_gun.isPlaying())
	            	SoundManager.sd_heavy_machin_gun.start();
	            isBall = true;
	            gamelayer.addBallsTimer(isBall);
	        }
	        
	        if (this.name == TagMonkeyName.MonkeyName_jet)
	        {
	        	if (SoundManager.sd_heavy_machin_gun.isPlaying())
	        		SoundManager.sd_heavy_machin_gun.pause();
	            velocity_Y = JUMP_VEL;
	            this.schedule("onTimeJet");
	        }
	        
	        if (name == TagMonkeyName.MonkeyName_moto)
	        {
	            this.schedule("onTimeMoto");
	        }
	    }
	    
	}

	public void doStepChange(float delta){
		
	    if (this.isWillChange == true)
	    {
	        if (isJumping == false)
	        {
	            if (name != nameWillChange)
	            {
	                this.changeMonkey();

	                this.isWillChange = false;
	            }
	            else
	                this.isWillChange = false;
	            
	            this.unschedule("doStepChange");
	            return;
	        }
	    }
	    else  
	        this.unschedule("doStepChange");
	}
	
	public void onTimeJump(float delta){
	    if (name == TagMonkeyName.MonkeyName_moto
	    		|| name == TagMonkeyName.MonkeyName_roll
	    		|| name == TagMonkeyName.MonkeyName_sboard)
	    {
	        
	        velocity_Y += JUMP_MINUS * delta;
	        setPosition(this.getPosition().x,
	        		this.getPosition().y + velocity_Y * delta * 9);

	        if (velocity_Y < 0.20f*Macros.m_fImageScaleY 
	        		&& velocity_Y > -0.20f*Macros.m_fImageScaleY) {
	            action_jump_A = null;
	        }
	        else if (velocity_Y < -0.5f*Macros.m_fImageScaleY){
	            if (action_jump_B == null)
	            {
	                action_jump_B = this.runAction(animate_J_B.copy());
	            }
	        }
	        
	        if (this.rectMonkey().origin.y < Global.LAND_Y - 0.3f*Macros.m_fImageScaleY)
	        {
	            action_run = this.runAction(CCRepeatForever.action(animate_N.copy()));
	            setPosition(this.getPosition().x, Global.LAND_Y + this.rectMonkey().size.height / 2);
	            this.unschedule("onTimeJump");
	            
	            isJumping = false;
	        }
	    }
	}

	public boolean collidgePlayerWithActorInX(){
	    GameLayer gameLayer = (GameLayer)this.parent_;
	    
	    float odaDistance = 100f * Macros.m_fImageScaleX;
	    
	    for (int i = 0; i < gameLayer.countObject; i ++) {

	    	ArrayList<Actor> targetsPerObject = new ArrayList<Actor>();
	    	targetsPerObject = gameLayer.groupTargets.get(i);
	        
	        for (int tarNum = 0; tarNum < Global.DEFAULT_COUNT_PER_GERNERAL; tarNum ++) {
	            Actor actor;
	            actor = targetsPerObject.get(tarNum);
	            
	            if (actor.isActive == true) {
	                
	                if ( ( (this.realRectMonkey().origin.x > actor.rect().origin.x) && 
	                      (this.realRectMonkey().origin.x < actor.rect().origin.x + actor.rect().size.width) ) ||
	                    ( (this.realRectMonkey().origin.x + this.realRectMonkey().size.width + odaDistance > actor.rect().origin.x ) && 
	                     (this.realRectMonkey().origin.x + this.realRectMonkey().size.width + odaDistance < actor.rect().origin.x + actor.rect().size.width) )
	                    ){
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}

	public void onTimeMoto(float delta){
	    if (name == TagMonkeyName.MonkeyName_moto)
	    {
	        timeCountMoto += delta;
	        if ( (timeCountMoto > timeLimit - 1.0) && (isBlink == false) )
	        {
	            isBlink = true;
	            this.runAction(CCBlink.action(0.5f, 10));
	            
	        }
	        
	        if ( (timeCountMoto > timeLimit && isJumping == false && this.collidgePlayerWithActorInX() == false) )
	        {
	            isBlink = false;
	            timeCountMoto = 0.0f;
	            if (nameBeforeJet == TagMonkeyName.MonkeyName_moto)
	            {
	                nameBeforeJet = TagMonkeyName.MonkeyName_roll;
	            }
	            nameWillChange = nameBeforeJet;
	            this.changeMonkey();
	        }
	    }
	}

	public void onTimeJet(float delta){
	    timeCountJet += delta;
	    
	    if ( (timeCountJet > timeLimit - 1.0) && (isBlink == false))
	    {
	        isBlink = true;
	        this.runAction(CCBlink.action(1.0f ,10));
	    }
	    if (timeCountJet > timeLimit)
	    {
	        if (this.collidgePlayerWithActorInX() == false)
	        {
	            isBlink = false;
	            timeCountJet = 0.0f;
	            if (nameBeforeJet == TagMonkeyName.MonkeyName_jet)
	            {
	                nameBeforeJet = TagMonkeyName.MonkeyName_roll;
	            }
	            nameWillChange = nameBeforeJet;
	            this.changeMonkey();
	        }
	    }
	    
	    velocity_Y = velocity_Y + (JUMP_MINUS * delta);
	    setPosition(this.getPosition().x, this.getPosition().y + velocity_Y * delta * 7);

	    if (this.rectMonkey().origin.y < Global.LAND_Y - 0.3f*Macros.m_fImageScaleY)
	    {
	    	setPosition(this.getPosition().x, Global.LAND_Y + this.rectMonkey().size.height / 2);
	        velocity_Y = 0.0f;
	    }
	    if ( (this.rectMonkey().origin.y + this.rectMonkey().size.height) > Macros.m_szWindow.height)
	    {
	    	setPosition(this.getPosition().x, Macros.m_szWindow.height - this.rectMonkey().size.height / 2);
	        velocity_Y = 0.0f;
	    }

	}

	public void runJump(){
	    if (isDelay == true)
	        return;
	        
	    if (name == TagMonkeyName.MonkeyName_jet) {
	        velocity_Y += JUMP_VEL;
	    }
	    else
	    {
	        isJumping = true;
	        
	        if (action_run != null)
	        {
	            this.stopAction(action_run);	
	            action_run = null;
	            
	        }
	        
	        action_jump_A = this.runAction(animate_J_A.copy());
	        action_jump_B = null;
	        
	        velocity_Y = JUMP_VEL;
	        this.schedule("onTimeJump");
	        
	    }
	}

	public void delay(){
	    {
	    	SoundManager.sharedSoundManager().pauseMusic();
	        GameLayer gamelayer = (GameLayer )this.parent_;
	        
	        this.stopAllActions();
	        this.unscheduleAllSelectors();
	        isJumping = false;
	        isDelay = true;
	        isWillChange = false;

	        SoundManager.sharedSoundManager().playEffect(R.raw.sd_delay_monkey);
	        
	        CCSequence seq = CCSequence.actions(CCRotateBy.action(0.5f,(360 * 3)),
	                           CCFadeOut.action(1.0f),
	                           CCCallFunc.action( gamelayer, "restartGameOver"));
	        this.runAction(seq);
	    }
	}

	public void processCollidgeJetWithActor(Actor actor){
	    GameLayer gamelayer = (GameLayer) this.parent_;
	    
	    gameScore += actor.addScore;
	    gamelayer.updateScore();

	    for (int i = 0; i < gamelayer.expresionCount; i ++) {
	        CCSprite sprite = gamelayer.expresions.get(i);
	        if (sprite.getVisible() == false) {
	            sprite.setVisible(true);
	            sprite.setPosition(actor.getPosition());
	            CCSequence seq = CCSequence.actions(Global.sharedGlobal().ani_explosion.copy(),
	                               CCFadeOut.action(0.2f),
	                               CCCallFunc.action(gamelayer, "scanEndedExpresion"));
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
	
	public void processCollidgePlayerWithActor(Actor actor){
	  
		GameLayer gamelayer = (GameLayer)this.parent_;
	    if (name == TagMonkeyName.MonkeyName_moto)
	    {
	        for (int i = 0; i < gamelayer.expresionCount; i ++) {
	            CCSprite sprite = gamelayer.expresions.get(i);
	            if (sprite.getVisible() == false) {
	                sprite.setVisible(true);
	                sprite.setPosition(actor.getPosition());
	                CCSequence seq = CCSequence.actions(Global.sharedGlobal().ani_explosion.copy(),
	                                   CCFadeOut.action(0.2f),
	                                   CCCallFunc.action(gamelayer, "scanEndedExpresion"));
	                sprite.runAction(seq);
	                break;
	            }
	        }
	        
	        actor.isActive = false;
	        actor.setVisible(false);
	        actor.initPosition(); 
	        actor.unscheduleAllSelectors();
	        actor.stopAllActions();
	                
	        this.isWillChange = true;
	        nameWillChange = TagMonkeyName.MonkeyName_roll;
	        this.schedule("doStepChange");
	        
	        if (isBall) {
	            isBall = false;
	            gamelayer.addBallsTimer(isBall);
	        }
	    }
	    else
	    {
	        this.delay();
	    }
	}
	
	public boolean collidgePlayerWithActor(Actor actor){
	    GameLayer gamelayer = (GameLayer) this.parent_;
	    if (this.name == TagMonkeyName.MonkeyName_jet)
	    {
	        if (CGRect.intersects(actor.rect(), this.realRectMonkey())) {
	            
	            switch (actor.type) {
	                case level01_snake:
	                case level01_spider:
	                case level01_bird:
	                case level02_bird:
	                case level02_ballon_01:
	                case level02_ballon_02:
	                {
	                    this.processCollidgeJetWithActor(actor);
	                    return false;
	                }
	                default:
	                    break;
	            }
	        }
	    }
	    else
	    {
	        if (CGRect.intersects(actor.rect(), this.realRectMonkey())) {
	            
	            switch (actor.type) {
	                case level01_spider:
	                case level01_bird:
	                case level01_log_up:
	                case level02_bird:
	                case level02_ballon_01:
	                case level02_ballon_02:
	                {                    
	                    this.processCollidgePlayerWithActor(actor);
	                    return false;
	                }
	                    
	                case level01_snake:
	                case level01_log_down:
	                case level01_stone:
	                case level02_car:
	                case level02_hole:
	                case level02_stop:
	                case level01_thorn:
	                case level02_thorn:
	                case level02_stopCar:
	                {
	                    if (isWillChange == true)
	                    {                        
	                        for (int i = 0; i < gamelayer.expresionCount; i ++) {
	                            CCSprite sprite = gamelayer.expresions.get(i);
	                            if (sprite.getVisible() == false) {
	                                sprite.setVisible(true);
	                                sprite.setPosition(actor.getPosition());
	                                CCSequence seq = CCSequence.actions(Global.sharedGlobal().ani_explosion.copy(),
	                                                   CCFadeOut.action(0.2f),
	                                                   CCCallFunc.action(gamelayer, "scanEndedExpresion"));
	                                sprite.runAction(seq);
	                                break;
	                            }
	                        }
	                        
	                        actor.isActive = false;
	                        actor.setVisible(false);
	                        actor.initPosition(); 
	                        actor.unscheduleAllSelectors();
	                        actor.stopAllActions();
	                        
	                        return false;
	                    }
	                    
	                    this.processCollidgePlayerWithActor(actor);
	                    
	                    return false;
	                }
	                default:
	                    break;
	            }
	        }
	    }
	    
	    if (CGRect.containsPoint(actor.rect(), this.position_))
	    {
	        switch (actor.type) {
	            case banana:
	            {
	                Global.g_velocityBack -= Global.VELOCITY_DELTA_X * 3;
	                
	                if (name == TagMonkeyName.MonkeyName_moto || name == TagMonkeyName.MonkeyName_jet)
	                {
	                    gameScore += actor.addScore;
	                    gamelayer.updateScore();
	                }
	                else
	                {
	                    if (this.isWillChange == false)
	                    {
	                        int i = (int)this.name.ordinal();
	                        i = (i + 1) % 3;
	                        
	                        this.isWillChange = true;
	                        this.nameWillChange = TagMonkeyName.values()[i];
	                        this.schedule("doStepChange");
	                    }
	                }
	                actor.isActive = false;
	                actor.setVisible(false);
	                actor.initPosition(); 
	                actor.unscheduleAllSelectors();
	                actor.stopAllActions();
	                gamelayer.removeAllTargets(2);
	                return true;
	            }
	            case banana_jet:
	            {
	                if (isBall) {
	                    isBall = false;
	                    gamelayer.addBallsTimer(isBall);
	                }

	                this.isWillChange = true;
	                this.nameWillChange = TagMonkeyName.MonkeyName_jet;
	                this.schedule("doStepChange");

	                actor.isActive = false;
	                actor.setVisible(false);
	                actor.initPosition(); 
	                actor.unscheduleAllSelectors();
	                actor.stopAllActions();
	                break;
	            }
	            case cake:
	            {
	                if (Global.g_bSoundState == Global.Sound.SOUND_ON) {
	                    if (gamelayer.m_bCongratulationStage == false) {
	                        SoundManager.sharedSoundManager().playEffect(R.raw.sd_cake);
	                    }
	                }
	                gameScore += actor.addScore;
	                gamelayer.updateScore();
	                actor.isActive=false;
	                actor.setVisible(false);
	                actor.initPosition(); 
	                actor.unscheduleAllSelectors();
	                actor.stopAllActions();
	                break;
	            }
	            default:
	                break;
	        }
	    }
	    return false;
	}
}