package com.Monkey.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.res.Configuration;
import android.util.DisplayMetrics;
/*
 * Declare common MACROs and CONSTANTs, Global variables.
 */
public class Macros{

	public static float m_ftPTM_RATIO = 32;							
	public static final CGSize m_szLogical = CGSize.make(480.0f, 320.0f);	// Logical Window size
	public static final CGSize m_szImageSize = CGSize.make(1024f, 768f);	// Image Scale size
	public static final CGPoint m_ptImageCenter = CGPoint.make(512f, 384f);	// center point of Image size
	public static CGSize m_szWindow = CGSize.zero();						// window size
	public static CGSize m_szScale = CGSize.zero();							// current Scale
	public static CGSize m_szLogicScale = CGSize.zero();					// logical Scale
	public static CGSize m_szImageScale = CGSize.zero();					// image size Scale
	
	public static CGPoint m_ptCenter = CGPoint.make(240.0f, 160.0f);	// center point of logical window
	public static float m_rScaleX, m_rScaleY, m_fImageScaleX, m_fImageScaleY;
	public static boolean m_bPad = true; 
	
	/** initialize scale. This method must be called at START point!!! */
	public static void INIT_SIZE_SCALE(){
		// media size
		DisplayMetrics displayMetrics = new DisplayMetrics();
		CCDirector.sharedDirector().getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		m_szWindow = CGSize.make( displayMetrics.widthPixels, displayMetrics.heightPixels );
		
		// logical size
		float nMin = Math.min(m_szLogical.width, m_szLogical.height);
		float nMax = Math.max(m_szLogical.width, m_szLogical.height);

		if(CCDirector.sharedDirector().getActivity().getResources().getConfiguration().orientation  == Configuration.ORIENTATION_LANDSCAPE ){
			m_szLogical.width  = nMax;
			m_szLogical.height  = nMin; 
		}else{
			m_szLogical.width  = nMin;
			m_szLogical.height  = nMax; 
		}
		
		m_szLogicScale.width = m_szWindow.width / m_szLogical.width;
		m_szLogicScale.height = m_szWindow.height / m_szLogical.height;

		m_szImageScale.width = m_szWindow.width / m_szImageSize.width;
		m_szImageScale.height = m_szWindow.height / m_szImageSize.height;
		
		m_fImageScaleX = m_szImageScale.width; m_fImageScaleY = m_szImageScale.height;

		m_szScale = m_szLogicScale;
		m_rScaleX = m_szScale.width; m_rScaleY = m_szScale.height;

		m_ptCenter = CGPoint.ccp(m_szLogical.width * 0.5f, m_szLogical.height * 0.5f);
		
		/** check if current media is pad */
    	if (displayMetrics.densityDpi >= DisplayMetrics.DENSITY_DEFAULT){
    		m_bPad = true;
    	}
	};

	/** set node scale to current scale */
	public static void CORRECT_SCALE(CCNode node){
    	node.setScaleX(m_szScale.width);
    	node.setScaleY(m_szScale.height);
	};
	
	/************************************************************************************************/
	///////////////////////////////// LOCATING MACROs ////////////////////////////////////////////////
	/************************************************************************************************/
	
    //////////////////////////////// LOCATE NODE WITH RATIO /////////////////////////////////////////////
	/** Locate and scale a node in the specified ratio with NODE_ALIGN. */
    public static final void LOCATE_NODE_RATIO(final CCNode node, final float x, final float y ){
    	POSITION_NODE_RATIO(node, x, y);		
    	node.setScaleX(m_szScale.width);
    	node.setScaleY(m_szScale.height);
    }
 
    /** Locate and scale a node in the specified ratio and  and add it to parent. */
    public static final void LOCATE_NODE_RATIO( CCNode parent, final CCNode node, final float x, final float y ) {
    	LOCATE_NODE_RATIO(node, x, y);
    	parent.addChild(node);
    }

	/** set position a node in the specified location. */
    public static final void POSITION_NODE_RATIO(final CCNode node, final float x, final float y ){
    	POSITION_NODE(node, x*m_szLogical.width, y*m_szLogical.height);		
    }
    
    //////////////////////////////// LOCATE NODE WITH LOCATION /////////////////////////////////////////////
	/** Locate and scale a node in the specified point. */
    public static final void LOCATE_NODE(final CCNode node, final CGPoint ptLogical ){
    	LOCATE_NODE(node, ptLogical.x, ptLogical.y);
    }
    
	/** Locate and scale a node in the specified location. */
    public static final void LOCATE_NODE( final CCNode node, final float x, final float y ){
    	POSITION_NODE(node, x, y);		
    	node.setScaleX(m_szScale.width);
    	node.setScaleY(m_szScale.height);
    }
    
	/** Locate and scale a node in the specified point and add it to parent. */
    public static final void LOCATE_NODE(CCNode parent, final CCNode node, final CGPoint ptLogical ){
    	LOCATE_NODE(parent, node, ptLogical.x, ptLogical.y);
    }

	/** Locate and scale a node in the specified location with NODE_ALIGN and add it to parent. */
    public static final void LOCATE_NODE(CCNode parent, final CCNode node, final float x, final float y ){
    	LOCATE_NODE(node, x, y);
    	parent.addChild(node);
    }
    
    //////////////////////////////// LOCATE SCALED NODE /////////////////////////////////////////////
	/** Locate and scale a node in the specified point. */
    public static final void LOCATE_NODE( final float ftScale, final CCNode node, final CGPoint ptLogical ){
    	LOCATE_NODE( ftScale, node, ptLogical.x, ptLogical.y);
    }
    
	/** Locate and scale a node in the specified location. */
    public static final void LOCATE_NODE( final float ftScale, final CCNode node, final float x, final float y ){
    	POSITION_NODE(node, x, y);		
    	node.setScaleX(m_szScale.width * ftScale);
    	node.setScaleY(m_szScale.height* ftScale);
    }
   
	/** Locate and scale a node in the specified point and add it to parent. */
    public static final void LOCATE_NODE(CCNode parent, final float ftScale, final CCNode node, final CGPoint ptLogical ){
    	LOCATE_NODE(parent, ftScale, node, ptLogical.x, ptLogical.y);
    }

	/** Locate and scale a node in the specified location and add it to parent. */
    public static final void LOCATE_NODE(CCNode parent, final float ftScale, final CCNode node, final float x, final float y ){
    	LOCATE_NODE( ftScale, node, x, y);
    	parent.addChild(node);
    }
    
	/////////////////////////////////// POSITIONING ///////////////////////////////////////
	/** set position a node in the specified point. */
    public static final void POSITION_NODE(final CCNode node, final CGPoint ptLogical ){
    	POSITION_NODE(node, ptLogical.x, ptLogical.y);
    }
    
	/** set position a node in the specified location. */
    public static final void POSITION_NODE( final CCNode node, final float x, final float y ){
       	node.setPosition( m_szScale.width * x, m_szScale.height * y );
    }

    
    /////////////////////// LOCATE CENTER ////////////////////////////
    /** Locate to center and set scale a node. */
    public static final void LOCATE_NODE_CENTER( final CCNode node ){
    	LOCATE_NODE(node, m_ptCenter);
    }
    
    /** Locate to center and set scale a node. */
    public static final void LOCATE_NODE_CENTER( final float ftScale, final CCNode node ){
    	LOCATE_NODE(ftScale, node, m_ptCenter);
    }
    
	/** Locate to center and set scale a node then add to parent. */
    public static final void LOCATE_NODE_CENTER( CCNode parent, final CCNode node ){
    	LOCATE_NODE(parent, node, m_ptCenter);
    }
    
	/** Locate to center and set scale a node then add to parent. */
    public static final void LOCATE_NODE_CENTER( CCNode parent, final float ftScale, final CCNode node ){
    	LOCATE_NODE(parent, ftScale, node, m_ptCenter);
    }
    
	/////////////////////////////////// CONVERT COORDINATE ///////////////////////////////////////
    ////////////////////////// REAL - LOGICAL  /////////////////////////////////
    /** convert real coordinate to logical coordinate. */
    public static final CGPoint REAL_TO_LOGICAL( final CGPoint ptReal ) {
    	return CGPoint.ccp( ptReal.x / m_szScale.width, ptReal.y / m_szScale.height );
    }

    public static final CGSize REAL_TO_LOGICAL( final CGSize szReal ) {
    	return CGSize.make( szReal.width / m_szScale.width, szReal.height / m_szScale.height );
    }

    public static final CGRect REAL_TO_LOGICAL( final CGRect rtReal ) {
    	return CGRect.make( REAL_TO_LOGICAL( rtReal.origin ), REAL_TO_LOGICAL( rtReal.size ));
    }

    /** convert real X-coordinate to logical coordinate. */
    public static final float REAL_TO_LOGICAL_X( final float ftLogical ) {
    	return ftLogical / m_szScale.width;
    }
    
    /** convert real Y-coordinate to logical coordinate. */
    public static final float REAL_TO_LOGICAL_Y( final float ftLogical ) {
    	return  ftLogical / m_szScale.height;
    }
    
    ////////////////////////// LOGICAL - REAL  /////////////////////////////////
    /** convert logical coordinate to real coordinate. */
    public static final CGPoint LOGICAL_TO_REAL( final CGPoint ptLogical ) {
    	return CGPoint.ccp(m_szScale.width * ptLogical.x, m_szScale.height * ptLogical.y);
    }

    public static final CGSize LOGICAL_TO_REAL( final CGSize szLogical ) {
    	return CGSize.make( m_szScale.width* szLogical.width, m_szScale.height * szLogical.height );
    }

    public static final CGRect LOGICAL_TO_REAL( final CGRect rtLogical ) {
    	return CGRect.make( LOGICAL_TO_REAL(rtLogical.origin), LOGICAL_TO_REAL(rtLogical.size) );
    }
 
    /** convert logical X-coordinate to real coordinate. */
    public static final float LOGICAL_TO_REAL_X( final float ftLogical ) {
    	return m_szScale.width * ftLogical;
    }
    
    /** convert logical Y-coordinate to real coordinate. */
    public static final float LOGICAL_TO_REAL_Y( final float ftLogical ) {
    	return m_szScale.height * ftLogical;
    }
    
//    ////////////////////////// LOGICAL-WORD /////////////////////////////////
//    /** convert logical point to world vector. */
//    public static final Vector2 LOGICAL_TO_WORLD( final CGPoint ptLogical ){
//    	Vector2 vec = new Vector2();
//		vec.x = (ptLogical.x * m_szScale.width) / m_ftPTM_RATIO;
//    	vec.y = (ptLogical.y * m_szScale.height) / m_ftPTM_RATIO;
//    	return vec;
//    }
//    
//    /** convert logical x - length to world length. */
//    public static final float LOGICAL_TO_WORLD_X( final float ftLogical ){
//    	return (ftLogical * m_szScale.width) / m_ftPTM_RATIO;
//    }
//    
//    /** convert logical y - length to world length. */
//    public static final float LOGICAL_TO_WORLD_Y( final float ftLogical ){
//    	return (ftLogical * m_szScale.height) / m_ftPTM_RATIO;
//    }
//    
//    /** convert world vector to logical point. */
//    public static final CGPoint WORLD_TO_LOGICAL( final Vector2 vecWorld ){
//    	return CGPoint.ccp((vecWorld.x / m_szScale.width) * m_ftPTM_RATIO, (vecWorld.y / m_szScale.height) * m_ftPTM_RATIO);
//    }
//    
//    /** convert  world length to logical x - length.*/
//    public static final float WORLD_TO_LOGICAL_X( final float ftWorld ){
//    	return (ftWorld / m_szScale.width) * m_ftPTM_RATIO;
//    }
//    
//    /** convert  world length to logical y - length.*/
//    public static final float WORLD_TO_LOGICAL_Y( final float ftWorld ){
//    	return (ftWorld / m_szScale.height) * m_ftPTM_RATIO;
//    }
//    
//    ////////////////////////// REAL-WORD /////////////////////////////////
//    /** convert real point to world vector. */
//    public static final Vector2 REAL_TO_WORLD( final CGPoint ptReal ){
//    	Vector2 vec = new Vector2();
//		vec.x = ptReal.x / m_ftPTM_RATIO;
//    	vec.y = ptReal.y / m_ftPTM_RATIO;
//    	return vec;
//    }
//    
//    /** convert real length to world length. */
//    public static final float REAL_TO_WORLD( final float ftReal ){
//    	return ftReal / m_ftPTM_RATIO;
//    }
//    
//    /** convert world vector to real point. */
//    public static final CGPoint WORLD_TO_REAL( final Vector2 vecWorld ){
//    	return CGPoint.ccp(vecWorld.x * m_ftPTM_RATIO, vecWorld.y * m_ftPTM_RATIO);
//    }
//    
//    /** convert world length to real length. */
//    public static final float WORLD_TO_REAL( final float ftWorld ){
//    	return ftWorld * m_ftPTM_RATIO;
//    }
//    
//    ///////////////////////////// VECTOR-POINT /////////////////////////////////////
//    public static final CGPoint TO_POINT( final Vector2 vecPos ){
//    	return CGPoint.make(vecPos.x, vecPos.y);
//    }
//    
//    public static final Vector2 TO_VECTOR( final CGPoint ptPos ){
//    	Vector2 vec = new Vector2();
//    	vec.x = ptPos.x;
//    	vec.y = ptPos.y;
//    	return vec;
//    }
//  
//	/////////////////////// DRAWING BODIES  ////////////////////////////////////////////////////////	
//	public static void DRAW_BODIES(GL10 gl, final World world){
//		DRAW_BODIES( gl, world, CGPoint.zero() );
//	}
//	
// 	public static void DRAW_BODIES(GL10 gl, final World world, final CGPoint ptOffest){
//      Iterator<Body> it = world.getBodies();
//	  	while(it.hasNext()){ 
//			Body b = it.next();
//			
//			for( Fixture fixture : b.getFixtureList() ){
//				if(fixture.getType() == Shape.Type.Polygon){
//		   			PolygonShape shape = (PolygonShape)fixture.getShape();
//		  			int nVertex = shape.getVertexCount();
//		  			
//		  			Vector2 vecVertex[] = new Vector2[nVertex];
//		  			CGPoint posVertex[] = new CGPoint[nVertex];
//		  			for(int i=0; i<nVertex ; i++){
//		  				vecVertex[i] = new Vector2();
//		  				shape.getVertex(i, vecVertex[i]);
//		  				float length = Macros.WORLD_TO_REAL(vecVertex[i].len());
//		  				float alpha = MathUtils.atan2(vecVertex[i].y, vecVertex[i].x) + b.getAngle();
//		     				posVertex[i] = CGPoint.ccpAdd( ptOffest, CGPoint.ccpAdd( Macros.WORLD_TO_REAL(b.getPosition()),
//		     						CGPoint.make(length * MathUtils.cos(alpha), length * MathUtils.sin(alpha))) );
//		     		}
//		  			
//		  			for(int i=1; i<nVertex ; i++){
//		  				CCDrawingPrimitives.ccDrawLine(gl, posVertex[i-1], posVertex[i]);
//		  			}
//				}else if(fixture.getType() == Shape.Type.Circle){
//					CircleShape shape = (CircleShape)fixture.getShape();
//					CCDrawingPrimitives.ccDrawCircle(gl, 
//							CGPoint.ccpAdd( ptOffest, Macros.WORLD_TO_REAL(b.getPosition())), 
//							Macros.WORLD_TO_REAL(shape.getRadius()), 0, 10, true);
//				}
//			}
//		}
//	}

    ///////////////////////////// SCENE MANAGEMENT /////////////////////////////////////////////////////
    /** replace the scene with layer. */
    public static final void REPLACE_LAYER( CCLayer oldLayer, final CCLayer newlayer ) {
		CCScene scene = CCScene.node();
		scene.addChild(newlayer);
		CCDirector.sharedDirector().replaceScene(scene);

		oldLayer.removeAllChildren(true);
		CCTextureCache.sharedTextureCache().removeUnusedTextures();
    }

    /** push the scene with layer. */
    public static final void PUSH_LAYER( final CCLayer newlayer ) {
		CCScene scene = CCScene.node();
		scene.addChild(newlayer);
		CCDirector.sharedDirector().pushScene(scene);
    	CCTextureCache.sharedTextureCache().removeUnusedTextures();
  }

    /** pop the top layer. */
    public static final void POP_LAYER() {
		CCDirector.sharedDirector().popScene();
    }

    /** Apply the FADE transition while replacing the scene. */
    public static final void REPLACE_LAYER_WITH_FADE( CCLayer oldLayer, final CCLayer newlayer, float transTime, ccColor3B color ) {
	    CCScene scene = CCScene.node();
	    scene.addChild(newlayer);
    	CCDirector.sharedDirector().replaceScene( new CCFadeTransition(transTime, scene, color ) );

    	oldLayer.removeAllChildren(true);
    	CCTextureCache.sharedTextureCache().removeUnusedTextures();
    }
    
    /** Apply the specified transition while replacing the scene. */
    public static final void REPLACE_LAYER_WITH_TRANS( CCLayer oldLayer, final CCLayer newlayer, Class<?> transition, float transTime ) {
		CCScene scene = CCScene.node();

		try {
		    scene.addChild(newlayer);
		    Method cons = transition.getMethod("transition", Float.TYPE, CCScene.class);
			CCScene s = (CCScene) cons.invoke(transition, transTime, scene);
			CCDirector.sharedDirector().replaceScene(s);

	    	oldLayer.removeAllChildren(true);
	    	CCTextureCache.sharedTextureCache().removeUnusedTextures();
	 
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
   }
    
    /** create label with specified text, size and color with default font */
    public static final CCLabel MAKE_LABEL( String strLabel, int nFontSize, ccColor3B color3 ) {
		return MAKE_LABEL(strLabel, "American Typewriter", nFontSize, color3);
    }

    /** create label with specified text, fontName, size and color */
    public static final CCLabel MAKE_LABEL( String strLabel, String strFontName, int nFontSize, ccColor3B color3 ) {
		CCLabel label = CCLabel.makeLabel(strLabel, strFontName, nFontSize);
		label.setColor( color3 );
		return label;
    }
    
    /** create label with specified text, fontName, size and color */
    public static final CCLabel MAKE_LABEL( String strLabel, String strFontName, int nFontSize ) {
		CCLabel label = CCLabel.makeLabel(strLabel, strFontName, nFontSize);
		return label;
    }
    
    /** create label with specified text and Bitmap Font */
    public static final CCBitmapFontAtlas MAKE_BMP_LABEL( String strLabel, String strBmpFontName ) {
    	return CCBitmapFontAtlas.bitmapFontAtlas(strLabel, strBmpFontName);
    }
	
}
 