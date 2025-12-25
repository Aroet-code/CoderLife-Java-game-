package gameObject.image;

import gameObject.collisionShapes2D.Vertex;

import java.awt.*;

public class DrawObject {
    private Image image;
    private Vertex vertex;
    private final String name;

    public DrawObject(String name, Vertex vertex, Image image) {
        this.name = name;
        this.vertex = vertex;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public String getName() {
        return name;
    }
}
