package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AnimationObject {
    private double x, y;
    private int width, height;
    private Color color;
    private BufferedImage image;

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setY(double y){
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
