package minigames.cooking;

import minigames.core.MinigameController;
import util.GameController;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CookingMinigameController extends MinigameController {
    protected CookingCoordinatesController cc;
    protected MovementController mc;
    protected CookingCollisionController collisionController;
    protected CookingScoreSystem scoreSystem;
    private Map<String, FoodTypes> objects = new HashMap<>();
    private Random random = new Random();

    public List<Map.Entry<String, FoodTypes>> getObjects(){
        return objects.entrySet().stream().toList();
    }

    public void init(float ar, float secondsDiff, float maxSecondsDiff, int objectsAmount){
        cc = new CookingCoordinatesController();
        mc = new MovementController();
        mc.setCoordinatesController(cc);
        scoreSystem = new CookingScoreSystem();

        scoreSystem.init();

        collisionController = new CookingCollisionController();

        cc.addCoordinates("Player", new Point(GameController.getScreen().getWidth() / 2, GameController.getScreen().getHeight() - 160));

        cc.addCoordinates("Floor", new Point(GameController.getScreen().getWidth() / 2, GameController.getScreen().getHeight()));

        collisionController.setCoordinatesController(cc);

        collisionController.addRectCollision("Player", 160, 160);

        collisionController.addRectCollision("Floor", GameController.getScreen().getWidth() * 2, 64);

        FoodMap foodMap = new FoodMap(cc);
        foodMap.createMap(ar, secondsDiff, maxSecondsDiff, objectsAmount);

        List<String> objectsNames = new ArrayList<>();
        for (var entry : cc.getAllCoordinates()){
            objectsNames.add(entry.getKey());
        }

        for (String name : objectsNames){
            FoodTypes[] types = FoodTypes.values();
            int funValue = random.nextInt(0, 1000);
            for (int i = 0; i < types.length; i++){
                FoodTypes t = types[funValue % types.length];
                objects.put(name, t);
            }
            if (name.equals("Player") || name.equals("Floor")){
                continue;
            }
            collisionController.addRectCollision(name, 32,32);
        }

//        cc.logAllCoordinates();
    }

    protected void reactToCollisionCommand(CookingCollisionCommand command){
        CookingCollisionCommandFlags flag = command.flag();
        if (flag == CookingCollisionCommandFlags.NO_COLLISION_DETECTED){
            System.out.println("No collision detected flag was called");
            return;
        }
        String objName = command.name();
        if (objName.equals("Player") || objName.equals("Floor")){
            System.out.println("Player or floor collision");
            return;
        }
        if (flag == CookingCollisionCommandFlags.COLLISION_MISSED){
            this.removeObject(objName);
            scoreSystem.onMiss();
            return;
        }
        this.removeObject(objName);
        scoreSystem.onHit();
    }

    private void removeObject(String key){
        objects.remove(key);
        collisionController.removeCollision(key);
        cc.removeCoordinates(key);
    }
}
