package Database;


public interface DatabaseLogik {

    //Feld
    FeldByCord getFeldByCord(int feldID);
    void insertFeld(int feldID, String item, int wachsstufe, float feld_x, float feld_y);

    void updateFeld(int feldID, String item, int wachsstufe, float feld_x, float feld_y);

    // Inventory
    void updateInventoryItem(int slot, String itemID, int anzahl);
    InventorySlotDB getInventorySlot(int slot);

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

}
