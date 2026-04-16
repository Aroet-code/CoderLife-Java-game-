package minigames.maze;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CoordinatesController {
    private Map<String, Point> coordinates = new HashMap<>();
    private MazeMap mazeMap;

    private boolean isPositionPossible(){
        for (Point p : coordinates.values()){
            if (mazeMap.getMap()[(int) p.getX()][(int) p.getY()] == TileType.VOID.ordinal() ||
                    mazeMap.getMap()[(int) p.getX()][(int) p.getY()] == TileType.WALL.ordinal()){
                System.out.println("The position is not possible");
                return false;
            }
        }
        return true;
    }
}
