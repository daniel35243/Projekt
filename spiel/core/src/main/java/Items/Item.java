package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.FarmLife.InventorySlot;

public class Item {
    private TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/ItemsZoomed.png")), 16, 16);
    private int itemCounter = 0;
    private Animation<TextureRegion> itemAnimation;
    private float stateTime = 0;
    private Vector2 cords = new Vector2();
    public Item() {

    }

    public void addItemCounter(int anzahl){
        itemCounter += anzahl;
    }

    public int getItemCounter(){
        return itemCounter;
    }
    public void setItemCounter(int anzahl){ itemCounter = anzahl;}

    public void drawInSlot(SpriteBatch batch,  Vector2 cords, BitmapFont font){}
    public void drawClicked(SpriteBatch batch, Vector2 touchPosition, BitmapFont font, InventorySlot invSlot){
    }

}
