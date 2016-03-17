package com.Monkey;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.Monkey.common.GameDoc;
import com.Monkey.common.Macros;
import com.Monkey.common.SoundManager;


public class ApeCake extends Activity {
    private CCGLSurfaceView mGLSurfaceView;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
 
        mGLSurfaceView = new CCGLSurfaceView(this);
        setContentView(mGLSurfaceView);

        CCDirector.sharedDirector().attachInView(mGLSurfaceView);
	   	
        Macros.INIT_SIZE_SCALE();
        GameDoc.sharedDoc().initialize();

    	CCScene scene = CCScene.node();
		scene.addChild(new Splash());
		CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        CCDirector.sharedDirector().pause();
        SoundManager.sharedSoundManager().pauseMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        CCDirector.sharedDirector().resume();
        SoundManager.sharedSoundManager().resumeMusic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GameDoc.sharedDoc().gameOver();
        GameDoc.uninitialize();
        CCTextureCache.sharedTextureCache().removeAllTextures();
    }
}