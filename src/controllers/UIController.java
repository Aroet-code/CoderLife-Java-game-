package controllers;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ui.*;

public class UIController {
    private HashMap<String, UIElementState> states = new HashMap<>();
    private HashMap<String, UIElement> elements = new HashMap<>();
    private HashMap<String, Switch> switches = new HashMap<>();
    private HashMap<String, Rectangle> collisionShapes = new HashMap<>();
    private HashMap<String, String[]> tags = new HashMap<>();
    private HashMap<String, Boolean> bools = new HashMap<>();
    private ArrayList<String> containers = new ArrayList<>();
    private HashMap<String, Boolean> hidden = new HashMap<>();

    public void onMouseClicked(){
        for (String key : states.keySet()){
            if (hidden.get(key)){
                continue;
            }
            UIElementState state = states.get(key);
            if (state == UIElementState.HOVER){
                UIElement element = elements.get(key);
//                states.replace(key, UIElementState.ACTIVE);
                try {
                    if (bools.containsKey(key)) {
                        bools.put(key, (!(bools.get(key))));
                    }
                    if (switches.containsKey(key)){
                        switches.get(key).onInteract();
                        elements.replace(key, switches.get(key));
                    } else {
                        element.onInteract();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void onMouseDragged(){

    }

    public void onMouseReleased(){

    }

    public void onUpdateMousePosition(int x, int y){
        for (String key : collisionShapes.keySet()){
            updateHoverState(x, y, key);
        }
    }

    public void updateState(String key, UIElementState state){
        states.replace(key, state);
    }

    public void updateHoverState(int x, int y, String key){
        Rectangle collisionShape = collisionShapes.get(key);
        UIElementState state = states.get(key);
        if (collisionShape.contains(x, y)){
            if (state == UIElementState.IN_PROGRESS || state == UIElementState.ACTIVE){
                return;
            }
            if (state == UIElementState.HOVER){
                return;
            } else {
                updateState(key, UIElementState.HOVER);
            }
        } else {
            if (state == UIElementState.HOVER){
                updateState(key, UIElementState.DEFAULT);
            }
        }
    }

    public void updateCollision(String key, Rectangle shape){
        collisionShapes.replace(key, shape);
//        System.out.println("Updating shape for " + key + ":");
//        System.out.println("x: " + shape.getX() + " y: " + shape.getY());
    }

    public UIElementState getElementState(String key){
        return states.get(key);
    }

    public UIElement getElement(String key) throws Exception {
        if (elements.containsKey(key)) {
            return elements.get(key);
        } else {
            throw new Exception("Some element, that is supposed to be initialized, isn't.");
        }
    }

    public void addElement(String key, UIElement element, UIElementState state){
        if (element instanceof UIContainer){
            containers.add(key);
        }
        elements.put(key, element);
        states.put(key, state);
        collisionShapes.put(key, element.getCollisionShape());
        tags.put(key, element.getTags());
        hidden.put(key, false);
    }

    public void addElement(String key, Switch sw, UIElementState state){
        bools.put(key, false);
        switches.put(key, sw);
        elements.put(key, sw);
        states.put(key, state);
        collisionShapes.put(key, sw.getCollisionShape());
        tags.put(key, sw.getTags());
        hidden.put(key, false);
    }

    public void flipAllElement(String[] exceptions){
        for (Map.Entry<String, Boolean> entry : hidden.entrySet()){
            boolean excepted = false;
            if (exceptions != null) {
                for (String ex : exceptions) {
                    if (Objects.equals(entry.getKey(), ex)) {
                        excepted = true;
                        break;
                    }
                }
            }
            if (!(excepted)){
                entry.setValue(!(entry.getValue()));
            }
        }
    }

    public void hideElement(String key){
        hidden.replace(key, true);
    }

    public void showElement(String key){
        hidden.replace(key, false);
    }

    public HashMap<String, Rectangle> getCollisionShapes() {
        return collisionShapes;
    }

    public ArrayList<String> getContainers() {
        return containers;
    }

    public boolean getElementHiddenState(String key){
        return hidden.get(key);
    }
}
