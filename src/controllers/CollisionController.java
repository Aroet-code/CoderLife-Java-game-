package controllers;

import gameObject.collisionShapes2D.CollisionShape2D;
import gameObject.collisionShapes2D.CollisionState;
import gameObject.collisionShapes2D.RestrictedShape2D;
import gameObject.collisionShapes2D.Vertex;

import java.util.*;

public class CollisionController {
    private GameObjectCoordinatesController coordinatesController;
    private HashMap<String, CollisionShape2D> collisions = new HashMap<>();
    private HashMap<String, CollisionState> states = new HashMap<>();
    private HashMap<String, RestrictedShape2D> restrictedShapes = new HashMap<>();
    private List<String> keys = new ArrayList<>();
    private List<String> restrictedKeys = new ArrayList<>();

    public CollisionController(GameObjectCoordinatesController gameObjectCoordinatesController){
        this.coordinatesController = gameObjectCoordinatesController;
    }

    public void addRestrictedShape(String key, RestrictedShape2D restrictedShape2D){
        restrictedShapes.put(key, restrictedShape2D);
        states.put(key, CollisionState.DEFAULT);
        restrictedKeys.add(key);
    }

    public void addCollision(String key, CollisionShape2D collisionShape, CollisionState state){
        collisions.put(key, collisionShape);
        states.put(key, state);
        keys.add(key);
    }

    public void updateCoordinates(String key, int addX, int addY){
        collisions.get(key).setCenterCoordinates(new Vertex(collisions.get(key).getCenterCoordinates().getX() + addX,
                collisions.get(key).getCenterCoordinates().getY() + addY));
        coordinatesController.updateCoordinates(key, new Vertex(collisions.get(key).getCenterCoordinates().getX() + addX,
                collisions.get(key).getCenterCoordinates().getY() + addY));
    }

    public void linkToCoordinatesController(){
        for (String key : collisions.keySet()){
            collisions.get(key).setCenterCoordinates(coordinatesController.getCoordinates(key));
        }
    }

    public void updateCoordinates(String key, Vertex coordinates){
        collisions.get(key).setCenterCoordinates(coordinates);
        coordinatesController.updateCoordinates(key, coordinates);
    }

    public ArrayList<String> checkCollisions(String key) {
        CollisionShape2D mainCollision = collisions.get(key);
        if (mainCollision == null){
            System.out.println("The main collision in CollisionController == null");
        }
        ArrayList<String> collidedWith = new ArrayList<>();
        for (String nextObjectKey : collisions.keySet()) {
            assert mainCollision != null;
            if (Objects.equals(nextObjectKey, key)){
                continue;
            }
//            System.out.println(nextObjectKey + "'s coordinates. X: " + collisions.get(nextObjectKey).getCenterCoordinates().getX() +
//                    " Y: " + collisions.get(nextObjectKey).getCenterCoordinates().getY());
            if (mainCollision.intersects(collisions.get(nextObjectKey))){
                collidedWith.add(nextObjectKey);
            }
        }
        if (collidedWith.isEmpty()){
            return null;
        }
        return collidedWith;
    }

    public void updateCollisionStates(String key){
        ArrayList<String> collidedWith = checkCollisions(key);
        if (collidedWith == null){
            for (String collisionKey : collisions.keySet()){
                states.replace(collisionKey, CollisionState.DEFAULT);
            }
            return;
        }
        for (String collisionKey : collisions.keySet()){
            if (collidedWith.contains(collisionKey)){
                states.replace(collisionKey, CollisionState.HOVER);
//                System.out.println("Collided!");
            } else {
                states.replace(collisionKey, CollisionState.DEFAULT);
            }
        }
    }

    public boolean isPositionPossible(String key){
        CollisionShape2D mainShape = collisions.get(key);
        for (RestrictedShape2D restrictedShape : restrictedShapes.values()){
            if (restrictedShape.intersects(mainShape)){
                return false;
            }
        }
        return true;
    }

    public List<String> getRestrictedKeys(){
        return restrictedKeys;
    }

    public List<String> getKeys() {
        return keys;
    }

    public CollisionState getObjectCollisionState(String key){
        return states.get(key);
    }

    public CollisionShape2D getObjectCollisionShape(String key){
        return collisions.get(key);
    }

    public Collection<RestrictedShape2D> getRestrictedShapes(){
        return restrictedShapes.values();
    }

    public RestrictedShape2D getRestrictedShape(String key){
        return restrictedShapes.get(key);
    }

    public Vertex getCoordinates(String key){
        return coordinatesController.getCoordinates(key);
    }

    public void removeRestrictedShape(String key){
        if (!(restrictedShapes.containsKey(key))){
            return;
        }
        restrictedShapes.remove(key);
    }
}
