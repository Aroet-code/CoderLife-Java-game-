package gameObject.interactions;

import gameObject.GameObject;

public class InteractingObject extends GameObject {
    private InteractableObject object;
    private InteractingArea2D area;

    public InteractableObject getInteractableObject() {
        return object;
    }

    public void setInteractableObject(InteractableObject object) {
        System.out.println("Object successfully set!");
        this.object = object;
    }

    public void interact() throws InteractionErrorException {
        System.out.println("This thing works!");
        try {
            if (!(object == null)) {
                if ( !(object.getArea().canInteract(this.getArea()))) {
                    throw new InteractionErrorException("Can't interact with the object");
                } else {
                    object.onInteract();
                }
            } else {
                throw new InteractionErrorException("The object is null.");
            }
        } catch (InteractionErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    public InteractingArea2D getArea() {
        return area;
    }

    public void setArea(InteractingArea2D area) {
        this.area = area;
    }
}
