package ui;

import util.ObjCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

public class Main {
    private static final int maxWidth = 10;
    private static final Random random = new Random();
    public static JFrame screen = ObjCreator.screen;

    private static final List<AnimationObject> objects = Collections.synchronizedList(new ArrayList<>());

    public static BackgroundAnimation animation = new BackgroundAnimation(new Callable<Image>() {
        @Override
        public Image call() throws Exception {
            int width, height;
          //  if (screen != null) {
                width = panel.getWidth();
                height = panel.getHeight();
 /*           } else {
                width = 1000;
                height = 1000;
            }*/

            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = finalImage.createGraphics();
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, width, height);

            List<AnimationObject> toRemove = new ArrayList<>();
            List<AnimationObject> toAdd = new ArrayList<>();

            synchronized (objects) {
                for (AnimationObject object : objects) {
                    if (object instanceof Star) {
                        Star star = (Star) object;
                        int returnValue = star.elevate((int) (object.getWidth() * 0.8f));
//                        int returnValue = star.elevate(speed);

                        object.setColor(new Color(object.getColor().getRed() / 255f, object.getColor().getGreen() / 255f,
                                object.getColor().getBlue() / 255f,
                                Math.clamp((object.getColor().getAlpha() / 255f) - (0.007f * object.getWidth() / 5), 0f, 1f)));
                        g.setColor(object.getColor());
                        g.fillRect((int)star.getX() - (star.getWidth() / 2),
                                (int)star.getY() - (star.getHeight() / 2),
                                star.getWidth(),
                                star.getHeight());

                        if (returnValue == -1) {
                            toRemove.add(star);
                            int newWidth = Math.max(3, random.nextInt(maxWidth));
                            toAdd.add(new Star(random.nextInt(width),
                                    height + newWidth,
                                    newWidth,
                                    newWidth));
                        }
                    }
                    if (object instanceof RectangledFire){
                        RectangledFire fire = (RectangledFire) object;
                        int returnValue = fire.burst();

                        object.setColor(new Color(object.getColor().getRed() / 255f, object.getColor().getGreen() / 255f,
                                object.getColor().getBlue() / 255f, Math.clamp((fire.getAlpha()), 0f, 1f)));
                        fire.setAlpha(fire.getAlpha() - 0.005f);
                        g.setColor(object.getColor());
                        g.fillRect(0, panel.getHeight() - object.getHeight(), panel.getWidth(), object.getHeight());
                        if (returnValue == -1) {
                            toRemove.add(fire);
//                            int newWidth = Math.max(1, random.nextInt(maxWidth));
                            toAdd.add(new RectangledFire(0, panel.getHeight(), panel.getWidth(), 1f));
                        }
                    }
                    if (object instanceof AnimatedLabel) {
                        AnimatedLabel label = (AnimatedLabel) object;
                        int returnValue = label.getToCoordinate((width - label.getWidth()) / 2, 600, 0.015f);
                        g.setColor(Color.RED);
//                        g.fillRect((int)label.getX(), (int)label.getY(), label.getImage().getWidth(), label.getImage().getHeight());
                        g.setColor(new Color(0.30f, 0.65f , 1.00f, 1f));
                        g.setFont(new Font("Comic Sans", Font.ITALIC, 48));
//                        g.drawString("TOUCH TO START", (int)label.getX(), (int)label.getY());

                        if (label.getImage() == null){
                            System.out.println("The image is null.");
                        }
                        g.drawImage(label.getImage(), (int)label.getX(), (int)label.getY(), null);
//                        System.out.println(label.getX() + " " + label.getY());
                    }
                    if (object instanceof LightLine){
                        LightLine line = (LightLine) object;
                        int returnValue = line.decay();
                        g.setColor(new Color((float) line.getColor().getRed() / 255, (float) line.getColor().getGreen() / 255,
                                (float) line.getColor().getBlue() / 255, line.getAlpha()));
                        g.fillRect((int) line.getX(), (int) line.getY(), line.getWidth(), line.getHeight());
                        if (returnValue == -1){
                            toRemove.add(line);
                            toAdd.add(new LightLine(10 + random.nextInt(screen.getWidth() - 10 - line.getWidth()), 5, screen.getHeight(), 0.02f, g.getColor()));
                        }
                    }
                }

                objects.removeAll(toRemove);
                objects.addAll(toAdd);
            }

            g.dispose();
            return finalImage;
        }
    }, objects);

    public static AnimatedBackground panel = new AnimatedBackground(animation);
    public static HashMap<UIElementState, Image> rootContainerImages = new HashMap<>();
    public static UIController uiController = new UIController();
    public static UIBatch uiBatch = new UIBatch(uiController);
    public static UIAnimationController uiAnimationController = new UIAnimationController();
    public static HashMap<UIElementState, Image> settingsButtonImages = new HashMap<>();
    public static boolean initialized = false;
//    private static Image[] settingsButtonAnimationImages = new Image[21];
//    private static HashMap<UIElementState, Image> settingsImages = new HashMap<>();
    public static UICommandsManager commandsManager = new UICommandsManager();
//    public static HashMap<UIElementState, Image> testButtonImages = new HashMap<>();

    public static void main(String[] args) {
        Main.screen.add(panel);
        panel.setVisible(true);

//        for (int i = 0; i < 21; i++){
//            settingsButtonAnimationImages[i] = AsyncImageLoader.createPlaceholder(50, 50);
//        }
//        settingsButtonImages.put(UIElementState.DEFAULT, AsyncImageLoader.createPlaceholder(50, 50));
//        settingsButtonImages.put(UIElementState.HOVER, AsyncImageLoader.createPlaceholder(50, 50));
//        settingsImages.put(UIElementState.DEFAULT, AsyncImageLoader.createPlaceholder(300, 500));
//        settingsImages.put(UIElementState.HOVER, AsyncImageLoader.createPlaceholder(300, 500));
//        testButtonImages.put(UIElementState.DEFAULT, AsyncImageLoader.createPlaceholder(100, 50));
//        testButtonImages.put(UIElementState.HOVER, AsyncImageLoader.createErrorImage(100, 50));
//
//        for (int i = 0; i < 21; i++){
//            settingsButtonAnimationImages[i] = new ImageIcon("assets/settingsButton/" + (i + 1) + ".png").getImage();
//        }
//        settingsButtonImages.replace(UIElementState.DEFAULT, new ImageIcon("assets/settingsButton/1.png").getImage());
//        settingsButtonImages.replace(UIElementState.HOVER, new ImageIcon("assets/settingsButton/settingsButtonHover.png").getImage());
//        settingsImages.replace(UIElementState.DEFAULT, new ImageIcon("assets/settingsMenu/settingsMenu.png").getImage());
//        settingsImages.replace(UIElementState.HOVER, new ImageIcon("assets/settingsMenu/settingsMenuHover.png").getImage());
//
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        synchronized (objects) {
            for (int i = 0; i < 100; i++) {
                objects.add(new Star(0, 0 + (i * 30), 10, 10));
            }
            for (int i = 0; i < 15; i++){
                objects.add(new RectangledFire(0, panel.getHeight(), panel.getWidth(), 1 + (0.08f * i)));
            }
            objects.add(new AnimatedLabel(500, 2800, 500, 50, "PRESS <ENTER> TO START", new Font("Arial", Font.ITALIC, 32), new Color(0.30f, 0.65f , 1.00f, 1f)));
            objects.add(new LightLine(100, 5, screen.getHeight(), 0.02f, new Color(0.30f, 0.65f , 1.00f, 1f)));
//            objects.add(new LightLine(500, 10, screen.getHeight(), 0.015f, new Color(0.30f, 0.65f , 1.00f, 1f)));
        }
        BufferedImage rootContainerImage = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rootContainerImages.put(UIElementState.DEFAULT, rootContainerImage);
        UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Settings button", "SpinForward",
                "assets/images/UI/settingsButton",
                50, Main.screen.getHeight() - 210, 50, Main.screen.getHeight() - 210, null, null);
//        UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Test button", null,
//                testButtonImages, null, 100, 150, 100, 350, null, new String[]{});
        UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Test switch", "TurnSwitch",
                "assets/images/UI/testSwitch", 118, 150, 150, 150, null, new String[]{"SWITCH"});
        UICreator.createTextLabel(uiController, uiAnimationController, "test", "assets/textFiles/settingsMenuText", 25, 60, 25, 25,
                new Font("Arial", Font.PLAIN, 16), Color.CYAN, Color.WHITE, 250, 450, 10, 10, 5, null);
        UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Settings menu", null,
                "assets/images/UI/settingsMenu", -300, 150, 25, 150, new String[]{"Test switch", "test"}, new String[]{"NO_ACTIVE_IMAGE", "NOT_INTERACTABLE"});
        UICreator.createUIElement(uiController, uiAnimationController, commandsManager, "Root", null, rootContainerImages, null, 0, 0,
                0, 0, new String[]{"Settings button", "Settings menu"}, new String[]{"NO_HOVER_IMAGE", "NOT_INTERACTABLE", "NO_ACTIVE_IMAGE"});
        initialized = true;
        UIUserLinker uiUserLinker = new UIUserLinker(uiController);
        screen.addMouseMotionListener(uiUserLinker);
        screen.addMouseListener(uiUserLinker);

//        MyThread thread = new MyThread();
//        UIAnimationThread uiAnimationThread = new UIAnimationThread(uiController, true);
//        thread.start();
//        uiAnimationThread.run();
    }
}