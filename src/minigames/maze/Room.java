package minigames.maze;

import java.awt.*;

class Room {
    private int width;
    private int height;
    private Point p;

    public Room(int width, int height, Point p) {
        this.width = width;
        this.height = height;
        this.p = p;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getPoint() {
        return p;
    }
}
