package ui;

import javax.swing.*;
import java.awt.*;

public class AnimatedBackground extends JPanel {
    private BackgroundAnimation animation;

    public AnimatedBackground(BackgroundAnimation animation) {
        this.animation = animation;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            if (animation != null) {
                Image image = animation.call();
                if (image != null) {
                    g.drawImage(image, 0, 0, this);
                }
                if (Main.initialized) {
                    Main.uiBatch.drawUI("Settings menu", null);
                    Main.uiBatch.drawUI("Root", null);
                    Main.uiBatch.drawContainer(g, "Root", null);
//                    Main.uiBatch.drawCollisionShapes(g);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in paintComponent:");
            e.printStackTrace();
        }
    }
}