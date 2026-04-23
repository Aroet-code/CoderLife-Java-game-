package ui;

import java.awt.*;
import java.awt.image.ImageObserver;

import controllers.UIController;

public class UIBatch {
    private UIController uiController;

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
            if (uiController.getElementHiddenState(name)){
                continue;
            }
            try {
                element = uiController.getElement(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            UIElementState state = uiController.getElementState(name);
            if (!(element instanceof UIContainer)) {
                element.updateImage(state);
                if (element instanceof Switch sw){
                    g.drawImage(sw.getCurrentImage(), sw.getX(), sw.getY(), observer);
                }
            }
            if (!(element instanceof Switch)) {
                g.drawImage(element.getCurrentImage(), element.getX(), element.getY(), observer);
            }
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

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public void drawUI(Graphics g, ImageObserver observer){
        String[] containerNames = uiController.getContainers().toArray(new String[0]);
        if (containerNames.length == 0){
            return;
        }
        for (int i = 0; i < containerNames.length; i++){
            drawUI(containerNames[i], observer);
        }
        drawContainer(g, containerNames[containerNames.length-1], observer);
    }

    public boolean isUiControllerNull(){
        if (this.uiController == null){
            System.out.println("UIController in UIBatch is null");
            return true;
        }
        return false;
    }
}
