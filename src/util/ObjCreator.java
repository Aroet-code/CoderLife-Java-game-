package util;

import camera.*;
import camera.Screen;
import gameObject.*;
import gameObject.animation.Animation;
import gameObject.areas.RestrictedArea2D;
import gameObject.image.ImageController;
import gameObject.image.ImageCreator;
import gameObject.image.ImageScaler;
import gameObject.interactions.InteractableArea2D;
import gameObject.interactions.InteractableObject;
import threads.MyThread;
import threads.UIAnimationThread;
import ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

public class ObjCreator {
    public static LayerGroup layers = new LayerGroup();

    public static Layer layer0 = new Layer(layers);
    public static Layer layer1 = new Layer(layers);
    public static Layer debugLayer = new Layer(layers);
    public static Layer restrictedAreaLayer = new Layer(layers);

    public static ArrayList<InteractableArea2D> interactables = new ArrayList<>();

    private static final Image playerImage = new ImageIcon("assets/images/player/down/0.png").getImage();
    private static final Image backGroundTest = new ImageIcon("assets/images/backgrounds/corridor.png").getImage();
    private static final Image boxImage = new ImageIcon("assets/images/interactableObjects/box.png").getImage();
    private static final Image[] coffeeImages = new Image[]{
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/0.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/1.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/2.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/3.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/4.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/5.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/6.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/7.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/8.png").getImage(),
        new ImageIcon("assets/images/interactableObjects/spillingCoffee/9.png").getImage()
    };

    static Random random = new Random();

    public static ImageCreator imageCreator = new ImageCreator();
    public static SpriteBatch spriteBatch = new SpriteBatch();
    public static Notificator notificator = new Notificator();
    public static Player player = new Player(768, 402, playerImage, 16, layer1, 64, 96, debugLayer, imageCreator);
//    public static InteractingArea2D playerArea = new InteractingArea2D(player, 64, 96, debugLayer, imageCreator, player);
    public static Camera camera = new Camera(player, new Resolution<>(16, 10, 6), layers);
    public static ImageScaler imageScaler = new ImageScaler(camera);
    public static ImageController imageController = new ImageController(imageScaler, camera);
    public static MyThread myThread = new MyThread();
    public static AnimationController animationController = new AnimationController();
    public static Background bg = new Background(backGroundTest, 560, 360, 0, layer0, notificator);
    public static MathStuff math = new MathStuff();
    public static InteractableObject box = new InteractableObject(boxImage, 900, 450, true, layer1, 114, 114, imageCreator, debugLayer, interactables, animationController);
//    public static InteractableArea2D area = new InteractableArea2D(875, 425, 114, 114, debugLayer, imageCreator, box, interactables);
    public static RestrictedArea2D restrictedArea2D = new RestrictedArea2D(560, 360, 500, 300, restrictedAreaLayer, imageCreator, player);
    public static InteractableObject coffee = new InteractableObject(coffeeImages[0], 100, 100, true, layer1, 100, 100, imageCreator, debugLayer, interactables, animationController);
    private static final Animation coffeeAnimation = new Animation(new ArrayList<>(Arrays.asList(coffeeImages)), coffee, null);

    public static Screen screen = new Screen(new Resolution<>(16, 10, 6));
    public static UIAnimationThread uiAnimationThread = new UIAnimationThread(Main.uiController, Main.uiAnimationController ,false);

    public static final List<AnimationObject> objects = Collections.synchronizedList(new ArrayList<>());

    private static final int maxWidth = 10;

    private static BackgroundAnimation mainMenuAnimation = new BackgroundAnimation(new Callable<Image>() {
        @Override
        public Image call() throws Exception {
            int width, height;
            //  if (screen != null) {
            width = screen.getWidth();
            height = screen.getHeight();
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
                        g.fillRect(0, screen.getHeight() - object.getHeight(), screen.getWidth(), object.getHeight());
                        if (returnValue == -1) {
                            toRemove.add(fire);
//                            int newWidth = Math.max(1, random.nextInt(maxWidth));
                            toAdd.add(new RectangledFire(0, screen.getHeight(), screen.getWidth(), 1f));
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

    public static void addObjects(List<AnimationObject> objects){
        synchronized (objects) {
            for (int i = 0; i < 100; i++) {
                objects.add(new Star(0, 0 + (i * 30), 10, 10));
            }
            for (int i = 0; i < 15; i++){
                objects.add(new RectangledFire(0, screen.getHeight(), screen.getWidth(), 1 + (0.08f * i)));
            }
            objects.add(new AnimatedLabel(500, 2800, 500, 50, "PRESS <ENTER> TO START", new Font("Arial", Font.ITALIC, 32), new Color(0.30f, 0.65f , 1.00f, 1f)));
            objects.add(new LightLine(100, 5, screen.getHeight(), 0.02f, new Color(0.30f, 0.65f , 1.00f, 1f)));
//            objects.add(new LightLine(500, 10, screen.getHeight(), 0.015f, new Color(0.30f, 0.65f , 1.00f, 1f)));
        }
    }

    public static AnimatedBackground mainMenuBackground = new AnimatedBackground(mainMenuAnimation);

    public static HashMap<UIElementState, Image> rootContainerImages = new HashMap<>(){
        {
            put(UIElementState.DEFAULT, new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_ARGB));
        }
    };
    public static UIController uiController = new UIController();
    public static UIBatch uiBatch = new UIBatch(uiController);
    public static UIAnimationController uiAnimationController = new UIAnimationController();
    public static UICommandsManager commandsManager = new UICommandsManager();

    public static void createUI(){
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
    }

    public static void addUserLinker(){
        UIUserLinker uiUserLinker = new UIUserLinker(uiController);
        screen.addMouseMotionListener(uiUserLinker);
        screen.addMouseListener(uiUserLinker);
    }
}
