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

    public abstract Image getImage(int width, int height);
}
