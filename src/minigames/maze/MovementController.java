package minigames.maze;

import gameObject.movement.Direction;

public class MovementController {
    private MazeMap mazeMap;
    private CoordinatesController coordinatesController;

    public void move(String key, Direction direction){
        switch (direction){
            case UP -> {
                coordinatesController.move(key, 0, 1);
            }
            case DOWN -> {
                coordinatesController.move(key, 0, -1);
            }
            case LEFT -> {
                coordinatesController.move(key, -1, 0);
            }
            case RIGHT -> {
                coordinatesController.move(key, 1, 0);
            }
        }
    }
}
