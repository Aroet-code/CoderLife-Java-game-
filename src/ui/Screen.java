package ui;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame {
    public Screen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()){
            this.setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            this.setExtendedState(MAXIMIZED_BOTH);
            this.setUndecorated(true);
        }
//        this.setSize(900, 900);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
            this.repaint();
            this.revalidate();
        });
    }
}
