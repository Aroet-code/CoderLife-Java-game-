package gameObject.interactions;

import gameObject.areas.DebugArea2D;
import gameObject.image.ImageCreator;
import camera.Layer;

import java.awt.*;
import java.util.ArrayList;

public class InteractableArea2D extends DebugArea2D implements Interactable{

    public InteractableArea2D(int xOnTheMap, int yOnTheMap, int width, int height, Layer layer, ImageCreator imageCreator, InteractableObject interactableObject, ArrayList<InteractableArea2D> list) {
        super(xOnTheMap, yOnTheMap, width, height, layer, imageCreator);
        this.setObject(interactableObject);
        list.add(this);
    }

    @Override
    public Color getInsideColor() {
        return null;
    }

    @Override
    public Color getOutsideColor() {
        return null;
    }

    @Override
    public void setOutsideColor(Color color) {

    }

    @Override
    public void setInsideColor(Color color) {

    }

    public void onCollision(InteractingArea2D object) {
        System.out.println("I've just collided with the player! YAY");
    }

    @Override
    public void onInteract() {
        System.out.println("A player has just interacted with me");
        getObject().onInteract();
    }

    @Override
    public boolean canInteract() {
        return false;
    }

    @Override
    public void calculateInteractable(DebugArea2D playerArea) {
        if (!this.intersects(playerArea)) {
            throw new InteractionErrorException("The object is too far away");
        }
    }


    @Override
    public void onMove(DebugArea2D playerArea) {

    }

    public boolean canInteract(InteractingArea2D other) {
        if (Math.abs(other.getCenterXOnTheMap() - this.getCenterXOnTheMap()) < (getWidth() / 2) + (other.getWidth() / 2)
                && Math.abs(other.getCenterYOnTheMap() - this.getCenterYOnTheMap()) < (getHeight() / 2) + (other.getHeight() / 2))
        {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public InteractableObject getObject() {
        return super.getObject();
    }

    @Override
    public void setObject(InteractableObject object) {
        super.setObject(object);
    }
}
