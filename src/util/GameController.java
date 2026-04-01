package util;

import camera.GamePanel;
import camera.Screen;
import controllers.*;
import gameObject.image.ImageScaler;
import sounds.AdvancedAudioManager;
import ui.UICommandsManager;

public class GameController {
    private static Screen screen;
    private static AnimationController animationController;
    private static UIAnimationController uiAnimationController;
    private static GameObjectCommandsManager gameObjectCommandsManager;
    private static GameObjectCoordinatesController gameObjectCoordinatesController;
    private static UICommandsManager uiCommandsManager;
    private static GameThreadController gameThreadController;
    private static AdvancedAudioManager advancedAudioManager;
    private static ImageController imageController;
    private static SceneBuilder sceneBuilder;
    private static SceneManager sceneManager;
    private static ImageScaler imageScaler;
    private static MovementController movementController;

    public static AnimationController getAnimationController() {
        if (animationController == null){
            animationController = new AnimationController(GameController.getImageController());
        }
        return animationController;
    }

    public static UIAnimationController getUiAnimationController(){
        if (uiAnimationController == null){
            uiAnimationController = new UIAnimationController();
        }
        return uiAnimationController;
    }

    public static GameObjectCommandsManager getGameObjectCommandsManager() {
        if (gameObjectCommandsManager == null) {
            gameObjectCommandsManager = new GameObjectCommandsManager();
        }
        return gameObjectCommandsManager;
    }

    public static GameThreadController getGameThreadController() {
        if (gameThreadController == null){
            gameThreadController = new GameThreadController();
        }
        return gameThreadController;
    }

    public static AdvancedAudioManager getAdvancedAudioManager() {
        if (advancedAudioManager == null){
            advancedAudioManager = new AdvancedAudioManager();
        }
        return advancedAudioManager;
    }

    public static SceneBuilder getSceneBuilder() {
        if (sceneBuilder == null){
            sceneBuilder = new SceneBuilder();
        }
        return sceneBuilder;
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null){
            sceneManager = new SceneManager();
        }
        return sceneManager;
    }

    public static UICommandsManager getUiCommandsManager() {
        if (uiCommandsManager == null) {
            uiCommandsManager = new UICommandsManager();
        }
        return uiCommandsManager;
    }

    public static Screen getScreen(){
        if (screen == null){
            throw new NullPointerException("No screen in GameController.");
        }
        return screen;
    }

    public static ImageController getImageController() {
        if (imageController == null){
            imageController = new ImageController();
        }
        return imageController;
    }

    public static GameObjectCoordinatesController getGameObjectCoordinatesController() {
        if (gameObjectCoordinatesController == null) {
            gameObjectCoordinatesController = new GameObjectCoordinatesController();
        }
        return gameObjectCoordinatesController;
    }

    public static ImageScaler getImageScaler() {
        if (imageScaler == null){
            imageScaler = new ImageScaler(getImageController());
        }
        return imageScaler;
    }

    public static MovementController getMovementController() {
        if (movementController == null){
            movementController = new MovementController(GameController.getGameObjectCoordinatesController());
        }
        return movementController;
    }

    public static void setScreen(Screen screen){
        GameController.screen = screen;
    }
}
