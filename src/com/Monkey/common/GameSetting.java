package com.Monkey.common;

import org.cocos2d.nodes.CCDirector;

import android.content.SharedPreferences;


/**
 * program`s setting model
 */
public class GameSetting{
	
	public static final String PREFS_NAME = "ApeGameSettings";
	public static SharedPreferences	m_settings;

	public static boolean isContain( String strKey ){
		return m_settings.contains(strKey);
	}
	
	public static int getIntValue( String strKey, int nDefValue ){
		return (Integer) m_settings.getInt(strKey, nDefValue);
	}
	
	public static boolean getBooleanValue( String strKey, boolean bDefValue ){
		return m_settings.getBoolean(strKey, bDefValue);
	}
	
	public static float getFloatValue( String strKey, float ftDefValue ){
		return m_settings.getFloat(strKey, ftDefValue);
	}
	
	public static long getLongValue( String strKey, long lDefValue ){
		return m_settings.getLong(strKey, lDefValue);
	}
	
	public static String getStringValue( String strKey, String strDefValue ){
		return m_settings.getString(strKey, strDefValue);
	}
	
	public static void putValue( String strKey, int nValue ){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.putInt(strKey, nValue);
		edit.commit();
	}
	
	public static void putValue( String strKey, boolean bValue ){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.putBoolean(strKey, bValue);
		edit.commit();
	}
	
	public static void putValue( String strKey, float ftValue ){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.putFloat(strKey, ftValue);
		edit.commit();
	}
	
	public static void putValue( String strKey, long lValue ){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.putLong(strKey, lValue);
		edit.commit();
	}
	
	public static void putValue( String strKey, String strValue ){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.putString(strKey, strValue);
		edit.commit();
	}
	
	public static void remove( String strKey ){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.remove(strKey);
		edit.commit();
	}
	
	public static void removeAll(){
		SharedPreferences.Editor edit = m_settings.edit();
		edit.clear();
		edit.commit();
	}
	
	/** initialize setting */
    public static void initialize(){
    	
    	m_settings = CCDirector.sharedDirector().getActivity().getSharedPreferences(PREFS_NAME, 0);
    	removeAll();
		
    	if( getBooleanValue("FIRST_INSTALL_FLAG", true) ){
        	removeAll();

        	putValue("FIRST_INSTALL_FLAG", false);
			
			// Game Info
			putValue("LEVEL_INFO", 0);
			// settings
			putValue("SOUND_VOLUME", 0.5f);
			putValue("EFFECT_VOLUME", 0.5f);
		
			// state
			putValue("HIGH_SCORE", 0);

		}
	}
}
