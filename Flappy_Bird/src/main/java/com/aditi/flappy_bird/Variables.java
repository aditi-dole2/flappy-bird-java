package com.aditi.flappy_bird;

import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Variables {

    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int PIPE_GAP = SCREEN_HEIGHT/5;
	public static int PIPE_DISTANCE = 250;
		public static final int PIPE_WIDTH = SCREEN_WIDTH/9;
		public static final int PIPE_HEIGHT = 4*PIPE_WIDTH;
		public static final int UPDATE_DIFFERENCE = 25;
		public static final int X_MOVEMENT_DIFFERENCE= 6;
		public static final int SCREEN_DELAY  = 250;
		public static final int BIRD_WIDTH = 120, BIRD_HEIGHT= 75;
		public static final int BIRD_X_LOC = SCREEN_WIDTH/7;
		public static final int BIRD_JUMP_DIFF = 70, BIRD_FALL_DIFF = 3;
		// BIRD_JUMP_HEIGHT = PIPE_GAP - BIRD_HEIGHT - BIRD_JUMP_DIFF*2;
		public static int birdYTracker = SCREEN_HEIGHT/2 - BIRD_HEIGHT;
		public static int currentLevel = 1;
		public static double gameSpeed = X_MOVEMENT_DIFFERENCE;
	
	
	
		
		//global variables
		public static boolean loopVar = true;
		public static boolean birdThrust = false; //false -> key has not been pressed to move the bird vertically
		public static boolean birdFired = false; //true -> button pressed before jump completes
		public static boolean released = true; //space bar released; starts as true so first press registers
		
	
	
	
	
	
		//global swing objects
		
		public final JFrame f = new JFrame("Flappy Bird");
		public static JButton startGame;
		public JPanel topPanel; //declared globally to accommodate the repaint operation and allow for removeAll(), etc.
		
			//other global objects
		public static GameScreen gs; //panel that has the moving background at the start of the game
        public static boolean gamePlay;





	public static int screenWidth, screenHeight;
	public static  int messageWidth = 0, scoreWidth = 0;
	public static String message = "Flappy Bird";
	public static boolean isSplash = true;


	
	public static int rewardScore =0;
	public static final int rewardThreshold =2;
	public static String rewardMessage = "";

				
		
    
}
