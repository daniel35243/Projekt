package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import Pflanzen.Pflanze;
import io.github.FarmLife.InventorySlot;

public abstract class Item {
    private int itemCounter = 0;
    protected Animation<TextureRegion> itemAnimation;
    private float stateTime = 0;
    private Vector2 cords = new Vector2();
    public Item() {
        loadAnimation();
    }
    public abstract void loadAnimation();
    public Pflanze getPflanze(){
        return null;
    }
    public void addItemCounter(int anzahl){
        itemCounter += anzahl;
    }

    public int getItemCounter(){
        return itemCounter;
    }
    public void setItemCounter(int anzahl){ itemCounter = anzahl;}

    public void drawInSlot(SpriteBatch batch,  Vector2 cords, BitmapFont font){
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(itemAnimation.getKeyFrame(stateTime, true), cords.x, cords.y);
        font.draw(batch, Integer.toString(itemCounter), cords.x + 90, cords.y + 25);
    }
    public void drawClicked(SpriteBatch batch, Vector2 touchPosition, BitmapFont font, InventorySlot invSlot){
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(itemAnimation.getKeyFrame(stateTime, true), touchPosition.x, touchPosition.y);
        font.draw(batch, Integer.toString(itemCounter), invSlot.getCords().x + 115, invSlot.getCords().y + 50);
    }

}
