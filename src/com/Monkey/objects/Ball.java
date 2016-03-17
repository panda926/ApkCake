package com.Monkey.objects;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.Monkey.GameLayer;
import com.Monkey.common.Global;
import com.Monkey.common.Macros;

public class Ball extends CCSprite{
	
	public CGPoint velocity; 
    public boolean isActive;

	public Ball(CCTexture2D texture){
		super(texture);
		Macros.CORRECT_SCALE(this);
		CCTextureCache.sharedTextureCache().removeTexture(texture);
	}

	public Ball(String filepath){
		super(filepath);
		Macros.CORRECT_SCALE(this);
		CCTextureCache.sharedTextureCache().removeTexture(filepath);
	}

	public void setIsActive(boolean isActiveTrue){
	    
		if (isActiveTrue == true) {
	        isActive = true;
	        this.setVisible(true);
	    } else {
	        isActive = false;
	        this.setVisible(false);
	    }
	}

	public boolean move(float delta){
	    
		this.setPosition(CGPoint.ccpAdd(
				this.getPosition(), CGPoint.ccpMult(velocity, delta)));
		if (this.position_.x > Macros.m_szWindow.width || this.position_.x < 0 ||
			this.position_.y > Macros.m_szWindow.height || this.position_.y < 0) {
	        this.setIsActive(false);
			return false;
		}
	    
	    return true;
	}

	public boolean collideWithActor(Actor actor){
		
	    GameLayer gameLayer = (GameLayer) this.parent_;
	    
	    if (CGRect.containsPoint(actor.rect(), this.getPosition())) {
	        
	        switch (actor.type) {
	            case level01_snake:
	            case level01_spider:
	            case level01_bird:
	            case level02_bird:
	            case level02_ballon_01:
	            case level02_ballon_02:
	            {
	                gameLayer.player.gameScore += actor.addScore;
	                gameLayer.updateScore();

	                for (int i = 0; i < gameLayer.expresionCount; i ++) {
	                    CCSprite sprite = gameLayer.expresions.get(i);
	                    if (sprite.getVisible() == false) {
	                        sprite.setVisible(true);
	                        sprite.setPosition(actor.getPosition());
	                        CCSequence seq = CCSequence.actions(Global.sharedGlobal().ani_explosion.copy(), CCFadeOut.action(0.2f),
	                                           	CCCallFunc.action(gameLayer, "scanEndedExpresion"));
	                        sprite.runAction(seq);
	                        break;
	                    }
	                }
	                this.setIsActive(false);
	                
	                actor.stopAllActions();
	                actor.unscheduleAllSelectors();
	                actor.isActive = false;
	                actor.initPosition();
	                actor.setVisible(false);

	                return true;
	            }
	            case level01_log_up:
	            {
	                if (gameLayer.fallWood == null){
	                    gameLayer.player.gameScore += actor.addScore;
	                    gameLayer.updateScore();

	                    CCTexture2D oldTex = actor.getTexture();
	                    CCTextureCache.sharedTextureCache().removeTexture(oldTex);
	                    
	                    CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage("log_up_two.png");
	                    if( texture != null ) {
	                        CGRect rect = CGRect.zero();
	                        rect.size = texture.getContentSize();
	                        actor.setTexture(texture);
	                        actor.setTextureRect(rect);
	                    }

	                    gameLayer.fallWood = actor;
	                    gameLayer.schedule("woodTime");
	                    
	                    this.setIsActive(false);
	                }
	                return true;
	            }
	            default:
	                break;
	        }
	    }
	    return false;
	}
}