package gameObject.interactions;

import gameObject.areas.DebugArea2D;
import gameObject.image.ImageCreator;
import camera.Layer;
import gameObject.movement.MovingObject;

public class InteractingArea2D extends DebugArea2D {
    private InteractingObject object;
    public InteractingArea2D(MovingObject object, int width, int height, Layer layer, ImageCreator imageCreator, InteractingObject interactingObject) {
        super(object, width, height, layer, imageCreator);
        interactingObject.setArea(this);
        this.object = interactingObject;
    }

    public void onCollision(InteractableArea2D other){
        System.out.println("I am the player area and i've just collided with some sort of area");
        if (!(other.getObject() == null)){
            this.object.setInteractableObject(other.getObject());
        } else {
            System.out.println("The interactableArea's object == !!!" + other.getObject().toString());
        }
    }

    public InteractingObject getInteractingObject() {
        return object;
    }

    public void setInteractingObject(InteractingObject object) {
        this.object = object;
    }
}
