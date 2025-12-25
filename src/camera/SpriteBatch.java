package camera;

import gameObject.GameObject;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;

import gameObject.image.DrawObject;
import gameObject.image.ImageScaler;
import gameObject.movement.MovingObject;

public class SpriteBatch{
    public void draw(Graphics g, GameObject object, ImageObserver observer){
        g.drawImage(object.getImage(), object.getX(), object.getY(), observer);
    }

    public void draw(Graphics g, DrawObject drawObject, ImageObserver observer){
        Image image = drawObject.getImage();
        g.drawImage(image, drawObject.getVertex().getX() - (image.getWidth(observer) / 2), drawObject.getVertex().getY() - (image.getHeight(observer) / 2),
                observer);
    }

    public void draw(Graphics g, DrawObject[] renderedObjects, ImageObserver observer){
        for (DrawObject renderedObject : renderedObjects){
            draw(g, renderedObject, observer);
        }
    }

    public static void draw(Graphics g, ArrayList<GameObject> arraylistOfObjects, ImageObserver observer){
        for (int i = 0; i < arraylistOfObjects.size(); i++){
            g.drawImage(arraylistOfObjects.get(i).getImage(),
                    arraylistOfObjects.get(i).getX(),
                    arraylistOfObjects.get(i).getY(),
                    observer);
        }
    }
    public static void draw(Graphics g, ArrayList<GameObject> objects, Camera camera, ImageScaler imageScaler, ImageObserver observer){
        float imageScaleMulti = imageScaler.getImageScaleMulti();
        MovingObject player = camera.getObject();
        int centerX = (camera.getWidth() / 2) - (player.getWidth() / 2);
        int centerY = (camera.getHeight() / 2) - (player.getHeight() / 2);
        for (GameObject gameObject : objects){
            if (gameObject != player ) {
                if ((gameObject.hasTag("FollowPlayer"))) {
                    g.drawImage(gameObject.getImage(), (camera.getWidth() / 2) - (gameObject.getImage().getWidth(observer) / 2),
                            (camera.getHeight() / 2) - (gameObject.getImage().getHeight(observer) / 2), null);
                } else {
                    int x = centerX + (int) ((gameObject.getXOnTheMap() - player.getXOnTheMap()) * imageScaleMulti);
                    int y = centerY + (int) ((gameObject.getYOnTheMap() - player.getYOnTheMap()) * imageScaleMulti);
                    g.drawImage(gameObject.getImage(), x, y, observer);
                }
            } else {
                g.drawImage(gameObject.getImage(), (camera.getWidth() / 2) - (gameObject.getImage().getWidth(observer) / 2),
                        (camera.getHeight() / 2) - (gameObject.getImage().getHeight(observer) / 2), null);
            }
        }
    }

//    ADD TRIANGLES THERE
    public static void draw(Graphics g){}

//    public static void drawAreas(Graphics g, ArrayList<GameObject.Areas.Area2D> layer, Camera.Camera camera, Camera.Camera.ImageScaler imageScaler, ImageObserver observer){
//        float imageScaleMulti = imageScaler.getImageScaleMulti();
//        GameObject.Movement.MovingObject player = camera.getObject();
//        int centerX = (camera.getWidth() / 2) - (player.getWidth() / 2);
//        int centerY = (camera.getHeight() / 2) - (player.getHeight() / 2);
//        for (GameObject.Areas.Area2D gameObject : layer){
//            g.setColor(gameObject.getInsideColor());
//            if (!(gameObject.hasTag("FollowPlayer"))) {
//                int x = centerX + (int)((gameObject.getXOnTheMap() - player.getXOnTheMap()) * imageScaleMulti);
//                int y = centerY + (int)((gameObject.getYOnTheMap() - player.getYOnTheMap()) * imageScaleMulti);
//                g.fillRect(x, y, gameObject.getWidth(), gameObject.getHeight());
//                g.setColor(gameObject.getOutsideColor());
//                g.drawRect(x, y, gameObject.getWidth(), gameObject.getHeight());
//            } else {
//                g.fillRect(centerX - (gameObject.getWidth() / 2), centerY - (gameObject.getHeight() / 2),
//                        gameObject.getWidth(), gameObject.getHeight());
//                g.setColor(gameObject.getOutsideColor());
//                g.drawRect(centerX - (gameObject.getWidth() / 2), centerY - (gameObject.getHeight() / 2),
//                        gameObject.getWidth(), gameObject.getHeight());
//            }
//        }
//    }
}