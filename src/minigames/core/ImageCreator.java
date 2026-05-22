package minigames.core;

import java.awt.*;

public abstract class ImageCreator {
    private MinigameController minigameController;

    public ImageCreator(MinigameController minigameController) {
        this.minigameController = minigameController;
    }

    public void setMinigameController(MinigameController minigameController) {
        this.minigameController = minigameController;
    }

    protected MinigameController getMinigameController(){
        return minigameController;
    }

    /// Returns the image for the GamePanel. Both width and height are final and calculated inside
    public abstract Image getImage(int width, int height);
}
