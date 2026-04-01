package gameObject.collisionShapes2D;

public class RectangleShape2D implements CollisionShape2D {
    Vertex centerCoordinate;
    int width;
    int height;

    public RectangleShape2D(Vertex centerCoordinate, int width, int height) {
        this.centerCoordinate = centerCoordinate;
        this.width = width;
        this.height = height;
    }

    public RectangleShape2D(int xTopLeft, int yTopLeft, int width, int height){
        this.width = width;
        this.height = height;
        this.centerCoordinate = new Vertex(xTopLeft + (width / 2), yTopLeft + (height / 2));
    }

    @Override
    public boolean intersects(CollisionShape2D other) {
        if (other instanceof RectangleShape2D){
            return this.intersects((RectangleShape2D) other);
        }
        System.out.println("The actual shape of the object is unknown");
        return false;
    }

    @Override
    public boolean contains(Vertex v) {
        return (Math.abs(v.getX() - centerCoordinate.getX()) < width / 2)
                && (Math.abs(v.getY() - centerCoordinate.getY()) < height / 2);
    }

    public boolean intersects(RectangleShape2D other){
        return (this.getCenterCoordinates().getX() - (getWidth() / 2) < other.getCenterCoordinates().getX() + (other.getWidth() / 2))
                && (this.getCenterCoordinates().getX() + (getWidth() / 2) > other.getCenterCoordinates().getX() - (other.getWidth() / 2))
                && (this.getCenterCoordinates().getY() - (getHeight() / 2) < other.getCenterCoordinates().getY() + (other.getHeight() / 2))
                && (this.getCenterCoordinates().getY() + (getHeight() / 2) > other.getCenterCoordinates().getY() - (other.getHeight() / 2));
    }

    @Override
    public Vertex getCenterCoordinates() {
        return centerCoordinate;
    }

    @Override
    public void setCenterCoordinates(Vertex centerCoordinate) {
        this.centerCoordinate = centerCoordinate;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
