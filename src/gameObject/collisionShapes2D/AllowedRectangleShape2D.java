package gameObject.collisionShapes2D;

public class AllowedRectangleShape2D extends RectangleShape2D implements AllowedShape2D{
    public AllowedRectangleShape2D(Vertex centerCoordinate, int width, int height) {
        super(centerCoordinate, width, height);
    }
}
