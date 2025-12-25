package gameObject.areas;

import camera.Layer;
import gameObject.image.ImageCreator;
import gameObject.movement.MovingObject;

import java.awt.*;

public class RestrictedArea2D extends DebugArea2D{
    private final MovingObject restrictedObject;
    private final Color outsideColor = new Color(0, 0.5f, 0.3f, 0.6f);
    private final Color insideColor = new Color(0, 0.7f, 0.5f, 0.6f);

    public RestrictedArea2D(int xOnTheMap, int yOnTheMap, int width, int height, Layer layer, ImageCreator imageCreator, MovingObject object) {
        super(xOnTheMap, yOnTheMap, width, height, layer, imageCreator);
        Image image = imageCreator.createImage(this.getWidth(), this.getHeight(), insideColor, outsideColor);
        setImage(image);
        setMainImage(image);
        this.restrictedObject = object;
    }

    public MovingObject getRestrictedObject() {
        return restrictedObject;
    }
}
