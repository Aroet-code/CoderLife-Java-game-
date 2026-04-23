package minigames.maze;

import minigames.core.MinigameController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeController extends MinigameController {
    protected MazeMap map;
    protected MovementController movementController;
    protected CoordinatesController coordinatesController;
    protected MazeCommandsExecutor commandsExecutor;
    protected InventoryController inventoryController;
    private final Random random = new Random();
    private final List<String> items = new ArrayList<>();
    private final List<String> entities = new ArrayList<>();

    public void createMap(int width, int height, int roomCount, int roomWidthMin, int roomWidthMax){
        coordinatesController = new CoordinatesController();
        movementController = new MovementController();
        map = new MazeMap(width, height, roomCount, roomWidthMin, roomWidthMax);
        commandsExecutor = new MazeCommandsExecutor(coordinatesController, map);
        inventoryController = new InventoryController(this);
        movementController.setInventoryController(inventoryController);
        List<Point> possibleSpawns = map.getPossibleSpawns();
        int id = random.nextInt(10000);
        Point exitKeySpawnPoint = possibleSpawns.get(id % possibleSpawns.size());
        coordinatesController.addCoordinates("exit key", exitKeySpawnPoint);
        items.add("exit key");
        id = random.nextInt(10000);
        Point playerSpawnPoint = possibleSpawns.get(id % possibleSpawns.size());
        coordinatesController.addCoordinates("player", playerSpawnPoint);
        items.add("player");
        for (Point p : coordinatesController.getPointsAround("player", 7)){
            map.getUnknownMap()[p.y][p.x] = true;
        }
        for (int i = 0; i < 3; i++) {
            id = random.nextInt(10000);
            Point bombSpawnPoint = possibleSpawns.get(id % possibleSpawns.size());
            coordinatesController.addCoordinates("bomb-" + i, bombSpawnPoint);
            items.add("bomb-" + i);
        }
        id = random.nextInt(10000);
        Point portalSpawnPoint = possibleSpawns.get(id % possibleSpawns.size());
        coordinatesController.addCoordinates("portal", portalSpawnPoint);
        items.add("portal");
    }

    public void addEntity(String name){
        entities.add(name);
    }

    public List<String> getEntities() {
        return entities;
    }

    public List<String> getItems() {
        return items;
    }

    public CoordinatesController getCoordinatesController() {
        return coordinatesController;
    }

    public MazeMap getMap() {
        return map;
    }

    public void removeItem(String key){
        items.remove(key);
    }
}
