package minigames.maze;

import minigames.core.BackWardsSecondsTimer;
import minigames.core.TickTimer;
import minigames.core.Timer;
import minigames.maze.entities.Entity;
import minigames.maze.entities.EntityType;
import minigames.maze.entities.TickingBomb;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.Callable;

public class EntityCreator {
    public static void createEntity(MazeController mazeController, EntityType entityType){
        Entity entity;
        byte[] arr = new byte[8];
        new Random().nextBytes(arr);
        String name = new String(arr, Charset.forName("UTF-8"));
        String finalName = name;
        switch (entityType){
            case EntityType.TICKING_BOMB -> {
                name = "bomb-" + name;
                entity = new TickingBomb(mazeController.commandsExecutor, name);
                mazeController.coordinatesController.addCoordinates(name, mazeController.coordinatesController.getCoordinates("player"));
                Timer bombTimer = new BackWardsSecondsTimer(new TickTimer(), 5, 60);
                Callable<Integer> command = () -> {
                    mazeController.commandsExecutor.executeCommand(new ItemUsePackage(finalName, 0,
                            new ItemUseFlags[]{ItemUseFlags.REMOVE_DESTRUCTIBLE_WALLS, ItemUseFlags.DEAL_DAMAGE}, UseTypeFlags.EXECUTE_RAD_2));
                    return 0;
                };
                mazeController.addTimer(finalName, bombTimer);
                mazeController.addTimerCommand(finalName, command);
            }
        }
    }
}
