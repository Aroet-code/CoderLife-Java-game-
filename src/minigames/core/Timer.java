package minigames.core;

public interface Timer {
    //// Returns 0 if the timer keeps going and 1 if the timer has reached 0 seconds remaining.
    int tick();
    //// Resets the timer back to its original value
    void reset();
}
