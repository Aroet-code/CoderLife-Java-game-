import camera.Resolution;
import camera.Screen;
import gameObject.GameObject;
import camera.Layer;
import sounds.AdvancedAudioManager;
import util.ObjCreator;
import util.Scene;

import static util.ObjCreator.screen;

public class Main {
    public static void main(String[] args) {
//        ScreenPanel panel = new ScreenPanel();
        AdvancedAudioManager audioManager = new AdvancedAudioManager();
        audioManager.playBackgroundMusic("assets/sounds/music/Untitled song.wav");
//        screen.add(ObjCreator.camera);
        ObjCreator.notificator.subscribe(ObjCreator.camera);
        screen.addKeyListener(new Mover());
        for (Layer layer : ObjCreator.layers) {
            for (GameObject object : layer) {
                object.onReady();
            }
        }
        ObjCreator.addObjects(ObjCreator.objects);
        ObjCreator.screen.addPanel(Scene.MAIN_GAME, ObjCreator.camera);
        ObjCreator.screen.addPanel(Scene.MAIN_MENU, ObjCreator.mainMenuBackground);
        ObjCreator.screen.displayScene(Scene.MAIN_MENU);
        ObjCreator.myThread.enable();
        ObjCreator.myThread.run();
        ObjCreator.uiAnimationThread.enable();
        ObjCreator.uiAnimationThread.run();
    }
}