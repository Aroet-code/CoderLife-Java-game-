package gameObject.movement;

public class Axis {
    private float x, y;
    private Direction directionX = null;
    private Direction directionY = null;

    public Axis(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setDirectionX(Direction direction) {
        this.directionX = direction;
    }

    public void setDirectionY(Direction directionY) {
        this.directionY = directionY;
    }

    public void slowDownX(){
        boolean isNegative = directionX == Direction.LEFT;
        if (isNegative){
            this.x = Math.min(x+0.5f, 0);
        } else {
            this.x = Math.max(x-0.5f, 0);
        }
    }

    public void slowDownY(){
        boolean isNegative = directionY == Direction.UP;
        if (isNegative){
            this.y = Math.min(y+0.5f, 0);
        } else {
            this.y = Math.max(y-0.5f, 0);
        }
    }

    public void speedUpX(){
        boolean isNegative = directionX == Direction.LEFT;
        if (isNegative){
            this.x = Math.clamp(x-0.5f, -1, 1);
        } else {
            this.x = Math.clamp(x+0.5f, -1, 1);
        }
    }

    public void speedUpY(){
        boolean isNegative = directionY == Direction.UP;
        if (isNegative){
            this.y = Math.clamp(y-0.5f, -1, 1);
        } else {
            this.y = Math.clamp(y+0.5f, -1, 1);
        }
    }
}
