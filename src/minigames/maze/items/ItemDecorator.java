package minigames.maze.items;

import java.util.List;

public class ItemDecorator implements Item{
    private final Item item;
    private final String name;
    private List<ItemFlag> flags;

    public ItemDecorator(String name, Item item, List<ItemFlag> flags){
        this.item = item;
        this.name = name;
        this.flags = flags;
    }

    @Override
    public void use() {
        item.use();
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean hasFlag(ItemFlag flag){
        try {
            return flags.contains(flag);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
