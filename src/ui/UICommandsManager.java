package ui;

import controllers.UIAnimationController;
import controllers.UIController;
import minigames.cooking.CookingGamePlayer;
import util.GameController;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class UICommandsManager {
    private HashMap<String, Callable<Integer>> commands = new HashMap<>();
    boolean hasEggs = false;
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
    Callable<Integer> openGuideMenu = () -> {
        uiAnimationController.animateElement("Guide menu", UIAnimationType.MOVE);
//        uiAnimationController.animateElement("Test button", UIAnimationType.MOVE);
        commandsManager.updateCommand("Guide button", "CloseGuideMenu");
        return 0;
    };
    Callable<Integer> closeGuideMenu = () -> {
        uiAnimationController.animateElement("Guide menu", UIAnimationType.MOVE_REVERSED);
//        uiAnimationController.animateElement("Test button", UIAnimationType.MOVE_REVERSED);
        commandsManager.updateCommand("Guide button", "SpinForward");
        return 0;
    };
    Callable<Integer> flipUIHideSwitch = () -> {
        uiController.flipAllElement(new String[]{"Root", "Show hide UI switch"});
        return 0;
    };
    Callable<Integer> backToMainGame = () -> {
        GameController.getSceneManager().loadUIScene(GameController.getScreen(), "MAIN_GAME");
        return 0;
    };
    Callable<Integer> turnSwitch = () -> {
        GameController.getScreen().getGamePanel().toggleDrawCollisions(!GameController.getScreen().getGamePanel().isDrawCollisions());
        return 0;
    };
    Callable<Integer> messWithABat = () -> {
        GameController.getUiAnimationController().animateElement("Bat", UIAnimationType.DEFAULT);
        return 0;
    };
    Callable<Integer> getEggs = () -> {
        hasEggs = true;
        GameController.getNotificationSystem().notify("Взял яйца");
        return 0;
    };
    Callable<Integer> closeFridge = () -> {
        GameController.getSceneManager().loadUIScene(GameController.getScreen(), "KITCHEN");
        return 0;
    };
    Callable<Integer> selectEasyDiff = () -> {
        System.out.println("Something important happened here.");
        ((CookingGamePlayer) GameController.getSceneManager().getMinigameScene("COOKING_MINIGAME").gp()).startGame(2, 0.5f, 0.2f, 50, "EASY");
        GameController.getSceneManager().changeScene(GameController.getScreen(), "COOKING_MINIGAME");
        return 0;
    };
    Callable<Integer> selectNormalDiff = () -> {
        System.out.println("Something important happened here.");
        ((CookingGamePlayer) GameController.getSceneManager().getMinigameScene("COOKING_MINIGAME").gp()).startGame(4, 0.3f, 0.2f, 125, "NORMAL");
        GameController.getSceneManager().changeScene(GameController.getScreen(), "COOKING_MINIGAME");
        return 0;
    };
    Callable<Integer> selectHardDiff = () -> {
        System.out.println("Something important happened here.");
        ((CookingGamePlayer) GameController.getSceneManager().getMinigameScene("COOKING_MINIGAME").gp()).startGame(8, 0.3f, 0.2f, 250, "HARD");
        GameController.getSceneManager().changeScene(GameController.getScreen(), "COOKING_MINIGAME");
        return 0;
    };

    public UICommandsManager() {
        commands.put("SpinBackwards", spinBackwards);
        commands.put("SpinForward", spinForward);
        commands.put("TurnSwitch", turnSwitch);
        commands.put("OpenKeybindsMenu", openKeybindsMenu);
        commands.put("CloseKeybindsMenu", closeKeybindsMenu);
        commands.put("flipShowUISwitch", flipUIHideSwitch);
        commands.put("backToMainGame", backToMainGame);
        commands.put("OpenGuideMenu", openGuideMenu);
        commands.put("CloseGuideMenu", closeGuideMenu);
        commands.put("getEggs", getEggs);
        commands.put("closeFridge", closeFridge);
        commands.put("selectEasyDiff", selectEasyDiff);
        commands.put("selectNormalDiff", selectNormalDiff);
        commands.put("selectHardDiff", selectHardDiff);
        commands.put("messWithABat", messWithABat);
    }

    public void updateCommand(String key, String commandName) throws Exception{
//        System.out.println("Trying to update command for " + key + ": to " + commandName);
        UIElement element = uiController.getElement(key);
        Callable<Integer> command = commands.get(commandName);
        if (command == null) {
            System.err.println("ERROR: No command found for '" + commandName + "'");
        }
        element.setCommand(command);
    }

    public void setUiAnimationController(UIAnimationController uiAnimationController) {
        this.uiAnimationController = uiAnimationController;
    }

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public boolean doesHaveEggs(){
        return hasEggs;
    }
}
