package io.github.FarmLife.android;

import android.content.Context;
import android.database.Cursor;

import Database.FeldByCord;
import Database.GameByID;
import Database.InventorySlotDB;
import Database.SellerByItem;
import Database.ShopByItem;
import Database.DatabaseLogik;

public class AndroidDatabaseLogik implements DatabaseLogik {

    private final io.github.FarmLife.android.DatabaseHelper db;

    public AndroidDatabaseLogik(Context context) {
        db = new io.github.FarmLife.android.DatabaseHelper(context);
    }

    // Inventory
    @Override
    public void updateInventoryItem(int slot, String itemID, int anzahl) {
        db.updateInventoryItem(slot, itemID, anzahl);
    }
    @Override
    public InventorySlotDB getInventorySlot(int slot) {
        Cursor cursor = db.getInventorySlot(slot);
        InventorySlotDB result = null;

        if (cursor != null && cursor.moveToFirst()) {
            String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
            int anzahl = cursor.getInt(cursor.getColumnIndexOrThrow("Anzahl"));
            result = new InventorySlotDB(slot, item, anzahl);
            cursor.close();
        }

        return result;
    }

    // Game
    @Override
    public void updateGame(int coins, int level, int xp) {
        db.updateGame(coins, level, xp);
    }
    @Override
    public GameByID getGame(int ID) {
        Cursor cursor = db.getGameByID(ID);
        GameByID result = null;

        if (cursor != null && cursor.moveToFirst()) {
            int Coins = cursor.getInt(cursor.getColumnIndexOrThrow("Coins"));
            int Level = cursor.getInt(cursor.getColumnIndexOrThrow("Level"));
            int XP = cursor.getInt(cursor.getColumnIndexOrThrow("XP"));
            result = new GameByID(ID, Coins, Level, XP);
            cursor.close();
        }

        return result;
    }

    @Override
    public GameByID getGameByID(int ID) {
        return null;
    }

    // Shop
    @Override
    public void insertShop(String itemID, int coins) {
        db.insertShop(itemID, coins);
    }
    @Override
    public ShopByItem getShopByItem (String item) {
        Cursor cursor = db.getShopByItem(item);
        ShopByItem result = null;

        if (cursor != null && cursor.moveToFirst()) {
            int Coins = cursor.getInt(cursor.getColumnIndexOrThrow("Coins"));
            result = new ShopByItem(item, Coins);
            cursor.close();
        }

        return result;
    }

    // Seller
    @Override
    public void insertSeller(int itemID, int coins) {
        db.insertSeller(itemID, coins);
    }
    @Override
    public SellerByItem getSellerByItem(String item) {
        Cursor cursor = db.getSellerByItem(item);
        SellerByItem result = null;

        if (cursor != null && cursor.moveToFirst()) {
            int Coins = cursor.getInt(cursor.getColumnIndexOrThrow("Coins"));
            result = new SellerByItem(item, Coins);
            cursor.close();
        }

        return result;
    }

    //Feld
    @Override
    public void insertFeld(int feldID, String item, int wachsstufe, float feld_x, float feld_y) {
        db.insertFeld(feldID, item, wachsstufe, feld_x, feld_y);
    }
    @Override
    public void updateFeld(int feldID, String item, int wachsstufe, float feld_x, float feld_y) {
        db.updateFeld(item, wachsstufe, feld_x, feld_y, feldID);
    }
    @Override
    public FeldByCord getFeldByCord(int feldID) {
        Cursor cursor = db.getFeldByCord(feldID);
        FeldByCord result = null;

        if (cursor != null && cursor.moveToFirst()) {
            String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
            int Wachsstufe = cursor.getInt(cursor.getColumnIndexOrThrow("Wachsstufe"));
            float feld_x = cursor.getInt(cursor.getColumnIndexOrThrow("feld_x"));
            float feld_y = cursor.getInt(cursor.getColumnIndexOrThrow("feld_y"));
            result = new FeldByCord(feldID, item, Wachsstufe, feld_x, feld_y);
            cursor.close();
        }

        return result;
    }
}
