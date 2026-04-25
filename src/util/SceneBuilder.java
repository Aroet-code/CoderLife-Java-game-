package util;

import controllers.*;
import gameObject.GameObjectCreator;
import gameObject.GameObjectScene;
import gameObject.collisionShapes2D.Vertex;
import minigames.core.ImageCreator;
import minigames.core.MinigameController;
import minigames.core.MinigameScene;
import minigames.maze.MazeController;
import minigames.maze.MazeImageCreator;
import minigames.maze.MazeMap;
import ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class SceneBuilder {
    public static Map<String, MinigameScene> buildMinigameScenes(){
        Map<String, MinigameScene> scenes = new HashMap<>();

        for (String name : new String[]{"MAIN_MENU", "MAIN_GAME", "STREET"}) {
            MinigameController minigameController = null;
            ImageCreator minigameImageCreator = null;
            switch (name){
                case "STREET" -> {
                    minigameController = new MazeController();
                    MazeController mz = (MazeController) minigameController;
                    mz.createMap(60, 60, 14, 5, 15);
                    minigameImageCreator = new MazeImageCreator(mz);
//                    InputMap defaultInputMap = GameController.getScreen().getGamePanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//                    ActionMap defaultActionMap = GameController.getScreen().getGamePanel().getActionMap();
//
//                    defaultActionMap.put("MINIGAME_MOVE_RIGHT", new AbstractAction() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            try {
//                                InputCommandsManager.callCommand("MINIGAME_MOVE_RIGHT");
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                        }
//                    });
//                    defaultInputMap.put(KeyStroke.getKeyStroke("D"), "MINIGAME_MOVE_RIGHT");
//                    defaultActionMap.put("MINIGAME_MOVE_LEFT", new AbstractAction() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            try {
//                                InputCommandsManager.callCommand("MINIGAME_MOVE_LEFT");
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                        }
//                    });
//                    defaultInputMap.put(KeyStroke.getKeyStroke("A"), "MINIGAME_MOVE_LEFT");
//                    defaultActionMap.put("MINIGAME_MOVE_DOWN", new AbstractAction() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            try {
//                                InputCommandsManager.callCommand("MINIGAME_MOVE_DOWN");
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                        }
//                    });
//                    defaultInputMap.put(KeyStroke.getKeyStroke("S"), "MINIGAME_MOVE_DOWN");
//                    defaultActionMap.put("MINIGAME_MOVE_UP", new AbstractAction() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            try {
//                                InputCommandsManager.callCommand("MINIGAME_MOVE_UP");
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                        }
//                    });
//                    defaultInputMap.put(KeyStroke.getKeyStroke("W"), "MINIGAME_MOVE_UP");
                }
            }
            scenes.put(name, new MinigameScene(minigameController, minigameImageCreator));
        }
        return scenes;
    }

    public static Map<String, UIScene> buildUIScenes(JFrame screen){
        Map<String, UIScene> scenes = new HashMap<>();

        UIAnimationController uiAnimationController = GameController.getUiAnimationController();
        Map<UIElementState, Image> rootContainerImages = new HashMap<>();
        BufferedImage rootContainerImage = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rootContainerImages.put(UIElementState.DEFAULT, rootContainerImage);

        UICommandsManager commandsManager = GameController.getUiCommandsManager();

        for (String name : new String[]{"MAIN_MENU", "MAIN_GAME", "STREET", "CITY_IN_THE_WINDOW"}){
            UIController uiController = new UIController();
            UIUserLinker uiUserLinker = new UIUserLinker(uiController);
            switch (name){
                case "CITY_IN_THE_WINDOW" -> {
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "City in the window", "backToMainGame", "assets/images/UI/cityInTheWindow",
                            screen.getWidth() / 2 - 480, screen.getHeight() / 2 - 270, screen.getWidth() / 2 - 480, screen.getHeight() / 2 - 270,
                            null, new String[]{"NO_HOVER_IMAGE"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Root", null, rootContainerImages, null, 0, 0,
                            0, 0, new String[]{"City in the window"},
                            new String[]{"NO_HOVER_IMAGE", "NOT_INTERACTABLE", "NO_ACTIVE_IMAGE"});
                }
                case "MAIN_MENU" -> {
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Settings button", "SpinForward",
                            "assets/images/UI/settingsButton",
                            50, screen.getHeight() - 210, 50, screen.getHeight() - 210, null, null);
                    UICreator.createTextLabel(uiController, uiAnimationController, "Keybinds text", "assets/textFiles/keybindsText", 0, 0,
                            0, 0, new Font("Arial", Font.PLAIN, 16), Color.CYAN, Color.WHITE, 300, 210, 40,
                            45, 5, new String[]{"NOT_INTERACTABLE"});
                    UICreator.createTextLabel(uiController, uiAnimationController, "test", "assets/textFiles/settingsMenuText", 0, 0, 0, 0,
                            new Font("Arial", Font.PLAIN, 16), Color.CYAN, Color.WHITE, 300, 500, 40, 65, 5, new String[]{"NOT_INTERACTABLE"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Keybinds menu", null,
                            "assets/images/UI/keybindsMenu", screen.getWidth() / 2 - 150, -300, screen.getWidth() / 2 - 150, 25,
                            new String[]{"Keybinds text"}, new String[]{"NOT_INTERACTABLE"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Test switch", "TurnSwitch",
                            "assets/images/UI/testSwitch", 118, 150, 150, 150, null, new String[]{"SWITCH"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Keybinds button", "OpenKeybindsMenu",
                            "assets/images/UI/keybindsButton", 70, 400, 70, 400,
                            null, null);
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Settings menu", null,
                            "assets/images/UI/settingsMenu", -300, 150, 25, 150,
                            new String[]{"Test switch", "test", "Keybinds button"}, new String[]{"NO_ACTIVE_IMAGE", "NOT_INTERACTABLE"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Show hide UI switch", "flipShowUISwitch", "assets/images/UI/hideShowUISwitch",
                            screen.getWidth() - 96 - 75, screen.getHeight() - 96 - 75, screen.getWidth() - 96 - 100, screen.getHeight() - 96 - 50,
                            null, new String[]{"SWITCH"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Root", null, rootContainerImages, null, 0, 0,
                            0, 0, new String[]{"Settings button", "Settings menu", "Keybinds menu", "Show hide UI switch"},
                            new String[]{"NO_HOVER_IMAGE", "NOT_INTERACTABLE", "NO_ACTIVE_IMAGE"});
                }
                case "MAIN_GAME" -> {
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Show hide UI switch", "flipShowUISwitch", "assets/images/UI/hideShowUISwitch",
                            screen.getWidth() - 96 - 75, screen.getHeight() - 96 - 75, screen.getWidth() - 96 - 100, screen.getHeight() - 96 - 50,
                            null, new String[]{"SWITCH"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Root", null, rootContainerImages, null, 0, 0,
                            0, 0, new String[]{"Show hide UI switch"}, new String[]{"NO_HOVER_IMAGE", "NOT_INTERACTABLE", "NO_ACTIVE_IMAGE"});
                }
                case "STREET" -> {
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Guide button", "OpenGuideMenu",
                            "assets/images/UI/guideButton",
                            50, screen.getHeight() - 210, 50, screen.getHeight() - 210, null, null);
                    UICreator.createTextLabel(uiController, uiAnimationController, "Guide menu text", "assets/textFiles/guideMenuText", 0, 0, 0, 0,
                            new Font("Arial", Font.PLAIN, 16), Color.CYAN, Color.WHITE, 300, 500, 40, 65, 5, new String[]{"NOT_INTERACTABLE"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Guide menu", null,
                            "assets/images/UI/guideMenu", -300, 150, 25, 150,
                            new String[]{"Guide menu text"}, new String[]{"NO_ACTIVE_IMAGE", "NOT_INTERACTABLE"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Show hide UI switch", "flipShowUISwitch", "assets/images/UI/hideShowUISwitch",
                            screen.getWidth() - 96 - 75, screen.getHeight() - 96 - 75, screen.getWidth() - 96 - 100, screen.getHeight() - 96 - 50,
                            null, new String[]{"SWITCH"});
                    UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Root", null, rootContainerImages, null, 0, 0,
                            0, 0, new String[]{"Guide button", "Guide menu", "Show hide UI switch"}, new String[]{"NO_HOVER_IMAGE", "NOT_INTERACTABLE", "NO_ACTIVE_IMAGE"});
                }
            }
            scenes.put(name, new UIScene(uiController, uiUserLinker));
        }

        return scenes;
    }

    public static Map<String, GameObjectScene> buildGameObjectScenes(){
        Map<String, GameObjectScene> scenes = new HashMap<>();

        GameObjectCommandsManager commandsManager = GameController.getGameObjectCommandsManager();
        AnimationController animationController = GameController.getAnimationController();

        InputMap defaultInputMap = GameController.getScreen().getGamePanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap defaultActionMap = GameController.getScreen().getGamePanel().getActionMap();

        defaultInputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "BACK_TO_MAIN_MENU");
        defaultActionMap.put("BACK_TO_MAIN_MENU", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("BACK_TO_MAIN_MENU");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("W"), "MOVE_UP");
        defaultActionMap.put("MOVE_UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("MOVE_UP");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("S"), "MOVE_DOWN");
        defaultActionMap.put("MOVE_DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("MOVE_DOWN");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("D"), "MOVE_RIGHT");
        defaultActionMap.put("MOVE_RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("MOVE_RIGHT");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("A"), "MOVE_LEFT");
        defaultActionMap.put("MOVE_LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("MOVE_LEFT");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("released D"), "STOP_MOVING_X");
        defaultActionMap.put("STOP_MOVING_X", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("STOP_MOVING_X");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("released A"), "STOP_MOVING_X");
        defaultActionMap.put("STOP_MOVING_X", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("STOP_MOVING_X");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("released W"), "STOP_MOVING_Y");
        defaultActionMap.put("STOP_MOVING_Y", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("STOP_MOVING_Y");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("released S"), "STOP_MOVING_Y");
        defaultActionMap.put("STOP_MOVING_Y", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine.");
                    InputCommandsManager.callCommand("STOP_MOVING_Y");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("F"), "INTERACT");
        defaultActionMap.put("INTERACT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine. The interaction, I mean");
                    InputCommandsManager.callCommand("INTERACT");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("O"), "EXPAND");
        defaultActionMap.put("EXPAND", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine. The interaction, I mean");
                    InputCommandsManager.callCommand("EXPAND");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("P"), "SHRINK");
        defaultActionMap.put("SHRINK", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                                System.out.println("It works just fine. The interaction, I mean");
                    InputCommandsManager.callCommand("SHRINK");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        defaultInputMap.put(KeyStroke.getKeyStroke("C"), "FREE_CAMERA");
        defaultActionMap.put("FREE_CAMERA", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InputCommandsManager.callCommand("FREE_CAMERA");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        for (String scene : new String[]{"MAIN_MENU", "MAIN_GAME", "STREET"}) {
            GameObjectController gameObjectController = new GameObjectController();
            CollisionController collisionController = new CollisionController(GameController.getGameObjectCoordinatesController());
            InteractionController interactionController = new InteractionController(collisionController, "Player");
            InputMap inputMap = GameController.getScreen().getGamePanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = GameController.getScreen().getGamePanel().getActionMap();
            switch (scene){
                case "MAIN_MENU" -> {
                    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "START_GAME");
                    actionMap.put("START_GAME", new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
//                                System.out.println("It works just fine.");
                                InputCommandsManager.callCommand("START_GAME");
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                }
                case "MAIN_GAME" -> {
                    inputMap = defaultInputMap;
                    actionMap = defaultActionMap;

                    GameObjectCreator.createGameObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Bg test", new Vertex(500, 500), 0, 0,
                            "assets/images/gameObjects/bg test", false, false);
                    GameObjectCreator.createGameObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Saturn", new Vertex(800, 800), 100, 100,
                            "assets/images/gameObjects/saturn", false, false);
                    GameObjectCreator.createGameObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Food table", new Vertex(480, 520), 0, 0,
                            "assets/images/gameObjects/food table", false, false);
                    GameObjectCreator.createGameObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Coffee", new Vertex(500, 496), 10, 10,
                            "assets/images/gameObjects/coffee", false, false);
                    GameObjectCreator.createMovingObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Player", new Vertex(600, 530), 100, 100,
                            "assets/images/gameObjects/player", false, GameController.getMovementController(), 2);
                    GameController.getGameObjectCommandsManager().addCommand("Coffee", new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            GameController.getAnimationController().queueAnimation("Coffee");
                            return 0;
                        }
                    });
                    interactionController.addCommand("Coffee", "Coffee");
                    GameObjectCreator.createGameObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Door outside", new Vertex(295, 500), 20, 50,
                            "assets/images/gameObjects/door outside", false, false);
                    GameController.getGameObjectCommandsManager().addCommand("Door outside", new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            GameController.getSceneManager().changeScene(GameController.getScreen(), "STREET");
                            return 0;
                        }
                    });
                    interactionController.addCommand("Door outside", "Door outside");
                    GameObjectCreator.createGameObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Window", new Vertex(550, 500), 20, 10,
                            "assets/images/gameObjects/window", false, false);
                    GameController.getGameObjectCommandsManager().addCommand("Window", new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            GameController.getSceneManager().loadUIScene(GameController.getScreen(), "CITY_IN_THE_WINDOW");
                            return 0;
                        }
                    });
                    interactionController.addCommand("Window", "Window");

                    GameObjectCreator.createRestrictedArea(collisionController, GameController.getGameObjectCoordinatesController(),
                            new Vertex(200, 200), 100, 100);
                    GameObjectCreator.createAllowedArea(collisionController, GameController.getGameObjectCoordinatesController(),
                            new Vertex(500, 530), 430, 100);
                }
                case "STREET" -> {
                    inputMap = defaultInputMap;
                    actionMap = defaultActionMap;
                    GameObjectCreator.createMovingObject(gameObjectController, GameController.getImageController(), GameController.getGameObjectCoordinatesController(),
                            collisionController, "Player", new Vertex(600, 530), 100, 100,
                            "assets/images/gameObjects/player", false, GameController.getMovementController(), 2);
                }
            }
            scenes.put(scene, new GameObjectScene(collisionController, gameObjectController, interactionController, animationController,
                    inputMap, actionMap));
        }
        GameController.getScreen().getGamePanel().setCoordinatesController(GameController.getGameObjectCoordinatesController());
        return scenes;
    }
}
