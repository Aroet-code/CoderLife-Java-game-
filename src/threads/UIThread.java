package threads;

import controllers.UIController;
import util.GameController;

public class UIThread extends ThreadBase{
    private UIController uiController;

    public UIThread() {
        super(true);
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()){
//                System.out.println("The ui should be animating. At least the thread executes.");
                GameController.getUiAnimationController().animateUI(uiController);
            }
        }
    }

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }
}
