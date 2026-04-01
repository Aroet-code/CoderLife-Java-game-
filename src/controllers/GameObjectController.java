package controllers;

import gameObject.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameObjectController {
    private HashMap<String, GameObject> gameObjects = new HashMap<>();
    private List<String> keys = new ArrayList<>();
    private CollisionController collisionController;

    public void onUpdate(String mainObjectKey){
        collisionController.updateCollisionStates(mainObjectKey);
    }

    public void setCollisionController(CollisionController collisionController){
        this.collisionController = collisionController;
    }

    public String[] getKeys(){
        return keys.toArray(new String[0]);
    }

    public void addKey(String key){
        keys.add(key);
    }

    public CollisionController getCollisionController(){
        return collisionController;
    }
}
