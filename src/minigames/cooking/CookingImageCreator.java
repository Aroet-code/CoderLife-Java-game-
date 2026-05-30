package minigames.cooking;

import minigames.core.ImageController;
import minigames.core.ImageCreator;
import minigames.core.MinigameController;
import util.GameController;
import util.MovementStateHandler;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
        imageController.preloadImage("Player-boosted", "assets/images/minigames/cooking/playerBoosted.png");
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
                if (MovementStateHandler.holdingShift){
                    objImg = imageController.getImage("Player-boosted");
                } else {
                    objImg = imageController.getImage("Player");
                }
            }
            if (key.equals("Floor")){
                objImg = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
            }

            if (coordinates == null){
                System.out.println("Coordinates of " + key + " == null in CookingImageCreator");
                continue;
            }
            g.drawImage(objImg, coordinates.x - objImg.getWidth(null) / 2, coordinates.y - objImg.getHeight(null) / 2, null);
        }

        CookingScoreSystem scoreSystem = mg.scoreSystem;

        long score = scoreSystem.score;
        int combo = scoreSystem.combo;
        float accuracy = scoreSystem.accuracy;

        File fontFile = new File("assets/fonts/minecraftfont.ttf");
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        font = font.deriveFont(30f);
        g.setFont(font);

        g.setColor(Color.WHITE);

        g.drawString("Score: " + score, width - (("Score: " + score).length() * font.getSize() / 1.41f), 100);

        if (accuracy == 100){
            g.setColor(Color.GREEN);
        }
        g.drawString("Accuracy: " + accuracy, width - (("Accuracy: " + accuracy).length() * font.getSize() / 1.41f), 50);

        String rankLabelText = "";
        Color textColor;

        if (accuracy == 100){
            rankLabelText += "SS";
            textColor = Color.YELLOW;
        } else if (98 <= accuracy){
            rankLabelText += "S";
            textColor = Color.YELLOW;
        } else if (95 <= accuracy){
            rankLabelText += "A";
            textColor = Color.MAGENTA;
        } else if (90 <= accuracy){
            rankLabelText += "B";
            textColor = Color.CYAN;
        } else if (80 <= accuracy){
            rankLabelText += "C";
            textColor = Color.PINK;
        } else if (50 <= accuracy){
            rankLabelText += "D";
            textColor = Color.RED;
        } else {
            rankLabelText += "F";
            textColor = Color.RED;
        }

        g.setColor(textColor);
        g.drawString(rankLabelText, width - ((rankLabelText).length() * font.getSize() / 1.41f) - 20, 150);

        g.setColor(Color.WHITE);

        font = font.deriveFont(50f);

        g.setFont(font);
        if (combo % 100 == 0){
            g.setColor(Color.YELLOW);
        }
        if (combo == 0){
            g.setColor(Color.RED);
        }
        g.drawString(String.valueOf(combo), 25, 75);

        return image;
    }
}
