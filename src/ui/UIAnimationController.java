package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UIAnimationController {
    private HashMap<String, Integer> animationStateNumbers = new HashMap<>();
    private HashMap<String, UIAnimatable> elements = new HashMap<>();
    private HashMap<String, UIAnimationType> animationTypes = new HashMap<>();
    private ArrayList<UIAnimatable> currentlyAnimated = new ArrayList<>();

    public void animateUI(UIController uiController){
        Iterator<UIAnimatable> iterator = currentlyAnimated.iterator();

        while (iterator.hasNext()){
            UIAnimatable element = iterator.next();
            String name = element.getName();
            UIAnimationType animationType = animationTypes.get(name);
            int animationStateNumber;
            switch (animationType) {
                case MOVE -> {
                    animationStateNumber = element.move(false, uiController);
                    animationStateNumbers.replace(name, animationStateNumber);
                }
                case MOVE_REVERSED -> {
                    animationStateNumber = element.move(true, uiController);
                    animationStateNumbers.replace(name, animationStateNumber);
                }
                case DEFAULT -> {
                    animationStateNumber = element.animate(UIElementState.DEFAULT, uiController, false);
                    animationStateNumbers.replace(name, animationStateNumber);
                }
                case DEFAULT_REVERSED -> {
                    animationStateNumber = element.animate(UIElementState.DEFAULT, uiController, true);
                    animationStateNumbers.replace(name, animationStateNumber);
                }
                default -> {
                    animationStateNumber = 0;
                }
            }
            switch (animationStateNumber){
                case 0 -> {
                    animationTypes.replace(name, UIAnimationType.NONE);
                    iterator.remove();
                }
                case -2 -> {
                    System.out.println("Something bad happened in the UIAnimationController in " + name);
                    System.out.println("Removing the iterator.");
                    iterator.remove();
                }
                default -> {

                }
            }
        }
    }

    public void addElement(String key, UIAnimatable element){
        elements.put(key, element);
        animationStateNumbers.put(key, 0);
        animationTypes.put(key, UIAnimationType.NONE);
    }

    public void animateElement(String key, UIAnimationType type){
        if (!(elements.containsKey(key))){
            System.out.println("The element isn't in the Hashmap");
        } else {
            animationStateNumbers.replace(key, 1);
            animationTypes.replace(key, type);
            currentlyAnimated.add(elements.get(key));
        }
    }
}
