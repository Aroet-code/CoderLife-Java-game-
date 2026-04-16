package minigames.maze;

public class ItemDecorator implements Item{
    private Item item;

    public ItemDecorator(Item item){
        this.item = item;
    }

    @Override
    public void use() {
        item.use();
    }
}
