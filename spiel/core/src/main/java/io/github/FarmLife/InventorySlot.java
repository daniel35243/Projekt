package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Items.Item;
import Items.Karotte;
import Items.KarottenSeed;
import Items.Weizen;
import Items.WeizenSeed;

public class InventorySlot {
    private Vector2 cords;
    private boolean isUsed;
    private Item item;
    private Texture[] slotTexture = new Texture[2];
    private boolean inventorySlotClicked;

    public InventorySlot(float x){
        cords = new Vector2(x, 100);
        isUsed = false;
        slotTexture[0] = new Texture("InventorySlot.png");
        slotTexture[1] = new Texture("UsedInventorySlot.png");
        inventorySlotClicked = false;
    }
    public void drawSlot(SpriteBatch batch, int numb){
        batch.draw(slotTexture[numb],cords.x,cords.y);
    }

    public Vector2 getCords(){
        return cords;
    }
    public void setInventorySlotClickedTrue(InventorySlot[] inventory) {
        int counterFalse = 0;
        for (InventorySlot invSlot : inventory) {
            if (!invSlot.getInventorySlotClicked()) {
                counterFalse++;
            }
        }
        if (counterFalse == 5) {
            this.inventorySlotClicked = true;
        }else {
            this.inventorySlotClicked = false;
        }
    }
    public void setInventorySlotClickedFalse() {
        this.inventorySlotClicked = false;
    }
    public boolean getInventorySlotClicked() {
        return inventorySlotClicked;
    }

    public void addItem(Item item,int anzahl){
        if(this.item == null){
            item.addItemCounter(anzahl);
            this.item = item;
            isUsed = true;
        } else if(item.getClass() == this.item.getClass()){
            this.item.addItemCounter(anzahl);
        }

    }



    public void addItem(String item, int anzahl){
        switch (item) {
            case "Karotte":
                this.item = new Karotte();
                break;
            case "Weizen":
                this.item = new Weizen();
                break;
            case "WeizenSeed":
                this.item = new WeizenSeed();
                break;
            case "KarottenSeed":
                this.item = new KarottenSeed();
                break;
            case "Leer":
                break;
        }
        this.item.addItemCounter(anzahl);
        isUsed = true;
    }
    public void setItem(Item item){
        this.item = item;
        isUsed = true;
    }

    public void removeItem(int anzahl){
        item.addItemCounter(-anzahl);
        if(item.getItemCounter() <= 0) {
            item = null;
            isUsed = false;
        }
    }

    public Item getItem(){
        return item;
    }
    public boolean getIsUsed(){
        return isUsed;
    }
}
