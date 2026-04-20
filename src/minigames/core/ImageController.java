package minigames.core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ImageController {
    private Map<String, Image> images = new HashMap<>();
    private List<String> preloadedImages = new ArrayList<>();

    public void addImage(String key, Image image){
        images.put(key, image);
    }

    public Image getImage(String key){
        try {
            return images.get(key);
        } catch (NullPointerException e) {
            if (!(preloadedImages.contains(key))) {
                System.out.println("The image in the ImageController in minigames has yet to be preloaded. Key: " + key);
            }
        }
        return new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
    }

    public void preloadImage(String key, String path){
        Image img = new ImageIcon(path).getImage();
        addImage(key, img);
        preloadedImages.add(key);
    }
}
