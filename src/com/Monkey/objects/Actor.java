package com.Monkey.objects;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.Monkey.GameLayer;
import com.Monkey.common.Global;
import com.Monkey.common.Macros;

public class Actor extends CCSprite{
	
	public final static float GENERATE_POS_X  = 1200*Macros.m_fImageScaleX;
	public final static float GENERATE_SPRIDER_POS_Y = 440*Macros.m_fImageScaleY;
	public final static float GENERATE_HOLE_POS_Y_DELTA = -20*Macros.m_fImageScaleY;
	public final static float BIRD_VELOCITY_X = -100*Macros.m_fImageScaleX;

	public static enum TagWeapon{
	    level01_log_down,   // 0
	    level01_snake,      // 1
	    level01_stone,      // 2
	    level01_thorn,      // 3
	    level01_log_up,     // 4
	    level01_spider,     // 5
	    level01_bird,       // 6
	    level02_bird,       // 7
	    level02_ballon_01,  // 8
	    level02_ballon_02,  // 9
	    level02_car,        // 10
	    level02_hole,       // 11
	    level02_stop,       // 12
	    level02_stopCar,    // 13
	    level02_thorn,      // 14  ////////////////add object default 3
	    banana,             // 15
	    banana_jet,         // 16  
	    cake,               // 17  ////////////////add object default 50
	    level02_cloud01,    // 18   //////////////
	    level02_cloud02,    // 19   //////////////
	    level02_cloud03,    // 20   //////////////
	    level01_cave,       // 21
	    level01_cave_01,    // 22
	    level02_tunnel_01,  // 23
	    level02_tunnel_02   // 24
	};
	
	public TagWeapon type;
	public int addScore;
	CGPoint velocity;
	public boolean isActive;
	
	public Actor(){
		super.init();
	}

	public Actor(String filepath){
		super(filepath);
		CCTextureCache.sharedTextureCache().removeTexture(filepath);
	}
	
	public Actor(String filename, TagWeapon m_type){

		super(filename);
		CCTextureCache.sharedTextureCache().removeTexture(filename);
		
		this.scaleX_ = Macros.m_rScaleX * 0.7f;
		this.scaleY_ = Macros.m_rScaleY * 0.7f;

		type = m_type;
		addScore = 0;
	    	
		switch (type) {
			case level01_log_down:
				velocity = CGPoint.ccp(0, 0);
				break;
			case level01_snake:
				velocity = CGPoint.ccp(0, 0);
				Macros.CORRECT_SCALE(this);
	            addScore = 50;
				break;
			case level01_stone:
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
				break;
			case level01_log_up:
				velocity = CGPoint.ccp(0, 0);
	            addScore = 50;
				break;
			case level01_spider:
				velocity = CGPoint.ccp(0, 0);
	            addScore = 50;
				break;
			case level01_bird:
	            velocity = CGPoint.ccp( BIRD_VELOCITY_X, 0);
	            addScore = 50;
	            break;
	            
	        case level02_bird:
	            velocity = CGPoint.ccp( BIRD_VELOCITY_X, 0);
	            scaleX_ = Macros.m_rScaleX * 1.2f;
	            scaleY_ = Macros.m_rScaleY * 1.2f;
	            addScore = 50;
				break;
	            
			case level02_car:
				velocity = CGPoint.ccp(0, 0);
				Macros.CORRECT_SCALE(this);
				break;
			case level02_hole:
				velocity = CGPoint.ccp(0, 0);
				Macros.CORRECT_SCALE(this);
				break;
			case level02_stop:
				velocity = CGPoint.ccp(0, 0);
				Macros.CORRECT_SCALE(this);
				break;
	        case level02_stopCar:
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            break;
	        case banana:
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            addScore = 200;
	            break;
	        case banana_jet:
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            break;
	        case cake:
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            addScore = 50;
	            break;
	        case level01_cave:
	        case level01_cave_01:{
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            break;
	        }
	        case level02_tunnel_01:
	        case level02_tunnel_02:{
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            break;
	        }
	        case level02_ballon_01:
	        case level02_ballon_02:{
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            addScore = 50;
	            break;
	        }
	        case level01_thorn:
	        case level02_thorn:{
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            break;
	        }
	        case level02_cloud01:
	        case level02_cloud02:
	        case level02_cloud03:{
	            velocity = CGPoint.ccp(0, 0);
	            Macros.CORRECT_SCALE(this);
	            break;
	        }
			default:
				break;
		}
		
	}

	public CGRect rect(){
		
		CGRect rt = CGRect.make(this.getPosition().x-this.getContentSize().width/2*Macros.m_rScaleX,
							   this.getPosition().y-this.getContentSize().height/2*Macros.m_rScaleY,
							   this.getContentSize().width*Macros.m_rScaleX,
							   this.getContentSize().height*Macros.m_rScaleY);
	    switch ( type) {
	        case level01_snake:
	            rt.size.width *= 0.3;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2; 
	            break;
	        case level01_log_down:
	            rt.size.width *= 0.65;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
	        case level01_stone:
	            rt.size.width *= 0.7;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
	        case level01_bird:
	            rt.size.height *= 0.6;
	            rt.origin.y = this.getPosition().y - rt.size.height / 2;
	            rt.size.width *= 0.7;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
	        case level01_spider:
	            rt.size.width *= 0.7;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
	        case level02_hole:
	            rt.size.width *= 0.5;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
	        case level02_car:
	            rt.size.width *= 0.8;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
	        case level01_thorn:
	            rt.size.width *= 0.9;
	            rt.origin.x = this.getPosition().x - rt.size.width / 2;
	            break;
//	        case cake:
//	            rt.size.height *= 0.5;
//	            rt.origin.y = this.getPosition().y - rt.size.height / 2;
//	            break;;
	        default:
	            break;
	    }
		return rt;
	}

	public void initPosition(){
		
		switch (type) {
			case level01_log_down:
				setPosition( GENERATE_POS_X, Global.LAND_Y);			
				break;
			case level01_snake:
				setPosition( GENERATE_POS_X, (Global.LAND_Y + this.rect().size.height / 2));			
				break;
			case level01_stone:
				setPosition( GENERATE_POS_X, (Global.LAND_Y + this.rect().size.height / 2));			
				break;
	        case level01_thorn:
				setPosition( GENERATE_POS_X, (Global.LAND_Y + this.rect().size.height / 2));			
				break;
	            
			case level01_log_up:
				setPosition( GENERATE_POS_X, 
									 (Macros.m_szWindow.height - this.rect().size.height / 2));			
				break;
			case level01_spider:
				setPosition( GENERATE_POS_X, 
									 (Macros.m_szWindow.height - this.rect().size.height / 2));			
				break;
	        case level01_bird:
				setPosition( GENERATE_POS_X, 
									 GENERATE_SPRIDER_POS_Y);		
	            break;
			case level02_car:
				setPosition( GENERATE_POS_X, (Global.LAND_Y + this.rect().size.height / 2 - 10 ));			
				break;
			case level02_hole:
				setPosition( GENERATE_POS_X, 
									 (Global.LAND_Y + GENERATE_HOLE_POS_Y_DELTA + 5));			
				break;
			case level02_stop:
				setPosition( GENERATE_POS_X, Global.LAND_Y);			
				break;
	        case level02_thorn:
				setPosition( GENERATE_POS_X, Global.LAND_Y);			
				break;
	        case level02_stopCar:
				setPosition( GENERATE_POS_X, Global.LAND_Y);			
				break;
	        case level02_bird:
				setPosition( GENERATE_POS_X, 
									 GENERATE_SPRIDER_POS_Y);	
	            break;
	        case level02_ballon_01:
				setPosition( GENERATE_POS_X, 
									 GENERATE_SPRIDER_POS_Y);	
	            break;
	        case level02_ballon_02:
				setPosition( GENERATE_POS_X, 
									 (Macros.m_szWindow.height - this.rect().size.height / 2));	
	            break;
	        case banana:
				setPosition( GENERATE_POS_X, 
									 GENERATE_SPRIDER_POS_Y);			
	            break;
	        case banana_jet:
				setPosition( GENERATE_POS_X, 
									 GENERATE_SPRIDER_POS_Y);
	            break;
	        case level02_cloud01:
	        {
	            float height = GENERATE_SPRIDER_POS_Y;
	            int randheight = (int) Math.random()%3;
	            switch (randheight) {
	                case 0:
	                    height = 600*Macros.m_fImageScaleY;
	                    break;
	                case 1:
	                    height = 650*Macros.m_fImageScaleY;
	                    break;
	                case 2:
	                    height = 700*Macros.m_fImageScaleY;
	                    break;
	                    
	                default:
	                    break;
	            }
				setPosition( GENERATE_POS_X, height);
				break;
	        }
	        case level02_cloud02:
	        {
	            float height = GENERATE_SPRIDER_POS_Y;
	            int randheight = (int) Math.random()%3;
	            switch (randheight) {
	                case 0:
	                    height = 600*Macros.m_fImageScaleY;
	                    break;
	                case 1:
	                    height = 650*Macros.m_fImageScaleY;
	                    break;
	                case 2:
	                    height = 700*Macros.m_fImageScaleY;
	                    break;
	                    
	                default:
	                    break;
	            }
				setPosition( GENERATE_POS_X, height);
				break;
	        }
	        case level02_cloud03:
	        {
	            float height = GENERATE_SPRIDER_POS_Y;
	            int randheight = (int) Math.random()%3;
	            switch (randheight) {
	                case 0:
	                    height = 600*Macros.m_fImageScaleY;
	                    break;
	                case 1:
	                    height = 650*Macros.m_fImageScaleY;
	                    break;
	                case 2:
	                    height = 700*Macros.m_fImageScaleY;
	                    break;
	                    
	                default:
	                    break;
	            }
				setPosition( GENERATE_POS_X, height);
				break;
	        }
			default:
				setPosition( GENERATE_POS_X, Global.LAND_Y);			
				break;
		}
		
	}

	public boolean move(float delta){

		float delta_X = (Global.g_velocityBack + velocity.x) * delta;
	    setPosition(this.getPosition().x + delta_X, this.getPosition().y);
		if ( (this.rect().origin.x + this.rect().size.width ) < 0 ) {
	        if ( type == TagWeapon.level01_log_up) {
	            CCTexture2D oldTex = this.getTexture();
	            CCTextureCache.sharedTextureCache().removeTexture(oldTex);
	            CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage("log_up.png");
	            if( texture!=null ) {
	                CGRect rect = CGRect.zero();
	                rect().size = texture.getContentSize();
	                this.setTexture(texture);
	                this.setTextureRect(rect);
	            }
	        }
	        this.isActive = false;
	        this.setVisible(false);
	        this.initPosition();
	        this.unscheduleAllSelectors();
	        this.stopAllActions();
			return false;
		}
	    return true;
	}

	private void doAnimate(){
	    switch (type) {
	        case level01_bird:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("Bird_right0.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_bird1.copy()));
	            break;
	        }
	        case level02_bird:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("bird_1_0.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_bird2.copy()));
	            break;
	        }
	        case level01_spider:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("spider01.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_spider.copy()));
	            break;
	        }
	        case level01_snake:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("snake01.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_snake.copy()));
	            break;
	        }
	        case banana:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("banana_01.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_banana.copy()));
	            CCSequence seq = CCSequence.actions(
	            			CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack * 0.3f, -300*Macros.m_fImageScaleY)),
	                        CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack * 0.3f, 300*Macros.m_fImageScaleY)));
	            this.runAction(CCRepeatForever.action(seq));
	            break;
	        }
	        case banana_jet:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("bananaJet_01.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_banana_jet.copy()));
	            CCSequence seq = CCSequence.actions(
	            			CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack * 1.5f, -250*Macros.m_fImageScaleY)),
	                        CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack * 1.5f, +250*Macros.m_fImageScaleY)));
	            this.runAction(CCRepeatForever.action(seq));
	            break;
	        }
	        case cake:
	        {
	            CCTextureCache.sharedTextureCache().removeTexture("cake_01.png");
	            this.runAction(CCRepeatForever.action(Global.sharedGlobal().ani_cake.copy()));
	            break;
	        }
	        case level02_ballon_01:
	        {
	            CCSequence seq = CCSequence.actions(
	            			CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack, -130*Macros.m_fImageScaleY)),
	                        CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack, 130*Macros.m_fImageScaleY)));
	            this.runAction(CCRepeatForever.action(seq));
	            break;
	        }

	        case level02_ballon_02:
	        {
	            GameLayer gameLayer = (GameLayer) this.parent_;
	            if (!(gameLayer.player.name == Monkey.TagMonkeyName.MonkeyName_sboard
	            		&& gameLayer.player.getPosition().y > Macros.m_szWindow.height/2))
	            {
	                CCSequence seq = CCSequence.actions(
	                			CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack, -400*Macros.m_fImageScaleY)),
	                            CCMoveBy.action(1.0f, CGPoint.ccp(Global.g_velocityBack, 400*Macros.m_fImageScaleY)));
	                this.runAction(CCRepeatForever.action(seq));
	            }
	            break;
	        }
	        case level02_cloud01:
	        case level02_cloud02:
	        case level02_cloud03:
	        {
	            this.runAction(CCMoveBy.action(3.0f, CGPoint.ccp(-Macros.m_szWindow.width * 3 / 2, 0)));
	            break;
	        }
	        default:
	            break;
	    }
	}

	public void runActor(){
		this.doAnimate();
	}
}