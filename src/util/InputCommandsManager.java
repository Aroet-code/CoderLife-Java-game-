package util;

import gameObject.movement.MovementCommand;
import gameObject.movement.MovementCommandPackage;
import ui.BackgroundAnimationCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class InputCommandsManager {
    private static Map<String, Callable<Integer>> commands;

    public static void callCommand(String key) throws Exception {
        Callable<Integer> command = getCommand(key);
        command.call();
    }

    private static Callable<Integer> getCommand(String key){
        if (commands == null) {
            throw new NullPointerException("Commands are null");
        }
        if (!(commands.containsKey(key))){
            throw new NullPointerException("No such command in InputCommandsManager");
        }
        return commands.get(key);
    }

    public static void createCommands(){
        commands = new HashMap<>();
        Callable<Integer> startGame = () -> {
//            GameController.getSceneManager().changeScene(GameController.getScreen(), "MAIN_GAME");
            GameController.getScreen().getGamePanel().setAnimation(BackgroundAnimationCreator.additionalAnimation);
            GameController.getGameThreadController().enableThread("GAMEOBJECT_ANIMATION");
            GameController.getGameThreadController().enableThread("SCENE_INTERACTIONS_MAIN");
            GameController.getGameThreadController().enableThread("IMAGE_RENDER");
            return 0;
        };
        commands.put("START_GAME", startGame);
        Callable<Integer> backToMainMenu = () -> {
            GameController.getSceneManager().changeScene(GameController.getScreen(), "MAIN_MENU");
            GameController.getScreen().getGamePanel().setAnimation(BackgroundAnimationCreator.mainMenuAnimation);
            GameController.getGameThreadController().disableThread("GAMEOBJECT_ANIMATION");
            GameController.getGameThreadController().disableThread("SCENE_INTERACTIONS_MAIN");
            GameController.getGameThreadController().enableThread("IMAGE_RENDER");
            return 0;
        };
        commands.put("BACK_TO_MAIN_MENU", backToMainMenu);
        Callable<Integer> moveRight = () -> {
            GameController.getMovementController().executeCommand(new MovementCommandPackage("Player", MovementCommand.START_MOVING_RIGHT));
            return 0;
        };
        commands.put("MOVE_RIGHT", moveRight);
        Callable<Integer> moveLeft = () -> {
            GameController.getMovementController().executeCommand(new MovementCommandPackage("Player", MovementCommand.START_MOVING_LEFT));
            return 0;
        };
        commands.put("MOVE_LEFT", moveLeft);
        Callable<Integer> moveDown = () -> {
            GameController.getMovementController().executeCommand(new MovementCommandPackage("Player", MovementCommand.START_MOVING_DOWN));
            return 0;
        };
        commands.put("MOVE_DOWN", moveDown);
        Callable<Integer> moveUp = () -> {
            GameController.getMovementController().executeCommand(new MovementCommandPackage("Player", MovementCommand.START_MOVING_UP));
            return 0;
        };
        commands.put("MOVE_UP", moveUp);
        Callable<Integer> stopMovingX = () -> {
            GameController.getMovementController().executeCommand(new MovementCommandPackage("Player", MovementCommand.STOP_X));
            return 0;
        };
        commands.put("STOP_MOVING_X", stopMovingX);
        Callable<Integer> stopMovingY = () -> {
            GameController.getMovementController().executeCommand(new MovementCommandPackage("Player", MovementCommand.STOP_Y));
            return 0;
        };
        commands.put("STOP_MOVING_Y", stopMovingY);
        Callable<Integer> interact = () -> {
            GameController.getSceneManager().tryInteracting();
            return 0;
        };
        commands.put("INTERACT", interact);
        Callable<Integer> expand = () -> {
            GameController.getImageScaler().expand();
            return 0;
        };
        commands.put("EXPAND", expand);
        Callable<Integer> shrink = () -> {
            GameController.getImageScaler().shrink();
            return 0;
        };
        commands.put("SHRINK", shrink);
        Callable<Integer> switchFreeCameraMovement = () -> {
            GameController.getMovementController().switchFollowPlayer();
            GameController.getMovementController().switchAllowPlayerMovement();
            return 0;
        };
        commands.put("FREE_CAMERA", switchFreeCameraMovement);
    }
}
