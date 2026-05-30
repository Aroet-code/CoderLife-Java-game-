package util;

public class TotalScoreSystem {
    public static long score = 0;

    public static void addScore(long s) {
        score += s;
    }

    public boolean isFinished(){
        return (score >= 500000);
    }
}
