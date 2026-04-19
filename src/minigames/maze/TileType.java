package minigames.maze;

public enum TileType {
    VOID(0),
    FLOOR(1),
    WALL(2),
    DESTRUCTIBLE_WALL(3),
    DOOR(4),
    PLATFORM(5);

    public final int value;
    TileType(int value) { this.value = value; }
}
