package util;

public class FlagsController {
    private static boolean hasKey = false;

    public static boolean isHasKey() {
        return hasKey;
    }

    public static void setHasKey(boolean hasKey) {
        FlagsController.hasKey = hasKey;
    }
}
