package ui;

import java.awt.*;
import java.util.HashMap;

public class UIContainer extends UIElement implements Parent {
    private String[] children;

    public UIContainer(UIController uiController, int startX, int startY, int targetX, int targetY, String name, HashMap<UIElementState, Image> stateImages, String[] elements, String[] tags) {
        super(startX, startY, targetX, targetY, name, stateImages, null, tags);
        this.children = elements;
        int totalChildren = children.length;
        String[] children = new String[totalChildren];
        for (String element : elements){
            UIElement child = null;
            try {
                child = uiController.getElement(element);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Child's name is " + element);
            }
            child.setParent(name);
            children[elements.length-totalChildren] = element;
            totalChildren--;
        }
        this.setChildren(children);
        this.children = elements;
    }
}
