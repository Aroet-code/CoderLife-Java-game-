package ui;

import java.awt.*;

public class Star extends AnimationObject{
    public Star(int x, int y, int width, int height){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
//        this.setColor(new Color(0.109f, 0.223f, 0.733f, 1f));
        this.setColor(new Color(0.30f, 0.65f , 1.00f, 1f));
//        this.setColor(new Color(1.00f,0.55f,0.10f, 1f));
//        this.setColor(new Color(0.65f, 0.6f, 0.55f, 1f));
    }

    public int elevate(int speed){
        this.setY(getY() - speed);
        if (this.getY() + this.getHeight() < 0){
            return -1;
        }
//        if (this.getColor().getAlpha() <= 0){
//            return -1;
//        }
        return 0;
    }
}
