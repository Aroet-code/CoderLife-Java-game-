package ui;

import java.awt.*;

public class LightLine extends AnimationObject{
    private float alpha;
    private float decayingSpeed;

    public LightLine(int x, int width, int height, float decayingSpeed, Color color){
        this.setX(x);
        this.setY(0);
        this.setWidth(width);
        this.setHeight(height);
        this.decayingSpeed = decayingSpeed;
        this.alpha = 1f;
        this.setColor(color);
    }

    public int decay(){
        if (this.alpha < 0.08f){
            return -1;
        }
        this.alpha -= decayingSpeed;
        return 0;
    }

    public float getAlpha(){
        return this.alpha;
    }
}
