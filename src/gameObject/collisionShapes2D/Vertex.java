package gameObject.collisionShapes2D;

public class Vertex {
    private int x, y, z;
    public Vertex(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vertex(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
