package ui;

public class AnimationTickTimer extends AnimationObject{
    private int expectedTick;
    private int tick;
    private boolean active = true;
    protected final int id;

    public AnimationTickTimer(int expectedTick, int id) {
        this.expectedTick = expectedTick;
        this.id = id;
    }

    public void setExpectedTick(int expectedTick) {
        this.expectedTick = expectedTick;
    }

    public void increaseTick(){
        tick++;
    }

    public boolean isTickReached(){
        return expectedTick == tick;
    }

    public void disable(){
        active = false;
    }

    public void enable(){
        active = true;
    }

    public int getTick(){
        return tick;
    }

    public int getTicksLeft(){
        return expectedTick - tick;
    }

    public float getCompletionState(){
        return (float) tick / expectedTick;
    }

    public void reset(){
        tick = 0;
    }
}
