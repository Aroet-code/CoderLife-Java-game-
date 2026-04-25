package minigames.core;

import gameObject.movement.Direction;
import minigames.maze.CoordinatesController;
import minigames.maze.InventoryController;
import minigames.maze.MazeMap;
import minigames.maze.MovementController;

public class MinigameMovementController {
    private MovementController mazeMovementController = new MovementController();

    public void move(String key, Direction direction){
        if (mazeMovementController != null){
            mazeMovementController.move(key, direction);
        }
    }

    public void setMazeMovementController(MovementController mazeMovementController) {
        this.mazeMovementController = mazeMovementController;
    }

    public void setMazeCoordinatesController(CoordinatesController coordinatesController){
        this.mazeMovementController.setCoordinatesController(coordinatesController);
    }

    public void setMazeInventoryController(InventoryController inventoryController){
        this.mazeMovementController.setInventoryController(inventoryController);
    }

    public void setMazeMap(CoordinatesController coordinatesController, MazeMap mazeMap){
        coordinatesController.setMazeMap(mazeMap);
    }
}
