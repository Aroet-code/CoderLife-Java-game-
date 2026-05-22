package minigames.core;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatesController {
    private Map<String, Point> coordinates = new HashMap<>();

    public Point getCoordinates(String key){
        return coordinates.get(key);
    }

    public void addCoordinates(String key, Point coords){
        coordinates.put(key, coords);
    }

    public void replaceCoordinates(String key, Point coords){
        coordinates.replace(key, coords);
    }

    public List<Map.Entry<String, Point>> getAllCoordinates(){
        return coordinates.entrySet().stream().toList();
    }

    public void logAllCoordinates(){
        System.out.println("Logging objects coordinates.");
        for (var entry : coordinates.entrySet()){
            System.out.println(entry.getKey() + ". x:" + entry.getValue().x + ", y: " + entry.getValue().y);
        }
        System.out.println("Finished logging coordinates.");
    }

    public void removeCoordinates(String key){
        coordinates.remove(key);
    }
}
