import gameObject.movement.Direction;
import util.ObjCreator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Mover implements KeyListener{
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> ObjCreator.player.move(Direction.UP, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_UP -> ObjCreator.player.move(Direction.UP, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_A -> ObjCreator.player.move(Direction.LEFT, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_LEFT -> ObjCreator.player.move(Direction.LEFT, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_S -> ObjCreator.player.move(Direction.DOWN, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_DOWN -> ObjCreator.player.move(Direction.DOWN, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_D -> ObjCreator.player.move(Direction.RIGHT, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_RIGHT -> ObjCreator.player.move(Direction.RIGHT, ObjCreator.notificator, ObjCreator.player.getArea(), ObjCreator.interactables);
            case KeyEvent.VK_MINUS -> ObjCreator.camera.onResolutionChange(ObjCreator.camera.getResolution().getSize() - 1, ObjCreator.layers, ObjCreator.imageScaler);
            case KeyEvent.VK_EQUALS -> ObjCreator.camera.onResolutionChange(ObjCreator.camera.getResolution().getSize() + 1, ObjCreator.layers, ObjCreator.imageScaler);
            case KeyEvent.VK_F -> ObjCreator.player.interact();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
