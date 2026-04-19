package minigames.maze;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CoordinatesController {
    private Map<String, Point> coordinates = new HashMap<>();
    private MazeMap mazeMap;

    public void setMazeMap(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }

    public void addCoordinates(String key, Point p){
        coordinates.put(key, p);
    }

    private boolean isPositionPossible(){
        for (Point p : coordinates.values()){
            if (mazeMap.getMap()[(int) p.getX()][(int) p.getY()] == TileType.VOID.ordinal() ||
                    mazeMap.getMap()[(int) p.getX()][(int) p.getY()] == TileType.WALL.ordinal() ||
                    mazeMap.getMap()[(int) p.getX()][(int) p.getY()] == TileType.DESTRUCTIBLE_WALL.ordinal()){
                System.out.println("The position is not possible");
                return false;
            }
        }
        return true;
    }

    public void move(String key, int addX, int addY){
        Point oldPos = coordinates.get(key);
        Point newPos = new Point((int) (oldPos.getX() + addX), (int) (oldPos.getY() + addY));
        coordinates.replace(key, newPos);
        if (!(isPositionPossible())){
            coordinates.replace(key, oldPos);
        }
    }

    public Point[] getPointsAround(String key, int rad){
        Point[] result = new Point[((2 * rad) + 1) * ((2 * rad) + 1)];
        Point startPos = coordinates.get(key);
        for (int i = 0; i < result.length; i++){
            result[i] = new Point(startPos.x - rad + (i % ((2 * rad) + 1)), startPos.y - rad + ((int) (i / ((2 * rad) + 1))));
        }
        return result;
    }
}
