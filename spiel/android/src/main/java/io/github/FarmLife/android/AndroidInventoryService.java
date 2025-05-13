package io.github.FarmLife.android;

import android.content.Context;
import android.database.Cursor;

import com.mygdx.game.InventoryDbHelper;

import Database.FeldByCord;
import Database.GameByID;
import Database.InventorySlotDB;
import Database.SellerByItem;
import Database.ShopByItem;
import Database.inventarLogik;

public class AndroidInventoryService implements inventarLogik {

    private final InventoryDbHelper db;

    public AndroidInventoryService(Context context) {
        db = new InventoryDbHelper(context);
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
    public void insertFeld(String item, int wachsstufe, float feld_x, float feld_y) {
        db.insertFeld(item, wachsstufe, feld_x, feld_y);
    }
    @Override
    public void updateFeld(String item, int wachsstufe, float feld_x, float feld_y) {
        db.updateFeld(item, wachsstufe, feld_x, feld_y);
    }
    @Override
    public FeldByCord getFeldByCord(float feld_x, float feld_y) {
        Cursor cursor = db.getFeldByCord(feld_x, feld_y);
        FeldByCord result = null;

        if (cursor != null && cursor.moveToFirst()) {
            String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
            int Wachsstufe = cursor.getInt(cursor.getColumnIndexOrThrow("Wachsstufe"));
            result = new FeldByCord(item, Wachsstufe, feld_x, feld_y);
            cursor.close();
        }

        return result;
    }
}
