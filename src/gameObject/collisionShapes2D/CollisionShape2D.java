package gameObject.collisionShapes2D;

public interface CollisionShape2D {
    boolean intersects(CollisionShape2D other);
    boolean contains(Vertex v);
    Vertex getCenterCoordinates();
    void setCenterCoordinates(Vertex coordinates);
    int getWidth();
    int getHeight();
    Vertex[] getPoints();
}
