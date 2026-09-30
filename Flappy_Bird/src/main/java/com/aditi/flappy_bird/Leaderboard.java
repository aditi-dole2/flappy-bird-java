package com.aditi.flappy_bird;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Leaderboard {
    private List<PlayerScore> scores;

    public Leaderboard() {
        scores = new ArrayList<>();
    }

    public void addScore(String playerName, int currentScore) {
        for (PlayerScore playerScore : scores) {
            if (playerScore.getPlayerName().equals(playerName)) {
                playerScore.addScore(currentScore); // Update total score
                return;
            }
        }

        scores.add(new PlayerScore(playerName, currentScore));
        Collections.sort(scores, Comparator.comparingInt(PlayerScore::getScore).reversed());
    }


    public PlayerScore getPlayerScore(String playerName) {
        for (PlayerScore playerScore : scores) {
            if (playerScore.getPlayerName().equals(playerName)) {
                return playerScore; // Return the PlayerScore instance
            }
        }
        return null; // Return null if player not found
    }



    public List<PlayerScore> getTopScores(int n) {
        return scores.subList(0, Math.min(n, scores.size()));
    }
    

    public static class PlayerScore {

            public static String playerName = "Player 1";
            private  int totalScore = 0;
     
        public PlayerScore(String playerName, int score) {
            this.playerName = playerName;
            this.totalScore = GameScreen.jumpScore;
        }

        public String getPlayerName() {
            return playerName;
        }

        public  int getScore() {
            return totalScore;
        }
        public void addScore(int currentScore) {
            if (currentScore > 0) {
                this.totalScore ++; 
                // Add current score to total score
            }
            
        }
    }
}