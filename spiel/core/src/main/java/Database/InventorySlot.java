package Database;

public class InventorySlot {
    public int slot;
    public String item;
    public int anzahl;

    public InventorySlot(int slot, String item, int anzahl) {
        this.slot = slot;
        this.item = item;
        this.anzahl = anzahl;
    }
}
