package gameObject.interactions;

import gameObject.areas.DebugArea2D;

public interface Interactable {

    void onInteract();

    boolean canInteract();
    void calculateInteractable(DebugArea2D playerArea);
    void onMove(DebugArea2D playerArea);
}
