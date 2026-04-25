import camera.Screen;
import gameObject.collisionShapes2D.Vertex;
import gameObject.image.GameObjectImagePackage;
import minigames.maze.*;
import sounds.AdvancedAudioManager;
import threads.*;
import ui.BackgroundAnimationCreator;
import util.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static Screen screen;

    public static void main(String[] args) throws Exception {
//        ScreenPanel panel = new ScreenPanel();
        AdvancedAudioManager audioManager = new AdvancedAudioManager();
        audioManager.loadSongs();
        audioManager.playBackgroundMusic(null, "MAIN_GAME");
        screen = new Screen();
        GameController.setScreen(screen);
        SceneManager.setGameObjectScenes(SceneBuilder.buildGameObjectScenes());
        GameController.getGameThreadController().addThread("UI_MAIN", new UIThread());
        SceneManager.setUiScenes(SceneBuilder.buildUIScenes(screen));
        SceneManager.setMinigameScenes(SceneBuilder.buildMinigameScenes());
        screen.displayUIScene(SceneManager.getUIScene("MAIN_MENU"));
        screen.displayGameObjectScene(SceneManager.getGameObjectScene("MAIN_MENU"));
        screen.getGamePanel().setImageController(GameController.getImageController());
        ((UIThread) GameController.getGameThreadController().getThreadBase("UI_MAIN")).setUiController(SceneManager.getUIScene("MAIN_MENU").uiController());
        GameController.getGameThreadController().addThread("GAMEPANEL_MAIN", new GamePanelUpdateThread(screen.getGamePanel()));
        GameController.getGameThreadController().addThread("GAMEOBJECT_ANIMATION", new GameObjectAnimationThread(false));
        GameController.getGameThreadController().addThread("IMAGE_RENDER", new ImageRenderingThread(false));
        GameController.getAdvancedAudioManager().preloadSound("coin pickup", "assets/sounds/sfx/coin-pickup.wav");
//        GameController.getGameThreadController().addThread("MOVEMENT_TEST", new MovementTestThread(true));
        GameController.getGameThreadController().addThread("MOVEMENT_MAIN", new MovementThread(true));
        GameController.getGameThreadController().addThread("SCENE_INTERACTIONS_MAIN", new SceneInteractionsUpdateThread(false, GameController.getSceneManager()));

        BackgroundAnimationCreator.create();
        GameController.getScreen().getGamePanel().setAnimation(BackgroundAnimationCreator.mainMenuAnimation);

        GameController.getGameObjectCoordinatesController().updateMainCoordinates(new Vertex(600, 530));

        InputCommandsManager.createCommands();

        GameController.getImageController().addImagePackage("Interaction arrow", new GameObjectImagePackage(null, new ImageIcon("assets/images/additional/interactionArrow.png").getImage(),
                null));
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

//        MazeMap mazeMap = new MazeMap(40, 40, 8, 5, 9);
//        MazeController mazeController = new MazeController();
//        mazeController.createMap(40, 40, 8, 5, 9);
//        MazeImageCreator mazeImageCreator = new MazeImageCreator(mazeController);
//        mazeImageCreator.getImage(640, 640);
//
//        mazeMap.updateTile(new Point(4, 4), TileType.DESTRUCTIBLE_WALL);
//        mazeMap.updateTile(new Point(5, 4), TileType.DESTRUCTIBLE_WALL);
//        mazeMap.updateTile(new Point(6, 4), TileType.DESTRUCTIBLE_WALL);
//        mazeMap.updateTile(new Point(7, 4), TileType.DESTRUCTIBLE_WALL);
//        mazeMap.updateTile(new Point(8, 4), TileType.DESTRUCTIBLE_WALL);
//
//        mazeMap.consoleLogMap();
//
//        CoordinatesController coordinatesController = new CoordinatesController();
//        coordinatesController.addCoordinates("Bomb", new Point(5, 5));
//
//        MazeCommandsExecutor mazeCommandsExecutor = new MazeCommandsExecutor(coordinatesController, mazeMap);
//        mazeCommandsExecutor.executeCommand(new ItemUsePackage("Bomb", 0, new ItemUseFlags[]{ItemUseFlags.REMOVE_DESTRUCTIBLE_WALLS}, UseTypeFlags.EXECUTE_RAD_2));
//
//        mazeMap.consoleLogMap();
    }
}