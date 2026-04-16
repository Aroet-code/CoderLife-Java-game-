package minigames.core;

public class BackWardsSecondsTimer implements Timer{
    private TickTimer tickTimer;
    private float seconds;
    private float maxSeconds;
    private int ticksPerSecond;

    public BackWardsSecondsTimer(TickTimer tickTimer, float seconds, int ticksPerSecond) {
        this.tickTimer = tickTimer;
        this.seconds = seconds;
        this.maxSeconds = seconds;
        this.ticksPerSecond = ticksPerSecond;
    }

    @Override
    public int tick() {
        tickTimer.tick();
        updateSeconds();
        if (seconds == 0){
            return 1;
        }
        return 0;
    }

    public void updateSeconds(){
        this.seconds = Math.max(maxSeconds - ((float) tickTimer.getTick() / ticksPerSecond), 0f);
    }

    @Override
    public void reset() {
        seconds = maxSeconds;
        tickTimer.reset();
    }

    public float getSeconds() {
        return seconds;
    }
}
