package minigames.cooking;

import minigames.core.ImageController;
import minigames.core.ImageCreator;
import minigames.core.MinigameController;
import util.GameController;

import java.util.List;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class CookingImageCreator extends ImageCreator {
    ImageController imageController = new ImageController();

    public CookingImageCreator(CookingMinigameController minigameController) {
        super(minigameController);
        initializeImages();
    }

    private void initializeImages(){
        imageController.preloadImage("APPLE", "assets/images/minigames/cooking/apple.png");
        imageController.preloadImage("Player", "assets/images/minigames/cooking/player.png");
        BufferedImage bg = new BufferedImage(GameController.getScreen().getWidth(), GameController.getScreen().getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bg.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameController.getScreen().getWidth(), GameController.getScreen().getHeight());
        imageController.addImage("bg", bg);
    }

    @Override
    public Image getImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Image bg = imageController.getImage("bg");

        Graphics2D g = image.createGraphics();

        g.drawImage(bg, 0, 0, null);

        MinigameController minigameController = getMinigameController();
        CookingMinigameController mg;
        if (minigameController instanceof CookingMinigameController){
            mg = (CookingMinigameController) minigameController;
        } else {
            System.out.println("The minigame controller is an instance of the wrong type. Type: " + minigameController.getClass().toString() + " Required type: " + CookingMinigameController.class.toString());
            return null;
        }
        List<Map.Entry<String, FoodTypes>> objects = mg.getObjects();

        for (var obj : objects){
            FoodTypes type = obj.getValue();
            String key = obj.getKey();
            Point coordinates = mg.cc.getCoordinates(key);

//            Image objImg = imageController.getImage(type.toString());
            Image objImg = imageController.getImage("APPLE");
            if (key.equals("Player")){
                objImg = imageController.getImage("Player");
            }

            g.drawImage(objImg, coordinates.x - objImg.getWidth(null) / 2, coordinates.y - objImg.getHeight(null) / 2, null);
        }

        return image;
    }
}
