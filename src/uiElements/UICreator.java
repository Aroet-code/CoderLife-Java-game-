import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class UICreator {
    private boolean busy = false;

    public static void createUIElement(UIController uiController, UIAnimationController uiAnimationController, UICommandsManager commandsManager,
                                       String name, String command, HashMap<UIElementState, Image> stateImages, Image[] animationImages,
                                       int startX, int startY, int targetX, int targetY, String[] children, String[] tags){
        if (children == null) {
            UIElement element = new UIElement(startX, startY, targetX, targetY, name, stateImages, animationImages, tags);
            uiController.addElement(name, element, UIElementState.DEFAULT);
            uiAnimationController.addElement(name, element);
            if (command != null) {
                try {
                    commandsManager.updateCommand(name, command);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            }
        } else {
            UIContainer element = new UIContainer(uiController, startX, startY, targetX, targetY, name, stateImages, children, tags);
            uiController.addElement(name, element, UIElementState.DEFAULT);
            uiAnimationController.addElement(name, element);
        }
//        busy = false;
    }
}
