package threads;

import minigames.core.GamePlayer;

public class MinigameThreadBase extends ThreadBase{
    private GamePlayer gp;

    public MinigameThreadBase(boolean enabled) {
        super(enabled);
    }

    public void setGp(GamePlayer gp) {
        this.gp = gp;
    }

    public GamePlayer getGp() {
        return gp;
    }
}
