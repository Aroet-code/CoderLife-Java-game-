package minigames.maze;

public class MazeMap {
    private int[][] map;

    private void createMaze(int width, int height){
        map = new int[width][height];
        for (int i = 0; i < width * height; i++){
            map[(i / height)][i % width] = 0;
        }
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
}
