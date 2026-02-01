import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UIUserLinker implements MouseMotionListener, MouseListener {
    private UIController uiController;

    public UIUserLinker(UIController uiController) {
        this.uiController = uiController;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.uiController.onMouseClick();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        uiController.onUpdateMousePosition(e.getX(), e.getY());
    }
}
