package io.github.FarmLife.android;

import android.content.Context;
import android.database.Cursor;
import com.mygdx.game.InventoryDbHelper;
import io.github.FarmLife.inventarLogik;

public class AndroidInventoryService implements inventarLogik {

    private final InventoryDbHelper db;

    public AndroidInventoryService(Context context) {
        db = new InventoryDbHelper(context);
    }

    // 🔁 Alte Methoden
    @Override
    public void saveItem(String name, int quantity) {
        db.insertInventoryItem(0, name, quantity);
    }

    @Override
    public int loadItem(String name) {
        Cursor cursor = db.getAllInventory();
        int result = 0;
        while (cursor.moveToNext()) {
            String itemID = cursor.getString(cursor.getColumnIndexOrThrow("ItemID"));
            if (itemID.equals(name)) {
                result = cursor.getInt(cursor.getColumnIndexOrThrow("Anzahl"));
                break;
            }
        }
        cursor.close();
        return result;
    }

    // ✅ Inventory
    @Override
    public void insertInventoryItem(int slot, String itemID, int anzahl) {
        db.insertInventoryItem(slot, itemID, anzahl);
    }

    @Override
    public void updateInventoryItem(int slot, String itemID, int anzahl) {
        db.updateInventoryItem(slot, itemID, anzahl);
    }

    @Override
    public void deleteInventorySlot(int slot) {
        db.deleteInventorySlot(slot);
    }


    // ✅ Items
    @Override
    public void insertItem(int itemID, String itemName) {
        db.insertItem(itemID, itemName);
    }

    @Override
    public void updateItem(int itemID, String itemName) {
        db.updateItem(itemID, itemName);
    }

    @Override
    public void deleteItem(int itemID) {
        db.deleteItem(itemID);
    }



    // ✅ Game
    @Override
    public void insertGame(int coins, int level, int xp) {
        db.insertGame(coins, level, xp);
    }

    @Override
    public void updateGame(int coins, int level, int xp) {
        db.updateGame(coins, level, xp);
    }

    @Override
    public void deleteGame() {
        db.deleteGame();
    }


    // ✅ Shop
    @Override
    public void insertShop(String itemID, int coins) {
        db.insertShop(itemID, coins);
    }

    @Override
    public void updateShop(String itemID, int coins) {
        db.updateShop(itemID, coins);
    }

    @Override
    public void deleteShop(String itemID) {
        db.deleteShop(itemID);
    }


    // ✅ Seller
    @Override
    public void insertSeller(int itemID, int coins) {
        db.insertSeller(itemID, coins);
    }

    @Override
    public void updateSeller(int itemID, int coins) {
        db.updateSeller(itemID, coins);
    }

    @Override
    public void deleteSeller(int itemID) {
        db.deleteSeller(itemID);
    }


    // ✅ Chest
    @Override
    public void insertChest(int slot, String item, int anzahl) {
        db.insertChest(slot, item, anzahl);
    }

    @Override
    public void updateChest(int slot, String item, int anzahl) {
        db.updateChest(slot, item, anzahl);
    }

    @Override
    public void deleteChest(int slot) {
        db.deleteChest(slot);
    }


    // ✅ Felder
    @Override
    public void insertFeld(int feldID, String itemID, int wachsstufe) {
        db.insertFeld(feldID, itemID, wachsstufe);
    }

    @Override
    public void updateFeld(int feldID, String itemID, int wachsstufe) {
        db.updateFeld(feldID, itemID, wachsstufe);
    }

    @Override
    public void deleteFeld(int feldID) {
        db.deleteFeld(feldID);
    }


    // ✅ Feld_Location
    @Override
    public void insertFeldLocation(int feldID, int feldX, int feldY) {
        db.insertFeldLocation(feldID, feldX, feldY);
    }

    @Override
    public void updateFeldLocation(int feldID, int feldX, int feldY) {
        db.updateFeldLocation(feldID, feldX, feldY);
    }

    @Override
    public void deleteFeldLocation(int feldID) {
        db.deleteFeldLocation(feldID);
    }

}
