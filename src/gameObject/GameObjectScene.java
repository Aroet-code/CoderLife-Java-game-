package gameObject;

import controllers.AnimationController;
import controllers.CollisionController;
import controllers.GameObjectController;
import controllers.InteractionController;

import javax.swing.*;

public record GameObjectScene(CollisionController collisionController, GameObjectController gameObjectController,
                              InteractionController interactionController, AnimationController animationController,
                              InputMap inputMap, ActionMap actionMap) {
    @Override
    public GameObjectController gameObjectController() {
        if (gameObjectController.getCollisionController() == null){
            gameObjectController.setCollisionController(collisionController);
        }
        return gameObjectController;
    }
}
