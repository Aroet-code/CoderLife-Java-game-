package gameObject.movement;

public interface Parallaxable {
    int getX();
    void setX(int x);
    int getY();
    void setY(int y);
    int getParallaxSpeed();

    default void onMove(Direction direction, int playerSpeed){
        switch (direction){
            case RIGHT -> setX((int)(getX() + ((playerSpeed / 100.0) * (100.0 - getParallaxSpeed()))));
            case LEFT -> setX((int)(getX() - ((playerSpeed / 100.0) * (100.0 - getParallaxSpeed()))));
            case UP -> setY((int)(getY() - ((playerSpeed / 100.0) * (100.0 - getParallaxSpeed()))));
            case DOWN -> setY((int)(getY() + ((playerSpeed / 100.0) * (100.0 - getParallaxSpeed()))));
        }
    }
}
