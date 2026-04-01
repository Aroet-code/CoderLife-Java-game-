package gameObject.collisionShapes2D;

public class RestrictedRectangleShape2D extends RectangleShape2D implements RestrictedShape2D{
    public RestrictedRectangleShape2D(Vertex centerCoordinate, int width, int height) {
        super(centerCoordinate, width, height);
    }
}
