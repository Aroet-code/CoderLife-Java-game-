package gameObject;

import camera.Layer;
import gameObject.movement.Parallaxable;

import java.awt.*;

public class Background extends GameObject implements Parallaxable {
    private int parallaxSpeed;
    public Background(Image image, int xOnTheMap, int yOnTheMap, int parallaxSpeed, Layer layer, Notificator notificator){
        this.image = image;
        this.mainImage = image;
        this.xOnTheMap = xOnTheMap;
        this.yOnTheMap = yOnTheMap;
        this.parallaxSpeed = parallaxSpeed;
        layer.add(this);
        notificator.addBackground(this);
    }

    @Override
    public int getParallaxSpeed() {
        return parallaxSpeed;
    }
}
