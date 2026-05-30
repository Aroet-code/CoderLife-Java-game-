package minigames.cooking;

import util.GameController;

import java.awt.*;
import java.util.Random;

public class FoodMap {
    protected float totalTime;
    private static final int FPS_PER_SECOND = 60;
    private final CookingCoordinatesController cc;
    private final Random random = new Random();
    int currentId = 0;

    public FoodMap(CookingCoordinatesController cc) {
        this.cc = cc;
    }

    public void createMap(float ar, float secondsDiff, float maxSecondsDiff, int objectsAmount, String difficulty){
        float playerSpeed = MovementController.PLAYER_SPEED;
        float startSpeed = MovementController.START_SPEED;
        float perArSpeed = MovementController.SPEED_PER_AR;
        totalTime = 0f;

        int currentHeight = (int) (0 - GameController.getScreen().getHeight() * 0.5f);

        int minFunValueForSpeed = 6;
        switch (difficulty){
            case "EASY" -> {
                minFunValueForSpeed = 22;
            }
            case "NORMAL" -> {
                minFunValueForSpeed = 16;
            }
            case "HARD" -> {
                minFunValueForSpeed = 6;
            }
        }

        Point coordinates = new Point(GameController.getScreen().getWidth() / 2, currentHeight);
        float distanceValue = 1;

        for (int i = 0; i < objectsAmount; i++){
            int funValue = random.nextInt(1, 21);
            int dirValue = random.nextInt(0, 10);
            int distValue = random.nextInt(0, 20);
            int secondDiffValue = random.nextInt(-100, 100);
            float dir = 1;
            if (dirValue >= 5){
                dir = -1;
            }
            float boosted = 1;
            if (funValue >= minFunValueForSpeed){
                boosted = 2;
            }
            if (distValue >= 8){
                distanceValue = 2;
            }
            float currentFallTime = secondsDiff + (secondDiffValue * maxSecondsDiff / 100f);
            String name = "Object-" + currentId;
            coordinates.x += (int) (playerSpeed * boosted * distanceValue * dir * FPS_PER_SECOND / 2 * currentFallTime * 2);
            if (coordinates.x < 30 || coordinates.x >= GameController.getScreen().getWidth() - 30){
//                System.out.println("So before changes x: " + coordinates.x);
                coordinates.x -= (int) (playerSpeed * boosted * distanceValue * dir * FPS_PER_SECOND * currentFallTime * 2);
//                System.out.println("So after changes x: " + coordinates.x);
            }
            coordinates.y -= (int) ((startSpeed + (ar * perArSpeed)) * (currentFallTime * FPS_PER_SECOND) * distanceValue);
            cc.addCoordinates(name, coordinates.getLocation());
            currentId++;
            totalTime += currentFallTime;
        }
    }
}
