package camera;

import controllers.*;
import gameObject.GameObjectBatch;
import ui.AnimatedBackground;
import ui.BackgroundAnimation;
import ui.UIBatch;
import util.CollisionBatch;
import util.GameController;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends AnimatedBackground {
    private UIBatch uiBatch;
    private CollisionBatch collisionBatch;
    private GameObjectBatch gameObjectBatch;
    private boolean isBackgroundAnimationAvailable = false;
    private boolean drawCollisions = true;

    public GamePanel() {
        uiBatch = new UIBatch(null);
        collisionBatch = new CollisionBatch();
        gameObjectBatch = new GameObjectBatch();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        System.out.println("Repainted.");
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//        if (isBackgroundAnimationAvailable) {
//            super.paintComponent(g);
//        }
        if (!(this.isBackgroundAnimationAvailable)){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (!(gameObjectBatch.isControllerNull())){
            gameObjectBatch.drawAllObjects(g, GameController.getImageController());
        }
        if (drawCollisions) {
            collisionBatch.linkToCoordinatesController();
            if (!(collisionBatch.isControllerNull())) {
                collisionBatch.drawCollisions(g);
            }
        }
        if (uiBatch.isUiControllerNull()){
            return;
        }
        uiBatch.drawUI(g, null);
//        uiBatch.drawCollisionShapes(g);
    }

    public void setUIController(UIController uiController) {
        uiBatch.setUiController(uiController);
    }

    public void setGameObjectController(GameObjectController gameObjectController){
        gameObjectBatch.setGameObjectController(gameObjectController);
    }

    public void setImageController(ImageController imageController){
        gameObjectBatch.setImageController(imageController);
    }

    public void setCollisionController(CollisionController collisionController){
        collisionBatch.setCollisionController(collisionController);
    }

    public void setCoordinatesController(GameObjectCoordinatesController gameObjectCoordinatesController){
        gameObjectBatch.setGameObjectCoordinatesController(gameObjectCoordinatesController);
    }

    @Override
    public void setAnimation(BackgroundAnimation animation){
        super.setAnimation(animation);
        isBackgroundAnimationAvailable = true;
        if (animation == null){
            isBackgroundAnimationAvailable = false;
        }
    }

    public void toggleDrawCollisions(boolean value){
        drawCollisions = value;
    }
}
