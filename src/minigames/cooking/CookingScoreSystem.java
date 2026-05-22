package minigames.cooking;

public class CookingScoreSystem {
    protected long score;
    protected int combo;
    protected float accuracy;
    private int hits;
    private int misses;

    protected void init(){
        score = 0;
        combo = 0;
        accuracy = 100;
        hits = 0;
        misses = 0;
    }

    protected void onHit(){
        score += (long) (300L * (1f + (float)(combo / 25)));
        hits++;
        combo++;
        recalculateAccuracy();
        System.out.println("Score: " + score + " Accuracy: " + accuracy);
    }

    protected void onMiss(){
        combo = 0;
        misses++;
        recalculateAccuracy();
        System.out.println("Score: " + score + " Accuracy: " + accuracy);
    }

    private void recalculateAccuracy(){
        accuracy = (100 * ((float) hits / (hits + misses)));
    }
}
