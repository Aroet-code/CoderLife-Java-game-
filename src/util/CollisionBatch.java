package util;

import controllers.CollisionController;
import gameObject.collisionShapes2D.CollisionShape2D;
import gameObject.collisionShapes2D.CollisionState;
import gameObject.collisionShapes2D.Vertex;

import java.awt.*;
import java.util.List;

public class CollisionBatch {
    private CollisionController collisionController;

    public void drawCollisions(Graphics g){
        List<String> keys = collisionController.getKeys();
        if (keys == null){
            System.out.println("No collisions are transferred to the CollisionBatch");
            return;
        }
        for (String key : keys){
            CollisionShape2D shape = collisionController.getObjectCollisionShape(key);
            CollisionState state = collisionController.getObjectCollisionState(key);
            Vertex coordinates = collisionController.getCoordinates(key);
            Color insideColor = null;
            Color outsideColor = null;
            switch (state){
                case DEFAULT -> {
                    insideColor = new Color(20, 240, 20, 64);
                    outsideColor = new Color(20, 170, 20, 64);
                }
                case HOVER -> {
                    insideColor = new Color(240, 0, 0, 64);
                    outsideColor = new Color(170, 0, 0, 64);
                }
            }
            drawObject(g, coordinates, shape.getWidth(), shape.getHeight(), insideColor, outsideColor);
        }
        List<String> restrictedKeys = collisionController.getRestrictedKeys();
        if (restrictedKeys == null){
            System.out.println("No restricted collisions are transferred to the CollisionBatch");
            return;
        }
        for (String key : restrictedKeys){
            CollisionShape2D shape = collisionController.getRestrictedShape(key);
            CollisionState state = collisionController.getObjectCollisionState(key);
            Vertex coordinates = collisionController.getCoordinates(key);
            Color insideColor = null;
            Color outsideColor = null;
            switch (state){
                case DEFAULT -> {
                    insideColor = new Color(20, 240, 240, 64);
                    outsideColor = new Color(20, 170, 240, 64);
                }
                case HOVER -> {
                    insideColor = new Color(240, 0, 255, 64);
                    outsideColor = new Color(170, 0, 255, 64);
                }
            }
//            System.out.println("Working with the key: " + key + ". In collisionBatch.");
            drawObject(g, coordinates, shape.getWidth(), shape.getHeight(), insideColor, outsideColor);
        }
        List<String> allowedKeys = collisionController.getAllowedKeys();
        if (allowedKeys == null){
            System.out.println("No allowed collisions are transferred to the CollisionBatch");
            return;
        }
        for (String key : allowedKeys){
            CollisionShape2D shape = collisionController.getAllowedShape(key);
            CollisionState state = collisionController.getObjectCollisionState(key);
            Vertex coordinates = collisionController.getCoordinates(key);
            Color insideColor = null;
            Color outsideColor = null;
            switch (state){
                case DEFAULT -> {
                    insideColor = new Color(225, 191, 0, 64);
                    outsideColor = new Color(255, 221, 0, 64);
                }
                case HOVER -> {
                    insideColor = new Color(240, 0, 255, 64);
                    outsideColor = new Color(170, 0, 255, 64);
                }
            }
//            System.out.println("Working with the key: " + key + ". In collisionBatch.");
            drawObject(g, coordinates, shape.getWidth(), shape.getHeight(), insideColor, outsideColor);
        }
    }

    public void drawObject(Graphics g, Vertex centerCoordinate, int width, int height, Color insideColor,
                           Color outsideColor){
        int x = centerCoordinate.getX() - (width / 2);
        int y = centerCoordinate.getY() - (height / 2);
        g.setColor(insideColor);
        g.fillRect(x, y, width, height);
        g.setColor(outsideColor);
        g.drawRect(x, y, width-1, height-1);
        g.drawRect(x+1, y+1, width-3, height-3);
        g.drawRect(x+2, y+2, width-5, height-5);
        g.drawRect(x+3, y+3, width-7, height-7);
        g.drawRect(x+4, y+4, width-9, height-9);
    }

    public void setCollisionController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public boolean isControllerNull(){
        if (this.collisionController == null) {
            System.out.println("The CollisionController in CollisionBatch is null.");
            return true;
        }
        return false;
    }

    public void linkToCoordinatesController(){
        if (collisionController == null){
            System.out.println("CollisionController == null in CollisionBatch");
            return;
        }
        this.collisionController.linkToCoordinatesController();
    }
}
