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
        // TABLE
        db.execSQL("CREATE TABLE Inventory (Slot INTEGER PRIMARY KEY, item TEXT, Anzahl INTEGER)");
        db.execSQL("CREATE TABLE Game (ID INTEGER PRIMARY KEY, Coins INTEGER, Level INTEGER, XP INTEGER)");
        db.execSQL("CREATE TABLE Shop (item TEXT PRIMARY KEY, Coins INTEGER)");
        db.execSQL("CREATE TABLE Seller (item TEXT PRIMARY KEY, Coins INTEGER)");
        db.execSQL("CREATE TABLE Felder (feldID INTEGER PRIMARY KEY, item TEXT, Wachsstufe INTEGER,feld_x INTEGER, feld_y INTEGER, feld_groesse INTEGER)");
        // Inventory
        db.execSQL("INSERT INTO Inventory (Slot, item, Anzahl) VALUES (1, 'Leer', 0)");
        db.execSQL("INSERT INTO Inventory (Slot, item, Anzahl) VALUES (2, 'Leer', 0)");
        db.execSQL("INSERT INTO Inventory (Slot, item, Anzahl) VALUES (3, 'Karotte', 2)");
        db.execSQL("INSERT INTO Inventory (Slot, item, Anzahl) VALUES (4, 'WeizenSeed', 12)");
        db.execSQL("INSERT INTO Inventory (Slot, item, Anzahl) VALUES (5, 'KarottenSeed', 12)");


        //Game
        db.execSQL("INSERT INTO Game (ID, Coins, Level, XP) VALUES (1, 5, 1, 0)");
        // Shop
        db.execSQL("INSERT INTO Shop (item, Coins) VALUES ('Karotten Samen', 1)");
        db.execSQL("INSERT INTO Shop (item, Coins) VALUES ('Weizen Samen', 2)");
        //Seller
        db.execSQL("INSERT INTO Seller (item, Coins) VALUES ('Karotten', 2)");
        db.execSQL("INSERT INTO Seller (item, Coins) VALUES ('Weizen', 5)");
        //Felder
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (1, NULL, NULL, 880, 688)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (2, NULL, NULL, 944, 688)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (3, NULL, NULL, 880, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (4, NULL, NULL, 944, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (11, NULL, NULL, 944, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (12, NULL, NULL, 944, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (13, NULL, NULL, 944, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (14, NULL, NULL, 944, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (15, NULL, NULL, 944, 624)");
        db.execSQL("INSERT INTO Felder (feldID, item, Wachsstufe, feld_x, feld_y) VALUES (16, NULL, NULL, 944, 624)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Inventory");
        db.execSQL("DROP TABLE IF EXISTS Game");
        db.execSQL("DROP TABLE IF EXISTS Shop");
        db.execSQL("DROP TABLE IF EXISTS Seller");
        db.execSQL("DROP TABLE IF EXISTS Felder");
        onCreate(db);
    }



    // Inventory
    public Cursor getInventorySlot(int slot) {
        return db.rawQuery("SELECT * FROM Inventory WHERE Slot = ?", new String[]{String.valueOf(slot)});
    }
    public void updateInventoryItem(int slot, String item, int anzahl) {
        db.execSQL("UPDATE Inventory SET item = ?, Anzahl = ? WHERE Slot = ?", new Object[]{item, anzahl, slot});
    }



    // Game
    public Cursor getGameByID(int ID) {
        return db.rawQuery("SELECT * FROM Game WHERE ID = ?", new String[]{String.valueOf(ID)});
    }
    public void updateGame(int coins, int level, int xp) {
        db.execSQL("UPDATE Game SET Coins = ?, Level = ?, XP = ?", new Object[]{coins, level, xp});
    }




    // Shop
    public Cursor getShopByItem(String item) {
        return db.rawQuery("SELECT * FROM Shop WHERE item = ?", new String[]{String.valueOf(item)});
    }
    public void insertShop(String item, int coins) {
        db.execSQL("INSERT INTO Shop (item, Coins) VALUES (?, ?)", new Object[]{item, coins});
    }




    // Seller
    public Cursor getSellerByItem(String item) {
        return db.rawQuery("SELECT * FROM Seller WHERE item = ?", new String[]{String.valueOf(item)});
    }
    public void insertSeller(int item, int coins) {
        db.execSQL("INSERT INTO Seller (item, Coins) VALUES (?, ?)", new Object[]{item, coins});
    }




    // Field
    public Cursor getFeldByCord(int feldID) {
        return db.rawQuery("SELECT * FROM Felder WHERE feldID = ?", new String[]{String.valueOf(feldID)});
    }
    public void insertFeld(int feldID, String item, int wachsstufe, float feld_x, float feld_y) {
        db.execSQL("INSERT INTO Felder (feldID, item, wachsstufe, feld_x, feld_y) VALUES (?, ?, ?, ?, ?)", new Object[]{item, wachsstufe, feld_x, feld_y});
    }

    public void updateFeld(String item, int wachsstufe, float feld_x, float feld_y, int feldID) {
        db.execSQL("UPDATE Felder SET item = ?, wachsstufe = ?, feld_x = ?, feld_y = ? WHERE FeldID = ?", new Object[]{feldID, item, wachsstufe, feld_x, feld_y});
    }




    @Override
    public synchronized void close() {
        if (db != null) db.close();
        super.close();
    }
}
