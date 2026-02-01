import java.awt.*;
import java.util.HashMap;

public class UIController {
    private HashMap<String, UIElementState> states = new HashMap<>();
    private HashMap<String, UIElement> elements = new HashMap<>();
    private HashMap<String, Rectangle> collisionShapes = new HashMap<>();
    private HashMap<String, String[]> tags = new HashMap<>();

    public void onMouseClick(){
        for (String key : states.keySet()){
            UIElementState state = states.get(key);
            if (state == UIElementState.HOVER){
                UIElement element = elements.get(key);
                try {
                    element.onInteract();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
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
        elements.put(key, element);
        states.put(key, state);
        collisionShapes.put(key, element.getCollisionShape());
    }

    public HashMap<String, Rectangle> getCollisionShapes() {
        return collisionShapes;
    }
}
