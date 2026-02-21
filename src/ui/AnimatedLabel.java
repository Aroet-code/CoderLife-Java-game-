package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedLabel extends AnimationObject {
    private String textFilePath;
    private double minimalSpeedX = 0;
    private double minimalSpeedY = 0;
    private JLabel label;
    private BufferedImage image;

    public AnimatedLabel(int x, int y, int width, int height, String text, Font font, Color color){
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.textFilePath = textFilePath;
        this.setWidth(width);
        this.setHeight(height);
        this.setX(x);
        this.setY(y);
//        this.label = new JLabel();
//        this.label.setText(text);
//        this.label.setFont(font);
//        this.label.setVerticalAlignment(SwingConstants.CENTER);
//        this.label.setHorizontalAlignment(SwingConstants.CENTER);
        BufferedImage newImg = image;
        Graphics g = newImg.getGraphics();
        g.setColor(color);
//        g.setColor(new Color(0.30f, 0.65f , 1f, 1f));
        g.setFont(font);
        g.drawString(text, 0, font.getSize());
        g.dispose();
        setImage(newImg);
    }

    public int getToCoordinate(int x, int y, float speed){
        if (Math.round(getY()) == y && Math.round(getX()) == x){
            return -1;
        }
        if (getX() - x < 0) {
            minimalSpeedX = -Math.min((getX() - x) * speed, minimalSpeedX);
        } else {
            minimalSpeedX = -Math.max((getX() - x) * speed, minimalSpeedX);
        }
        if (getY() - y < 0) {
            minimalSpeedY = -Math.min((getY() - y) * speed, minimalSpeedY);
        } else {
            minimalSpeedY = -Math.max((getY() - y) * speed, minimalSpeedY);
        }
        this.setX((getX() + minimalSpeedX));
        this.setY((getY() + minimalSpeedY));
        return 0;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
