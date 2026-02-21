package util;

import camera.Screen;
import threads.UIAnimationThread;
import ui.Main;
import ui.UIBatch;
import ui.UIController;
import ui.UIUserLinker;

public class SceneManager {
    private Scene scene;

    public static void updateCurrentScene(Screen screen, Scene scene, UIAnimationThread uiAnimationThread, UIUserLinker uiUserLinker, UIBatch uiBatch){
        switch (scene){
            case MAIN_MENU -> {
                UIController uiController = Main.uiController;
                uiAnimationThread.setUIController(uiController);
                uiUserLinker.setUiController(uiController);
                uiBatch.setUiController(uiController);
            }
            case MAIN_GAME -> {
                UIController uiController = new UIController();
                uiAnimationThread.setUIController(uiController);
                uiUserLinker.setUiController(uiController);
                uiBatch.setUiController(uiController);
            }
        }
        screen.displayScene(scene);
    }
}
