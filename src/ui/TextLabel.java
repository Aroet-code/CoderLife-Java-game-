package ui;

import util.TextWrapper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TextLabel extends UIElement {
    public TextLabel(int startX, int startY, int targetX, int targetY, String name, String text, Font font, Color textColor, Color hoverColor, int width, int height, int xOffset, int yOffset, int yPad, String[] tags) {
        super(startX, startY, targetX, targetY, name, new HashMap<>(), null, tags);
        Image defaultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        defaultImage = TextWrapper.placeWrappedText(defaultImage, text, font, textColor, yPad, xOffset, yOffset, true);
        Image hoverImage = null;
        if (hoverColor != null){
            hoverImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            hoverImage = TextWrapper.placeWrappedText(hoverImage, text, font, hoverColor, yPad, xOffset, yOffset, true);
        }
        this.changeStateImage(UIElementState.DEFAULT, defaultImage);
        this.changeStateImage(UIElementState.HOVER, defaultImage);
        if (hoverImage != null){
            this.changeStateImage(UIElementState.HOVER, hoverImage);
        }
        this.updateCollisionShape();
    }
}
