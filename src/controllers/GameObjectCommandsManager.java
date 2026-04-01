package controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class GameObjectCommandsManager {
    private Map<String, Callable<Integer>> commands = new HashMap<>();

//// Should return 0 if the execution was successful
//// Should return 1 if the execution failed
//// Should return 2 if the command isn't in the HashMap
    public int executeCommand(String key){
        Callable<Integer> command = commands.get(key);
        if (command == null){
            return 2;
        }
        try {
            command.call();
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return 1;
        }
        return 0;
    }

    public void addCommand(String key, Callable<Integer> command){
        if (commands.containsKey(key)){
            commands.replace(key, command);
            System.out.println("Careful. The command was replaced under the key of " + key);
            System.out.println("Fix if not intended");
        } else {
            commands.put(key, command);
        }
    }
}
