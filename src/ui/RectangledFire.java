package ui;

import java.awt.*;

public class RectangledFire extends AnimationObject{
    private float alpha;
    public RectangledFire(int x, int y, int width, float alpha) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(1);
        this.setColor(new Color(0.30f, 0.65f , 1.00f, 1f));
//        this.setColor(new Color(1.00f,0.55f,0.10f, 1f));
//        this.setColor(new Color(0.65f, 0.6f, 0.55f, 1f));
        this.alpha = alpha;
    }

    public int burst(){
        if (alpha < 0.07f){
            return -1;
        }
        this.setHeight((int) (1 * (1/Math.pow(alpha, 2))));
        return 0;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
