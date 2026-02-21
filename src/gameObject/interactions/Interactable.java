package gameObject.interactions;

import gameObject.areas.DebugArea2D;

import java.util.concurrent.Callable;

public interface Interactable {
    Callable<Integer> command = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            return 0;
        }
    };

    void onInteract();

    boolean canInteract();
    void calculateInteractable(DebugArea2D playerArea);
    void onMove(DebugArea2D playerArea);
    void setCommand(Callable<Integer> command);
}
