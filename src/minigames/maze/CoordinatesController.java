package minigames.maze;

import minigames.maze.items.Bomb;
import minigames.maze.items.Item;
import minigames.maze.items.ItemBase;
import util.GameController;

import java.awt.*;
import java.util.*;
import java.util.List;

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
        for (String key : coordinates.keySet()){
            Point p = coordinates.get(key);
            if (mazeMap.getMap()[(int) p.y][(int) p.x] == TileType.VOID.ordinal() ||
                    mazeMap.getMap()[(int) p.y][(int) p.x] == TileType.WALL.ordinal() ||
                    mazeMap.getMap()[(int) p.y][(int) p.x] == TileType.DESTRUCTIBLE_WALL.ordinal()){
//                System.out.println("The position is not possible. Key: " + key);
//                System.out.println("The position is: x: " + p.x + ", y: " + p.y);
                return false;
            }
        }
        return true;
    }

    public void move(String key, int addX, int addY, InventoryController inventoryController){
        Point oldPos = coordinates.get(key);
        Point newPos = new Point((int) (oldPos.getX() + addX), (int) (oldPos.getY() + addY));
        coordinates.replace(key, newPos);
        if (!(isPositionPossible())){
            coordinates.replace(key, oldPos);
            return;
        }
        if (Objects.equals(key, "player")){
            for (Point p : getPointsAround("player", 4)) {
                mazeMap.getUnknownMap()[p.y][p.x] = true;
            }
            String[] items = isOnCoordinates(coordinates.get(key));
            if (items == null){
                return;
            }
            for (String item : items){
                Item i = getItem(item, inventoryController);
                if (item.equals("player")){
                    continue;
                }
                if (i == null){
                    continue;
                }
                inventoryController.pickUpItem(i);
            }
        }
    }

    private Item getItem(String item, InventoryController inventoryController) {
        Item i = null;
        String searchString = null;
        if (item.indexOf('-') == -1){
            searchString = item;
        } else {
            searchString = item.substring(0, item.indexOf('-'));
        }
        switch (searchString) {
            case "bomb" -> {
                i = new Bomb(item, new Item() {
                    @Override
                    public void use() {

                    }

                    @Override
                    public String getName() {
                        return item;
                    }
                });
            }
            case "coin" -> {
                System.out.println("Just interacted with a coin");
                i = new ItemBase(item);
                GameController.getAdvancedAudioManager().playCachedSound("coin pickup");

            }
            case "exit key" -> {
                System.out.println("Just interacted with the key");
                i = new ItemBase("exit key");
                for (Item it : inventoryController.getItems()){
                    if (it == null){
                        continue;
                    }
                    System.out.println("Item: " + it.getName());
                }
            }
            case "portal" -> {
                System.out.println("A portal has been interacted with just now");
                if (inventoryController.hasItem("exit key")){
                    GameController.getSceneManager().changeScene(GameController.getScreen(), "MAIN_GAME");
                    coordinates.replace("player", new Point(1, 1));
                }
            }
        }
        return i;
    }

    public Point[] getPointsAround(String key, int rad){
        Point[] result = new Point[((2 * rad) + 1) * ((2 * rad) + 1)];
        Point startPos = coordinates.get(key);
        for (int i = 0; i < result.length; i++){
            result[i] = new Point(startPos.x - rad + (i % ((2 * rad) + 1)), startPos.y - rad + ((int) (i / ((2 * rad) + 1))));
        }
        return result;
    }

    protected Point getCoordinates(String key){
        if (!(coordinates.containsKey(key))) {
            return null;
        }
        return coordinates.get(key);
    }

    protected String[] isOnCoordinates(Point p){
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Point> entry : coordinates.entrySet()){
            if (entry.getValue().equals(p)){
                result.add(entry.getKey());
            }
        }
        if (result.isEmpty()){
            return null;
        }
        return result.toArray(new String[0]);
    }

    protected void removeObject(String key){
        coordinates.remove(key);
    }
}
