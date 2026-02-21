package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;

public class UICreator {
    private boolean busy = false;

    public static void createUIElement(UIController uiController, UIAnimationController uiAnimationController, UICommandsManager commandsManager,
                                       String name, String command, String folderPath,
                                       int startX, int startY, int targetX, int targetY, String[] children, String[] tags){
        HashMap<UIElementState, Image> stateImages = new HashMap<>();
        Image[] animationImages;
        if ((tags != null) && Arrays.asList(tags).contains("SWITCH")){
            for (String fileName : new String[]{"active", "inactive"}){
                File file = new File(folderPath + "/" + fileName + ".png");
                if (file.exists()) {
                    Image image = new ImageIcon(folderPath + "/" + fileName + ".png").getImage();
                    switch (fileName) {
                        case "active" -> {
                            stateImages.put(UIElementState.POSITIVE, image);
                        }
                        case "inactive" -> {
                            stateImages.put(UIElementState.NEGATIVE, image);
                            stateImages.put(UIElementState.DEFAULT, image);
                            stateImages.put(UIElementState.HOVER, image);
                        }
                    }
                }
            }
        } else {
            for (String fileName : new String[]{"main", "hover", "active"}) {
                File file = new File(folderPath + "/" + fileName + ".png");
                if (file.exists()) {
                    Image image = new ImageIcon(folderPath + "/" + fileName + ".png").getImage();
                    switch (fileName) {
                        case "main" -> {
                            stateImages.put(UIElementState.DEFAULT, image);
                        }
                        case "hover" -> {
                            stateImages.put(UIElementState.HOVER, image);
                        }
                        case "active" -> {
                            stateImages.put(UIElementState.ACTIVE, image);
                        }
                    }
                }
            }
        }
        File animationImagesFolder = new File(folderPath + "/animation");
        if (animationImagesFolder.exists()){
            animationImages = new Image[animationImagesFolder.listFiles().length];
            for (int i = 0; i < animationImages.length; i++){
                animationImages[i] = new ImageIcon(folderPath + "/animation/" + (i + 1) + ".png").getImage();
            }
        } else {
            animationImages = null;
        }
        if (children == null) {

            if ((tags != null) && Arrays.asList(tags).contains("SWITCH")){
                Switch sw = new Switch(startX, startY, targetX, targetY, name, stateImages, animationImages, tags);
                uiController.addElement(name, sw, UIElementState.DEFAULT);
                uiAnimationController.addElement(name, sw);
            } else {
                UIElement element = new UIElement(startX, startY, targetX, targetY, name, stateImages, animationImages, tags);
                uiController.addElement(name, element, UIElementState.DEFAULT);
                uiAnimationController.addElement(name, element);
            }
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

    public static void createTextLabel(UIController uiController, UIAnimationController uiAnimationController, String name,
                                       String folderPath, int startX, int startY, int targetX, int targetY, Font font,
                                       Color textColor, Color hoverColor, int width, int height, int xOffset, int yOffset, int padY, String[] tags){
        File folder = new File(folderPath);
        String text = null;
        if (folder.exists()){
            File file = new File(folderPath + "/" + name + ".txt");
            try {
                text = Files.readString(file.toPath());
            } catch (IOException e) {
                System.out.println("Error while reading text files in UICreator:");
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        if (tags == null){
            tags = new String[0];
        }
        TextLabel textLabel = new TextLabel(startX, startY, targetX, targetY, name, text, font, textColor, hoverColor, width, height, xOffset, yOffset, padY, tags);
        textLabel.updateCollisions(uiController);
        uiController.addElement(name, textLabel, UIElementState.DEFAULT);
        uiAnimationController.addElement(name, textLabel);
    }
}
