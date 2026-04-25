package minigames.maze;

import minigames.maze.entities.EntityType;
import minigames.maze.items.Bomb;
import minigames.maze.items.Item;

import java.util.Objects;

public class InventoryController {
    private final MazeController mazeController;
    private final Item[] items = new Item[4];

    public InventoryController(MazeController mazeController) {
        this.mazeController = mazeController;
    }

    public void useItem(int slot){
        if (slot > items.length){
            return;
        }
        if (items[slot] == null){
            return;
        }
        Item item = items[slot];
        if (item instanceof Bomb){
            EntityCreator.createEntity(mazeController, EntityType.TICKING_BOMB);
            items[slot] = null;
        }
    }

    public void pickUpItem(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == null){
                items[i] = item;
                mazeController.coordinatesController.removeObject(item.getName());
                mazeController.removeItem(item.getName());
                break;
            }
        }
    }

    public void destroyItem(int slot){
        items[slot] = null;
    }

    public boolean hasItem(String name){
        for (Item item : items){
            if (item == null){
                continue;
            }
            if (Objects.equals(item.getName(), name)){
                return true;
            }
        }
        return false;
    }

    protected Item[] getItems(){
        return items;
    }
}
