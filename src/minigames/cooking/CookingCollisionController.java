package minigames.cooking;

import gameObject.collisionShapes2D.CollisionShape2D;
import gameObject.collisionShapes2D.RectangleShape2D;
import gameObject.collisionShapes2D.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookingCollisionController {
    CookingCoordinatesController cc;
    private Map<String, CollisionShape2D> collisions = new HashMap<>();

    public void addCollision(String key, CollisionShape2D collision){
        collisions.put(key, collision);
    }

    public void addRectCollision(String key, int width, int height){
        CollisionShape2D collision = new RectangleShape2D(new Vertex(cc.getCoordinates(key)), width, height);
        addCollision(key, collision);
    }

    public void removeCollision(String key){
        collisions.remove(key);
    }

    public List<String> getCollisions(String key){
        List<String> result = new ArrayList<>();

        CollisionShape2D mainCollision = collisions.get(key);

//        if (key.equals("Floor")){
//            System.out.println("Debugger here.");
//        }

        for (Map.Entry<String, CollisionShape2D> entry : collisions.entrySet()){
            if (entry.getKey().equals(key)){
                continue;
            }
            CollisionShape2D collision = entry.getValue();
            if (!mainCollision.intersects(collision)){
                continue;
            }
            int height = mainCollision.getHeight();
            if (mainCollision.getCenterCoordinates().getY() - (height / 2) < collision.getCenterCoordinates().getY()){
                continue;
            }
            result.add(entry.getKey());
        }

        return result;
    }

    public void linkToCoordinatesController(){
        for (String key : collisions.keySet()){
            collisions.get(key).setCenterCoordinates(new Vertex(cc.getCoordinates(key)));
        }
    }

    public void setCoordinatesController(CookingCoordinatesController cc){
        this.cc = cc;
    }
}
