package minigames.maze;

import java.awt.*;

public class MazeCommandsExecutor {
    private CoordinatesController coordinatesController;
    private MazeMap mazeMap;

    public MazeCommandsExecutor(CoordinatesController coordinatesController, MazeMap mazeMap) {
        this.coordinatesController = coordinatesController;
        this.mazeMap = mazeMap;
    }

    public void setMazeMap(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }

    public void setCoordinatesController(CoordinatesController coordinatesController) {
        this.coordinatesController = coordinatesController;
    }

    public void executeCommand(ItemUsePackage pkg){
        String key = pkg.key();
        int damage = pkg.damage();
        UseTypeFlags useTypeFlag = pkg.useTypeFlag();
        ItemUseFlags[] itemUseFlags = pkg.itemUseFlags();
        Point[] executionCoordinates = null;

        switch (useTypeFlag) {
            case EXECUTE_RAD_2 -> {
                executionCoordinates = coordinatesController.getPointsAround(key, 2);
            }
            case EXECUTE_RAD_1 -> {
                executionCoordinates = coordinatesController.getPointsAround(key, 1);
            }
        }

        for (ItemUseFlags flag : itemUseFlags){
            switch (flag){
                case REMOVE_DESTRUCTIBLE_WALLS -> {
                    assert executionCoordinates != null;
                    for (Point p : executionCoordinates){
                        if (mazeMap.getMap()[p.y][p.x] == TileType.DESTRUCTIBLE_WALL.ordinal()){
                            mazeMap.updateTile(p, TileType.FLOOR);
                        }
                    }
                }
                case DEAL_DAMAGE -> {
                    assert executionCoordinates != null;
                    for (Point p : executionCoordinates) {
                        String[] keys = coordinatesController.isOnCoordinates(p);
                        if (keys == null){
                            continue;
                        }
                        for (String obj : keys){
                            coordinatesController.removeObject(obj);
                        }
                    }
                }
            }
        }
    }
}
