package minigames.maze.items;

public class Player extends ItemDecorator{
    public Player(String name, Item item) {
        super(name, item, null);
    }
}
