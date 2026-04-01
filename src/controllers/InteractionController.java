package controllers;

import util.GameController;

import java.util.HashMap;
import java.util.List;

public class InteractionController {
    private GameObjectCommandsManager commandsManager = GameController.getGameObjectCommandsManager();
    private HashMap<String, String> commands = new HashMap<>();
    private CollisionController collisionController;
    private String mainCollision;

    public InteractionController(CollisionController collisionController, String mainCollision){
        this.collisionController = collisionController;
        this.mainCollision = mainCollision;
    }

//// Should return:
/// - 0 if interaction was successful
/// - 1 if something went wrong
/// - 2 if the command is null or doesn't exist
    public int interact(String key){
        if ((!(commands.containsKey(key))) || commands.get(key) == null){
            return 2;
        }
        String commandKey = commands.get(key);
        int returnValue = commandsManager.executeCommand(commandKey);
        return returnValue;
    }

    public void addCommand(String key, String commandKey){
        commands.put(key, commandKey);
    }

    public void changeCommand(String key, String commandKey){
        commands.replace(key, commandKey);
    }

    public void tryInteracting(){
        List<String> collidedKeys = collisionController.checkCollisions(mainCollision);
        if (collidedKeys == null){
            return;
        }
        for (String key : collidedKeys){
            int returnValue = interact(key);
            switch (returnValue){
                case 2 -> {
                    System.out.println("The command doesn't exist. Key: " + key);
                }
                case 1 -> {
                    System.out.println("Something went wrong with the command. Key: " + key);
                }
            }
        }
    }
}
