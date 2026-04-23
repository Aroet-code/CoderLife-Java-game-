package minigames.maze.items;

public class ItemBase implements Item{
    private String name;

    public ItemBase(String name) {
        this.name = name;
    }

    @Override
    public void use() {

    }

    @Override
    public String getName() {
        return "";
    }
}
