package camera;

import gameObject.GameObject;

import java.util.ArrayList;
import java.util.Iterator;

public class AnimationController {
    private ArrayList<GameObject> objects = new ArrayList<>();

    public void receiveObject(GameObject object){
        if (!(objects.contains(object))) {
            objects.add(object);
        } else {
            System.out.println("The object is already in the arrayList");
        }
    }

    public void removeObject(GameObject object){
        if ((objects.contains(object))) {
            objects.remove(object);
        } else {
            System.out.println("Hey, the object isn't in the array, you can't remove it from there.");
        }
    }

    public void animate() {
        if (!(objects.isEmpty())){
            Iterator<GameObject> iterator = objects.iterator();
            while (iterator.hasNext()){
                GameObject object = iterator.next();
                boolean animationResult = object.getCurrentAnimation().animate();
//                try{
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                System.out.println("Animation result for " + object + ": " + animationResult);

                if (!(animationResult)){
                    System.out.println("Removing " + object);
                    iterator.remove();
                }
            }
        }
    }
}
