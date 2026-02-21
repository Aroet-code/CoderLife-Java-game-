package threads;

import ui.Main;
import ui.UIAnimationController;
import ui.UIController;

public class UIAnimationThread implements Runnable{
    boolean enabled = false;
    private UIController uiController;
    private UIAnimationController currentUIAnimationController;

    public UIAnimationThread(UIController uiController, UIAnimationController uiAnimationController, boolean enabled) {
        this.uiController = uiController;
        this.enabled = enabled;
        this.currentUIAnimationController = uiAnimationController;
    }

    @Override
    public void run() {
        while (enabled) {
            try {
                currentUIAnimationController.animateUI(uiController);
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void enable(){
        enabled = true;
    }

    public void disable(){
        enabled = false;
    }

    public void setUIController(UIController uiController){
        this.uiController = uiController;
    }
}
