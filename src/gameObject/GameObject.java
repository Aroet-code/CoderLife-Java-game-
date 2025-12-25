package gameObject;

import gameObject.animation.Animation;
import gameObject.interactions.Interactable;

import java.awt.*;
import java.util.ArrayList;

public abstract class GameObject {
    Image image;
    Image mainImage;
    int x, y;
    int xOnTheMap, yOnTheMap;
    int width, height;
    ArrayList<String> tags = new ArrayList<>();
    volatile Animation currentAnimation;

    public Image getImage() {
        return image;
    }

    public Image getMainImage() {
        return mainImage;
    }

    public int getXOnTheMap() {
        return xOnTheMap;
    }

    public void setXOnTheMap(int xOnTheMap) {
        this.xOnTheMap = xOnTheMap;
    }

    public int getYOnTheMap() {
        return yOnTheMap;
    }

    public void setYOnTheMap(int yOnTheMap) {
        this.yOnTheMap = yOnTheMap;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCenterX() {
        return this.x + (this.image.getWidth(null) / 2);
    }

    public int getCenterY() {
        return this.y + (this.image.getHeight(null) / 2);
    }

    public void setCenterX(int centerX) {
        this.x = centerX - (this.image.getWidth(null) / 2);
    }

    public void setCenterXOnTheMap(int centerXOnTheMap) {
        this.xOnTheMap = centerXOnTheMap - (getImage().getWidth(null) / 2);
    }

    public void setCenterYOnTheMap(int centerYOnTheMap) {
        this.yOnTheMap = centerYOnTheMap - (getImage().getHeight(null) / 2);
    }

    public int getCenterXOnTheMap() {
        return getXOnTheMap() + (getImage().getWidth(null) / 2);
    }

    public int getCenterYOnTheMap() {
        return getYOnTheMap() + (getImage().getHeight(null) / 2);
    }

    public void setCenterY(int centerY) {
        this.y = centerY - (this.image.getHeight(null) / 2);
    }

    public void onReady() {

    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }

    public void setxOnTheMap(int xOnTheMap) {
        this.xOnTheMap = xOnTheMap;
    }

    public void setyOnTheMap(int yOnTheMap) {
        this.yOnTheMap = yOnTheMap;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public boolean hasTag(String tag) {
        for (String string : tags) {
            if (string.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public Interactable getObject() {
        return null;
    }
}
