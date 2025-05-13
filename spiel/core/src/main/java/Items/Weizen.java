package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.FarmLife.InventorySlot;

public class Weizen extends Item{
    private TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Outdoor_Decor_Free.png")), 105, 105);
    private int itemCounter = 0;
    private TextureRegion[] itemFrames = new TextureRegion[4];
    private Animation<TextureRegion> itemAnimation;
    private float stateTime = 0;

    public Weizen() {
        itemAnimation = new Animation<>(0.1f, itemSpriteSheet[3][6]);
    }
    @Override
    public void addItemCounter(int anzahl){
        itemCounter += anzahl;
    }
    @Override
    public int getItemCounter() {
        return itemCounter;
    }


    @Override
    public void draw(SpriteBatch batch, InventorySlot[] inventory){
        stateTime += Gdx.graphics.getDeltaTime();
        for (InventorySlot invSlot : inventory) {
            if(!invSlot.getIsUsed() || invSlot.getItem() instanceof Weizen){
                invSlot.addItem(this);
                batch.draw(itemAnimation.getKeyFrame(stateTime,true),invSlot.getCords().x+25,invSlot.getCords().y+30);
                break;
            }
        }
    }
}
