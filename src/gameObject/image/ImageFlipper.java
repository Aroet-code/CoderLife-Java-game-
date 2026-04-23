package gameObject.image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageFlipper {
    public static Image getRotatedInstance(Image image, ImageRotationFlag flag){
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = resultImage.createGraphics();

        AffineTransform tx = new AffineTransform();

        switch (flag){
            case CW_90, CCW_270 -> {

            }
            case CW_180, CCW_180 -> {

            }
            case CW_270, CCW_90 -> {

            }
        }

        return resultImage;
    }

    public static BufferedImage toBufferedImage(Image image){
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage resultImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resultImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return resultImage;
    }
}
