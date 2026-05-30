package util;

public class ObjectiveController {
    private String currentObjective = "Find the key in the basement";

    public String getCurrentObjective() {
        return currentObjective;
    }

    public void setCurrentObjective(String currentObjective) {
        this.currentObjective = currentObjective;
    }
}
