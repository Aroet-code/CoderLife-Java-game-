package util;

import camera.*;
import gameObject.*;
import gameObject.animation.Animation;
import gameObject.areas.RestrictedArea2D;
import gameObject.image.ImageController;
import gameObject.image.ImageCreator;
import gameObject.image.ImageScaler;
import gameObject.interactions.InteractableArea2D;
import gameObject.interactions.InteractableObject;
import threads.MyThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    public static ImageCreator imageCreator = new ImageCreator();
    public static SpriteBatch spriteBatch = new SpriteBatch();
    public static Notificator notificator = new Notificator();
    public static Player player = new Player(768, 402, playerImage, 16, layer1, 64, 96, debugLayer, imageCreator);
//    public static InteractingArea2D playerArea = new InteractingArea2D(player, 64, 96, debugLayer, imageCreator, player);
    public static Camera camera = new Camera(player, new Resolution<>(16, 9, 4), layers);
    public static ImageScaler imageScaler = new ImageScaler(camera);
    public static ImageController imageController = new ImageController(imageScaler, camera);
    public static Thread myThread = new Thread(new MyThread());
    public static AnimationController animationController = new AnimationController();
    public static Background bg = new Background(backGroundTest, 560, 360, 0, layer0, notificator);
    public static MathStuff math = new MathStuff();
    public static InteractableObject box = new InteractableObject(boxImage, 900, 450, true, layer1, 114, 114, imageCreator, debugLayer, interactables, animationController);
//    public static InteractableArea2D area = new InteractableArea2D(875, 425, 114, 114, debugLayer, imageCreator, box, interactables);
    public static RestrictedArea2D restrictedArea2D = new RestrictedArea2D(560, 360, 500, 300, restrictedAreaLayer, imageCreator, player);
    public static InteractableObject coffee = new InteractableObject(coffeeImages[0], 100, 100, true, layer1, 100, 100, imageCreator, debugLayer, interactables, animationController);
    private static final Animation coffeeAnimation = new Animation(new ArrayList<>(Arrays.asList(coffeeImages)), coffee, null);
}
