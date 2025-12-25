package gameObject.image;

import camera.Camera;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;

// How would this system work? First of all, we have all the images and the coordinates data in DrawObject class
// Second of all, we have an ImageController class that also acts like an ImageObserver
// There would be another class that would help to determine which objects to draw based on the distance between the player and the object
// This class' purpose is to transfer that DrawObjects data to the image scaler that would scale them
// And store the scaled images as well, transferring that data to the SpriteBatch class that would draw the images

public class ImageController implements ImageObserver {
    private Map<String, DrawObject> drawObjects = new HashMap<>();
    private Map<String, DrawObject> renderedDrawObjects = new HashMap<>();
    private final ImageScaler imageScaler;

    public ImageController(ImageScaler imageScaler, Camera camera) {
        this.imageScaler = imageScaler;
        camera.setImageController(this);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if (infoflags == ImageObserver.ALLBITS){
            System.out.println(infoflags);
            return false;
        } else {
            System.out.println(infoflags);
            return true;
        }
    }

    public void addDrawObject(String name, DrawObject drawObject){
        drawObjects.put(name, drawObject);
    }

    public void renderImages(String[] keys, Camera camera){
        imageScaler.scale(drawObjects, renderedDrawObjects, keys, camera.getResolution().getScaleNumber(),
                camera, camera.getResolution().getSize());
    }

    public void updateImage(String key, Image image){
        if (drawObjects.containsKey(key)){
            DrawObject oldValue = drawObjects.get(key);
            drawObjects.replace(key, new DrawObject(oldValue.getName(), oldValue.getVertex(), image));
        } else {
            System.out.println("The hashmap doesn't contain that key: " + key);
        }
    }
}
