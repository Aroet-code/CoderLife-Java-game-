package threads;

import minigames.core.GamePlayer;

import java.util.concurrent.Callable;

public class CookingMinigameThread extends MinigameThreadBase{
    GamePlayer gp;

    public CookingMinigameThread(boolean enabled, GamePlayer gp) {
        super(enabled);
        this.gp = gp;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Thread.sleep(16);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()) {
                if (gp == null){
                    continue;
                }
                if (!gp.isOnline()){
                    gp.init();
                    continue;
                }
                Callable<Integer> command = null;
                try {
                    command = gp.getThreadCommand();
                    command.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
