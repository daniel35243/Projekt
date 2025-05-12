package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class InventorySlot {
    private Vector2 cords;
    private boolean isUsed;
    //private Item item;
    private Texture slotTexture;


    public InventorySlot(float x){
        cords = new Vector2(x, Gdx.graphics.getHeight()-50);
        isUsed = false;
        slotTexture = new Texture("InventorySlot.png");
    }
    public void drawSlot(SpriteBatch batch){
        batch.begin();
        batch.draw(slotTexture,cords.x,cords.y);
        batch.end();
    }
    public Vector2 getCords(){
        return cords;
    }
}
