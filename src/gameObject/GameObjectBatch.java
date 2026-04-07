package gameObject;

import controllers.GameObjectController;
import controllers.GameObjectCoordinatesController;
import controllers.ImageController;
import gameObject.collisionShapes2D.CollisionState;
import gameObject.collisionShapes2D.Vertex;
import gameObject.image.DrawObject;
import gameObject.image.ImageScaler;
import util.GameController;

import java.awt.*;
import java.awt.image.ImageObserver;

public class GameObjectBatch {
    private GameObjectController gameObjectController;
    private GameObjectCoordinatesController gameObjectCoordinatesController;
    private ImageController imageController;
    private ImageScaler imageScaler = GameController.getImageScaler();

    public void setGameObjectController(GameObjectController gameObjectController) {
        this.gameObjectController = gameObjectController;
    }

    public void setImageScaler(ImageScaler imageScaler) {
        this.imageScaler = imageScaler;
    }

    public void setGameObjectCoordinatesController(GameObjectCoordinatesController gameObjectCoordinatesController) {
        this.gameObjectCoordinatesController = gameObjectCoordinatesController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
        this.imageScaler.setImageController(imageController);
    }

    @Deprecated
    public void drawGameObjects(Graphics g, ImageObserver observer){
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight());
//        String[] keys = gameObjectController.getKeys();
//        imageController.renderImages(keys, null);
//        for (String key : keys){
//            DrawObject drawObject = imageController.getRenderedObject(key);
//            drawObject(g, drawObject, observer);
//        }
    }

    @Deprecated
    public void drawObject(Graphics g, DrawObject drawObject, ImageObserver observer){
        g.drawImage(drawObject.getImage(), drawObject.getVertex().getX() - drawObject.getImage().getWidth(observer) / 2,
                drawObject.getVertex().getY() - drawObject.getImage().getHeight(observer), observer);
    }

    public boolean isControllerNull(){
        if (this.gameObjectController == null){
            System.out.println("GameObjectController is null in GameObjectBatch");
            return true;
        }
        if (this.imageController == null){
            System.out.println("ImageController is null in GameObjectBatch");
            return true;
        }
        return false;
    }

    public void drawObject(Graphics g, String key, ImageObserver imageObserver){
        Image image = imageController.getRenderedImage(key);
        Vertex coordinates = gameObjectCoordinatesController.getActualDrawingCoordinates(key, imageScaler.getImageScalingMultiplier());
        int width = image.getWidth(imageObserver);
        int height = image.getHeight(imageObserver);
        g.drawImage(image, (GameController.getScreen().getWidth() / 2) + coordinates.getX() - (width / 2),
                (GameController.getScreen().getHeight() / 2) + coordinates.getY() - (height /2 ), imageObserver);
    }

    public void drawAllObjects(Graphics g, ImageObserver imageObserver){
        String[] keys = gameObjectController.getKeys();
        for (String key : keys){
            drawObject(g, key, imageObserver);
        }
    }

    public void drawInteractionArrows(Graphics g, ImageObserver imageObserver){
        Image image = imageController.getRenderedImage("Interaction arrow");
        for (String key : gameObjectController.getKeys()){
            CollisionState state = gameObjectController.getCollisionController().getObjectCollisionState(key);
            if (state != CollisionState.HOVER){
                continue;
            }
            GameObjectState gameObjectState = imageController.getState(key);
            if (gameObjectState != GameObjectState.DEFAULT){
                continue;
            }
            int width = image.getWidth(imageObserver);
            int height = image.getHeight(imageObserver);
            Vertex coordinates = gameObjectCoordinatesController.getActualDrawingCoordinates(key, imageScaler.getImageScalingMultiplier());
            coordinates.setY(coordinates.getY() - height / 2);
            g.drawImage(image, (GameController.getScreen().getWidth() / 2) + coordinates.getX() - (width / 2),
                    (GameController.getScreen().getHeight() / 2) + coordinates.getY() - (height /2 ), imageObserver);
        }
    }
}
