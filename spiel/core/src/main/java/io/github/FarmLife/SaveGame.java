package io.github.FarmLife;

import com.badlogic.gdx.Gdx;

import Database.InventorySlotDB;
import Database.ShopByItem;
import Items.Item;

public class SaveGame {

    private final InventorySlotDB db;
    private final InventorySlot[] inventory;
    Main game = (Main) Gdx.app.getApplicationListener();

    public SaveGame(InventorySlotDB db, InventorySlot[] inventory) {
        this.db = db;
        this.inventory = inventory;
    }

    public void saveInventory() {
        String itemString = "";
        for (int i = 1; i < inventory.length; i++) {
            InventorySlot slot = inventory[i];
            Item item = slot.getItem();
            itemString = inventory[i-1].getItem().getClass().getName();
            InventorySlotDB updateInventoryItem = ((Main) game).db.updateInventoryItem(1, itemString, inventory[i].getAnzahl());

        }
    }
}
