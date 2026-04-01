package ui;

import javax.swing.*;
import java.awt.*;

public class AnimatedBackground extends JPanel {

    private BackgroundAnimation animation;

    public AnimatedBackground() {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        try {
//            System.out.println("PaintComponent call.");
            if (animation != null) {
                Image image = animation.call();
                if (image != null) {
                    g.drawImage(image, 0, 0, this);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in paintComponent:");
            e.printStackTrace();
        }
    }

    public BackgroundAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(BackgroundAnimation animation) {
        this.animation = animation;
    }
}