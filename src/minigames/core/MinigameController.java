package minigames.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MinigameController {
    private Map<String, Timer> timers = new HashMap<>();
    private Map<String, Callable<Integer>> timerCommands = new HashMap<>();

    protected void tickTimer(String key){
        timers.get(key).tick();
    }

    protected void tickAllTimers(){
        for (Map.Entry<String, Timer> timerEntry : timers.entrySet()){
            Timer t = timerEntry.getValue();
            int returnValue = t.tick();
            if (returnValue == 1){
                try {
                    timerCommands.get(timerEntry.getKey()).call();
                    timers.remove(timerEntry.getKey());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void addTimer(String key, Timer timer){
        timers.put(key, timer);
    }

    public void addTimerCommand(String key, Callable<Integer> command){
        timerCommands.put(key, command);
    }
}
