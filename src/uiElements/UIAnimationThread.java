public class UIAnimationThread implements Runnable{
    boolean enabled = true;
    UIController uiController;

    public UIAnimationThread(UIController uiController, boolean enabled) {
        this.uiController = uiController;
        this.enabled = enabled;
    }

    @Override
    public void run() {
        while (enabled) {
            try {
                Main.uiAnimationController.animateUI(uiController);
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
