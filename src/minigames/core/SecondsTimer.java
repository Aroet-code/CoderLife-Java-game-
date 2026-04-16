package minigames.core;

public class SecondsTimer implements Timer{
    private float seconds;
    private TickTimer tickTimer;
    private int ticksPerSecond;

    public SecondsTimer(TickTimer tickTimer, int ticksPerSecond) {
        this.tickTimer = tickTimer;
        this.seconds = (float) tickTimer.getTick() / ticksPerSecond;
        this.ticksPerSecond = ticksPerSecond;
    }

    @Override
    public int tick() {
        int returnValue = tickTimer.tick();
        updateSeconds();
        return returnValue;
    }

    @Override
    public void reset() {
        tickTimer.reset();
    }

    private void updateSeconds(){
        this.seconds = ((float) tickTimer.getTick() / ticksPerSecond);
    }

    public float getSeconds() {
        return seconds;
    }
}
