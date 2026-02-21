package util;

import camera.Camera;
import gameObject.image.ImageController;

public class SceneBuilder {
    public static void buildScene(Scene scene, ImageController imageController, Camera camera){
        switch (scene){
            case MAIN_MENU -> {

            }
            case MAIN_GAME -> {
                imageController.renderImages(new String[]{}, camera);
            }
            case null, default -> {
                System.out.println("There's no such scene in SceneBuilder");
            }
        }
    }
}
