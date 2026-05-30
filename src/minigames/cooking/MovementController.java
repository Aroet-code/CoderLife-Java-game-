package minigames.cooking;

import util.GameController;

import java.awt.*;
import java.util.Map;
import java.util.List;

public class MovementController {
    private CookingCoordinatesController cc;

    public static float PLAYER_SPEED = 5;
    public static float START_SPEED = 2;
    public static float SPEED_PER_AR = 1;
    private static final String[] NOT_FALLING_OBJECTS = new String[]{"Player", "Floor"};

    private void moveObjectsDown(float ar){
        float speed = START_SPEED + (SPEED_PER_AR * ar);

        List<Map.Entry<String, Point>> coordinates = cc.getAllCoordinates();

        for (var entry : coordinates){
            String name = entry.getKey();

            boolean flag = false;
            for (String key : NOT_FALLING_OBJECTS){
                if (key.equals(name)){
                    flag = true;
                    break;
                }
            }
            if (flag){
                continue;
            }

            Point coords = entry.getValue();

            coords.y += (int) speed;

            cc.replaceCoordinates(name, coords);
        }
    }

    private void movePlayer(float dirX, boolean fastMode){
        if (dirX == 0){
            return;
        }
        Point playerCoords = cc.getCoordinates("Player");
        playerCoords.x += (int) (dirX * PLAYER_SPEED);
        if (playerCoords.x + 80 >= GameController.getScreen().getWidth() - 30 || playerCoords.x - 80 <= 30){
            playerCoords.x -= (int) (dirX * PLAYER_SPEED);
            return;
        }
        if (fastMode){
            playerCoords.x += (int) (dirX * PLAYER_SPEED);
        }
        cc.replaceCoordinates("Player", playerCoords);
    }

    public void play(float ar, float dirX, boolean fastMode){
        moveObjectsDown(ar);
        movePlayer(dirX, fastMode);
    }

    public static void setPlayerSpeed(float playerSpeed) {
        PLAYER_SPEED = playerSpeed;
    }

    public static void setStartSpeed(float startSpeed) {
        START_SPEED = startSpeed;
    }

    public static void setSpeedPerAr(float speedPerAr) {
        SPEED_PER_AR = speedPerAr;
    }

    public void setCoordinatesController(CookingCoordinatesController cc) {
        this.cc = cc;
    }
}