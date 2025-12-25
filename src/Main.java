import camera.Resolution;
import camera.Screen;
import gameObject.GameObject;
import camera.Layer;
import sounds.AdvancedAudioManager;
import util.ObjCreator;

public class Main {
    public static void main(String[] args) {
        Screen screen = new Screen(new Resolution<>(16, 9, 4));
        ScreenPanel panel = new ScreenPanel();
        AdvancedAudioManager audioManager = new AdvancedAudioManager();
        audioManager.playBackgroundMusic("assets/sounds/music/Untitled song.wav");
        screen.add(ObjCreator.camera);
        ObjCreator.notificator.subscribe(ObjCreator.camera);
        screen.addKeyListener(new Mover());
        for (Layer layer : ObjCreator.layers) {
            for (GameObject object : layer) {
                object.onReady();
            }
        }
        ObjCreator.myThread.start();
    }
}