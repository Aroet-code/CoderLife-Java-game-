package controllers;

import gameObject.GameObjectState;
import gameObject.image.*;
import gameObject.movement.Direction;
import gameObject.movement.SetDirectionCommand;
import util.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;

//// Never mind. This system would work differently.
// How would this system work? First of all, we have all the images and the coordinates data in DrawObject class
// Second of all, we have an ImageController class that also acts like an ImageObserver
// There would be another class that would help to determine which objects to draw based on the distance between the player and the object
// This class' purpose is to transfer that DrawObjects data to the image scaler that would scale them
// And store the scaled images as well, transferring that data to the SpriteBatch class that would draw the images

public class ImageController implements ImageObserver {
    private final Map<String, GameObjectState> states = new HashMap<>();
    private final Map<String, Image> renderedImages = new HashMap<>();
    private Map<String, Integer> frameNumbers = new HashMap<>();
    private Map<String, GameObjectImagePackage> images = new HashMap<>();
    private Map<String, GameObjectImagePackage> scaledImages = new HashMap<>();

//    private Map<String, DrawObject> drawObjects = new HashMap<>();
//    private Map<String, DrawObject> renderedDrawObjects = new HashMap<>();
//    private final ImageScaler imageScaler;

    public ImageController() {

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if (infoflags == ImageObserver.ALLBITS){
//            System.out.println(infoflags);
            return false;
        } else {
//            System.out.println(infoflags);
            return true;
        }
    }

//    public void addDrawObject(String name, DrawObject drawObject){
//        drawObjects.put(name, drawObject);
//    }
//
//    public void renderImages(String[] keys, Camera camera){
//        imageScaler.scale(drawObjects, renderedDrawObjects, keys, camera.getResolution().getScaleNumber(),
//                camera, camera.getResolution().getSize());
//    }
//
//    public void updateImage(String key, Image image){
//        if (drawObjects.containsKey(key)){
//            DrawObject oldValue = drawObjects.get(key);
//            drawObjects.replace(key, new DrawObject(oldValue.getVertex(), image));
//        } else {
//            System.out.println("The hashmap doesn't contain that key: " + key);
//        }
//    }

    public void executeCommand(SetDirectionCommand command){
        String key = command.key();
        Direction direction = command.direction();

    }

    public int updateGameObjectImage(ImageUpdateCommand imageUpdateCommand){
        if (scaledImages.isEmpty()){
            scaledImages = new HashMap<>(images);
        }
        synchronized (renderedImages) {
            String key = imageUpdateCommand.key();
            if (imageUpdateCommand.direction() != null){
                switch (imageUpdateCommand.direction()){
                    case LEFT -> {
                        key += "-left";
                    }
                    case RIGHT -> {
                        key += "-right";
                    }
                }
            }
            Image image = null;
            Graphics g = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB).createGraphics();
            int returnValue = 0;
            switch (imageUpdateCommand.flag()) {
                case NEXT_IMAGE -> {
                    int frameNumber = frameNumbers.get(key);
                    if (frameNumber >= scaledImages.get(key).animationImages().length) {
                        image = scaledImages.get(key).finalImage();
                        returnValue = 1;
                        frameNumbers.replace(key, 0);
                    } else {
                        image = scaledImages.get(key).animationImages()[frameNumber];
                        frameNumbers.replace(key, frameNumbers.get(key) + 1);
                    }
                }
                case NEXT_IMAGE_REPEATABLE -> {
                    int frameNumber = frameNumbers.get(key);
                    if (frameNumber >= scaledImages.get(key).animationImages().length) {
                        image = scaledImages.get(key).animationImages()[0];
                        frameNumbers.replace(key, 0);
                    } else {
                        image = scaledImages.get(key).animationImages()[frameNumber];
                        frameNumbers.replace(key, frameNumbers.get(key) + 1);
                    }
                }
            }
            renderedImages.replace(imageUpdateCommand.key(), image);
            return returnValue;
        }
    }

//    public DrawObject getRenderedObject(String key){
//        if (renderedDrawObjects.containsKey(key)){
//            return renderedDrawObjects.get(key);
//        } else {
//            throw new NoImageException("The image is not rendered.");
//        }
//    }

    public Image getRenderedImage(String key){
        if (renderedImages.containsKey(key)){
            return renderedImages.get(key);
        } else {
            throw new NoImageException("The image is not rendered. Key: " + key);
        }
    }

    public void replaceAllRenderedImages(){
        for (String key : renderedImages.keySet()){
            replaceRenderedImage(key);
        }
    }

    public void replaceRenderedImage(String key){
        if (scaledImages.isEmpty()){
            scaledImages = new HashMap<>(images);
        }
        GameObjectState state = states.get(key);
        Image img = null;
//        System.out.println("Trying to get the scaled instance of " + key);
        switch(state){
            case FINAL -> {
                img = scaledImages.get(key).finalImage();
            }
            case DEFAULT -> {
                img = scaledImages.get(key).defaultImage();
            }
            case ANIMATING -> {
                return;
            }
        }
        if (img == null){
            return;
        }
        renderedImages.replace(key, img);
    }

    public void renderImages(){
        Graphics g = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB).createGraphics();
        for (GameObjectImagePackage pkg : scaledImages.values()){
            if (pkg.animationImages() != null) {
                for (Image animationImage : pkg.animationImages()){
                    g.drawImage(animationImage, 0, 0, this);
                }
            }
            if (pkg.defaultImage() != null){
                g.drawImage(pkg.defaultImage(), 0, 0, this);
            }
            if (pkg.finalImage() != null){
                g.drawImage(pkg.finalImage(), 0, 0, this);
            }
        }
    }

    public Map<String, GameObjectImagePackage> getImagePackages() {
        return images;
    }

    public void updateScaledImagePackage(String key, GameObjectImagePackage gameObjectImagePackage){
        scaledImages.replace(key, gameObjectImagePackage);
    }

    public void addImagePackage(String key, GameObjectImagePackage gameObjectImagePackage){
        images.put(key, gameObjectImagePackage);
        renderedImages.put(key, gameObjectImagePackage.defaultImage());
        if (gameObjectImagePackage.animationImages() != null){
            frameNumbers.put(key, 0);
        }
        states.put(key, GameObjectState.DEFAULT);
    }

    public void changeState(String key, GameObjectState state){
        states.replace(key, state);
    }

    public GameObjectState getState(String key){
        return states.get(key);
    }

    public void replacePackage(String key, String replaceKey){
        images.put(key, images.get(replaceKey));
    }
}
