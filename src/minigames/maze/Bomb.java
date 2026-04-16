package minigames.maze;

public class Bomb extends ItemDecorator{
    public Bomb(Item item) {
        super(item);
    }

    public void explode(){

    }

    @Override
    public void use(){
        super.use();
        explode();
    }
}
