import java.util.HashMap;
import java.util.concurrent.Callable;

public class UICommandsManager {
    private HashMap<String, Callable<Integer>> commands = new HashMap<>();
    UIAnimationController uiAnimationController = Main.uiAnimationController;
    UIController uiController = Main.uiController;
    UICommandsManager commandsManager = this;
    Callable<Integer> spinForward = () -> {
        uiAnimationController.animateElement("Settings button", UIAnimationType.DEFAULT);
        uiAnimationController.animateElement("Settings menu", UIAnimationType.MOVE);
        commandsManager.updateCommand("Settings button", "SpinBackwards");
        return 0;
    };
    Callable<Integer> spinBackwards = () -> {
        uiAnimationController.animateElement("Settings button", UIAnimationType.DEFAULT_REVERSED);
        uiAnimationController.animateElement("Settings menu", UIAnimationType.MOVE_REVERSED);
        commandsManager.updateCommand("Settings button", "SpinForward");
        return 0;
    };

    public UICommandsManager() {
        commands.put("SpinBackwards", spinBackwards);
        commands.put("SpinForward", spinForward);
    }

    public void updateCommand(String key, String commandName) throws Exception{
        UIElement element = uiController.getElement(key);
        Callable<Integer> command = commands.get(commandName);
        element.setCommand(command);
    }
}
