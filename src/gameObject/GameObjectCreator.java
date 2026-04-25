package gameObject;

import controllers.*;
import gameObject.animation.AnimatableObject;
import gameObject.animation.PassivelyAnimatedGameObject;
import gameObject.collisionShapes2D.*;
import gameObject.image.GameObjectImagePackage;
import util.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameObjectCreator {
    private static int restrictedAreaQuantity = 0;
    private static int allowedAreaQuantity = 0;

    public static void createAllowedArea(CollisionController collisionController, GameObjectCoordinatesController coordinatesController,
                                         Vertex coords, int width, int height){
        String key = "Allowed area " + allowedAreaQuantity;
        AllowedRectangleShape2D shape = new AllowedRectangleShape2D(coords, width, height);
        collisionController.addAllowedShape(key, shape);
        coordinatesController.addCoordinates(key, coords);
        allowedAreaQuantity++;
    }

    public static void createRestrictedArea(CollisionController collisionController, GameObjectCoordinatesController coordinatesController,
                                            Vertex coords, int width, int height){
        String key = "Restricted area " + restrictedAreaQuantity;
        RestrictedRectangleShape2D shape = new RestrictedRectangleShape2D(coords, width, height);
        collisionController.addRestrictedShape(key, shape);
        coordinatesController.addCoordinates(key, coords);
        restrictedAreaQuantity++;
    }

    public static void createGameObject(GameObjectController gameObjectController, ImageController imageController,
                                        GameObjectCoordinatesController gameObjectCoordinatesController,
                                        CollisionController collisionController, String name, Vertex startPos, int collisionWidth, int collisionHeight,
                                        String folderPath, boolean passivelyAnimated, boolean directional){
        if (directional) {
            for (String s : new String[]{"-left", "-right"}) {
                Image defaultImage = null;
                Image finalImage = null;
                for (String fileName : new String[]{"default", "final"}) {
                    File file = new File(folderPath + "/" + fileName + s + ".png");
                    if (file.exists()) {
                        Image image = new ImageIcon(folderPath + "/" + fileName + s + ".png").getImage();
                        switch (fileName) {
                            case "default" -> {
                                defaultImage = image;
                            }
                            case "final" -> {
                                finalImage = image;
                            }
                        }
                    }
                }
                Image[] animationImages = null;
                File animationFolder = new File(folderPath + "/animation" + s + "/");
                if (animationFolder.exists()) {
                    animationImages = new Image[animationFolder.listFiles().length];
                    for (int i = 0; i < animationImages.length; i++) {
                        animationImages[i] = new ImageIcon(folderPath + "/animation" + s + "/" + (i + 1) + ".png").getImage();
                    }
                }
                if (defaultImage == null) {
                    defaultImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
                    System.out.println("Warning: an empty default image for: " + name + ". And the -s value is: " + s);
                }
                GameObjectImagePackage pkg = new GameObjectImagePackage(animationImages, defaultImage, finalImage);
                if (animationFolder.exists()) {
                    if (passivelyAnimated) {
                        GameController.getAnimationController().addAnimation(name, new PassivelyAnimatedGameObject(new AnimatableObject()));
                    } else {
                        GameController.getAnimationController().addAnimation(name, new AnimatableObject());
                    }
                } else {
//            System.out.println("The animation folder doesn't exist for the key: " + name);
                }
                imageController.addImagePackage(name + s, pkg);
            }
        }
        Image defaultImage = null;
        Image finalImage = null;
        for (String fileName : new String[]{"default", "final"}) {
            File file = new File(folderPath + "/" + fileName + ".png");
            if (file.exists()) {
                Image image = new ImageIcon(folderPath + "/" + fileName + ".png").getImage();
                switch (fileName) {
                    case "default" -> {
                        defaultImage = image;
                    }
                    case "final" -> {
                        finalImage = image;
                    }
                }
            }
        }
        Image[] animationImages = null;
        File animationFolder = new File(folderPath + "/animation" + "/");
        if (animationFolder.exists()) {
            animationImages = new Image[animationFolder.listFiles().length];
            for (int i = 0; i < animationImages.length; i++) {
                animationImages[i] = new ImageIcon(folderPath + "/animation/" + (i + 1) + ".png").getImage();
            }
        }
        if (defaultImage == null) {
            defaultImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            System.out.println("Warning: an empty default image for: " + name + ". And the -s value is: ");
        }
        GameObjectImagePackage pkg = new GameObjectImagePackage(animationImages, defaultImage, finalImage);
        if (animationFolder.exists()) {
            if (passivelyAnimated) {
                GameController.getAnimationController().addAnimation(name, new PassivelyAnimatedGameObject(new AnimatableObject()));
            } else {
                GameController.getAnimationController().addAnimation(name, new AnimatableObject());
            }
        } else {
//            System.out.println("The animation folder doesn't exist for the key: " + name);
        }
        imageController.addImagePackage(name, pkg);
        gameObjectCoordinatesController.addCoordinates(name, startPos);
        gameObjectCoordinatesController.addCoordinates(name + " collision", startPos);
        if (!(collisionWidth == 0 || collisionHeight == 0)){
            CollisionShape2D collision = new RectangleShape2D(startPos, collisionWidth, collisionHeight);
            collisionController.addCollision(name, collision, CollisionState.DEFAULT);
        }
        gameObjectController.addKey(name);
    }

    public static void createMovingObject(GameObjectController gameObjectController, ImageController imageController,
                                          GameObjectCoordinatesController gameObjectCoordinatesController,
                                          CollisionController collisionController, String name, Vertex startPos, int collisionWidth, int collisionHeight,
                                          String folderPath, boolean passivelyAnimated, MovementController movementController, int speed) {
        createGameObject(gameObjectController, imageController, gameObjectCoordinatesController, collisionController, name, startPos, collisionWidth, collisionHeight, folderPath, passivelyAnimated, true);
        movementController.addObject(name, speed);
    }
}
