package minigames.maze.entities;

import minigames.maze.ItemUseFlags;
import minigames.maze.ItemUsePackage;
import minigames.maze.MazeCommandsExecutor;
import minigames.maze.UseTypeFlags;

public class TickingBomb implements Entity{
    private final MazeCommandsExecutor commandsExecutor;
    private final String name;

    public TickingBomb(MazeCommandsExecutor mazeCommandsExecutor, String name){
        this.commandsExecutor = mazeCommandsExecutor;
        this.name = name;
    }

    public void onFuseEnd(){
        commandsExecutor.executeCommand(new ItemUsePackage(name, 0,
                new ItemUseFlags[]{ItemUseFlags.REMOVE_DESTRUCTIBLE_WALLS, ItemUseFlags.DEAL_DAMAGE}, UseTypeFlags.EXECUTE_RAD_2));
    }
}
