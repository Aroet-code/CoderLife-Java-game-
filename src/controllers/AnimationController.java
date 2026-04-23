package controllers;

import gameObject.GameObjectState;
import gameObject.animation.Animatable;
import gameObject.animation.AnimationCommandFlag;
import gameObject.animation.AnimationUpdateCommand;
import gameObject.image.AnimationFlags;
import gameObject.image.ImageUpdateCommand;
import gameObject.movement.Direction;
import gameObject.movement.SetDirectionCommand;
import util.GameController;

import java.util.*;

public class AnimationController {
    private final Map<String, Animatable> animations = new HashMap<>();
//    private List<String> hasAnimationKeys = new ArrayList<>();
    private static final int ANIMATION_FRAMES = 6;
    private final ImageController imageController;
    private final Queue<String> animationQueue = new ArrayDeque<>();
    private final Map<String, Boolean> animatingLeft = new HashMap<>();

    public AnimationController(ImageController imageController) {
        this.imageController = imageController;
    }

    public void queueAnimation(String key){
        if (animationQueue.contains(key)){
            return;
        }
        animationQueue.add(key);
    }

    public void stopAnimating(String key){
        synchronized (animationQueue) {
            if (animationQueue.contains(key)) {
                animationQueue.remove(key);
                imageController.changeState(key, GameObjectState.FINAL);
            } else {
                System.out.println("The animation queue doesn't contain such key in AnimationController: " + key);
            }
        }
    }

//    public void addObjectKey(String key){
//        hasAnimationKeys.add(key);
//    }

    public boolean containsKey(String key){
        return animations.containsKey(key);
    }

    public void executeCommand(SetDirectionCommand command){
        String key = command.key();
        Direction direction = command.direction();
        switch (direction){
            case LEFT -> {
                updateAnimationDirection(key, true);
            }
            case RIGHT -> {
                updateAnimationDirection(key, false);
            }
        }
    }

    public void executeCommand(AnimationUpdateCommand command){
        String key = command.key();
        AnimationCommandFlag flag = command.flag();
        switch (flag){
            case ANIMATE -> {
                queueAnimation(key);
            }
            case STOP_ANIMATING -> {
                stopAnimating(key);
            }
        }
    }

    public void receiveObject(String key, Animatable animatable){
        if (animations.containsKey(key)){
            System.out.println("The object key already exists in AnimationController");
            return;
        }
        animations.put(key, animatable);
    }

//    public void removeObject(GameObject object){
//        if ((objects.contains(object))) {
//            objects.remove(object);
//        } else {
//            System.out.println("Hey, the object isn't in the array, you can't remove it from there.");
//        }
//    }

    public void animateObjects(){
//        System.out.println("Trying to animate objects. The queue consists of " + animationQueue.size() + " elements.");
        synchronized (animationQueue) {
            if (!(animationQueue.isEmpty())) {
                int size = animationQueue.size();
                for (String key : animationQueue) {
                    imageController.changeState(key, GameObjectState.ANIMATING);
                }
                for (int i = 0; i < size; i++) {
                    String key = animationQueue.poll();
//                System.out.println(key);
//                System.out.println(animations.keySet());
                    int returnValue = animations.get(key).animate(ANIMATION_FRAMES);
                    int animationState = 0;
                    boolean addElementBack = true;
                    switch (returnValue) {
                        case -1 -> {
                            Direction direction = null;
                            if (animatingLeft.containsKey(key)){
                                if (animatingLeft.get(key)){
                                    direction = Direction.LEFT;
                                } else {
                                    direction = Direction.RIGHT;
                                }
                            }
                            animationState = imageController.updateGameObjectImage(new ImageUpdateCommand(key, AnimationFlags.NEXT_IMAGE, direction));
                        }
                        case -3 -> {
                            animationState = imageController.updateGameObjectImage(new ImageUpdateCommand(key, AnimationFlags.NEXT_IMAGE_REPEATABLE, null));
                        }
                        default -> {

                        }
                    }
                    switch (animationState) {
                        case 1 -> {
                            imageController.changeState(key, GameObjectState.FINAL);
                            addElementBack = false;
                        }
                        default -> {

                        }
                    }
                    if (addElementBack) {
                        animationQueue.add(key);
                    }
                }
            }
        }
    }

    public void addAnimation(String key, Animatable animatable){
        animations.put(key, animatable);
    }

    public void removeAnimation(String key){
        animations.remove(key);
    }

//    public void animate() {
//        if (!(objects.isEmpty())){
//            Iterator<GameObject> iterator = objects.iterator();
//            while (iterator.hasNext()){
//                GameObject object = iterator.next();
//                boolean animationResult = object.getCurrentAnimation().animate();
//                System.out.println("Animation result for " + object + ": " + animationResult);
//
//                if (!(animationResult)){
//                    System.out.println("Removing " + object);
//                    iterator.remove();
//                }
//            }
//        }
//    }

    public void updateAnimationDirection(String key, boolean animatingLeft){
        this.animatingLeft.put(key, animatingLeft);
    }
}
