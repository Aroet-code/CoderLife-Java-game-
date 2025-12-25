package gameObject.movement;

import gameObject.interactions.InteractableArea2D;
import gameObject.interactions.InteractingArea2D;
import gameObject.Notificator;

import java.util.ArrayList;

public interface MovingObject{
    int DEFAULT_SPEED = 5;

    int getMainSpeed();
    void setMainSpeed(int mainSpeed);
    int getSpeed();
    void setSpeed(int speed);
    int getX();
    void setX(int x);
    int getY();
    void setY(int y);
    int getWidth();
    int getHeight();
    int getXOnTheMap();
    void setXOnTheMap(int xOnTheMap);
    int getYOnTheMap();
    void setYOnTheMap(int yOnTheMap);
    default int getCenterX(){
        return (int) (getX() + (getWidth() / 2.0));
    }
    void setCenterX(int centerX);
    default int getCenterY(){
        return (int) (getY() + (getHeight() / 2));
    }
    void setCenterY(int centerY);
    default int getCenterXOnTheMap(){
        return getXOnTheMap() + (getWidth() / 2);
    }
    default int getCenterYOnTheMap(){
        return getYOnTheMap() + (getHeight() / 2);
    }
    MovingObject getFollowObject();
    void setObject(MovingObject object);
//    default void moveRight(GameObject.Notificator publisher){
//        setXOnTheMap(getXOnTheMap() + getSpeed());
//        publisher.update();
//    };
//    default void moveLeft(GameObject.Notificator publisher){
//        setXOnTheMap(getXOnTheMap() - getSpeed());
//        publisher.update();
//    };
//    default void moveUp(GameObject.Notificator publisher){
//        setYOnTheMap(getYOnTheMap() - getSpeed());
//        publisher.update();
//    };
//    default void moveDown(GameObject.Notificator publisher){
//        setYOnTheMap(getYOnTheMap() + getSpeed());
//        publisher.update();
//    };
    default void move(Direction direction, Notificator publisher, InteractingArea2D playerArea, ArrayList<InteractableArea2D> interactableAreas){
        switch (direction){
            case UP -> setYOnTheMap(getYOnTheMap() - getSpeed());
            case RIGHT -> setXOnTheMap(getXOnTheMap() + getSpeed());
            case DOWN -> setYOnTheMap(getYOnTheMap() + getSpeed());
            case LEFT -> setXOnTheMap(getXOnTheMap() - getSpeed());
        }
        publisher.update();
        playerArea.invertedMove(direction);
        publisher.onMove(direction, getSpeed());
        publisher.onMove(playerArea, interactableAreas);
//        for (InteractableArea2D area : interactableAreas){
//            if (playerArea.intersects(area)){
//                playerArea.setObject(area);
//                area.onCollision();
//            }
//        }
        System.out.println("Oh, i moved, my coordinates are now " + getXOnTheMap() + " and " + getYOnTheMap());
    }
    default void move(Direction direction){
        switch (direction){
            case UP -> setYOnTheMap(getYOnTheMap() + getSpeed());
            case RIGHT -> setXOnTheMap(getXOnTheMap() - getSpeed());
            case DOWN -> setYOnTheMap(getYOnTheMap() - getSpeed());
            case LEFT -> setXOnTheMap(getXOnTheMap() + getSpeed());
        }
    }

    default void invertedMove(Direction direction){
        switch (direction){
            case UP -> setYOnTheMap(getYOnTheMap() - getSpeed());
            case RIGHT -> setXOnTheMap(getXOnTheMap() + getSpeed());
            case DOWN -> setYOnTheMap(getYOnTheMap() + getSpeed());
            case LEFT -> setXOnTheMap(getXOnTheMap() - getSpeed());
        }
    }
}
