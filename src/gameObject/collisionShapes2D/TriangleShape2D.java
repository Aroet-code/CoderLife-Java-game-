package gameObject.collisionShapes2D;

import java.awt.*;

public class TriangleShape2D implements CollisionShape2D{
    private Vertex v1, v2, v3;
    public TriangleShape2D(Vertex v1, Vertex v2, Vertex v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    @Override
    public void onCollision(Shape other) {

    }
}
