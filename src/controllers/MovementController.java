package controllers;

import gameObject.collisionShapes2D.Vertex;
import gameObject.movement.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MovementController {
    private CollisionController collisionController;
    private GameObjectCoordinatesController coordinatesController;
    private Map<String, Axis> axisMap = new HashMap<>();
    private Map<String, MovementState> stateMapX = new HashMap<>();
    private Map<String, MovementState> stateMapY = new HashMap<>();
    private Map<String, Integer> speeds = new HashMap<>();

    public MovementController(GameObjectCoordinatesController gameObjectCoordinatesController){
        this.coordinatesController = gameObjectCoordinatesController;
    }

    public void manageAcceleratingX(){
        for (String key : stateMapX.keySet()){
            if (stateMapX.get(key) == MovementState.ACCELERATING){
                axisMap.get(key).speedUpX();
                if (axisMap.get(key).getX() == 1f || axisMap.get(key).getX() == -1f){
                    stateMapX.replace(key, MovementState.MOVING);
                }
            }
        }
    }

    public void addObject(String key, int speed){
        axisMap.put(key, new Axis(0, 0));
        stateMapX.put(key, MovementState.STILL);
        stateMapY.put(key, MovementState.STILL);
        speeds.put(key, speed);
    }

    public void manageAcceleratingY(){
        for (String key : stateMapY.keySet()){
            if (stateMapY.get(key) == MovementState.ACCELERATING){
                axisMap.get(key).speedUpY();
                if (axisMap.get(key).getY() == 1f || axisMap.get(key).getY() == -1f){
                    stateMapY.replace(key, MovementState.MOVING);
                }
            }
        }
    }

    public void manageStoppingX(){
        for (String key : stateMapX.keySet()){
            if (stateMapX.get(key) == MovementState.STOPPING){
                axisMap.get(key).slowDownX();
                if (axisMap.get(key).getX() == 0f){
                    stateMapX.replace(key, MovementState.STILL);
                }
            }
        }
    }

    public void manageStoppingY(){
        for (String key : stateMapY.keySet()){
            if (stateMapY.get(key) == MovementState.STOPPING){
                axisMap.get(key).slowDownY();
                if (axisMap.get(key).getY() == 0f){
                    stateMapY.replace(key, MovementState.STILL);
                }
            }
        }
    }

    public void manageAccelerating(){
        manageAcceleratingX();
        manageAcceleratingY();
    }

    public void manageStopping(){
        manageStoppingX();
        manageStoppingY();
    }

    public boolean keyExists(String key){
        return axisMap.containsKey(key);
    }

    public void moveX(String key){
        Vertex oldCoordinate = coordinatesController.getCoordinates(key);
        Vertex newCoordinates = new Vertex((int) (coordinatesController.getCoordinates(key).getX() + (speeds.get(key) * axisMap.get(key).getX())),
                (int) (coordinatesController.getCoordinates(key).getY()));
        Vertex checkCoordinates = new Vertex((int) (coordinatesController.getCoordinates(key).getX() + (speeds.get(key) * axisMap.get(key).getX())),
                (int) (coordinatesController.getCoordinates(key).getY()));
        coordinatesController.updateCoordinates(key, checkCoordinates);
        collisionController.updateCoordinates(key, checkCoordinates);
        if ((collisionController.isPositionPossible("Player"))){
//            axisMap.get(key).setX(0);
//            stateMapX.replace(key, MovementState.STILL);
//            System.out.println("Old coordinate x: " + oldCoordinate.getX() + " old coordinate y: " + oldCoordinate.getY());
//            System.out.println("New coordinate x: " + newCoordinates.getX() + " new coordinate y: " + newCoordinates.getY());
            coordinatesController.updateCoordinates(key, newCoordinates);
            collisionController.updateCoordinates(key, newCoordinates);
        } else {
            coordinatesController.updateCoordinates(key, oldCoordinate);
            collisionController.updateCoordinates(key, oldCoordinate);
        }
    }

    public void moveY(String key){
        Vertex oldCoordinate = coordinatesController.getCoordinates(key);
        Vertex newCoordinates = new Vertex((int) (coordinatesController.getCoordinates(key).getX()),
                (int) (coordinatesController.getCoordinates(key).getY() + (speeds.get(key) * axisMap.get(key).getY())));
        Vertex checkCoordinates = new Vertex((int) (coordinatesController.getCoordinates(key).getX()),
                (int) (coordinatesController.getCoordinates(key).getY() + (speeds.get(key) * axisMap.get(key).getY())));
        coordinatesController.updateCoordinates(key, newCoordinates);
        collisionController.updateCoordinates(key, checkCoordinates);
        if ((collisionController.isPositionPossible("Player"))){
//            axisMap.get(key).setY(0);
//            stateMapX.replace(key, MovementState.STILL);
//            System.out.println("Old coordinate x: " + oldCoordinate.getX() + " old coordinate y: " + oldCoordinate.getY());
//            System.out.println("New coordinate x: " + newCoordinates.getX() + " new coordinate y: " + newCoordinates.getY());
            coordinatesController.updateCoordinates(key, newCoordinates);
            collisionController.updateCoordinates(key, newCoordinates);
        } else {
            coordinatesController.updateCoordinates(key, oldCoordinate);
            collisionController.updateCoordinates(key, oldCoordinate);
        }
    }

    public void manageMovement(){
//        manageStopping();
//        manageAccelerating();
//        System.out.println("X values: ");
//        System.out.println(stateMapX.values());
//        System.out.println("Y values: ");
//        System.out.println(stateMapY.values());
        for (String key : stateMapX.keySet()){
            MovementState state = stateMapX.get(key);
            if (state == MovementState.STILL){
                if (axisMap.get(key).getX() != 0f){
                    System.out.println("There it is.");
                }
                continue;
            }
            moveX(key);
            if (Objects.equals(key, "Player")){
                updateCameraCoordinates((int) (axisMap.get(key).getX() * speeds.get(key)), 0);
            }
        }
        for (String key : stateMapY.keySet()){
            MovementState state = stateMapY.get(key);
            if (state == MovementState.STILL){
                if (axisMap.get(key).getY() != 0f){
                    System.out.println("There it is. Except it's the Y one");
                }
                continue;
            }
            moveY(key);
            if (Objects.equals(key, "Player")){
                updateCameraCoordinates(0, (int) (axisMap.get(key).getY() * speeds.get(key)));
            }
        }

    }

    public void executeCommand(MovementCommandPackage commandPackage){
        String key = commandPackage.key();
        MovementCommand command = commandPackage.command();
        if (!(keyExists(key))){
            System.out.println("The object isn't in the system in Movement Controller. Object ID: " + key);
        }
        switch (command){
            case STOP_X -> {
                if (stateMapX.get(key) != MovementState.STILL && stateMapX.get(key) != MovementState.STOPPING){
                    stateMapX.replace(key, MovementState.STOPPING);
                }
            }
            case STOP_Y -> {
                if (stateMapY.get(key) != MovementState.STILL && stateMapY.get(key) != MovementState.STOPPING){
                    stateMapY.replace(key, MovementState.STOPPING);
                }
            }
            case START_MOVING_UP -> {
                if (stateMapY.get(key) != MovementState.MOVING && stateMapY.get(key) != MovementState.ACCELERATING){
                    stateMapY.replace(key, MovementState.ACCELERATING);
                    axisMap.get(key).setDirectionY(Direction.UP);
                }
            }
            case START_MOVING_DOWN -> {
                if (stateMapY.get(key) != MovementState.MOVING && stateMapY.get(key) != MovementState.ACCELERATING){
                    stateMapY.replace(key, MovementState.ACCELERATING);
                    axisMap.get(key).setDirectionY(Direction.DOWN);
                }
            }
            case START_MOVING_LEFT -> {
                if (stateMapX.get(key) != MovementState.MOVING && stateMapX.get(key) != MovementState.ACCELERATING){
                    stateMapX.replace(key, MovementState.ACCELERATING);
                    axisMap.get(key).setDirectionX(Direction.LEFT);
                }
            }
            case START_MOVING_RIGHT -> {
                if (stateMapX.get(key) != MovementState.MOVING && stateMapX.get(key) != MovementState.ACCELERATING){
                    stateMapX.replace(key, MovementState.ACCELERATING);
                    axisMap.get(key).setDirectionX(Direction.RIGHT);
                }
            }
        }
    }

    public void updateCameraCoordinates(int addX, int addY){
        coordinatesController.updateMainCoordinates(addX, addY);
    }

    public void setCollisionController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }
}
