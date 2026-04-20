package minigames.maze;

import minigames.core.MinigameController;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class MazeController extends MinigameController {
    protected MazeMap map;
    protected MovementController movementController;
    protected CoordinatesController coordinatesController;
    private final Random random = new Random();

    public void createMap(int width, int height, int roomCount, int roomWidthMin, int roomWidthMax){
        coordinatesController = new CoordinatesController();
        movementController = new MovementController();
        map = new MazeMap(width, height, roomCount, roomWidthMin, roomWidthMax);
        List<Point> possibleSpawns = map.getPossibleSpawns();
        int id = random.nextInt(10000);
        Point playerSpawnPoint = possibleSpawns.get(id % possibleSpawns.size());
        coordinatesController.addCoordinates("player", playerSpawnPoint);
        id = random.nextInt(10000);
        Point exitKeySpawnPoint = possibleSpawns.get(id % possibleSpawns.size());
        coordinatesController.addCoordinates("exit key", exitKeySpawnPoint);
    }


}
