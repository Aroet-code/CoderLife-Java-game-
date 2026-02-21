package camera;

import ui.Main;
import util.ObjCreator;
import util.Scene;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Screen extends JFrame {
    private int scaleNumber;
    private JPanel scenes = new JPanel(new CardLayout());
    public Screen(Resolution<Integer, Integer, Integer> resolution) {
        switch (resolution.getSize()){
            case 0 -> scaleNumber = 0;
            case 1 -> scaleNumber = 5;
            case 2 -> scaleNumber = 10;
            case 3 -> scaleNumber = 20;
            case 4 -> scaleNumber = 40;
            case 5 -> scaleNumber = 80;
            case 6 -> scaleNumber = 120;
            case 7 -> scaleNumber = 160;
            case 8 -> scaleNumber = 240;
            case 9 -> scaleNumber = 480;
        }
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()){
            this.setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            this.setExtendedState(MAXIMIZED_BOTH);
            this.setUndecorated(true);
        }
        setVisible(true);
        setSize(resolution.getWidth() * scaleNumber, resolution.getHeight() * scaleNumber);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(scenes);
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
            this.repaint();
            this.revalidate();
        });
    }

    public void addPanel(Scene scene, JPanel panel){
        scenes.add(panel, scene.toString());
    }

    public void displayScene(Scene scene){
        CardLayout cards = (CardLayout) scenes.getLayout();
        cards.show(scenes, String.valueOf(scene));
    }
}
