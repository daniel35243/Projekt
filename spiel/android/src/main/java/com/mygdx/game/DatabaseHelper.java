package com.mygdx.game;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "Datenbank.db";
    private static String DB_PATH = "";
    private final Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        createDatabase();
    }

    private void createDatabase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private void copyDatabase() {
        try {
            InputStream input = context.getAssets().open(DB_NAME);
            File dir = new File(DB_PATH);
            if (!dir.exists()) dir.mkdirs();
            OutputStream output = new FileOutputStream(DB_PATH + DB_NAME);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() throws SQLiteException {
        String path = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    // crud für Inventory
    public Cursor getAllInventory() {
        return db.rawQuery("SELECT * FROM Inventory", null);
    }

    public void insertInventoryItem(int slot, String itemID, int anzahl) {
        db.execSQL("INSERT INTO Inventory (Slot, ItemID, Anzahl) VALUES (?, ?, ?)", new Object[]{slot, itemID, anzahl});
    }

    public void updateInventoryItem(int slot, String itemID, int anzahl) {
        db.execSQL("UPDATE Inventory SET ItemID = ?, Anzahl = ? WHERE Slot = ?", new Object[]{itemID, anzahl, slot});
    }

    public void deleteInventorySlot(int slot) {
        db.execSQL("DELETE FROM Inventory WHERE Slot = ?", new Object[]{slot});
    }

    //crud für Items
    public Cursor getAllItems() {
        return db.rawQuery("SELECT * FROM Items", null);
    }

    public void insertItem(int itemID, String itemName) {
        db.execSQL("INSERT INTO Items (ItemID, ItemName) VALUES (?, ?)", new Object[]{itemID, itemName});
    }

    public void updateItem(int itemID, String itemName) {
        db.execSQL("UPDATE Items SET ItemName = ? WHERE ItemID = ?", new Object[]{itemName, itemID});
    }

    public void deleteItem(int itemID) {
        db.execSQL("DELETE FROM Items WHERE ItemID = ?", new Object[]{itemID});
    }

    //crud für game
    public Cursor getGame() {
        return db.rawQuery("SELECT * FROM Game", null);
    }

    public void insertGame(int coins, int level, int xp) {
        db.execSQL("INSERT INTO Game (Coins, Level, XP) VALUES (?, ?, ?)", new Object[]{coins, level, xp});
    }

    public void updateGame(int coins, int level, int xp) {
        db.execSQL("UPDATE Game SET Coins = ?, Level = ?, XP = ?", new Object[]{coins, level, xp});
    }

    public void deleteGame() {
        db.execSQL("DELETE FROM Game");
    }

    //crud für shop
    public Cursor getAllShop() {
        return db.rawQuery("SELECT * FROM Shop", null);
    }

    public void insertShop(String itemID, int coins) {
        db.execSQL("INSERT INTO Shop (ItemID, Coins) VALUES (?, ?)", new Object[]{itemID, coins});
    }

    public void updateShop(String itemID, int coins) {
        db.execSQL("UPDATE Shop SET Coins = ? WHERE ItemID = ?", new Object[]{coins, itemID});
    }

    public void deleteShop(String itemID) {
        db.execSQL("DELETE FROM Shop WHERE ItemID = ?", new Object[]{itemID});
    }

    //crud für seller
    public Cursor getAllSeller() {
        return db.rawQuery("SELECT * FROM Seller", null);
    }

    public void insertSeller(int itemID, int coins) {
        db.execSQL("INSERT INTO Seller (ItemID, Coins) VALUES (?, ?)", new Object[]{itemID, coins});
    }

    public void updateSeller(int itemID, int coins) {
        db.execSQL("UPDATE Seller SET Coins = ? WHERE ItemID = ?", new Object[]{coins, itemID});
    }

    public void deleteSeller(int itemID) {
        db.execSQL("DELETE FROM Seller WHERE ItemID = ?", new Object[]{itemID});
    }

    //crud für chest
    public Cursor getAllChest() {
        return db.rawQuery("SELECT * FROM Chest", null);
    }

    public void insertChest(int slot, String item, int anzahl) {
        db.execSQL("INSERT INTO Chest (Slot, Item, Anzahl) VALUES (?, ?, ?)", new Object[]{slot, item, anzahl});
    }

    public void updateChest(int slot, String item, int anzahl) {
        db.execSQL("UPDATE Chest SET Item = ?, Anzahl = ? WHERE Slot = ?", new Object[]{item, anzahl, slot});
    }

    public void deleteChest(int slot) {
        db.execSQL("DELETE FROM Chest WHERE Slot = ?", new Object[]{slot});
    }

    // crud für felder
    public Cursor getAllFelder() {
        return db.rawQuery("SELECT * FROM Felder", null);
    }

    public void insertFeld(int feldID, String itemID, int wachsstufe) {
        db.execSQL("INSERT INTO Felder (FeldID, ItemID, wachsstufe) VALUES (?, ?, ?)", new Object[]{feldID, itemID, wachsstufe});
    }

    public void updateFeld(int feldID, String itemID, int wachsstufe) {
        db.execSQL("UPDATE Felder SET ItemID = ?, wachsstufe = ? WHERE FeldID = ?", new Object[]{itemID, wachsstufe, feldID});
    }

    public void deleteFeld(int feldID) {
        db.execSQL("DELETE FROM Felder WHERE FeldID = ?", new Object[]{feldID});
    }

    //crud für Feld_Location
    public Cursor getAllFeldLocation() {
        return db.rawQueryu("SELECT * FROM Feld_Location", null);
    }

    public void insertFeldLocation(int feldID, int feldX, int feldY) {
        db.execSQL("INSERT INTO Feld_Location (FeldID, feld_x, feld_y) VALUES (?, ?, ?)", new Object[]{feldID, feldX, feldY});
    }

    public void updateFeldLocation(int feldID, int feldX, int feldY) {
        db.execSQL("UPDATE Feld_Location SET feld_x = ?, feld_y = ? WHERE FeldID = ?", new Object[]{feldX, feldY, feldID});
    }

    public void deleteFeldLocation(int feldID) {
        db.execSQL("DELETE FROM Feld_Location WHERE FeldID = ?", new Object[]{feldID});
    }

    @Override
    public synchronized void close() {
        if (db != null) db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
