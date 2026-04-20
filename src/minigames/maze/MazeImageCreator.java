package minigames.maze;

import minigames.core.ImageController;
import minigames.core.ImageCreator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MazeImageCreator extends ImageCreator {
    private ImageController imageController = new ImageController();
    private static final int cellPixelSize = 32;

    public MazeImageCreator(MazeController minigameController) {
        super(minigameController);
        for (String key : new String[]{"floor", "void", "door", "wall", "platform", "player"}){
            imageController.preloadImage(key, "assets/images/minigames/maze/" + key + ".png");
        }
    }

    @Override
    public Image getImage(int width, int height) {
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        MazeController mc = (MazeController) this.getMinigameController();
        MazeMap mm = mc.map;
        CoordinatesController cc = mc.coordinatesController;
        MovementController mvc = mc.movementController;

        int[][] map = mm.getMap();

        Point playerCoordinates = cc.getCoordinates("player");

        Graphics2D g = resultImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        int cellWidth = width / cellPixelSize;
        int cellHeight = height / cellPixelSize;

        Point cameraCoordinates = new Point(Math.clamp(((long) playerCoordinates.x * cellPixelSize) + (cellPixelSize / 2) - (width / 2), 0, map[0].length * cellPixelSize - width),
                Math.clamp(((long) playerCoordinates.y * cellPixelSize) + (cellPixelSize / 2) - (height / 2), 0, map.length * cellPixelSize - height));

        g.setColor(new Color(120, 230, 40));
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                int cell = map[i][j];
                Point coordinates = new Point(j * cellPixelSize, i * cellPixelSize);
                if (coordinates.x - cameraCoordinates.x < -cellPixelSize || coordinates.y - cameraCoordinates.y < -cellPixelSize ||
                        coordinates.x - cameraCoordinates.x > width + cellPixelSize || coordinates.y - cameraCoordinates.y > height + cellPixelSize){
                    continue;
                }
                Image drawImage = null;
                if (cell == TileType.VOID.ordinal()){
                    drawImage = imageController.getImage("void");
                } else if (cell == TileType.FLOOR.ordinal()){
                    drawImage = imageController.getImage("floor");
                } else if (cell == TileType.WALL.ordinal()){
                    drawImage = imageController.getImage("wall");
                } else if (cell == TileType.PLATFORM.ordinal()){
                    drawImage = imageController.getImage("platform");
                } else if (cell == TileType.DOOR.ordinal()){
                    drawImage = imageController.getImage("door");
                }
                g.drawImage(drawImage, coordinates.x - cameraCoordinates.x, coordinates.y - cameraCoordinates.y, null);
                // Drawing grid
                g.drawRect(coordinates.x - cameraCoordinates.x, coordinates.y - cameraCoordinates.y, cellPixelSize, cellPixelSize);
            }
        }

        return resultImage;
    }
}
