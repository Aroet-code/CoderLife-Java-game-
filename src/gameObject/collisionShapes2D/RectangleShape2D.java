package gameObject.collisionShapes2D;
import gameObject.movement.MovingObject;

import java.awt.*;

abstract class RectangleShape2D extends Rectangle implements CollisionShape2D {
    MovingObject parent;

    public MovingObject getParent(){
        return this.parent;
    }
    public void setParent(MovingObject parent){
        this.parent = parent;
    }

}
