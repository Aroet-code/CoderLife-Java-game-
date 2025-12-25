package gameObject.areas;

import java.awt.*;

public interface Area2D {
    int getX();
    void setX(int x);
    int getY();
    void setY(int y);
    int getXOnTheMap();
    void setXOnTheMap(int xOnTheMap);
    int getYOnTheMap();
    void setYOnTheMap(int yOnTheMap);
    int getWidth();
    void setWidth(int width);
    int getHeight();
    void setHeight(int height);
    Color getInsideColor();
    Color getOutsideColor();
    void setOutsideColor(Color color);
    void setInsideColor(Color color);
    boolean hasTag(String tag);
    default int getCenterX(){
        return (int) (getX() + (getWidth() / 2.0));
    }
    void setCenterX(int centerX);
    default int getCenterY(){
        return (int) (getY() + (getHeight() / 2.0));
    }
    void setCenterY(int centerY);
    default boolean intersects(Area2D other){
        if (other != null) {
            return getXOnTheMap() < other.getXOnTheMap() + other.getWidth() && getXOnTheMap() + getWidth() > other.getXOnTheMap() &&
                    getYOnTheMap() > other.getYOnTheMap() + other.getHeight() && getYOnTheMap() + getHeight() < other.getYOnTheMap();
        }
        else {
            System.out.println("The area that is supposed to intersect something there is null");
            return false;
        }
    }
}
