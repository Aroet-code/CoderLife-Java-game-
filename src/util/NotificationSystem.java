package util;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class NotificationSystem {
    public Queue<Notification> notifications = new ArrayDeque<>();
    private final Random random = new Random();
    private static final int FALLING_SPEED = 5;

    public void notify(String text){
        int maxX = GameController.getScreen().getWidth() - 400;
        int y = -100;
        Notification notification = new Notification(random.nextInt(0, maxX), y, text);
        notifications.add(notification);
    }

    public void notify(String text, int yOffset){
        int maxX = GameController.getScreen().getWidth() - 400;
        int y = -100 - yOffset;
        Notification notification = new Notification(random.nextInt(0, maxX), y, text);
        notifications.add(notification);
    }


    public void fallDown(){
        int size = notifications.size();
        for (int i = 0; i < size; i++){
            Notification notification = notifications.poll();
            if (notification == null){
                continue;
            }
            notification.setY(notification.getY() + FALLING_SPEED);
            if (notification.getY() > GameController.getScreen().getHeight()){
                continue;
            }
            notifications.add(notification);
        }
    }
}
