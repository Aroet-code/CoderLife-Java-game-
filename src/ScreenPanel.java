import util.ObjCreator;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class ScreenPanel extends JPanel implements Flow.Subscriber {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ObjCreator.spriteBatch.draw(g, ObjCreator.player, null);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Object item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        repaint();
    }
}
