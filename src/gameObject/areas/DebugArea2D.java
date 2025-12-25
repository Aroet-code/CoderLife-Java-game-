package gameObject.areas;

import camera.Layer;
import gameObject.GameObject;
import gameObject.image.ImageCreator;
import gameObject.interactions.InteractableObject;
import gameObject.movement.Direction;
import gameObject.movement.MovingObject;

import java.awt.*;

public class DebugArea2D extends GameObject implements Area2D, MovingObject{
    private Color insideColor = new Color(0.5f, 0f, 0.5f, 0.6f);
    private Color outsideColor = new Color(0.5f, 0f, 0.5f);
    private InteractableObject object = null;
    private MovingObject followObject;
    private int speed, mainSpeed;

    public DebugArea2D(MovingObject object, int width, int height, Layer layer, ImageCreator imageCreator){
        Image image = imageCreator.createImage(width, height, insideColor, outsideColor);
        this.setMainImage(image);
        this.setImage(image);
        setWidth(width);
        setHeight(height);
        setCenterXOnTheMap(object.getCenterXOnTheMap());
        setCenterYOnTheMap(object.getCenterYOnTheMap());
        setSpeed(object.getSpeed());
        setMainSpeed(object.getMainSpeed());
        this.object = null;
        this.followObject = object;
        layer.add(this);
        this.addTag("FollowPlayer");
    }

    public DebugArea2D(int xOnTheMap, int yOnTheMap, int width, int height, Layer layer, ImageCreator imageCreator){
        Image image = imageCreator.createImage(width, height, insideColor, outsideColor);
        this.setMainImage(image);
        this.setImage(image);
        setXOnTheMap(xOnTheMap);
        setYOnTheMap(yOnTheMap);
        setWidth(width);
        setHeight(height);
        this.object = null;
        this.followObject = null;
        layer.add(this);
    }

    @Override
    public int getWidth() {
        return getImage().getWidth(null);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public int getHeight() {
        return getImage().getHeight(null);
    }

    @Override
    public MovingObject getFollowObject() {
        return this.followObject;
    }

    @Override
    public void setObject(MovingObject object) {

    }

    @Override
    public void move(Direction direction) {
        MovingObject.super.move(direction);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    public Color getInsideColor() {
        return insideColor;
    }

    @Override
    public Color getOutsideColor() {
        return outsideColor;
    }

    @Override
    public void setOutsideColor(Color color) {
        outsideColor = color;
    }

    @Override
    public void setInsideColor(Color color) {
        insideColor = color;
    }

    @Override
    public int getMainSpeed() {
        return mainSpeed;
    }

    @Override
    public void setMainSpeed(int mainSpeed) {
        this.mainSpeed = mainSpeed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public InteractableObject getObject() {
        return object;
    }

    public void setObject(InteractableObject object) {
        this.object = object;
    }
}
