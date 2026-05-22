package minigames.cooking;

public enum FoodTypes {
    /// Edible
    CHEESE(0), NOODLES(1), EGG(2), BACON(3), GOLDEN_EGG(4),
    /// Not edible
    FLOWER_POT(1000);

    public final int value;

    FoodTypes(int value) {
        this.value = value;
    }
}
