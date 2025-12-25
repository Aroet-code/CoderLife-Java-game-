package gameObject;

import gameObject.interactions.InteractableArea2D;
import gameObject.interactions.InteractingArea2D;
import gameObject.movement.Direction;
import gameObject.movement.Parallaxable;

import java.util.ArrayList;
import java.util.concurrent.Flow;

public class Notificator implements Flow.Publisher {
    ArrayList<Flow.Subscriber> subscribers = new ArrayList<>();
    ArrayList<Parallaxable> backgrounds = new ArrayList<>();
    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Flow.Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void addBackground(Parallaxable background){
        backgrounds.add(background);
    }

    public void removeBackground(Parallaxable background){
        backgrounds.remove(background);
    }

    public void update() {
        subscribers.forEach(Flow.Subscriber::onComplete);
    }

    public void onMove(Direction direction, int speed){
        for (Parallaxable background : backgrounds) {
            background.onMove(direction, speed);
        }
    }

    public void onMove(InteractingArea2D playerArea, ArrayList<InteractableArea2D> interactableAreas){
//        playerArea.setCenterXOnTheMap(playerArea.getInteractingObject().getCenterXOnTheMap());
//        playerArea.setCenterYOnTheMap(playerArea.getInteractingObject().getCenterYOnTheMap());
//        System.out.println(playerArea.getCenterXOnTheMap() + ", " + playerArea.getCenterYOnTheMap());
        System.out.println("Player area coordinates are: " + playerArea.getXOnTheMap() + ", " + playerArea.getYOnTheMap());
//        for (InteractableArea2D area : interactableAreas){
//            System.out.println("Areas coordinates are: " + area.getXOnTheMap() + ", " + area.getYOnTheMap());
//            if (area.intersects(playerArea)){
//                playerArea.onCollision(area);
//                break;
//            }
//        }
        for (InteractableArea2D area : interactableAreas) {
            System.out.println("Areas coordinates are: " + area.getXOnTheMap() + ", " + area.getYOnTheMap());
            if (area.canInteract(playerArea)){
                playerArea.onCollision(area);
                break;
            }
        }
    }
}
