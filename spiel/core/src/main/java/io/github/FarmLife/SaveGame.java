package io.github.FarmLife;

import Database.InventorySlotDB;
import Items.Item;

public class SaveGame {

    private final InventorySlotDB db;
    private final InventorySlot[] inventory;

    public SaveGame(InventorySlotDB db, InventorySlot[] inventory) {
        this.db = db;
        this.inventory = inventory;
    }

    public void saveInventory() {
        for (int i = 1; i < inventory.length; i++) {
            InventorySlot slot = inventory[i];
            Item item = slot.getItem();

            if (item != null) {
                String itemName = item.getClass().getSimpleName(); // z. B. "Karotte"
                int count = item.getItemCounter();

                db.updateInventoryItem(i, itemName, count); // Speichere direkt in DB
            } else {
                db.updateInventoryItem(i, "Leer", 0); // Falls leer, Slot zurücksetzen
            }
        }
    }
}
