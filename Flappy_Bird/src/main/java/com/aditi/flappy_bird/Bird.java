package com.aditi.flappy_bird;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Bird extends GameObject {
    private Image flappyBird;

    public Bird(int initialWidth, int initialHeight) {
        super(0, 0, initialWidth, initialHeight); // Initialize GameObject with x, y, width, height
        flappyBird = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/aditi/resources/blue_bird.png"));
        scaleBird(initialWidth, initialHeight);
    }

    private void scaleBird(int width, int height) {
        flappyBird = flappyBird.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.width = width;  // Update width in GameObject
        this.height = height; // Update height in GameObject
    }

    public Image getBird() {
        return flappyBird;
    }

    public BufferedImage getBI() {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(flappyBird, 0, 0, null);
        g.dispose();
        return bi;
    }
}