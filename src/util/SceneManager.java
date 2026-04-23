package util;

import camera.Screen;
import gameObject.GameObjectScene;
import minigames.core.MinigameScene;
import minigames.maze.MazeController;
import ui.UIScene;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static Map<String, UIScene> uiScenes;
    private static Map<String, GameObjectScene> gameObjectScenes;
    private static Map<String, MinigameScene> minigameScenes;
    private String scene;
    private Screen screen;

    public void changeScene(Screen screen, String scene){
        screen.displayUIScene(uiScenes.get(scene));
        screen.displayGameObjectScene(gameObjectScenes.get(scene));
        screen.displayMinigameScene(minigameScenes.get(scene));
        doNecessitiesForMinigameScene(minigameScenes.get(scene));
//        GameController.getUiAnimationController().clearAnimations();
        this.scene = scene;
    }

    public static UIScene getUIScene(String key){
        if (uiScenes.containsKey(key)){
            return uiScenes.get(key);
        } else {
            throw new NullPointerException("No such UI scene in the hashmap");
        }
    }

    public static GameObjectScene getGameObjectScene(String key){
        if (gameObjectScenes.containsKey(key)){
            return gameObjectScenes.get(key);
        } else {
            throw new NullPointerException("No such GameObject scene in the hashmap");
        }
    }

    public static void setUiScenes(Map<String, UIScene> uiScenes) {
        SceneManager.uiScenes = uiScenes;
    }

    public static void setMinigameScenes(Map<String, MinigameScene> minigameScenes){
        SceneManager.minigameScenes = minigameScenes;
    }

    public static void setGameObjectScenes(Map<String, GameObjectScene> gameObjectScenes) {
        SceneManager.gameObjectScenes = gameObjectScenes;
    }

    public void updateSceneInteractions(){
        if (scene == null){
            return;
        }
        gameObjectScenes.get(scene).gameObjectController().onUpdate("Player");
    }

    public void tryInteracting(){
        gameObjectScenes.get(scene).interactionController().tryInteracting();
    }

    public void doNecessitiesForMinigameScene(MinigameScene scene){
        if (scene.minigameController() instanceof MazeController){
            ((MazeController) scene.minigameController()).getCoordinatesController().setMazeMap(((((MazeController)scene.minigameController()).getMap())));
            GameController.getMinigameMovementController().setMazeCoordinatesController(
                    ((MazeController) scene.minigameController()).getCoordinatesController());

        }
    }
}
