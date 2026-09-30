package com.aditi.flappy_bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.aditi.flappy_bird.Leaderboard.PlayerScore;




public final class GameScreen extends JPanel {
	//default reference ID
	private static final long serialVersionUID = 1L;
	
	//global variables
	
	private final Font scoreFont1 = new Font("Goudy Stout", Font.BOLD, 56), scoreFont2 = new Font("Calibri", Font.BOLD, 46), scoreFont3 = new Font("Calibri", Font.ITALIC, 46);
	private BottomPipe bp1, bp2;
	private TopPipe tp1, tp2;
	private Bird bird;
	private Leaderboard leaderboard;
	public static  int jumpScore = 0;

	private Timer rewardTimer; // Timer for displaying the reward message
    private boolean isRewardVisible; // Flag to check if reward is visible
	private String EndMessage = "";

		
		/**
		 * Default constructor for the GameScreen class
		 */
	public GameScreen(int screenWidth, int screenHeight, boolean isSplash) {
		Variables.screenWidth = screenWidth;
		Variables.screenHeight = screenHeight;
		Variables.isSplash = isSplash;
		rewardTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
							Variables.rewardMessage = ""; // Clear the reward message
							isRewardVisible = false; // Set the flag to false
							rewardTimer.stop(); // Stop the timer
						}
					});

		leaderboard = new Leaderboard();
        Leaderboard.PlayerScore playerScore;
    

	}
			
		
		/**
		 * Manual controlling what's drawn on this JPanel by calling the paintComponent method
		 * with a graphics object and painting using that object
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(new Color(25,25,112)); //color for the blue sky
			g.fillRect(0, 0, Variables.screenWidth, Variables.screenHeight*7/8); //create the sky rectangle
			g.setColor(new Color(147, 136, 9)); //brown color for ground
			g.fillRect(0, Variables.screenHeight*7/8, Variables.screenWidth, Variables.screenHeight/8); //create the ground rectangle
			g.setColor(Color.BLACK); //dividing line color
			g.drawLine(0, Variables.screenHeight*7/8, Variables.screenWidth, Variables.screenHeight*7/8); //draw the dividing line
			
			//objects must be instantiated before they're drawn!
			if(bp1 != null && bp2 != null && tp1 != null && tp2 != null) {
				g.drawImage(bp1.getBottomPipe(), bp1.getX(), bp1.getY(), null);
				g.drawImage(bp2.getBottomPipe(), bp2.getX(), bp2.getY(), null);
				g.drawImage(tp1.getTopPipe(), tp1.getX(), tp1.getY(), null);
				g.drawImage(tp2.getTopPipe(), tp2.getX(), tp2.getY(), null);
			}
			
			if(!Variables.isSplash && bird != null) {
				g.drawImage(bird.getBird(), bird.getX(), bird.getY(), null);
			}

			if (!Variables.rewardMessage.isEmpty()) {
				displayRewardMessage(g);
			}
  

			if (!EndMessage.isEmpty()) {
				g.setColor(Color.magenta); // Color for the reward message
				g.setFont(scoreFont2);
				FontMetrics metric = g.getFontMetrics(scoreFont2);
				int EndWidth = metric.stringWidth(EndMessage);
				g.drawString(EndMessage, Variables.screenWidth / 2 - EndWidth / 2, Variables.screenHeight / 4); //your score is 
				displayLeaderboard(g);
			}

			
	
			
			//needed in case the primary font does not exist
			try {
				g.setFont(scoreFont1);
				FontMetrics metric = g.getFontMetrics(scoreFont1);
				Variables.messageWidth = metric.stringWidth(Variables.message);
				//System.out.println("Current Score GS: " + SM.getScore()); // Check the score value
	
				Variables.scoreWidth = metric.stringWidth(String.format("%d", getScore()));	
			}
			catch(Exception e) {
				g.setFont(scoreFont2);
				FontMetrics metric = g.getFontMetrics(scoreFont2);
				Variables.messageWidth = metric.stringWidth(Variables.message);
				Variables.scoreWidth = metric.stringWidth(String.format("%d",getScore()));
				}
							
			g.drawString(Variables.message, Variables.screenWidth/2- Variables.messageWidth/2,110);//game over
							
			if(!Variables.isSplash) {
				Leaderboard.PlayerScore playerScore = leaderboard.getPlayerScore("Player 1");
        		g.drawString(String.format("C: %d", getScore()), 
                     Variables.screenWidth / 2 - Variables.scoreWidth / 2, 50);  // Current score
    		}

		}

			
		
		/**
		 * Parsing method for GameScreen's global BottomPipe variables
		 * @param bp1 The first BottomPipe
		 * @param bp2 The second BottomPipe
		 */


		public void setBottomPipe(BottomPipe bp1, BottomPipe bp2) {
			this.bp1 = bp1;
			this.bp2 = bp2;
		}
		
		/**
		 * Parsing method for GameScreen's global TopPipe variables
		 * @param tp1 The first TopPipe
		 * @param tp2 The second TopPipe
		 */
		public void setTopPipe(TopPipe tp1, TopPipe tp2) {
			this.tp1 = tp1;
			this.tp2 = tp2;
		}
		
		/**
		 * Parsing method for GameScreen's global Bird variable
		 * @param bird The Bird object
		 */
		public void setBird(Bird bird) {
			this.bird = bird;
		}
		
		
		/**
		 * Method called to parse a message onto the screen
		 * @param message The message to parse
		 */
		public void sendText(String message) {
			Variables.message = message;
		}

		public void sendEndText(String EndMessage) {
			this.EndMessage = EndMessage;
		}
	
		public void incrementJump(){
			jumpScore++;
			if (jumpScore % Variables.rewardThreshold == 0) {
				Variables.rewardScore++;
				Variables.rewardMessage = "Reward! Total Rewards: " + Variables.rewardScore;
				isRewardVisible = true; // Set the flag to true
            	rewardTimer.start(); // Start the timer
        
			}
		}

		public int getScore() {
			return jumpScore;
		}
		public int getReward() {
			return Variables.rewardScore;
		}
		public void displayRewardMessage(Graphics g){
			g.setColor(Color.ORANGE); // Color for the reward message
			g.setFont(scoreFont2);
			FontMetrics metric = g.getFontMetrics(scoreFont2);
			int rewardWidth = metric.stringWidth(Variables.rewardMessage);
			g.drawString(Variables.rewardMessage, Variables.screenWidth / 2 - rewardWidth / 2, Variables.screenHeight / 2);
			
		}
		
		public void updateLeaderboard() {
        leaderboard.addScore(PlayerScore.playerName, getScore());
    	}

    // Method to display the leaderboard
    	public void displayLeaderboard(Graphics g) {
        	List<Leaderboard.PlayerScore> topScores = leaderboard.getTopScores(1);
        	g.setColor(Color.WHITE);
        	g.setFont(scoreFont3 );
        	int yPosition = Variables.SCREEN_HEIGHT/2; // Starting position for the leaderboard

        
			g.drawString("Leader Board",Variables.screenWidth / 2 -100 ,Variables.screenHeight/2 - 50);
			g.drawString(String.format("%-20s %-20s %-20s", "Player", "CurrentScore", "Rewards"), Variables.screenWidth / 2 - 300, yPosition-10);

			// Loop through the top scores and display each player's score
			for (Leaderboard.PlayerScore playerScore : topScores) {
				yPosition += 30; // Space between scores
				g.drawString(String.format("%-20s %-30d %-30d", playerScore.getPlayerName(), playerScore.getScore(), Variables.rewardScore), 
					Variables.screenWidth / 2 - 200, yPosition+50);
		}
	}

	
}