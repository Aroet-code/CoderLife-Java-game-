package minigames.maze;

import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MazeMap {
    private int[][] map;
    private final Random random = new Random();
    private List<Room> rooms = new ArrayList<>();
    int width, height;

    public MazeMap(int width, int height, int roomCount, int roomSizeMin, int roomSizeMax) {
        this.width = width;
        this.height = height;
        createMap(roomCount, roomSizeMin, roomSizeMax);
    }

    private void createMap(int roomCount, int roomSizeMin, int roomSizeMax){
        createBase(width, height);
        System.out.println("Creating map step 1");
        consoleLogMap();
        createRooms(roomCount, roomSizeMin, roomSizeMax);
        System.out.println("Creating map step 2");
        consoleLogMap();
        createFloor();
        System.out.println("Creating map step 3");
        consoleLogMap();
        connectRooms();
        System.out.println("Creating map step 4");
        consoleLogMap();
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    private void createBase(int width, int height){
        map = new int[height][width];
        for (int i = 0; i < width * height; i++){
            map[i / width][i % width] = TileType.VOID.ordinal();
        }
    }

    private void createFloor(){
        for (Room r : rooms){
            int w = r.getWidth() - 2;
            int h = r.getHeight() - 2;
            Point pos = new Point(r.getPoint().x + 1, r.getPoint().y + 1);
            int x = pos.x;
            int y = pos.y;
            for (int i = 0; i < h; i++){
                for (int j = 0; j < w; j++){
                    map[y + i][x + j] = TileType.FLOOR.ordinal();
                }
            }
        }
//        for (int i = 0; i < (width - 2) * (height - 2); i++){
//            map[((i / (height - 2)) + 1)][(i % (width - 2) + 1)] = TileType.FLOOR.ordinal();
//        }
    }

    private void createRooms(int roomCount, int minSize, int maxSize){
//        Point startPos = new Point(random.nextInt(3, map[0].length - 1), random.nextInt(3, map[0].length - 1));
        for (int i = 0; i < roomCount; i++){
            int w = random.nextInt(minSize, maxSize);
            int h = random.nextInt(minSize, maxSize);
            Point pos = new Point(random.nextInt(3, map[0].length - maxSize), random.nextInt(3, map[0].length - maxSize));
            Room r = new Room(w, h, pos);
            rooms.add(r);
        }
        for (Room r : rooms){
            int x = r.getPoint().x;
            int y = r.getPoint().y;
            for (int i = 0; i < r.getHeight(); i++){
                for (int j = 0; j < r.getWidth(); j++){
                    map[y + i][x + j] = TileType.WALL.ordinal();
                }
            }
        }
//        for (int i = 0; i < width * height; i++){
//            map[(i / height)][i % width] = TileType.WALL.ordinal();
//        }
    }

    private void connectRooms(){
        Point startPos = new Point(rooms.getFirst().getPoint().x + 2, rooms.getFirst().getPoint().y + 2);
        List<Point> mustTouchPoints = new ArrayList<>();
        for (Room r : rooms){
            Point touchPoint = new Point((int) (r.getPoint().x + (r.getWidth() / 2)), (int) (r.getPoint().y + (r.getHeight() / 2)));
            mustTouchPoints.add(touchPoint);
        }
        List<List<Point>> paths = waveConnect(startPos, mustTouchPoints.toArray(new Point[0]));
        for (List<Point> path : paths){
            for (Point p : path){
                int newOrdinal = -1;
                int oldOrdinal = map[p.y][p.x];
                if (oldOrdinal == TileType.VOID.ordinal()){
                    newOrdinal = TileType.PLATFORM.ordinal();
                } else if (oldOrdinal == TileType.WALL.ordinal()){
                    newOrdinal = TileType.DOOR.ordinal();
                } else if (oldOrdinal == TileType.FLOOR.ordinal()){
                    continue;
                }
                if (newOrdinal == -1){
//                    System.out.println("So it's pretty weird, but the value of a point is: " + map[p.y][p.x]);
                    continue;
                }
                map[p.y][p.x] = newOrdinal;
            }
        }
    }

    private List<List<Point>> waveConnect(Point startPos, Point[] endPosArray){
        int[][] waveMap = new int[height][width];
        for (int i = 0; i < width * height; i++){
            waveMap[i / width][i % width] = -1;
        }
        waveMap[startPos.y][startPos.x] = 0;
        Queue<Point> q = new ArrayDeque<>();
        q.add(startPos);
        while (!(q.isEmpty())){
            Point pos = q.poll();
            if (!(pos.x + 1 >= width)){
                int difficultyValue = 0;
                if (map[pos.y][pos.x + 1] == TileType.FLOOR.ordinal()){
                    difficultyValue = 1;
                } else if (map[pos.y][pos.x + 1] == TileType.VOID.ordinal()){
                    difficultyValue = 25;
                } else if (map[pos.y][pos.x + 1] == TileType.WALL.ordinal()){
                    difficultyValue = 75;
                } else {
                    difficultyValue = 10000;
                }
                if (waveMap[pos.y][pos.x + 1] == -1 || waveMap[pos.y][pos.x + 1] > waveMap[pos.y][pos.x] + difficultyValue){
                    waveMap[pos.y][pos.x + 1] = waveMap[pos.y][pos.x] + difficultyValue;
                    q.add(new Point(pos.x + 1, pos.y));
                }
            }
            if (!(pos.x - 1 < 0)){
                int difficultyValue = 0;
                if (map[pos.y][pos.x - 1] == TileType.FLOOR.ordinal()){
                    difficultyValue = 1;
                } else if (map[pos.y][pos.x - 1] == TileType.VOID.ordinal()){
                    difficultyValue = 25;
                } else if (map[pos.y][pos.x - 1] == TileType.WALL.ordinal()){
                    difficultyValue = 75;
                } else {
                    difficultyValue = 10000;
                }
                if (waveMap[pos.y][pos.x - 1] == -1 || waveMap[pos.y][pos.x - 1] > waveMap[pos.y][pos.x] + difficultyValue){
                    waveMap[pos.y][pos.x - 1] = waveMap[pos.y][pos.x] + difficultyValue;
                    q.add(new Point(pos.x - 1, pos.y));
                }
            }
            if (!(pos.y + 1 >= height)){
                int difficultyValue = 0;
                if (map[pos.y + 1][pos.x] == TileType.FLOOR.ordinal()){
                    difficultyValue = 1;
                } else if (map[pos.y + 1][pos.x] == TileType.VOID.ordinal()){
                    difficultyValue = 25;
                } else if (map[pos.y + 1][pos.x] == TileType.WALL.ordinal()){
                    difficultyValue = 75;
                } else {
                    difficultyValue = 10000;
                }
                if (waveMap[pos.y + 1][pos.x] == -1 || waveMap[pos.y + 1][pos.x] > waveMap[pos.y][pos.x] + difficultyValue){
                    waveMap[pos.y + 1][pos.x] = waveMap[pos.y][pos.x] + difficultyValue;
                    q.add(new Point(pos.x, pos.y + 1));
                }
            }
            if (!(pos.y - 1 < 0)){
                int difficultyValue = 0;
                if (map[pos.y - 1][pos.x] == TileType.FLOOR.ordinal()){
                    difficultyValue = 1;
                } else if (map[pos.y - 1][pos.x] == TileType.VOID.ordinal()){
                    difficultyValue = 25;
                } else if (map[pos.y - 1][pos.x] == TileType.WALL.ordinal()){
                    difficultyValue = 75;
                } else {
                    difficultyValue = 10000;
                }
                if (waveMap[pos.y - 1][pos.x] == -1 || waveMap[pos.y - 1][pos.x] > waveMap[pos.y][pos.x] + difficultyValue){
                    waveMap[pos.y - 1][pos.x] = waveMap[pos.y][pos.x] + difficultyValue;
                    q.add(new Point(pos.x, pos.y - 1));
                }
            }
        }
//        System.out.println("Logging waveMap int[][] array:");
//        for (int[] row : waveMap){
//            System.out.println(Arrays.toString(row));
//        }
        List<List<Point>> result = new ArrayList<>(endPosArray.length);
        for (int i = 0; i < endPosArray.length; i++){
            Point endPos = endPosArray[i];
            if (waveMap[endPos.y][endPos.x] == -1) {
                result.add(new ArrayList<>());
                continue;
            }
            List<Point> wayBack = new ArrayList<>();
            wayBack.add(endPos);
            int currentValue = waveMap[endPos.y][endPos.x];
            while (currentValue != 0){
                Map<Integer, Point> neighbours = new HashMap<>();
                Point currentPos = wayBack.getLast();
                if (currentPos == null){
                    break;
                }
                if (currentPos.x - 1 >= 0)
                    if (!(neighbours.containsKey(waveMap[currentPos.y][currentPos.x - 1]))) {
                        neighbours.put(waveMap[currentPos.y][currentPos.x - 1], new Point(currentPos.x - 1, currentPos.y));
                    }
                if (currentPos.x + 1 < width)
                    if (!(neighbours.containsKey(waveMap[currentPos.y][currentPos.x + 1]))) {
                        neighbours.put(waveMap[currentPos.y][currentPos.x + 1], new Point(currentPos.x + 1, currentPos.y));
                    }
                if (currentPos.y - 1 >= 0)
                    if (!(neighbours.containsKey(waveMap[currentPos.y - 1][currentPos.x]))) {
                        neighbours.put(waveMap[currentPos.y - 1][currentPos.x], new Point(currentPos.x, currentPos.y - 1));
                    }
                if (currentPos.y + 1 < height)
                    if (!(neighbours.containsKey(waveMap[currentPos.y + 1][currentPos.x]))) {
                        neighbours.put(waveMap[currentPos.y + 1][currentPos.x], new Point(currentPos.x, currentPos.y + 1));
                    }
                for (int value : neighbours.keySet()){
                    currentValue = Math.min(currentValue, value);
                }
                if (neighbours.get(currentValue) == null){
                    System.out.println("Somehow, the point has value lower than all of its neighbours. That value is: " + currentValue);
                    System.out.println("And you know what's funny? Its coordinates are x: " + currentPos.x + ", y: " + currentPos.y);
                    System.out.println(neighbours.keySet());
                    break;
                }
                wayBack.add(neighbours.get(currentValue));
            }
            result.add(wayBack);
        }
        return result;
    }

    public void consoleLogMap(){
        System.out.println("/-------------------------------------------------\\");
        for (int[] row : map){
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\\-------------------------------------------------/");
    }

    public void updateTile(Point p, TileType tileType){
        map[p.y][p.x] = tileType.ordinal();
    }

    public List<Point> getPossibleSpawns(){
        List<Point> result = new ArrayList<>();
        for (Room room : rooms){
            Point rPos = room.getPoint();
            for (int i = 0; i < 3; i++) {
                Point newPos = new Point((int) (rPos.getX() + random.nextInt(1, room.getWidth() - 2)),
                        (int) (rPos.getY() + random.nextInt(1, room.getHeight() - 2)));
                result.add(newPos);
            }
        }
        return result;
    }
}