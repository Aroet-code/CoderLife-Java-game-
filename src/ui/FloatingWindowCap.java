package ui;

import java.awt.*;
import java.util.HashMap;

public class FloatingWindowCap extends UIElement{
    public FloatingWindowCap(String name, HashMap<UIElementState, Image> stateImages, String[] tags) {
        super(0, 0, 0, 0, name, stateImages, null, tags);
    }
}
