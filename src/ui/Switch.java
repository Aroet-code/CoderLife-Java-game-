package ui;

import java.awt.*;
import java.util.HashMap;

public class Switch extends UIElement {
    private boolean activated = false;

    public Switch(int startX, int startY, int targetX, int targetY, String name, HashMap<UIElementState, Image> stateImages, Image[] animationImages, String[] tags) {
        super(startX, startY, targetX, targetY, name, stateImages, animationImages, tags);
    }

    @Override
    public void onInteract() throws Exception {
        this.flip();
        super.onInteract();
    }

    @Override
    public void updateImage(UIElementState newState) {
        if (newState == UIElementState.DEFAULT || newState == UIElementState.HOVER) {
            Image originalImage = null;
            if (activated) {
                originalImage = getStateImage(UIElementState.POSITIVE);
            } else {
                originalImage = getStateImage(UIElementState.NEGATIVE);
            }
            updateOriginalImage(originalImage);
        }
    }

    public void flip(){
        this.activated = (!(activated));
        if (activated){
            this.changeStateImage(UIElementState.DEFAULT, this.getStateImage(UIElementState.POSITIVE));
            this.changeStateImage(UIElementState.HOVER, this.getStateImage(UIElementState.POSITIVE));
        } else {
            this.changeStateImage(UIElementState.DEFAULT, this.getStateImage(UIElementState.NEGATIVE));
            this.changeStateImage(UIElementState.HOVER, this.getStateImage(UIElementState.NEGATIVE));
        }
    }

    public boolean getState(){
        return activated;
    }
}
