package Database;


public interface inventarLogik {



    // Inventory
    void updateInventoryItem(int slot, String itemID, int anzahl);
    InventorySlot getInventorySlot(int slot);



    // Game
    void updateGame(int coins, int level, int xp);
    GameByID getGame(int ID);


    GameByID getGameByID(int ID);

    // Shop
    void insertShop(String itemID, int coins);
    ShopByItem getShopByItem (String Item);


    // Seller
    void insertSeller(int itemID, int coins);
    SellerByItem getSellerByItem(String item);

    //Feld
    FeldByCord getFeldByCord(float feld_x, float feld_y);
    void insertFeld(String item, int wachsstufe, float feld_x, float feld_y);

    void updateFeld(String item, int wachsstufe, float feld_x, float feld_y);
}
