package minigames.core;

public class TickTimer implements Timer{
    long tick = 0;

    @Override
    public int tick(){
        tick++;
        return 0;
    }

    long getTick(){
        return tick;
    }

    @Override
    public void reset() {
        this.tick = 0;
    }
}
