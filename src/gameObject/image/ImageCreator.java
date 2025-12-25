package gameObject.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCreator {
    public Image createImage(int width, int height, Color insideColor, Color outsideColor){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setColor(insideColor);
        g.fillRect(0, 0, width, height);
        g.setColor(outsideColor);
        g.drawRect(0, 0, width - 1, height - 1);
        g.dispose();
        return image;
    }
}
