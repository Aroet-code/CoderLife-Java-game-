package gameObject.interactions;

import camera.AnimationController;
import camera.Layer;
import gameObject.areas.DebugArea2D;
import gameObject.GameObject;
import gameObject.image.ImageCreator;

import java.awt.*;
import java.util.ArrayList;

public class InteractableObject extends GameObject implements Interactable {
    private boolean interactable = true;
    private InteractableArea2D interactableArea2D;
    private AnimationController animationController;
    public InteractableObject(Image image, int xOnTheMap, int yOnTheMap, boolean interactable, Layer layer, int areaWidth, int areaHeight, ImageCreator imageCreator, Layer areaLayer, ArrayList<InteractableArea2D> list, AnimationController animationController) {
        this.setImage(image);
        this.setMainImage(image);
        this.setXOnTheMap(xOnTheMap);
        this.setYOnTheMap(yOnTheMap);
        this.interactable = interactable;
        this.animationController = animationController;
        interactableArea2D = new InteractableArea2D(getCenterXOnTheMap() - (areaWidth / 2), getCenterYOnTheMap() - (areaHeight / 2), areaWidth, areaHeight,
                areaLayer, imageCreator, this, list);
        layer.add(this);
    }

    @Override
    public void onInteract() {
        if (interactable) {
            animationController.receiveObject(this);
            System.out.println("And with me too!!!");
        } else {
            System.out.println("But not with me!");
        }
    }

    @Override
    public boolean canInteract() {
        return interactable;
    }

    @Override
    public void calculateInteractable(DebugArea2D interactingArea) {

    }

    @Override
    public void onMove(DebugArea2D playerArea) {

    }

    public InteractableArea2D getArea() {
        return this.interactableArea2D;
    }

    public void setArea(InteractableArea2D interactableArea2D) {
        this.interactableArea2D = interactableArea2D;
    }

    //    public void setInteractable(boolean interactable) {
//        this.interactable = interactable;
//    }
}
