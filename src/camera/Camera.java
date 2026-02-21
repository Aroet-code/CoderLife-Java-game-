package camera;

import gameObject.image.ImageController;
import gameObject.image.ImageScaler;
import gameObject.movement.MovingObject;
import util.ObjCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Camera extends JPanel implements Flow.Subscriber {
    private int x, y;
    private int width, height;
    private MovingObject object;
    private int size;
    private int scaleNumber;
    private LayerGroup layers;
    private ImageScaler imageScaler;
    private ImageController imageController;

    private Resolution<Integer, Integer, Integer> resolution;

    public Camera(MovingObject object, Resolution<Integer, Integer, Integer> resolution, LayerGroup layers) {
        this.resolution = resolution;
        this.object = object;
        switch (resolution.getSize()) {
            case 0 -> this.scaleNumber = 0;
            case 1 -> this.scaleNumber = 5;
            case 2 -> this.scaleNumber = 10;
            case 3 -> this.scaleNumber = 20;
            case 4 -> this.scaleNumber = 40;
            case 5 -> this.scaleNumber = 80;
            case 6 -> this.scaleNumber = 120;
            case 7 -> this.scaleNumber = 160;
            case 8 -> this.scaleNumber = 240;
            case 9 -> this.scaleNumber = 480;
        }
        this.width = this.resolution.getWidth() * this.scaleNumber;
        this.height = this.resolution.getHeight() * this.scaleNumber;
        this.setSize(this.width, this.height);
//        this.setSize(1920, 1200);
        this.layers = layers;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < layers.size(); i++) {
            SpriteBatch.draw(g, layers.get(i), this, imageScaler, imageController);
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public MovingObject getObject() {
        return object;
    }

    public void setObject(MovingObject object) {
        this.object = object;
    }

    public Resolution<Integer, Integer, Integer> getResolution() {
        return resolution;
    }

    public void setResolution(Resolution<Integer, Integer, Integer> resolution) {
        this.resolution = resolution;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Object item) {

    }

    public void setImageScaler(ImageScaler imageScaler) {
        this.imageScaler = imageScaler;
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        repaint();
        System.out.println("Notified!");
    }

    public void onResolutionChange(int size, ArrayList<Layer> layers, ImageScaler scaler) {
        System.out.println("Hell yeah! It finally does something... Although I wouldn't assume it does it the right way, haha...");
        System.out.println("The size value is now " + size + " though. Not quite sure if this would help you anyway");
        System.out.println(getObject().getX() + ", " + getObject().getY());
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        try {
//            FutureTask<Integer> task = new FutureTask<>(() -> {
                for (Layer layer : layers) {
                    scaler.scaleAnimations(this, size, layer, ObjCreator.imageController);
                    scaler.scale(this, size, layer);
                }
//                return 0;
//            }
//            );
//            executor.submit(task);
//            Integer result = task.get(50, TimeUnit.MILLISECONDS);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        this.object.setSpeed((int) (this.object.getMainSpeed() * scaler.getImageScaleMulti()));
    }

    public ImageController getImageController() {
        return imageController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }
}
