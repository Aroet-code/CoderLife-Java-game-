package minigames.cooking;

import java.text.DecimalFormat;

public class CookingScoreSystem {
    private final DecimalFormat df = new DecimalFormat("#.00");
    protected long score;
    protected int combo;
    protected float accuracy;
    private int hits;
    private int misses;

    protected void init(){
        score = 0;
        combo = 0;
        accuracy = 100.00f;
        hits = 0;
        misses = 0;
    }

    protected void onHit(){
        score += (long) ((300f * (1f + ((float)combo / 25f))));
        hits++;
        combo++;
        recalculateAccuracy();
    }

    protected void onMiss(){
        combo = 0;
        misses++;
        recalculateAccuracy();
    }

    private void recalculateAccuracy(){
        accuracy = Float.parseFloat(df.format((100 * ((float) hits / (hits + misses)))));
    }
}
