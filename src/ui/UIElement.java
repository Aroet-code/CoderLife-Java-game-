package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class UIElement implements Interactable, UIAnimatable {
    private UIImageObserver imageObserver;
    private HashMap<UIElementState, Image> stateImages;
    private boolean animatable = false;
    private String[] tags;
    private Image currentImage;
    private String parent;
    private int x, y;
    private int targetX, targetY;
    private int startX, startY;
    private String[] children;
    private String name;
    private Rectangle collisionShape;
    private final int animationFPSDivider = 6;
    private final int moveFPSDivider = 20;
    private int animationStateNumber = 0;
    private int currentSpeedX = 0;
    private int currentSpeedY = 0;
    private Image[] animationImages;
    private int[] phaseSpeedsX = new int[10];
    private int[] phaseSpeedsY = new int[10];

    Callable<Integer> command = null;

    public UIElement(int startX, int startY, int targetX, int targetY, String name, HashMap<UIElementState, Image> stateImages, Image[] animationImages, String[] tags) {
        this.imageObserver = new UIImageObserver();
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.name = name;
        if (animationImages != null){
            this.animationImages = animationImages;
            this.animatable = true;
        }
        Image defaultImg = stateImages.get(UIElementState.DEFAULT);
        if (defaultImg == null || defaultImg.getWidth(null) <= 0 || defaultImg.getHeight(null) <= 0) {
            defaultImg = createDefaultImage(100, 50);
            stateImages.put(UIElementState.DEFAULT, defaultImg);
        }
        this.currentImage = stateImages.get(UIElementState.DEFAULT);
        this.stateImages = stateImages;
        int imgWidth = Math.max(1, currentImage.getWidth(imageObserver));
        int imgHeight = Math.max(1, currentImage.getHeight(imageObserver));
        this.collisionShape = new Rectangle(startX, startY, imgWidth, imgHeight);
        if (!(tags == null)) {
            this.tags = tags;
        } else {
            this.tags = new String[1];
        }
    }

    @Override
    public void onInteract() throws Exception {
        if (hasTag("NOT_INTERACTABLE")){
//            System.out.println(name + " is not interactable.");
            return;
        }
        if (command == null){
            System.out.println(name + "'s command is null, but it was called");
            return;
        }
        command.call();
    }

    @Override
    public void setCommand(Callable<Integer> command) {
        this.command = command;
    }

    @Override
    public Callable<Integer> getCommand() {
        return command;
    }

    @Override
    public int animate(UIElementState nextState, UIController uiController, boolean reversed) {
        switch (this.animationStateNumber){
            case 0 -> {
                uiController.updateState(this.name, UIElementState.IN_PROGRESS);
                if (!(reversed)) {
                    currentImage = animationImages[animationStateNumber / animationFPSDivider];
                } else {
                    currentImage = animationImages[animationImages.length - 1 - (animationStateNumber / animationFPSDivider)];
                }
            }
            default -> {
                if (animationStateNumber + 1 > animationImages.length * animationFPSDivider){
                    uiController.updateState(this.name, nextState);
                    this.currentImage = stateImages.get(nextState);
                    animationStateNumber = 0;
                    return 0;
                }
                if (!(reversed)) {
                    this.currentImage = animationImages[animationStateNumber / animationFPSDivider];
                } else {
                    this.currentImage = animationImages[animationImages.length - 1 - (animationStateNumber / animationFPSDivider)];
                }
            }
        }
//        System.out.println("AnimationStateNumber = " + animationStateNumber);
        animationStateNumber++;
        return animationStateNumber;
    }

    @Override
    public int move(boolean reversed, UIController uiController) {
        int xDiff, yDiff;
        if (!(reversed)) {
            xDiff = targetX - x;
            yDiff = targetY - y;
        } else {
            xDiff = startX - x;
            yDiff = startY - y;
        }
        if ((x == targetX && y == targetY) && (!(reversed)) || (x == startX && y == targetY) && reversed){
            animationStateNumber = 0;
            return 0;
        }
        switch (animationStateNumber){
            case 0 -> {
                phaseSpeedsX[0] = xDiff / 30 / 2;
                phaseSpeedsY[0] = yDiff / 30 / 2;
                phaseSpeedsX[1] = xDiff / 45 / 2;
                phaseSpeedsY[1] = yDiff / 45 / 2;
                phaseSpeedsX[2] = xDiff / 60 / 2;
                phaseSpeedsY[2] = yDiff / 60 / 2;
                phaseSpeedsX[3] = xDiff / 67 / 2;
                phaseSpeedsY[3] = yDiff / 67 / 2;
                phaseSpeedsX[4] = xDiff / 75 / 2;
                phaseSpeedsY[4] = yDiff / 75 / 2;
                phaseSpeedsX[5] = xDiff / 83 / 2;
                phaseSpeedsY[5] = yDiff / 83 / 2;
                phaseSpeedsX[6] = xDiff / 90 / 2;
                phaseSpeedsY[6] = yDiff / 90 / 2;
                phaseSpeedsX[7] = xDiff / 105 / 2;
                phaseSpeedsY[7] = yDiff / 105 / 2;
                phaseSpeedsX[8] = xDiff / 120 / 2;
                phaseSpeedsY[8] = yDiff / 120 / 2;
                phaseSpeedsX[9] = xDiff / 135 / 2;
                phaseSpeedsY[9] = yDiff / 135 / 2;
                animationStateNumber = 1;
                if (reversed) {
                    for (int[] array : new int[][]{phaseSpeedsX, phaseSpeedsY}){
                        int left = 0;
                        int right = array.length - 1;

                        while (right > left){
                            int temp = array[left];
                            array[left] = array[right];
                            array[right] = temp;

                            left++;
                            right--;
                        }
                    }
                }
            }
            case -1 -> {
                System.out.println("Animation finished.");
                currentSpeedX = 0;
                currentSpeedY = 0;
            }
            case -2 -> {
                System.out.println("An error occurred during animating a UI element. Element: " + this.name);
            }
            default -> {
                if ((!(reversed)) && animationStateNumber / moveFPSDivider > phaseSpeedsX.length - 1){
                    System.out.println("Animation ended.");
                    x = targetX;
                    y = targetY;
                    animationStateNumber = 0;
                    updateCollisions(uiController);
                    return 0;
                } else if (reversed && animationStateNumber / moveFPSDivider > phaseSpeedsX.length - 1){
                    System.out.println("Reversed animation ended.");
                    x = startX;
                    y = startY;
                    animationStateNumber = 0;
                    this.collisionShape.setLocation(getAbsoluteX(uiController), getAbsoluteY(uiController));
                    updateCollisions(uiController);
                    return 0;
                }
                currentSpeedX = phaseSpeedsX[animationStateNumber / moveFPSDivider];
                currentSpeedY = phaseSpeedsY[animationStateNumber / moveFPSDivider];
                x += currentSpeedX;
                y += currentSpeedY;
                updateCollisions(uiController);
                animationStateNumber++;
            }
        }
        return animationStateNumber;
    }

    @Override
    public int getTargetX() {
        return targetX;
    }

    @Override
    public int getTargetY() {
        return targetY;
    }

    @Override
    public int getStartX() {
        return startX;
    }

    @Override
    public int getStartY() {
        return startY;
    }

    public void updateImage(UIElementState newState) {
        Image originalImage = null;
        if (newState == UIElementState.IN_PROGRESS) {
            originalImage = currentImage;
        } else if (newState == UIElementState.HOVER && this.hasTag("NO_HOVER_IMAGE")) {
            originalImage = this.stateImages.get(UIElementState.DEFAULT);
        } else if (newState == UIElementState.ACTIVE && this.hasTag("NO_ACTIVE_IMAGE")) {
            originalImage = this.stateImages.get(UIElementState.DEFAULT);
        }  else {
            originalImage = this.stateImages.get(newState);
        }
        updateOriginalImage(originalImage);
    }

    public Image getCurrentImage(){
        return this.currentImage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName(){
        return name;
    }

    public Rectangle getCollisionShape() {
        return collisionShape;
    }

    public void setCollisionShape(Rectangle collisionShape) {
        this.collisionShape = collisionShape;
    }

    public String getParent(){
        if (parent == null){
            return null;
        }
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getAbsoluteX(UIController uiController){
        int result = this.getX();
        Parent currentParent = null;
        try {
            currentParent = (Parent) uiController.getElement(parent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        while (currentParent != null){
            result += currentParent.getX();
            try {
                currentParent = (Parent) uiController.getElement(currentParent.getParent());
            } catch (Exception e) {
//                System.out.println(e.getMessage());
                break;
            }
        }
        return result;
    }

    public int getAbsoluteY(UIController uiController){
        int result = this.getY();
        Parent currentParent = null;
        try {
            currentParent = (Parent) uiController.getElement(parent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        while (currentParent != null){
            result += currentParent.getY();
            try {
                currentParent = (Parent) uiController.getElement(currentParent.getParent());
            } catch (Exception e) {
//                System.out.println(e.getMessage());
                break;
            }
        }
        return result;
    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }

    public void updateCollisions(UIController uiController){
        this.collisionShape.setLocation(getAbsoluteX(uiController), getAbsoluteY(uiController));
        uiController.updateCollision(this.name, this.collisionShape);
        if (!(this.getChildren() == null)){
            for (String childName : this.getChildren()) {
                UIElement child = null;
                try {
                    child = uiController.getElement(childName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Rectangle childCollisionShape = child.getCollisionShape();
                childCollisionShape.setLocation(this.getAbsoluteX(uiController) + child.getX(),
                        this.getAbsoluteY(uiController) + child.getY());
                uiController.updateCollision(childName, childCollisionShape);
            }
        }
    }

    private Image createDefaultImage(int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(0.30f, 0.65f, 1.00f, 0.5f));
        g.fillRect(0, 0, width, height);
        g.dispose();
        return image;
    }

    public void updateStateImage(UIElementState state, Image newImage){
        if (newImage == null || newImage.getWidth(imageObserver) <= 0 || newImage.getHeight(imageObserver) <= 0){
            return;
        }
        UIElementState currentState = Main.uiController.getElementState(name);
        if (currentState == state){
            this.currentImage = newImage;
            updateCollisionShape();
            updateCollisions(Main.uiController);
        }
    }

    public void updateCollisionShape() {
        int width = Math.max(1, currentImage.getWidth(imageObserver));
        int height = Math.max(1, currentImage.getHeight(imageObserver));
        this.collisionShape.setSize(width, height);
    }

    public void setCurrentImage(Image image){
        currentImage = image;
    }

    public Image getStateImage(UIElementState state){
        return stateImages.get(state);
    }

    public void updateOriginalImage(Image originalImage){
        if (originalImage == null) {
            int estimatedWidth = this.stateImages.get(UIElementState.DEFAULT).getWidth(imageObserver);
            int estimatedHeight = this.stateImages.get(UIElementState.DEFAULT).getHeight(imageObserver);
            if (estimatedWidth <= 0 || estimatedHeight <= 0) {
                originalImage = createDefaultImage(100, 50);
            } else {
                originalImage = createDefaultImage(estimatedWidth, estimatedHeight);
            }
        }
        int width = originalImage.getWidth(imageObserver);
        int height = originalImage.getHeight(imageObserver);
        if (width <= 0 || height <= 0) {
            width = 100;
            height = 50;
        }
        BufferedImage copiedImage = new BufferedImage(width,
                height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copiedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, imageObserver);
        currentImage = copiedImage;
    }

    public boolean hasTag(String tag){
        if (tags != null){
            return Arrays.stream(tags).toList().contains(tag);
        }
        return false;
    }

    public void changeStateImage(UIElementState state, Image image){
        if (!(stateImages.containsKey(state))){
            stateImages.put(state, image);
        }
        stateImages.replace(state, image);
    }

    public String[] getTags(){
        return tags;
    }
}
