package gameObject;

import camera.Layer;
import gameObject.image.ImageCreator;
import gameObject.interactions.InteractingArea2D;
import gameObject.interactions.InteractingObject;
import gameObject.movement.MovingObject;

import java.awt.*;
import java.util.ArrayList;

public class Player extends InteractingObject implements MovingObject{
    private int speed;
    private int mainSpeed;
    private InteractingArea2D area;

    public Player(int x, int y, Image image, int speed, ArrayList<GameObject> layer, int areaWidth, int areaHeight, Layer areaLayer, ImageCreator imageCreator) {
        this.setX(x);
        this.setY(y);
        this.setXOnTheMap(x);
        this.setYOnTheMap(y);
        this.setMainImage(image);
        this.setImage(image);
        this.speed = speed;
        this.mainSpeed = speed;
        area = new InteractingArea2D(this, areaWidth, areaHeight, areaLayer, imageCreator, this);
        setArea(area);
        layer.add(this);
    }

    public int getMainSpeed() {
        return mainSpeed;
    }

    @Override
    public void setMainSpeed(int mainSpeed) {
        this.mainSpeed = mainSpeed;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getWidth() {
        return this.getImage().getWidth(null);
    }

    @Override
    public int getHeight() {
        return this.getImage().getHeight(null);
    }

    @Override
    public MovingObject getFollowObject() {
        return null;
    }

    @Override
    public void setObject(MovingObject object) {

    }
}
