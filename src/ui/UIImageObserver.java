package ui;

import java.awt.*;
import java.awt.image.ImageObserver;

public class UIImageObserver implements ImageObserver {
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if (infoflags == ALLBITS) {
            return false;
        } else if (infoflags == ERROR) {
            System.out.println("An error occurred while trying to load an image");
            return true;
        }
        return true;
    }
}
