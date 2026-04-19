package minigames.maze.items;

public class ItemDecorator implements Item{
    private final Item item;
    private final String name;

    public ItemDecorator(String name, Item item){
        this.item = item;
        this.name = name;
    }

    @Override
    public void use() {
        item.use();
    }

    @Override
    public String getName() {
        return name;
    }
}
