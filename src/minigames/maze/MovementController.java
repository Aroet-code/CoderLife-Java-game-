package minigames.maze;

import gameObject.movement.Direction;

public class MovementController {
    private CoordinatesController coordinatesController;
    private InventoryController inventoryController;

    public void move(String key, Direction direction){
        if (!(canMove(key))) {
//            System.out.println("can't move");
            return;
        }
        switch (direction){
            case UP -> {
                coordinatesController.move(key, 0, -1, inventoryController);
            }
            case DOWN -> {
                coordinatesController.move(key, 0, 1, inventoryController);
            }
            case LEFT -> {
                coordinatesController.move(key, -1, 0, inventoryController);
            }
            case RIGHT -> {
                coordinatesController.move(key, 1, 0, inventoryController);
            }
        }
    }

    public void setCoordinatesController(CoordinatesController coordinatesController) {
        this.coordinatesController = coordinatesController;
    }

    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    public boolean canMove(String key){
        if (coordinatesController == null) {
            return false;
        }
        return coordinatesController.getCoordinates(key) != null;
    }
}
