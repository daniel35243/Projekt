package io.github.FarmLife;

import java.awt.Cursor;

public interface inventarLogik {

    // 🔁 Alte Methoden
    void saveItem(String name, int quantity);

    int loadItem(String name);

    // Inventory
    void insertInventoryItem(int slot, String itemID, int anzahl);
    void updateInventoryItem(int slot, String itemID, int anzahl);
    void deleteInventorySlot(int slot);
    android.database.Cursor getAllInventory();

    // Items
    void insertItem(int itemID, String itemName);
    void updateItem(int itemID, String itemName);
    void deleteItem(int itemID);
    Cursor getAllItems();

    // Game
    void insertGame(int coins, int level, int xp);
    void updateGame(int coins, int level, int xp);
    void deleteGame();
    Cursor getGame();

    // Shop
    void insertShop(String itemID, int coins);
    void updateShop(String itemID, int coins);
    void deleteShop(String itemID);
    Cursor getAllShop();

    // Seller
    void insertSeller(int itemID, int coins);
    void updateSeller(int itemID, int coins);
    void deleteSeller(int itemID);
    Cursor getAllSeller();

    // Chest
    void insertChest(int slot, String item, int anzahl);
    void updateChest(int slot, String item, int anzahl);
    void deleteChest(int slot);
    Cursor getAllChest();

    // Felder
    void insertFeld(int feldID, String itemID, int wachsstufe);
    void updateFeld(int feldID, String itemID, int wachsstufe);
    void deleteFeld(int feldID);
    Cursor getAllFelder();

    // Feld_Location
    void insertFeldLocation(int feldID, int feldX, int feldY);
    void updateFeldLocation(int feldID, int feldX, int feldY);
    void deleteFeldLocation(int feldID);
    Cursor getAllFeldLocation();
}
