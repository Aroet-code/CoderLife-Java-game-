package threads;

import util.SceneManager;

public class SceneInteractionsUpdateThread extends ThreadBase{
    SceneManager sceneManager;
    public SceneInteractionsUpdateThread(boolean enabled, SceneManager sceneManager) {
        super(enabled);
        this.sceneManager = sceneManager;
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()){
                sceneManager.updateSceneInteractions();
            }
        }
    }
}
