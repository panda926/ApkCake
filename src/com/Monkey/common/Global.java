package com.Monkey.common;

import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;

public class Global {
	
	private static Global _sharedGlobal = null;
    public static Global sharedGlobal() {
    	if( _sharedGlobal == null ){
    		_sharedGlobal = new Global();
    	}
        return _sharedGlobal;
    }

    public Global(){
    	
    }
	
	public static enum Monkey{
	  YOUNG_MONEKY,
	  MUM_MONKEY,
	};

	public static enum Sound{
	    SOUND_ON,
	    SOUND_OFF,
	};

	public static enum Place{
	    FOREST_PLACE,
	    CITY_PLACE,
	};
	
	public static final int REGISTER_COUNT = 5;
	
	public static final int COUNT_GERNERAL_TARGET_LEVEL1 = 7;
	public static final int COUNT_GERNERAL_TARGET_LEVEL2 = 8;
	public static final int COUNT_BANANA                 = 2;
	public static final int COUNT_CAKE                   = 20;

	public static final int DEFAULT_COUNT_PER_GERNERAL   = 3;

	public static final float LAND_Y              = Macros.m_fImageScaleY*100f;
	public static final float VELOCITY_START1_X   = -300*Macros.m_fImageScaleX;
	public static final float VELOCITY_START2_X   = -500*Macros.m_fImageScaleX;
	public static final float VELOCITY_LIMIT1     = -700*Macros.m_fImageScaleX;
	public static final float VELOCITY_LIMIT2     = -900*Macros.m_fImageScaleX;
	public static final float VELOCITY_DELTA_X    = -30*Macros.m_fImageScaleX;

	public static float 	g_velocityBack 	= -400f*Macros.m_rScaleX;
	public static float 	delta = 1.0f/40f;

	public static int 		g_levelNumber 	= 1;
	public static boolean 	g_momkeyMom = false;

	public static int 		g_nLevelCompleteInfo = 0;
	public static int 		g_nSelecetedMonkey;
	public static int 		g_nSelectedPlace;
	public static Sound 	g_bSoundState = Sound.SOUND_ON;
	
	////////////////////// Animate /////////////////////

	public CCAnimate ani_moto_N;
	public CCAnimate ani_moto_J_A;
	public CCAnimate ani_moto_J_B;
	
	public CCAnimate ani_roll_N;
	public CCAnimate ani_roll_J_A;
	public CCAnimate ani_roll_J_B;
	
	public CCAnimate ani_sboard_N;
	public CCAnimate ani_sboard_J_A;
	public CCAnimate ani_sboard_J_B;
	
	public CCAnimate ani_jet_N;
	
	public CCAnimate ani_moto_mom_N;
	public CCAnimate ani_moto_mom_J_A;
	public CCAnimate ani_moto_mom_J_B;
	
	public CCAnimate ani_roll_mom_N;
	public CCAnimate ani_roll_mom_J_A;
	public CCAnimate ani_roll_mom_J_B;
	
	public CCAnimate ani_sboard_mom_N;
	public CCAnimate ani_sboard_mom_J_A;
	public CCAnimate ani_sboard_mom_J_B;
	
	public CCAnimate ani_jet_mom_N;
	
	public CCAnimate ani_bird1;
	public CCAnimate ani_bird2;
	
	public CCAnimate ani_snake;
	public CCAnimate ani_spider;
	public CCAnimate ani_wood;
	
	public CCAnimate ani_after_moto;
	public CCAnimate ani_explosion;
	public CCAnimate ani_explosionNew;
	
	public CCAnimate ani_banana;
	public CCAnimate ani_banana_jet;
	public CCAnimate ani_cake;
	
	public CCAnimate ani_monkey_hoho;
	public CCAnimate ani_monkey_mom_hoho;
	
		//////////////////// Image Names ////////////////////

		public static String IMG_HEART[] = {
		    "heart01.png",
		    "heart02.png",
		    "heart03.png",
		    null
		};
		
		private String IMG_MONKEY_HOHO[] = {
		    "endmonkey01.png",
		    "endmonkey02.png",
		    "endmonkey03.png",
		    "endmonkey04.png",
		    "endmonkey05.png",
		    "endmonkey06.png",
		    "endmonkey07.png",
		    "endmonkey08.png",
		    "endmonkey09.png",
		    "endmonkey10.png",
		    "endmonkey11.png",
		    "endmonkey12.png",
		    null
		};
		
		private String IMG_MONKEY_MOM_HOHO[] = {
		    "endmonkeyMom01.png",
		    "endmonkeyMom02.png",
		    "endmonkeyMom03.png",
		    "endmonkeyMom04.png",
		    null
		};
		
		public static String IMG_CAKE_EXP[] = {
		    "endcake01.png",
		    "endcake02.png",
		    "endcake03.png",
		    "endcake04.png",
		    "endcake05.png",
		    "endcake06.png",
		    "endcake07.png",
		    null
		};
		
		private String IMG_BANANA[] = {
		    "banana_01.png",
		    "banana_02.png",
		    "banana_03.png",
		    null
		};
		
		private String IMG_BANANA_JET[] = {
		    "bananaJet_01.png",
		    "bananaJet_02.png",
		    "bananaJet_03.png",
		    null
		};
		
		
		private String IMG_CAKE[] = {
		    "cake_01.png",
		    "cake_02.png",
		    "cake_03.png",
		    null
		};
		
		public static String IMG_CHANGE[] = {
		    "change_roll.png",
		    "change_sboad.png",
		    "change_moto.png",
		    "change_jet.png",
		    null
		};
		
		public static String IMG_STAGE_ICON[] = {
		    "stageIcon_1_1.png",
		    "stageIcon_1_2.png",
		    "stageIcon_1_3.png",
		    "stageIcon_2_1.png",
		    "stageIcon_2_2.png",
		    "stageIcon_2_3.png",
		    null
		};

		private String IMG_MOTO[] = {
			"moto01.png",
		    "moto02.png",
		    "moto03.png",
		    "moto04.png",
		    "moto05.png",
		    "moto06.png",
		    "moto07.png",
		    "moto08.png",
		    "moto09.png",
		    "moto10.png",
		    "moto11.png",
		    "moto12.png",
		    "moto13.png",
		    "moto14.png",
		    "moto15.png",
		    null
		};
		
		private String IMG_MOTO_MOM[] = {
			"motoMom01.png",
		    "motoMom02.png",
		    "motoMom03.png",
		    "motoMom04.png",
		    "motoMom05.png",
		    "motoMom06.png",
		    "motoMom07.png",
		    "motoMom08.png",
		    "motoMom09.png",
		    "motoMom10.png",
		    "motoMom11.png",
		    "motoMom12.png",
		    "motoMom13.png",
		    "motoMom14.png",
		    "motoMom15.png",
		    null
		};
		
		private String IMG_ROLL[] = {
			"roll01.png",
		    "roll02.png",
		    "roll03.png",
		    "roll04.png",
		    "roll05.png",
		    "roll06.png",
		    "roll07.png",
		    "roll08.png",
		    "roll09.png",
		    "roll10.png",
		    "roll11.png",
		    "roll12.png",
		    "roll13.png",
		    "roll14.png",
		    "roll15.png",
		    "roll16.png",
		    null
		};
		
		private String IMG_ROLL_MOM[] = {
			"rollMom01.png",
		    "rollMom02.png",
		    "rollMom03.png",
		    "rollMom04.png",
		    "rollMom05.png",
		    "rollMom06.png",
		    "rollMom07.png",
		    "rollMom08.png",
		    "rollMom09.png",
		    "rollMom10.png",
		    "rollMom11.png",
		    "rollMom12.png",
		    "rollMom13.png",
		    "rollMom14.png",
		    "rollMom15.png",
		    "rollMom16.png",
		    null
		};
		
		private String IMG_SBOARD[] = {
			"sboard01.png",
		    "sboard02.png",
		    "sboard03.png",
		    "sboard04.png",
		    "sboard05.png",
		    "sboard06.png",
		    "sboard07.png",
		    "sboard08.png",
		    "sboard09.png",
		    "sboard10.png",
		    "sboard11.png",
		    null
		};
		
		private String IMG_SBOARD_MOM[] = {
			"sbordMom01.png",
		    "sbordMom02.png",
		    "sbordMom03.png",
		    "sbordMom04.png",
		    "sbordMom05.png",
		    "sbordMom06.png",
		    "sbordMom07.png",
		    "sbordMom08.png",
		    "sbordMom09.png",
		    "sbordMom10.png",
		    "sbordMom11.png",
		    null
		};
		
		private String IMG_JET[] = {
			"jet01.png",
		    "jet02.png",
		    "jet03.png",
		    null
		};
		
		private String IMG_JET_MOM[] = {
			"jetMom01.png",
		    "jetMom02.png",
		    "jetMom03.png",null
		};
		
		private String IMG_AFTER_MOTO[] = {
		    "aftermoto_01.png",
		    "aftermoto_02.png",
		    "aftermoto_03.png",null
		};
		
		private String IMG_SNAKE[] = {
		    "snake01.png",
		    "snake02.png",
		    "snake03.png",
		    "snake04.png",
		    "snake05.png",
		    "snake06.png",null
		};
		
		private String IMG_SPIDER[] = {
		    "spider01.png",
		    "spider02.png",
		    "spider03.png",
		    "spider04.png",
		    "spider05.png",
		    "spider06.png",null
		};
		
		public static String IMG_STAGE_1[] = {
		    "level0101.jpg",
		    "level0102.jpg",
		    "level0103.jpg",null
		};
		
		public static String IMG_STAGE_2_2[] = {
		    "store_01.jpg",
		    "store_02.jpg",
		    "store_03.jpg",null
		};
		
		public static String IMG_STAGE_2[] = {
		    "level0201.jpg",
		    "level0202.jpg",
		    "level0203.jpg",null
		};
		
		private String IMG_EXPLOSION[] = {
		    "explosion_01.png",
		    "explosion_02.png",
		    "explosion_03.png",null
		};
		
		private String IMG_BIRD1[] = {
		    "Bird_right0.png",
		    "Bird_right1.png",
		    "Bird_right2.png",
		    "Bird_right3.png",null
		};
		
		private String IMG_BIRD2[] = {
		    "bird_1_0.png",
		    "bird_1_1.png",
		    "bird_1_2.png",
		    "bird_1_3.png",
		    "bird_1_4.png",null
		};


	/////////////////// Functions ///////////////////////
	
	public boolean initPlayerAnimation(){
		
		String filename;
		CCAnimation animation = CCAnimation.animation("Animation1", 0.1f);

		///// Moto_N
		for (int num = 0; num < 2; num ++) {
			filename = IMG_MOTO[num];
			animation.addFrame(filename);
		}
		ani_moto_N = CCAnimate.action(animation, false);
		
		///// Moto_J_A
		animation = CCAnimation.animation("Animation3", 0.1f);
		for (int num = 2; num < 11; num ++) {
			filename = IMG_MOTO[num];
			animation.addFrame(filename);
		}
		ani_moto_J_A = CCAnimate.action(animation, false);

		///// Moto_J_B
		animation = CCAnimation.animation("Animation5", 0.1f);
	    for (int num = 11; IMG_MOTO[num] != null; num ++) {
			filename = IMG_MOTO[num];
			animation.addFrame(filename);
		}
		ani_moto_J_B = CCAnimate.action(animation, false);

		// Roll_N
		animation = CCAnimation.animation("Animation7", 0.1f);
	    for (int num = 0; num < 8; num ++) {
			filename = IMG_ROLL[num];
			animation.addFrame(filename);
		}
		ani_roll_N = CCAnimate.action(animation, false);

		// Roll_J_A
		animation = CCAnimation.animation("Animation9", 0.1f);
	    for (int num = 8; num < 11; num ++) {
			filename = IMG_ROLL[num];
			animation.addFrame(filename);
		}
		ani_roll_J_A = CCAnimate.action(animation, false);
	
		// Roll_J_B
		animation = CCAnimation.animation("Animation11", 0.1f);
	    for (int num = 11; IMG_ROLL[num] != null; num ++) {
			filename = IMG_ROLL[num];
			animation.addFrame(filename);
		}
		ani_roll_J_B = CCAnimate.action(animation, false);
	
		// SBoard_N
		animation = CCAnimation.animation("Animation13", 0.1f);
	    for (int num = 0; num < 4; num ++) {
			filename = IMG_SBOARD[num];
			animation.addFrame(filename);
		}
		ani_sboard_N = CCAnimate.action(animation, true);;
	    
		// SBoard_J_A
		animation = CCAnimation.animation("Animation15", 0.1f);
	    for (int num = 4; num < 7; num ++) {
			filename = IMG_SBOARD[num];
			animation.addFrame(filename);
		}
		ani_sboard_J_A = CCAnimate.action(animation, false);
	    
		// SBoard_J_B
		animation = CCAnimation.animation("Animation17", 0.1f);
	    for (int num = 7; IMG_SBOARD[num] != null; num ++) {
			filename = IMG_SBOARD[num];
			animation.addFrame(filename);
		}
		ani_sboard_J_B = CCAnimate.action(animation, false);
	    
		// Jet_N
		animation = CCAnimation.animation("Animation19", 0.1f);
		for (int num = 0; IMG_JET[num] != null; num ++) {
			filename = IMG_JET[num];
			animation.addFrame(filename);
		}
		ani_jet_N = CCAnimate.action(animation, false);
	    
		// MONKEY HOHO
		animation = CCAnimation.animation("Animation32", 0.1f);
	    for (int num = 0; IMG_MONKEY_HOHO[num] != null; num ++) {
			filename = IMG_MONKEY_HOHO[num];
			animation.addFrame(filename);
		}
		ani_monkey_hoho = CCAnimate.action(animation, false);
	
		return true;
	}
	
	public boolean initPlayerMomAnimation(){

		String filename;
		CCAnimation animation = CCAnimation.animation("Animation2", 0.1f);

		///// Moto_N
		for (int num = 0; num < 2; num ++) {
			filename = IMG_MOTO_MOM[num];
			animation.addFrame(filename);
		}
		ani_moto_mom_N = CCAnimate.action(animation, false);
		
		///// Moto_J_A
		animation = CCAnimation.animation("Animation4", 0.1f);
		for (int num = 2; num < 11; num ++) {
			filename = IMG_MOTO_MOM[num];
			animation.addFrame(filename);
		}
		ani_moto_mom_J_A = CCAnimate.action(animation, false);
	
		///// Moto_J_B
		animation = CCAnimation.animation("Animation6", 0.1f);
	    for (int num = 11; IMG_MOTO_MOM[num]!=null; num ++) {
			filename = IMG_MOTO_MOM[num];
			animation.addFrame(filename);
		}
		ani_moto_mom_J_B = CCAnimate.action(animation, false);
	
	// Roll_N
		animation = CCAnimation.animation("Animation8", 0.1f);
	    for (int num = 0; num < 8; num ++) {
			filename = IMG_ROLL_MOM[num];
			animation.addFrame(filename);
		}
		ani_roll_mom_N = CCAnimate.action(animation, false);
	
	// Roll_J_A
		animation = CCAnimation.animation("Animation10", 0.1f);
	    for (int num = 8; num < 11; num ++) {
			filename = IMG_ROLL_MOM[num];
			animation.addFrame(filename);
		}
		ani_roll_mom_J_A = CCAnimate.action(animation, false);
	
	// Roll_J_B
		animation = CCAnimation.animation("Animation12", 0.1f);
	    for (int num = 11; IMG_ROLL_MOM[num] != null; num ++) {
			filename = IMG_ROLL_MOM[num];
			animation.addFrame(filename);
		}
		ani_roll_mom_J_B = CCAnimate.action(animation, false);
	
	// SBoard_N
		animation = CCAnimation.animation("Animation14", 0.1f);
	    for (int num = 0; num < 4; num ++) {
			filename = IMG_SBOARD_MOM[num];
			animation.addFrame(filename);
		}
		ani_sboard_mom_N = CCAnimate.action(animation, true);;
	
	// SBoard_J_A
		animation = CCAnimation.animation("Animation16", 0.1f);
	    for (int num = 4; num < 7; num ++) {
			filename = IMG_SBOARD_MOM[num];
			animation.addFrame(filename);
		}
		ani_sboard_mom_J_A = CCAnimate.action(animation, false);
	    
	// SBoard_J_B
		animation = CCAnimation.animation("Animation18", 0.1f);
	    for (int num = 7; IMG_SBOARD_MOM[num] != null; num ++) {
			filename = IMG_SBOARD_MOM[num];
			animation.addFrame(filename);
		}
		ani_sboard_mom_J_B = CCAnimate.action(animation, false);

	// Jet_N
		animation = CCAnimation.animation("Animation20", 0.1f);
	    for (int num = 0; IMG_JET_MOM[num] != null; num ++) {
			filename = IMG_JET_MOM[num];
			animation.addFrame(filename);
		}
		ani_jet_mom_N = CCAnimate.action(animation, false);
	
		// MONKEY MOM HOHO
		animation = CCAnimation.animation("Animation33", 0.1f);
	    for (int num = 0; IMG_MONKEY_MOM_HOHO[num] != null; num ++) {
			filename = IMG_MONKEY_MOM_HOHO[num];
			animation.addFrame(filename);
		}
		ani_monkey_mom_hoho = CCAnimate.action(animation, false);
	
		return true;
	}
	
	public boolean initAnimation(){

		String filename;
		CCAnimation animation = CCAnimation.animation("Animation21", 0.1f);

		// snake
	    for (int num = 0; IMG_SNAKE[num] != null; num ++) {
			filename = IMG_SNAKE[num];
			animation.addFrame(filename);
		}
		ani_snake = CCAnimate.action(animation, false);
	
	// spider
		animation = CCAnimation.animation("Animation22", 0.1f);
	    for (int num = 0; IMG_SPIDER[num] != null; num ++) {
			filename = IMG_SPIDER[num];
			animation.addFrame(filename);
		}
		ani_spider = CCAnimate.action(animation, false);
	
	// After moto
		animation = CCAnimation.animation("Animation23", 0.1f);
	    for (int num = 0; IMG_AFTER_MOTO[num] != null; num ++) {
			filename = IMG_AFTER_MOTO[num];
			animation.addFrame(filename);
		}
		ani_after_moto = CCAnimate.action(animation, false);
	    
	// Explosion
		animation = CCAnimation.animation("Animation24", 0.1f);
	    for (int num = 0; IMG_EXPLOSION[num] != null; num ++) {
			filename = IMG_EXPLOSION[num];
			animation.addFrame(filename);
		}
		ani_explosion = CCAnimate.action(animation, false);
	
	// Bird1
		animation = CCAnimation.animation("Animation26", 0.1f);
	    for (int num = 0; IMG_BIRD1[num] != null; num ++) {
			filename = IMG_BIRD1[num];
			animation.addFrame(filename);
		}
		ani_bird1 = CCAnimate.action(animation, false);
	
	// Bird2
		animation = CCAnimation.animation("Animation27", 0.1f);
	    for (int num = 0; IMG_BIRD2[num] != null; num ++) {
			filename = IMG_BIRD2[num];
			animation.addFrame(filename);
		}
		ani_bird2 = CCAnimate.action(animation, false);
	
	// Banana
		animation = CCAnimation.animation("Animation28", 0.1f);
	    for (int num = 0; IMG_BANANA[num] != null; num ++) {
			filename = IMG_BANANA[num];
			animation.addFrame(filename);
		}
		ani_banana = CCAnimate.action(animation, false);
	
	// Banana Jet
		animation = CCAnimation.animation("Animation29", 0.1f);
	    for (int num = 0; IMG_BANANA_JET[num] != null; num ++) {
			filename = IMG_BANANA_JET[num];
			animation.addFrame(filename);
		}
		ani_banana_jet = CCAnimate.action(animation, false);
	
	// CAKE
		animation = CCAnimation.animation("Animation30", 0.1f);
	    for (int num = 0; IMG_CAKE[num] != null; num ++) {
			filename = IMG_CAKE[num];
			animation.addFrame(filename);
		}
		ani_cake = CCAnimate.action(animation, false);
	    
		return true;
	}

}	

