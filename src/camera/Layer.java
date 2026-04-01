package camera;

import gameObject.GameObject;

import java.util.ArrayList;

@Deprecated
public class Layer extends ArrayList<GameObject> {
    private ArrayList<ArrayList<GameObject>> layersGroup;
    public Layer(LayerGroup group){
        group.add(this);
    }
}
