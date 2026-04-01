package util;

import camera.Screen;
import gameObject.GameObjectScene;
import ui.UIScene;

import java.util.HashMap;

public class SceneManager {
    private static HashMap<String, UIScene> uiScenes;
    private static HashMap<String, GameObjectScene> gameObjectScenes;
    private String scene;
    private Screen screen;

    public void changeScene(Screen screen, String scene){
        screen.displayUIScene(uiScenes.get(scene));
        screen.displayGameObjectScene(gameObjectScenes.get(scene));
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

    public static void setUiScenes(HashMap<String, UIScene> uiScenes) {
        SceneManager.uiScenes = uiScenes;
    }

    public static void setGameObjectScenes(HashMap<String, GameObjectScene> gameObjectScenes) {
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
}
