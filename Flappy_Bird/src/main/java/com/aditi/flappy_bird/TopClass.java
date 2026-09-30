package  com.aditi.flappy_bird;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;


public class TopClass implements ActionListener,KeyListener {
		//global swing objects

		private int restartCount =0;

		private final Object buildComplete;
		private final JFrame f = new JFrame("Flappy Bird");
		private JButton startGame;
		private JPanel topPanel; 
		
		private static final TopClass tc = new TopClass();
		private static GameScreen gs; //panel that has the moving background at the start of the game	
		private static Leaderboard leaderboard;	
		/**
		* Default constructor
		*/
		public TopClass() {
			this.buildComplete = new Object();

		}
			
			/**
			 * Main executable method invoked when running .jar file
			 * @param args
			 */
	
		public static void main(String[] args) {
		// Build the GUI on a new thread
			javax.swing.SwingUtilities.invokeLater(() -> {
						tc.buildFrame();
						
						// Create a new thread to keep the GUI responsive while the game runs
						Thread t;
						t = new Thread(() -> {
							// Run the game 
							tc.gameScreen(true);
						});
						t.start(); 
						});
		}
	
			/**
			 * Method to construct the JFrame and add the program content
			 */
		private void buildFrame() {
				Image icon = Toolkit.getDefaultToolkit().getImage("src//main//java//com//aditi//resources//blue_bird.png");
				f.setContentPane(createContentPane());
					f.setResizable(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setAlwaysOnTop(false);
					f.setVisible(true);
					f.setMinimumSize(new Dimension(Variables.SCREEN_WIDTH*1/4, Variables.SCREEN_HEIGHT*1/4));
					f.setExtendedState(JFrame.MAXIMIZED_BOTH);
					f.setIconImage(icon);
					f.addKeyListener((KeyListener) this);
			}
			
		private JPanel createContentPane() {
				topPanel = new JPanel(); //top-most JPanel in layout hierarchy
				topPanel.setBackground(Color.BLACK);
				
				//allow us to layer the panels:
				LayoutManager overlay = new OverlayLayout(topPanel);
				topPanel.setLayout(overlay);
				
				//Start Game JButton
				startGame = new JButton("Start Playing!");
				startGame.setBackground(Color.BLUE);
				startGame.setForeground(Color.WHITE);
				startGame.setFocusable(false);
				startGame.setFont(new Font("Calibri", Font.BOLD, 42));
				startGame.setAlignmentX(0.5f); //center horizontally on-screen
				startGame.setAlignmentY(0.5f); //center vertically on-screen
				startGame.addActionListener(this);
				topPanel.add(startGame);
				
				//must add last to ensure button's visibility
				gs = new GameScreen(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, true); //true --> we want pgs to be the splash screen
				topPanel.add(gs);
				
				return topPanel;
			}
			
			/**
			 * Implementation for action events
			 */
			@Override
		public void actionPerformed(ActionEvent e) {
				if(e.getSource() == startGame) {
					Variables.loopVar = false;
					fadeOperation();
				}
				else if (e.getSource() == buildComplete){
					Thread t = new Thread(){
					@Override
						public void run(){
							Variables.loopVar = true;
							tc.gameScreen(false);
						}
					};
					t.start();
				}
				
			}
		@Override
		public void keyPressed(KeyEvent e) {
			// Check for space or up arrow key press to make the bird jump
			if ((e.getKeyCode() == KeyEvent.VK_SPACE) && Variables.gamePlay == true && Variables.released == true) {
				if (Variables.birdThrust) {
						Variables.birdFired = true; // Register that the bird has been fired
				}
				Variables.birdThrust = true; // Set thrust to true to initiate jump
				Variables.released = false; // Prevent further jumps until the key is Variables.released
				System.out.println("Key Pressed: UP | Variables.birdThrust: " + Variables.birdThrust + " | Variables.birdFired: " + Variables.birdFired);
			}
	
			// Check for Enter key to restart the game
			else if (e.getKeyCode() == KeyEvent.VK_ENTER && Variables.gamePlay == false) {
				restartCount++;
				if (restartCount>3){
					resetGame();
				}
					Variables.birdYTracker = Variables.SCREEN_HEIGHT / 2 - Variables.BIRD_HEIGHT; // Reset bird position
					Variables.birdThrust = false; // Reset thrust
					Variables.released = true;
					Variables.gamePlay = true;
					Variables.gameSpeed = Variables.X_MOVEMENT_DIFFERENCE;
					actionPerformed(new ActionEvent(startGame, -1, "")); // Start the game
					System.out.println("Game Restarted | Variables.birdYTracker: " + Variables.birdYTracker);
			}
			// Check for Escape key to exit the game
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				
				System.exit(0); // Exit the application
			}
		}
			
		@Override
		public void keyReleased(KeyEvent e) {
			// Reset Variables.released state when space or up arrow key is Variables.released
			if (e.getKeyCode() == KeyEvent.VK_SPACE  && e.getID() == KeyEvent.KEY_RELEASED) {
				Variables.released = true; // Allow the bird to jump again
				//System.out.println("Key Pressed: UP | Variables.birdThrust: " + Variables.birdThrust + " | Variables.birdFired: " + Variables.birdFired);

			}
		}	
			
		@Override
		public void keyTyped(KeyEvent e) {
			// This method left empty as not used
		}		
		
		
		private void fadeOperation(){
			Thread t = new Thread(){
			@Override
				public void run(){
					topPanel.remove(startGame);
					topPanel.remove(gs);
					topPanel.revalidate();
					topPanel.repaint();
	
	
					JPanel temp = new JPanel();
					int alpha=0;
					temp.setBackground(new Color(0, 0, 0, alpha)); //transparent, black JPanel
					topPanel.add(temp);
					topPanel.add(gs);
					topPanel.revalidate();
					topPanel.repaint();
	
					long currentTime = System.currentTimeMillis();
	
					while(temp.getBackground().getAlpha() != 255) {
						if((System.currentTimeMillis() - currentTime) > Variables.UPDATE_DIFFERENCE/2) {
							if(alpha < 255 - 10) {
								alpha += 10;
							}
							else {
								alpha = 255;
							}
							
							temp.setBackground(new Color(0, 0, 0, alpha));
						
							topPanel.revalidate();
							topPanel.repaint();
							currentTime = System.currentTimeMillis();
						}
					}
					topPanel.removeAll();
					topPanel.add(temp);
					gs = new GameScreen(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, false);
					gs.sendText(""); //remove title text
					topPanel.add(gs);
	
					while(temp.getBackground().getAlpha() != 0) {
						if((System.currentTimeMillis() - currentTime) > Variables.UPDATE_DIFFERENCE/2) {
							if(alpha > 10) {
								alpha -= 10;
							}
							else {
								alpha = 0;
							}
							
							temp.setBackground(new Color(0, 0, 0, alpha));
						
							topPanel.revalidate();
							topPanel.repaint();
							currentTime = System.currentTimeMillis();
						}
					}
					actionPerformed(new ActionEvent(buildComplete, -1, "Build Finished"));
	
				}
				
			};
					
			t.start();
		}
	
		private void gameScreen(boolean isSplash){
			BottomPipe bp1 = new BottomPipe(Variables.PIPE_WIDTH, Variables.PIPE_HEIGHT);
			BottomPipe bp2 = new BottomPipe(Variables.PIPE_WIDTH, Variables.PIPE_HEIGHT);
			TopPipe tp1 = new TopPipe(Variables.PIPE_WIDTH, Variables.PIPE_HEIGHT);
			TopPipe tp2 = new TopPipe(Variables.PIPE_WIDTH, Variables.PIPE_HEIGHT);
			Bird bird = new Bird(Variables.BIRD_WIDTH, Variables.BIRD_HEIGHT);
			
			int xLoc1 = Variables.SCREEN_WIDTH + Variables.SCREEN_DELAY;
			int xLoc2 = (int)((double) 3.0/2.0 * Variables.SCREEN_WIDTH + Variables.PIPE_WIDTH/2.0 + Variables.PIPE_DISTANCE);
			int yLoc1 = bottomPipeHeight(), yLoc2 = bottomPipeHeight();
			int birdX = Variables.BIRD_X_LOC, birdY = Variables.birdYTracker;

		
			long startTime = System.currentTimeMillis();
	
			while(Variables.loopVar){
				if((System.currentTimeMillis() - startTime)> Variables.UPDATE_DIFFERENCE){
					if(xLoc1 < (0-Variables.PIPE_WIDTH)){
						xLoc1 = Variables.SCREEN_WIDTH;
						yLoc1 = bottomPipeHeight();
	
					}
					else if(xLoc2 < (0-Variables.PIPE_WIDTH)){
						xLoc2 = Variables.SCREEN_WIDTH;
						yLoc2 = bottomPipeHeight();
					}

					xLoc1 -= Variables.gameSpeed;
					xLoc2 -= Variables.gameSpeed;
	
					
	
					if(Variables.birdFired && !isSplash) {
						Variables.birdYTracker = birdY;
						Variables.birdFired = false;
					}
	
					if (Variables.birdThrust && !isSplash) {
						// Move bird vertically
						if (birdY - Variables.BIRD_JUMP_DIFF > 0) {
							birdY -= Variables.BIRD_JUMP_DIFF; // Move the bird up
							//System.out.println("going up");
						} else {
							birdY = 0;
						}
	
						Variables.birdYTracker = birdY; // Update tracker
						Variables.birdThrust = false; // Reset thrust after jump
						System.out.println("Bird Thrust | New birdY: " + birdY + " | Variables.birdYTracker: " + Variables.birdYTracker);
			 
					} else if (!isSplash) {
						birdY += Variables.BIRD_FALL_DIFF; // Apply gravity
						Variables.birdYTracker = birdY; // Update tracker
						//System.out.println("Bird Falling | New birdY: " + birdY + " | Variables.birdYTracker: " + Variables.birdYTracker);
					}
	
	
					bp1.setX(xLoc1);
					bp2.setX(xLoc2);
					tp1.setX(xLoc1);
					tp2.setX(xLoc2);
	
					bp1.setY(yLoc1);
					bp2.setY(yLoc2);
					tp1.setY(yLoc1 - Variables.PIPE_GAP - Variables.PIPE_HEIGHT);
					tp2.setY(yLoc2 - Variables.PIPE_GAP - Variables.PIPE_HEIGHT);
					
					if(!isSplash) {
						bird.setX(birdX);
						bird.setY(birdY);
						gs.setBird(bird);
					}
	
					gs.setBottomPipe(bp1, bp2);
					gs.setTopPipe(tp1, tp2);
	
					if(!isSplash && bird.getWidth() != -1) { //need the second part because if bird not on-screen, cannot get image width and have cascading error in collision
						collisionDetection(bp1,bp2,tp1,tp2,bird);
						updateScore(bp1, bp2, bird);
					}
	
					topPanel.revalidate();
					topPanel.repaint();
	
					startTime =  System.currentTimeMillis();
	
				}
			}
	
		}
	
	
		private int bottomPipeHeight(){
			int temp = 0;
			while( temp <= Variables.PIPE_GAP + 50 || temp>= Variables.SCREEN_HEIGHT - Variables.PIPE_GAP){
				temp = (int) (Math.random() * (Variables.SCREEN_HEIGHT));
			}
			return temp;
		}



		private void updateScore(BottomPipe bp1, BottomPipe bp2, Bird bird) {
			if(bp1.getX() + Variables.PIPE_WIDTH < bird.getX() && bp1.getX() + Variables.PIPE_WIDTH > bird.getX() - Variables.X_MOVEMENT_DIFFERENCE) {
				System.out.println("bp1 passed");	
				gs.incrementJump();
				updateLevel();
				gs.updateLeaderboard();
	
					
			}
			else if((bp2.getX() + Variables.PIPE_WIDTH < bird.getX()) && ((bp2.getX() + Variables.PIPE_WIDTH) > (bird.getX() - Variables.X_MOVEMENT_DIFFERENCE))) {
				System.out.println("bp2 passed");
				gs.incrementJump();	
				updateLevel();
				gs.updateLeaderboard();
	
				
			}
		}
	

	private void updateLevel(){
		if (gs.getScore() == 2 && Variables.currentLevel == 1) {
			Variables.currentLevel = 2; // Move to level 2
			Variables.gameSpeed += 1.00; // Increase speed
			System.out.println("Level up to 2: Speed increased to: " + Variables.gameSpeed);
		} else if (gs.getScore() == 5 && Variables.currentLevel == 2) {
			Variables.currentLevel = 3; // Move to level 3
			Variables.gameSpeed += 1.50; // Further increase speed
			System.out.println("Level up to 3: Speed increased to: " + Variables.gameSpeed);
		}
		else if (gs.getScore() == 7 && Variables.currentLevel == 3) {
			Variables.currentLevel = 4; // Move to level 3
			Variables.gameSpeed += 1.50; // Further increase speed
			System.out.println("Level up to 3: Speed increased to: " + Variables.gameSpeed);
		}
		
	}


	private void collisionDetection(BottomPipe bp1, BottomPipe bp2, TopPipe tp1, TopPipe tp2, Bird bird ){
		collisionHelper(bird.getRectangle(), bp1.getRectangle(), bird.getBI(), bp1.getBI());
		collisionHelper(bird.getRectangle(), bp2.getRectangle(), bird.getBI(), bp2.getBI());
		collisionHelper(bird.getRectangle(), tp1.getRectangle(), bird.getBI(), tp1.getBI());
		collisionHelper(bird.getRectangle(), tp2.getRectangle(), bird.getBI(), tp2.getBI());
		
		if(bird.getY() + Variables.BIRD_HEIGHT > Variables.SCREEN_HEIGHT*7/8) { //ground detection
			gs.sendText("Game Over");
			gs.sendEndText(" " );

			Variables.loopVar = false;
			Variables.gamePlay = false; //game has ended
		}
	}

	private void collisionHelper(Rectangle r1, Rectangle r2, BufferedImage b1, BufferedImage b2){
		if(r1.intersects(r2)) {
			Rectangle r = r1.intersection(r2);
			
			int firstI = (int) (r.getMinX() - r1.getMinX()); //firstI is the first x-pixel to iterate from
			int firstJ = (int) (r.getMinY() - r1.getMinY()); //firstJ is the first y-pixel to iterate from
			int bp1XHelper = (int) (r1.getMinX() - r2.getMinX()); //helper variables to use when referring to collision object
			int bp1YHelper = (int) (r1.getMinY() - r2.getMinY());
			
			for(int i = firstI; i < r.getWidth() + firstI; i++) { //
				for(int j = firstJ; j < r.getHeight() + firstJ; j++) {
					if((b1.getRGB(i, j) & 0xFF000000) != 0x00 && (b2.getRGB(i + bp1XHelper, j + bp1YHelper) & 0xFF000000) != 0x00) {
						gs.sendText("Game Over");
						gs.sendEndText(" " );
   						Variables.loopVar = false; //stop the game loop
						Variables.gamePlay = false; //game has ended
						break;
					}
				}
			}
		}
	} 

	private void resetGame() {
        // Reset all necessary variables to their initial state
        Variables.currentLevel = 1;
        Variables.gameSpeed = Variables.X_MOVEMENT_DIFFERENCE;
        Variables.rewardScore = 0;
        gs.jumpScore = 0; 
        Variables.birdYTracker = Variables.SCREEN_HEIGHT / 2 - Variables.BIRD_HEIGHT; // Reset bird position
        Variables.loopVar = true; // Allow the game loop to run again
        Variables.gamePlay = true; // Set gamePlay to false to indicate the game is not currently active
		leaderboard = new Leaderboard();
        System.out.println("Game has been reset after 3 restarts.");
    }



}