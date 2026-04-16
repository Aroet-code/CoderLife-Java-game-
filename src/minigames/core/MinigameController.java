package minigames.core;

import java.util.HashMap;
import java.util.Map;

public class MinigameController {
    private Map<String, Timer> timers = new HashMap<>();

    protected void tickTimer(String key){
        timers.get(key).tick();
    }

    protected void tickAllTimers(){
        for (Timer t : timers.values()){
            t.tick();
        }
    }

    public void addTimer(String key, Timer timer){
        timers.put(key, timer);
    }
}
