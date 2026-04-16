package ui;

import controllers.UIAnimationController;
import controllers.UIController;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class UICommandsManager {
    private HashMap<String, Callable<Integer>> commands = new HashMap<>();
    UIAnimationController uiAnimationController;
    UIController uiController;
    UICommandsManager commandsManager = this;

    Callable<Integer> openKeybindsMenu = () -> {
        uiAnimationController.animateElement("Keybinds menu", UIAnimationType.MOVE);
        commandsManager.updateCommand("Keybinds button", "CloseKeybindsMenu");
        return 0;
    };
    Callable<Integer> closeKeybindsMenu = () -> {
        uiAnimationController.animateElement("Keybinds menu", UIAnimationType.MOVE_REVERSED);
        commandsManager.updateCommand("Keybinds button", "OpenKeybindsMenu");
        return 0;
    };
    Callable<Integer> spinForward = () -> {
        uiAnimationController.animateElement("Settings button", UIAnimationType.DEFAULT);
        uiAnimationController.animateElement("Settings menu", UIAnimationType.MOVE);
//        uiAnimationController.animateElement("Test button", UIAnimationType.MOVE);
        commandsManager.updateCommand("Settings button", "SpinBackwards");
        return 0;
    };
    Callable<Integer> spinBackwards = () -> {
        uiAnimationController.animateElement("Settings button", UIAnimationType.DEFAULT_REVERSED);
        uiAnimationController.animateElement("Settings menu", UIAnimationType.MOVE_REVERSED);
//        uiAnimationController.animateElement("Test button", UIAnimationType.MOVE_REVERSED);
        commandsManager.updateCommand("Settings button", "SpinForward");
        closeKeybindsMenu.call();
        commandsManager.updateCommand("Keybinds button", "OpenKeybindsMenu");
        return 0;
    };
    Callable<Integer> turnSwitch = () -> {
        return 0;
    };

    public UICommandsManager() {
        commands.put("SpinBackwards", spinBackwards);
        commands.put("SpinForward", spinForward);
        commands.put("TurnSwitch", turnSwitch);
        commands.put("OpenKeybindsMenu", openKeybindsMenu);
        commands.put("CloseKeybindsMenu", closeKeybindsMenu);
    }

    public void updateCommand(String key, String commandName) throws Exception{
//        System.out.println("Trying to update command for " + key + ": to " + commandName);
        UIElement element = uiController.getElement(key);
        Callable<Integer> command = commands.get(commandName);
        element.setCommand(command);
    }

    public void setUiAnimationController(UIAnimationController uiAnimationController) {
        this.uiAnimationController = uiAnimationController;
    }

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }
}
