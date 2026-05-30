package minigames.cooking;

import minigames.core.GamePlayer;
import util.MovementStateHandler;

import java.util.List;
import java.util.concurrent.Callable;

public class CookingGamePlayer extends GamePlayer {
    private float ar = 5;

    public CookingGamePlayer(String threadName) {
        super(threadName);
    }

    @Override
    public void init() {
        CookingMinigameController gc = new CookingMinigameController();
        setMinigameController(gc);
        this.changeOnlineState(true);
    }

    public void startGame(float ar, float secondsDiff, float maxSecondsDiff, int objectsAmount, String difficulty){
        CookingMinigameController gc = getMinigameController();
        gc.init(ar, secondsDiff, maxSecondsDiff, objectsAmount, difficulty);
        this.ar = ar;
    }

    @Override
    public Callable<Integer> getThreadCommand() throws Exception {
        if (!isOnline()){
            throw new Exception("The CookingGamePlayer is offline.");
        }
        return () -> {
            var gc = getMinigameController();
            List<String> playerCollisions = gc.collisionController.getCollisions("Player");

            for (var c : playerCollisions){
                if (c.equals("Player") || c.equals("Floor")){
                    continue;
                }
                CookingCollisionCommand ccc = new CookingCollisionCommand(CookingCollisionCommandFlags.COLLISION_DETECTED, c);
                gc.reactToCollisionCommand(ccc);
            }

            List<String> floorCollisions = gc.collisionController.getCollisions("Floor");

            for (var c : floorCollisions){
                if (c.equals("Floor") || c.equals("Player")){
                    continue;
                }
                CookingCollisionCommand ccc = new CookingCollisionCommand(CookingCollisionCommandFlags.COLLISION_MISSED, c);
                gc.reactToCollisionCommand(ccc);
            }

            boolean right = MovementStateHandler.movingRight;
            boolean left = MovementStateHandler.movingLeft;

            float dir = 0;

            if (!(right || left) || (right && left)){
                dir = 0;
            } else if (right){
                dir = 1;
            } else if (left){
                dir = -1;
            }

            boolean boosted = MovementStateHandler.holdingShift;

            gc.mc.play(ar, dir, boosted);

            gc.collisionController.linkToCoordinatesController();

            return 0;
        };
    }

    @Override
    public CookingMinigameController getMinigameController() {
        return (CookingMinigameController) super.getMinigameController();
    }

    public float getAr() {
        return ar;
    }

    public void setAr(float ar) {
        this.ar = ar;
    }

    public void stop(){
        changeOnlineState(false);
    }
}
