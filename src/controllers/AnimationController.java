package controllers;

import gameObject.GameObjectState;
import gameObject.animation.Animatable;
import gameObject.image.AnimationFlags;
import gameObject.image.ImageUpdateCommand;
import util.GameController;

import java.util.*;

public class AnimationController {
    private Map<String, Animatable> animations = new HashMap<>();
//    private List<String> hasAnimationKeys = new ArrayList<>();
    private static final int ANIMATION_FRAMES = 5;
    private ImageController imageController;
    private Queue<String> animationQueue = new ArrayDeque<>();

    public AnimationController(ImageController imageController) {
        this.imageController = imageController;
    }

    public void queueAnimation(String key){
        animationQueue.add(key);
    }

//    public void addObjectKey(String key){
//        hasAnimationKeys.add(key);
//    }

    public boolean containsKey(String key){
        return animations.containsKey(key);
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
        if (!(animationQueue.isEmpty())){
            int size = animationQueue.size();
            for (String key : animationQueue){
                imageController.changeState(key, GameObjectState.ANIMATING);
            }
            for (int i = 0; i < size; i++) {
                String key = animationQueue.poll();
//                System.out.println(key);
//                System.out.println(animations.keySet());
                int returnValue = animations.get(key).animate(ANIMATION_FRAMES);
                int animationState = 0;
                boolean addElementBack = true;
                switch (returnValue){
                    case -1 -> {
                        animationState = GameController.getImageController().updateGameObjectImage(new ImageUpdateCommand(key, AnimationFlags.NEXT_IMAGE));
                    }
                    case -3 -> {
                        animationState = GameController.getImageController().updateGameObjectImage(new ImageUpdateCommand(key, AnimationFlags.NEXT_IMAGE_REPEATABLE));
                    }
                    default -> {

                    }
                }
                switch (animationState){
                    case 1 -> {
                        imageController.changeState(key, GameObjectState.FINAL);
                        addElementBack = false;
                    }
                    default -> {

                    }
                }
                if (addElementBack){
                    animationQueue.add(key);
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
}
