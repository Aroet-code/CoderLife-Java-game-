package gameObject.animation;

import gameObject.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class Animation implements AnimationState{
    private int imageNumber;
    private volatile ArrayList<Image> images;
    private final ArrayList<Image> mainImages;
    private final GameObject parent;
    private final Animation nextAnimation;

    public Animation(ArrayList<Image> images, GameObject parent, Animation nextAnimation) {
        this.mainImages = images;
        this.images = images;
        this.imageNumber = 0;
        this.parent = parent;
        if (nextAnimation != null){
            this.nextAnimation = nextAnimation;
        } else {
            this.nextAnimation = this;
        }
        this.parent.setCurrentAnimation(this);
    }

//    @Override
//    public boolean animate(ImageScaler scaler) {
//        if (imageNumber < images.toArray().length){
//            System.out.println("Animation in progress...");
//            float imageScaleMulti = scaler.getImageScaleMulti();
//            parent.setMainImage(images.get(imageNumber));
//            Image scaledInstance = parent.getMainImage().getScaledInstance((int) (parent.getMainImage().getWidth(null) * imageScaleMulti),
//                    (int) (parent.getMainImage().getHeight(null) * imageScaleMulti), Image.SCALE_FAST);
//            parent.setImage(scaledInstance);
//            imageNumber++;
//            return true;
//        } else {
//            parent.setCurrentAnimation(nextAnimation);
//            parent.getCurrentAnimation().setImageNumber(0);
//            return false;
//        }
//    }
@Override
    public boolean animate() {
        if (imageNumber < images.size()){
            System.out.println("Animation in progress...");
            parent.setImage(images.get(imageNumber));
            imageNumber++;
            return true;
        } else {
            parent.setCurrentAnimation(nextAnimation);
            parent.getCurrentAnimation().setImageNumber(0);
            return false;
        }
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public ArrayList<Image> getMainImages() {
        return mainImages;
    }
}
