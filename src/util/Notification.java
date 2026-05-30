package util;

public class Notification {
    private int x, y;
    private String text;

    public Notification(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
