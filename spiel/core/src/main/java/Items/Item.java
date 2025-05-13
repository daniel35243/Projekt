package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.FarmLife.InventorySlot;

public class Item {
    private TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Outdoor_Decor_Free.png")), 16, 16);
    private int itemCounter = 0;
    private Animation<TextureRegion> itemAnimation;
    private float stateTime = 0;

    public Item() {

    }

    public void addItemCounter(int anzahl){
        itemCounter += anzahl;
    }

    public int getItemCounter(){
        return itemCounter;
    }


    public void draw(SpriteBatch batch, InventorySlot[] inventory){
        stateTime += Gdx.graphics.getDeltaTime();
        for (InventorySlot invSlot : inventory) {
            if(!invSlot.getIsUsed()){
                invSlot.addItem(this);
                batch.draw(itemAnimation.getKeyFrame(stateTime,false),invSlot.getCords().x+20,invSlot.getCords().y+20);
                break;
            }
        }
    }


}
