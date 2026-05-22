package minigames.core;

import util.GameController;

import java.util.concurrent.Callable;

public abstract class GamePlayer {
    private String threadName;
    private MinigameController minigameController;
    private boolean online = false;

    public GamePlayer(String threadName) {
        this.threadName = threadName;
    }

    public void stopThread(){
        GameController.getGameThreadController().disableThread(threadName);
    }

    public void startThread(){
        GameController.getGameThreadController().enableThread(threadName);
    }

    public String getThreadName() {
        return threadName;
    }

    public abstract Callable<Integer> getThreadCommand() throws Exception;

    public void setMinigameController(MinigameController minigameController) {
        this.minigameController = minigameController;
    }

    public MinigameController getMinigameController() {
        return minigameController;
    }

    public abstract void init();

    public boolean isOnline() {
        return online;
    }

    public void changeOnlineState(boolean state){
        this.online = state;
    }
}
