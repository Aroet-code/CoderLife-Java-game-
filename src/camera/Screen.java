package camera;

import javax.swing.*;

public class Screen extends JFrame {
    private int scaleNumber;
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
        setVisible(true);
        setSize(resolution.getWidth() * scaleNumber, resolution.getHeight() * scaleNumber);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
