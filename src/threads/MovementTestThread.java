package threads;

import util.GameController;

public class MovementTestThread extends ThreadBase{
    public MovementTestThread(boolean enabled) {
        super(enabled);
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()){
                GameController.getGameObjectCoordinatesController().updateMainCoordinates(10, 10);
            }
        }
    }
}
