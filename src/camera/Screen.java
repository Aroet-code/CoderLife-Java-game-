package camera;

import gameObject.GameObjectScene;
import minigames.core.MinigameScene;
import minigames.maze.CoordinatesController;
import ui.UIScene;
import ui.UIUserLinker;
import util.GameController;
import util.InputCommandsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class Screen extends JFrame {
    private int scaleNumber;
    private GamePanel gamePanel;
//    private MouseListener mouseListener = new MouseListener() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//
//        }
//    };
//    private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
//        @Override
//        public void mouseDragged(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//
//        }
//    };

    @Deprecated
    public Screen(Resolution<Integer, Integer, Integer> resolution) {
        switch (resolution.getSize()){
            case 0 -> scaleNumber = 0;
            case 1 -> scaleNumber = 5;
            case 2 -> scaleNumber = 10;
            case 3 -> scaleNumber = 20;
            case 4 -> scaleNumber = 40;
            case 5 -> scaleNumber = 80;
            case 6 -> scaleNumber = 120;
            case 7 -> scaleNumber = 160;
            case 8 -> scaleNumber = 240;
            case 9 -> scaleNumber = 480;
        }
//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        if (gd.isFullScreenSupported()){
//            this.setUndecorated(true);
//            gd.setFullScreenWindow(this);
//        } else {
//            this.setExtendedState(MAXIMIZED_BOTH);
//            this.setUndecorated(true);
//        }
        setVisible(true);
        setSize(resolution.getWidth() * scaleNumber, resolution.getHeight() * scaleNumber);
//        setSize(1000, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        this.add(gamePanel);

        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
            this.repaint();
            this.revalidate();
        });
    }

    public Screen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()){
            this.setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            this.setExtendedState(MAXIMIZED_BOTH);
            this.setUndecorated(true);
        }
        setVisible(true);
//        setSize(1000, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        this.add(gamePanel);

        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
            this.repaint();
            this.revalidate();
        });
        setTitle("CoderLife");
    }

    public void setUILinker(UIUserLinker uiUserLinker){
//        System.out.println(mouseListener.toString());
//        System.out.println(mouseMotionListener.toString());
//        System.out.println(Arrays.toString(this.getMouseListeners()));
//        System.out.println(Arrays.toString(this.getMouseMotionListeners()));
        for (MouseListener mouseListener1 : this.getMouseListeners()){
            this.removeMouseListener(mouseListener1);
        }
        for (MouseMotionListener mouseMotionListener1 : this.getMouseMotionListeners()){
            this.removeMouseMotionListener(mouseMotionListener1);
        }
//        this.removeMouseListener(mouseListener);
//        this.removeMouseMotionListener(mouseMotionListener);
        this.addMouseListener(uiUserLinker);
        this.addMouseMotionListener(uiUserLinker);
    }

    public void displayUIScene(UIScene uiScene) {
        gamePanel.setUIController(uiScene.uiController());
        GameController.getUiCommandsManager().setUiController(uiScene.uiController());
        this.setUILinker(uiScene.uiUserLinker());
    }

    public void displayGameObjectScene(GameObjectScene gameObjectScene) {
        gamePanel.setCollisionController(gameObjectScene.collisionController());
        gamePanel.setGameObjectController(gameObjectScene.gameObjectController());
        gamePanel.setInputMap(JComponent.WHEN_FOCUSED, gameObjectScene.inputMap());
        gamePanel.setActionMap(gameObjectScene.actionMap());
        GameController.getMovementController().setCollisionController(gameObjectScene.collisionController());
        GameController.getImageController().renderImages();
        try {
            for (int i = 0; i < 4; i++) {
                InputCommandsManager.callCommand("EXPAND");
            }
        } catch (Exception e) {
            System.out.println("It's fine, no need for exceptions");
        }
    }

    public void displayMinigameScene(MinigameScene minigameScene) {
        gamePanel.setMiniGamesImageCreator(minigameScene.imageCreator());
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
}
