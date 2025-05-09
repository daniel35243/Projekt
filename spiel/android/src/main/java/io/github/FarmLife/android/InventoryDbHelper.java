package com.mygdx.game;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Datenbank.db";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    public InventoryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Inventory (Slot INTEGER PRIMARY KEY, ItemID TEXT, Anzahl INTEGER)");
        db.execSQL("CREATE TABLE Items (ItemID INTEGER PRIMARY KEY, ItemName TEXT)");
        db.execSQL("CREATE TABLE Game (Coins INTEGER, Level INTEGER, XP INTEGER)");
        db.execSQL("CREATE TABLE Shop (ItemID TEXT PRIMARY KEY, Coins INTEGER)");
        db.execSQL("CREATE TABLE Seller (ItemID INTEGER PRIMARY KEY, Coins INTEGER)");
        db.execSQL("CREATE TABLE Chest (Slot INTEGER PRIMARY KEY, Item TEXT, Anzahl INTEGER)");
        db.execSQL("CREATE TABLE Felder (FeldID INTEGER PRIMARY KEY, ItemID TEXT, wachsstufe INTEGER)");
        db.execSQL("CREATE TABLE Feld_Location (FeldID INTEGER PRIMARY KEY, feld_x INTEGER, feld_y INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Inventory");
        db.execSQL("DROP TABLE IF EXISTS Items");
        db.execSQL("DROP TABLE IF EXISTS Game");
        db.execSQL("DROP TABLE IF EXISTS Shop");
        db.execSQL("DROP TABLE IF EXISTS Seller");
        db.execSQL("DROP TABLE IF EXISTS Chest");
        db.execSQL("DROP TABLE IF EXISTS Felder");
        db.execSQL("DROP TABLE IF EXISTS Feld_Location");
        onCreate(db);
    }

    // Inventory CRUD
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
    public Cursor getInventoryBySlot(int slot) {
        return db.rawQuery("SELECT * FROM Inventory WHERE Slot = ?", new String[]{String.valueOf(slot)});
    }

    // Items CRUD
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
    public Cursor getItemById(int itemID) {
        return db.rawQuery("SELECT * FROM Items WHERE ItemID = ?", new String[]{String.valueOf(itemID)});
    }

    // Game CRUD
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

    public Cursor getGameSingle() {
        return db.rawQuery("SELECT * FROM Game LIMIT 1", null);
    }

    // Shop CRUD
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

    public Cursor getShopItemById(String itemID) {
        return db.rawQuery("SELECT * FROM Shop WHERE ItemID = ?", new String[]{itemID});
    }

    // Seller CRUD
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

    public Cursor getSellerItemById(int itemID) {
        return db.rawQuery("SELECT * FROM Seller WHERE ItemID = ?", new String[]{String.valueOf(itemID)});
    }

    // Chest CRUD
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

    public Cursor getChestBySlot(int slot) {
        return db.rawQuery("SELECT * FROM Chest WHERE Slot = ?", new String[]{String.valueOf(slot)});
    }

    // Felder CRUD
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

    public Cursor getFeldById(int feldID) {
        return db.rawQuery("SELECT * FROM Felder WHERE FeldID = ?", new String[]{String.valueOf(feldID)});
    }

    // Feld_Location CRUD
    public Cursor getAllFeldLocation() {
        return db.rawQuery("SELECT * FROM Feld_Location", null);
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

    public Cursor getFeldLocationById(int feldID) {
        return db.rawQuery("SELECT * FROM Feld_Location WHERE FeldID = ?", new String[]{String.valueOf(feldID)});
    }

    @Override
    public synchronized void close() {
        if (db != null) db.close();
        super.close();
    }
}
