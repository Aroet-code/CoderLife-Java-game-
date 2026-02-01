import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;

public class UIBatch {
    private final UIController uiController;

    public UIBatch(UIController uiController) {
        this.uiController = uiController;
    }

    public void drawUI(String containerName, ImageObserver observer){
        UIElementState containerState = uiController.getElementState(containerName);
        UIContainer container = null;
        String[] elementNames = null;
        Graphics g = null;
        try {
            container = (UIContainer) uiController.getElement(containerName);
            container.updateImage(containerState);
            g = container.getCurrentImage().getGraphics();
            elementNames = container.getChildren();
        } catch (Exception e) {
            System.out.println("Dude, you need to enter the actual container name.");
            System.out.println(containerName + " doesn't work for some reason.");
        }
        for (String name : elementNames){
            UIElement element = null;
            try {
                element = uiController.getElement(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            UIElementState state = uiController.getElementState(name);
            if (!(element instanceof UIContainer)) {
                element.updateImage(state);
            }
            g.drawImage(element.getCurrentImage(), element.getX(), element.getY(), observer);
        }
    }

    public void drawCollisionShapes(Graphics g){
        for (Rectangle rect : uiController.getCollisionShapes().values()){
            g.setColor(Color.WHITE);
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
        }
    }

    public void drawContainer(Graphics g, String containerName, ImageObserver observer){
        UIElement container = null;
        try {
            container = uiController.getElement(containerName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        g.drawImage(container.getCurrentImage(), container.getX(), container.getY(), observer);
    }
}
