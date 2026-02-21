package ui;

import java.awt.*;
import java.util.List;
import java.util.concurrent.Callable;

public class BackgroundAnimation implements Callable<Image> {
    private Callable<Image> code;
    private List<AnimationObject> objects;

    public BackgroundAnimation(Callable<Image> callable, List<AnimationObject> objects) {
        this.code = callable;
        this.objects = objects;
    }

    public List<AnimationObject> getObjects() {
        return objects;
    }

    @Override
    public Image call() throws Exception {
        return code.call();
    }
}
