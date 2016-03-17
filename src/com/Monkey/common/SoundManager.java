package com.Monkey.common;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

import android.media.MediaPlayer;

import com.Monkey.R;
import com.Monkey.common.GameDoc.GameState;

/**
 * model of Sound Manager
 */
public class SoundManager{

	private static SoundManager _sharedSoundManager = null;
    public static SoundManager sharedSoundManager(){
    	if (_sharedSoundManager == null ) 
    		_sharedSoundManager = new SoundManager();
    	
    	return _sharedSoundManager;
    }
	
	public static MediaPlayer sd_background;
	public static MediaPlayer sd_heavy_machin_gun;

	public static void initialize(){
		sd_background = MediaPlayer.create(CCDirector.sharedDirector().getActivity(), R.raw.sd_background);
		sd_background.setLooping(true);

		sd_heavy_machin_gun = MediaPlayer.create(CCDirector.sharedDirector().getActivity(), R.raw.sd_heavy_machin_gun);
		sd_heavy_machin_gun.setLooping(true);
		
    	SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.sd_motor);
    	SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.sd_jet);
    	SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.sd_sboard);
    	SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.sd_roll);
    	SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.sd_end_cake_after);
    	SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.sd_crush_cake_after);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_cake);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_bonus);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_change_monkey);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_crush_cake);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_delay_monkey);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_delay_monkey_after);
    	SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.sd_end_cake);
   	
    }    
    
    public static void release(){
    	if (_sharedSoundManager != null ){
    		SoundEngine.sharedEngine().stopSound();
    		SoundEngine.sharedEngine().realesAllSounds();
//    		SoundEngine.sharedEngine().realesAllEffects();
    	}
		if(sd_background.isPlaying()){
			sd_background.stop();
			sd_background.release();
		}

		if(sd_heavy_machin_gun.isPlaying()){
			sd_heavy_machin_gun.stop();
			sd_heavy_machin_gun.release();
		}
    }

	
    public SoundManager(){
        setSoundVolume(1f);
        setEffectVolume(1f);
    }
    
    public void playMusic(int soundId){
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), soundId, true);
    }

    public void playMusic(int soundId, boolean flag){
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), soundId, flag);
    }

    public void stopMusic(){
    	if (sd_background.isPlaying())
    		sd_background.stop();
    		
    	if (sd_heavy_machin_gun.isPlaying())
    		sd_heavy_machin_gun.stop();
    	SoundEngine.sharedEngine().stopSound();
    }

    private static boolean sound_moto_flag = false;

    public void pauseMusic(){
    	if (sd_background.isPlaying())
    		sd_background.pause();
    	if (sd_heavy_machin_gun.isPlaying())
    	{
    		sd_heavy_machin_gun.pause();
    		sound_moto_flag  = true;
    	}else{
    		sound_moto_flag = false;
    	}
    	SoundEngine.sharedEngine().pauseSound();
    }

    public void resumeMusic(){
    	if(GameDoc.sharedDoc().m_GameState == GameState.STATE_PLAYING)
    	{
    		sd_background.start();
    		if (sound_moto_flag)
    			sd_heavy_machin_gun.start();
    	}
    	SoundEngine.sharedEngine().resumeSound();
    }

    public void setSoundVolume(float fVolume){
    	SoundEngine.sharedEngine().setSoundVolume(fVolume);
    }

    public void setMute(boolean bMute){
	//  if(bMute){
	//    	SoundEngine.sharedEngine().mute();
	//	}else{
	//    	SoundEngine.sharedEngine().unmute();
	//	}
	    	if(bMute){
    		SoundEngine.sharedEngine().setSoundVolume(0f);
		}else{
    		SoundEngine.sharedEngine().setSoundVolume(1f);
		}
    }

    public void setEffectMute(boolean bMute){
    	if(bMute){
    		SoundEngine.sharedEngine().setEffectsVolume(0f);
		}else{
    		SoundEngine.sharedEngine().setEffectsVolume(1f);
		}
    }

    public boolean isMute(){
    	return SoundEngine.sharedEngine().isMute();
    }

    public void playEffect(int effectId){
    	SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), effectId);
	}

    public void setEffectVolume(float fVolume){
    	SoundEngine.sharedEngine().setEffectsVolume(fVolume);
    }
}
