package gameObject.collisionShapes2D;

public class Vector {
    private Vertex v1, v2;
    private int projX, projY;

    public Vector(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
        this.projX = v2.getX() - v1.getX();
        this.projY = v2.getY() - v1.getY();
    }

    public Vector getMultipliedVector(float scale){
        Vertex v2 = new Vertex((int) (this.projX * scale), (int) (this.projY * scale));
        return new Vector(this.v1, v2);
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public int getProjX() {
        return projX;
    }

    public int getProjY() {
        return projY;
    }
}
