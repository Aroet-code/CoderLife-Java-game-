package threads;

import gameObject.collisionShapes2D.Vertex;
import util.GameController;

public class MovementThread extends ThreadBase{
    public MovementThread(boolean enabled) {
        super(enabled);
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()){
                GameController.getMovementController().manageAccelerating();
                GameController.getMovementController().manageStopping();
                GameController.getMovementController().manageMovement();
//                Vertex pc = GameController.getGameObjectCoordinatesController().getCoordinates("Player");
//                System.out.println("Player coordinates: " + pc.getX() + ", " + pc.getY());
            }
        }
    }
}
