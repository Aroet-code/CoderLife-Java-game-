package gameObject.collisionShapes2D;

import java.awt.*;

public class RectangleArea2D extends RectangleShape2D {

    public void onMove(PlayerArea2D player){
        if (player.intersects(this)){
            System.out.println("Player intersects this");
            onCollision(player);
//            player.update(this);
        }
    }
    @Override
    public void onCollision(Shape other) {
        System.out.println("OnCollision function works properly too!");
    }
}
