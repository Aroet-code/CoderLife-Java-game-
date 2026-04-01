package controllers;

import gameObject.collisionShapes2D.Vector;
import gameObject.collisionShapes2D.Vertex;
import util.GameController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class GameObjectCoordinatesController {
    private int mainX, mainY;
    private Vertex mainVertex;
    private Map<String, Vertex> collisionsCoordinates = new HashMap<>();
    private Map<String, Vertex> otherCoordinates = new HashMap<>();
    private Map<String, Vertex> renderedCoordinates = new HashMap<>();
    private boolean rendered = false;
    private float lastRenderedMultiplier = 1.0f;

    public GameObjectCoordinatesController() {
        mainX = GameController.getScreen().getWidth() / 2;
        mainY = GameController.getScreen().getHeight() / 2;
        mainVertex = new Vertex(mainX, mainY);
    }

    public void updateMainCoordinates(int addX, int addY){
        mainX += addX;
        mainY += addY;
        mainVertex = new Vertex(mainX, mainY);
    }

    public void updateCoordinates(String key, Vertex newCoordinates){
        otherCoordinates.replace(key, newCoordinates);
    }

    public Vertex getCoordinates(String key){
        if (!(otherCoordinates.containsKey(key))){
            System.out.println(key + "'s coordinates in GameObjectCoordinatesController are null.");
        }
        return otherCoordinates.get(key);
    }

    public void updateMainCoordinates(Vertex newVertex){
        mainX = newVertex.getX();
        mainY = newVertex.getY();
        mainVertex = newVertex;
    }

    public Vector getDrawingVector(Vertex vertex){
        return new Vector(mainVertex, vertex);
    }

    public Vector getDrawingVector(String key){
        return new Vector(mainVertex, otherCoordinates.get(key));
    }

    public Vertex getActualDrawingCoordinates(String key, float scalingMultiplier){
        Vector objVector = getDrawingVector(key);
        Vector multipliedVector = objVector.getMultipliedVector(scalingMultiplier);
        return new Vertex(multipliedVector.getProjX() + (mainVertex.getX()),
                multipliedVector.getProjY() + (mainVertex.getY()));
    }

    public void renderVectors(float scalingMultiplier){
        if (scalingMultiplier == lastRenderedMultiplier){
//            System.out.println("Still rendering...");
            return;
        }
        lastRenderedMultiplier = scalingMultiplier;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            Map<String, Vertex> newRender = new HashMap<>();
            for (String key : otherCoordinates.keySet()) {
                newRender.put(key, getActualDrawingCoordinates(key, scalingMultiplier));
            }
            renderedCoordinates.clear();
            renderedCoordinates = newRender;
            return 0;
        });
        future.thenRun(() -> {
            rendered = true;
        });
    }

    public Map<String, Vertex> getRenderedCoordinates(){
        return renderedCoordinates;
    }

    public boolean isRendered(){
        return rendered;
    }

    public void addCoordinates(String key, Vertex coords){
        otherCoordinates.put(key, coords);
    }

    @Deprecated
    public void addCollisionCoordinates(String key, Vertex coords){
        collisionsCoordinates.put(key, coords);
    }
}
