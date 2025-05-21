package Database;

import Items.Item;

public class InventorySlotDB {
    public int slot;
    public String item;
    public int anzahl;

    public InventorySlotDB(int slot, String item, int anzahl) {
        this.slot = slot;
        this.item = item;
        this.anzahl = anzahl;
    }

    public String getItem() {
        return item;
    }

    public int getAnzahl() {
        return anzahl;
    }
}
