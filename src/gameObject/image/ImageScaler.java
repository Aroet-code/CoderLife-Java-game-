package gameObject.image;//package gameObject.image;
//
//import camera.Camera;
//import camera.Layer;
//import camera.Resolution;
//import camera.Screen;
//import gameObject.GameObject;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;
//import java.text.AttributedCharacterIterator;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.concurrent.*;
//
//import gameObject.collisionShapes2D.Vertex;
//import gameObject.movement.MovingObject;
//
//import javax.swing.*;
//
//public class ImageScaler {
//    private float ImageScaleMulti = 1.0f;
//    private Resolution resolution;
//
//    public ImageScaler(Screen screen){
//        screen.setImageScaler(this);
//        this.resolution = camera.getResolution();
//    }
//
//    public void scale(Camera camera, int size, Layer layer){
//        float imageScaleNumber = resolution.getScaleNumber();
//        camera.setResolution(new Resolution<>(camera.getResolution().getWidth(),
//                camera.getResolution().getHeight(), size));
//        float imageScaleMulti = camera.getResolution().getScaleNumber() / imageScaleNumber;
//        System.out.println(camera.getResolution().getScaleNumber() + " / " + imageScaleNumber);
//        MovingObject player = camera.getObject();
//        int centerX = (camera.getWidth() / 2) - (player.getWidth() / 2);
//        int centerY = (camera.getHeight() / 2) - (player.getHeight() / 2);
//        for (int i = 0; i<layer.size(); i++){
//            GameObject gameObject = layer.get(i);

import controllers.ImageController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;

////            if (positive){
////                System.out.println("positive");
////                gameObject.x = (int) (gameObject.getX() * imageScaleMulti);
////                gameObject.y = (int) (gameObject.getY() * imageScaleMulti);
////            } else {
////                System.out.println("negative");
////                gameObject.x = (int) (gameObject.getX() * imageScaleMulti);
////                gameObject.y = (int) (gameObject.getY() * imageScaleMulti);
////            }
//            int x = centerX + (int)((gameObject.getXOnTheMap() - player.getXOnTheMap()) * imageScaleMulti);
//            int y = centerY + (int)((gameObject.getYOnTheMap() - player.getYOnTheMap()) * imageScaleMulti);
//            Image scaledInstance = gameObject.getMainImage().getScaledInstance((int) (gameObject.getMainImage().getWidth(null) * imageScaleMulti),
//                    (int) (gameObject.getMainImage().getHeight(null) * imageScaleMulti), Image.SCALE_SMOOTH);
//            gameObject.setImage(scaledInstance);
//            if (gameObject != player) {
//                gameObject.setX(x);
//                gameObject.setY(y);
//            } else {
//                gameObject.setX((camera.getWidth() / 2) - (scaledInstance.getWidth(null) / 2));
//                gameObject.setY((camera.getHeight() / 2) - (scaledInstance.getHeight(null) / 2));
//            }
//        }
//        ImageScaleMulti = imageScaleMulti;
//    }
//
//    public void scaleAnimations(Camera camera, int size, Layer layer, ImageObserver observer){
//        float imageScaleNumber = resolution.getScaleNumber();
//        camera.setResolution(new Resolution<>(camera.getResolution().getWidth(),
//                camera.getResolution().getHeight(), size));
//        float imageScaleMulti = camera.getResolution().getScaleNumber() / imageScaleNumber;
//        System.out.println(camera.getResolution().getScaleNumber() + " / " + imageScaleNumber);
//        for (int i = 0; i<layer.size(); i++) {
//            GameObject gameObject = layer.get(i);
//            if (gameObject.getCurrentAnimation() != null){
//                ArrayList<Image> scaledImages = new ArrayList<>();
//                ArrayList<Image> animationImages = gameObject.getCurrentAnimation().getMainImages();
//                ExecutorService executor = Executors.newSingleThreadExecutor();
//                BufferedImage helpImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//                Graphics2D g = helpImage.createGraphics();
//                    for (Image img : animationImages){
//                        FutureTask<Image> task = new FutureTask<>(() ->
//                        {Image scaledInstance = (img.getScaledInstance((int) (img.getWidth(observer) * imageScaleMulti),
//                                (int) (img.getHeight(observer) * imageScaleMulti), Image.SCALE_FAST));
//                        return scaledInstance; });
//                        executor.submit(task);
//                        Image result = null;
//                        try {
//                            result = task.get(50, TimeUnit.MILLISECONDS);
//                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        g.drawImage(result, 0, 0, observer);
//                        scaledImages.add(result);
//                    }
//                gameObject.getCurrentAnimation().setImages(new ArrayList<>(scaledImages));
//                executor.close();
//            }
//        }
//        ImageScaleMulti = imageScaleMulti;
//    }
//
//    public float getImageScaleMulti() {
//        return ImageScaleMulti;
//    }
//
//    public Image scale(Image image, float imageScaleMulti) {
//        return image.getScaledInstance((int) (image.getWidth(null) * imageScaleMulti),
//                (int) (image.getHeight(null) * imageScaleMulti), Image.SCALE_SMOOTH);
//    }
//
//    public void scale(Map<String, DrawObject> allObjects, Map<String, DrawObject> renderedObjects, String[] keys, float imageScaleMulti, Camera camera, int size){
//        DrawObject[] drawObjects = new DrawObject[keys.length];
//        for (int i = 0; i < keys.length; i++){
//            drawObjects[i] = allObjects.get(keys[i]);
//        }
//        drawObjects = scaleVectors(drawObjects, camera, size);
//        for (DrawObject drawObject : drawObjects){
//            drawObject.setImage(scale(drawObject.getImage(), imageScaleMulti));
//        }
//        renderedObjects.clear();
//        for (int i = 0; i < keys.length; i++){
//            renderedObjects.put(keys[i], drawObjects[i]);
//        }
//    }
//
//    public DrawObject[] scaleVectors(DrawObject[] drawObjects, Camera camera, int size){
//        float imageScaleNumber = resolution.getScaleNumber();
//        camera.setResolution(new Resolution<>(camera.getResolution().getWidth(),
//                camera.getResolution().getHeight(), size));
//        float imageScaleMulti = camera.getResolution().getScaleNumber() / imageScaleNumber;
//        float actualImageScaleNumber = imageScaleMulti / ImageScaleMulti;
//        for (DrawObject drawObject : drawObjects){
//            Vertex drawObjectVertex = drawObject.getVertex();
//            int xDiff = drawObjectVertex.getX() - (camera.getWidth() / 2);
//            int yDiff = drawObjectVertex.getY() - (camera.getHeight() / 2);
//            Vertex newVertex = new Vertex((int) ((camera.getWidth() / 2) + (xDiff * actualImageScaleNumber)), (int)((camera.getHeight() / 2) + (yDiff * actualImageScaleNumber)));
//            drawObject.setVertex(newVertex);
//        }
//        ImageScaleMulti = imageScaleMulti;
//        return drawObjects;
//    }
//}

public class ImageScaler {
    private float imageScalingMultiplier = 1.0f;
    private float lastScaledMultiplier = 0f;
    private ImageController imageController;

    public ImageScaler(ImageController imageController){
        this.imageController = imageController;
    }

    public float getImageScalingMultiplier() {
        return imageScalingMultiplier;
    }

    public static Image scaleImage(Image image, float imageScalingMultiplier, ImageObserver observer){
        return image.getScaledInstance((int) (image.getWidth(observer) * imageScalingMultiplier),
                (int) (image.getHeight(observer) * imageScalingMultiplier), Image.SCALE_SMOOTH);
    }

    public void scaleImagesIfNeeded(){
        if (imageScalingMultiplier == lastScaledMultiplier){
            return;
        }
        scaleImagesAndSendToImageController();
        lastScaledMultiplier = imageScalingMultiplier;
    }

    public void scaleImagesAndSendToImageController() {
        String[] keys = imageController.getImagePackages().keySet().toArray(new String[0]);
        Map<String, GameObjectImagePackage> packages = new HashMap<>(imageController.getImagePackages());
        BufferedImage helperImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = helperImage.createGraphics();
        for (String key : keys){
            GameObjectImagePackage pkg = packages.get(key);
            Image[] scaledAnimation = null;
            if (pkg.animationImages() != null) {
                scaledAnimation = new Image[pkg.animationImages().length];
                for (int i = 0; i < pkg.animationImages().length; i++) {
                    scaledAnimation[i] = scaleImage(pkg.animationImages()[i], imageScalingMultiplier, imageController);
                    g.drawImage(scaledAnimation[i], 0, 0, imageController);
                }
            }
            Image scaledDefaultImage = null;
            if (pkg.defaultImage() != null){
            scaledDefaultImage = scaleImage(pkg.defaultImage(), imageScalingMultiplier, imageController);
            }
            Image scaledFinalImage = null;
            if (pkg.finalImage() != null) {
                scaledFinalImage = scaleImage(pkg.finalImage(), imageScalingMultiplier, imageController);
            }
            imageController.updateScaledImagePackage(key, new GameObjectImagePackage(scaledAnimation, scaledDefaultImage, scaledFinalImage));
        }
        imageController.replaceAllRenderedImages();
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    public void expand(){
        this.imageScalingMultiplier *= 2;
        if (imageScalingMultiplier > 8){
            this.imageScalingMultiplier = 8;
        }
    }

    public void shrink(){
        this.imageScalingMultiplier /= 2;
        if (imageScalingMultiplier < 1f){
            this.imageScalingMultiplier = 1f;
        }
    }
}
