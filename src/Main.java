import camera.Resolution;
import camera.Screen;
import gameObject.collisionShapes2D.Vertex;
import sounds.AdvancedAudioManager;
import threads.*;
import util.*;

import javax.swing.*;

public class Main {
    public static Screen screen;

    public static void main(String[] args) throws Exception {
//        ScreenPanel panel = new ScreenPanel();
        AdvancedAudioManager audioManager = new AdvancedAudioManager();
        audioManager.playBackgroundMusic("assets/sounds/music/Untitled song.wav");
        screen = new Screen();
        GameController.setScreen(screen);
        SceneManager.setGameObjectScenes(SceneBuilder.buildGameObjectScenes());
        GameController.getGameThreadController().addThread("UI_MAIN", new UIThread());
        SceneManager.setUiScenes(SceneBuilder.buildUIScenes(screen));
        screen.displayUIScene(SceneManager.getUIScene("MAIN_MENU"));
        screen.displayGameObjectScene(SceneManager.getGameObjectScene("MAIN_MENU"));
        screen.getGamePanel().setImageController(GameController.getImageController());
        ((UIThread) GameController.getGameThreadController().getThreadBase("UI_MAIN")).setUiController(SceneManager.getUIScene("MAIN_MENU").uiController());
        GameController.getGameThreadController().addThread("GAMEPANEL_MAIN", new GamePanelUpdateThread(screen.getGamePanel()));
        GameController.getGameThreadController().addThread("GAMEOBJECT_ANIMATION", new GameObjectAnimationThread(false));
        GameController.getGameThreadController().addThread("IMAGE_RENDER", new ImageRenderingThread(false));
//        GameController.getGameThreadController().addThread("MOVEMENT_TEST", new MovementTestThread(true));
        GameController.getGameThreadController().addThread("MOVEMENT_MAIN", new MovementThread(true));
        GameController.getGameThreadController().addThread("SCENE_INTERACTIONS_MAIN", new SceneInteractionsUpdateThread(false, GameController.getSceneManager()));

        BackgroundAnimationCreator.create();
        GameController.getScreen().getGamePanel().setAnimation(BackgroundAnimationCreator.mainMenuAnimation);

        GameController.getGameObjectCoordinatesController().updateMainCoordinates(new Vertex(600, 594));

        InputCommandsManager.createCommands();
        
//        screen.add(ObjCreator.camera);
//        ObjCreator.notificator.subscribe(ObjCreator.camera);
//        screen.addKeyListener(new Mover());
//        for (Layer layer : ObjCreator.layers) {
//            for (GameObject object : layer) {
//                object.onReady();
//            }
//        }
//        ObjCreator.addObjects(ObjCreator.objects);
//        ObjCreator.myThread.enable();
//        ObjCreator.myThread.run();
//        ObjCreator.uiAnimationThread.enable();
//        ObjCreator.uiAnimationThread.run();

        GameController.getGameThreadController().runThreads();
    }
}