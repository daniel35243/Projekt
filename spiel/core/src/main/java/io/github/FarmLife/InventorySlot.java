package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class InventorySlot {
    private Vector2 cords;
    private boolean isUsed;
    //private Item item;
    private Texture[] slotTexture = new Texture[2];
    private boolean inventorySlotClicked;

    public InventorySlot(float x){
        cords = new Vector2(x, Gdx.graphics.getHeight()-180);
        isUsed = false;
        slotTexture[0] = new Texture("InventorySlot.png");
        slotTexture[1] = new Texture("UsedInventorySlot.png");
        inventorySlotClicked = false;
    }
    public void drawSlot(SpriteBatch batch, int numb){
        batch.begin();
        batch.draw(slotTexture[numb],cords.x,cords.y);
        batch.end();
    }
    public Vector2 getCords(){
        return cords;
    }
    public void setInventorySlotClicked(boolean inventorySlotClicked) {
        this.inventorySlotClicked = inventorySlotClicked;
    }
    public boolean getInventorySlotClicked() {
        return inventorySlotClicked;
    }
}
