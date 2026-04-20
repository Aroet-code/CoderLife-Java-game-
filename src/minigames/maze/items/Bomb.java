package minigames.maze.items;

public class Bomb extends ItemDecorator {
    public Bomb(String name, Item item) {
        super(name, item, null);
    }

    public void explode(){

    }

    @Override
    public void use(){
        super.use();
        explode();
    }
}
