/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aditi.flappy_bird;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aditi
 */
public class TopPipe extends GameObject {
    private Image topPipe;

    public TopPipe(int initialWidth, int initialHeight) {
        super(0, 0, initialWidth, initialHeight); // Initialize GameObject with x, y, width, height
        topPipe = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/aditi/resources/tube_top.png"));
        scaleTopPipe(initialWidth, initialHeight);
    }

    private void scaleTopPipe(int width, int height) {
        topPipe = topPipe.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.width = width;  // Update width in GameObject
        this.height = height; // Update height in GameObject
    }

    public Image getTopPipe() {
        return topPipe;
    }
    

    public BufferedImage getBI() {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(topPipe, 0, 0, null);
        g.dispose();
        return bi;
    }
}

