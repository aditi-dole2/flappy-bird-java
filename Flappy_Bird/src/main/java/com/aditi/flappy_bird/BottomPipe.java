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
 * @author Aditiz
 */
public class BottomPipe extends GameObject {
    private Image bottomPipe;


    public BottomPipe(int initialWidth, int initialHeight) {
        super(0, 0, initialWidth, initialHeight); 
        bottomPipe = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/aditi/resources/tube_bottom.png"));
        scaleBottomPipe(initialWidth, initialHeight);
    }

    private void scaleBottomPipe(int width, int height) {
        bottomPipe = bottomPipe.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.width = width;  
        this.height = height; 
    }

    public Image getBottomPipe() {
        return bottomPipe;
    }
    

    public BufferedImage getBI() {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(bottomPipe, 0, 0, null);
        g.dispose();
        return bi;
    }
}

