/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aditi.flappy_bird;

import java.awt.Rectangle;

public class GameObject {
    protected int xLoc;
    protected int yLoc;
    protected int width;
    protected int height;

    public GameObject(int x, int y, int width, int height) {
        this.xLoc = x;
        this.yLoc = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return xLoc;
    }

    public void setX(int x) {
        this.xLoc = x;
    }

    public int getY() {
        return yLoc;
    }

    public void setY(int y) {
        this.yLoc = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getRectangle() {
        return new Rectangle(xLoc, yLoc, width, height);
    }

    public void move(int dx, int dy) {
        xLoc += dx;
        yLoc += dy;
    }
}