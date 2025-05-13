package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.FarmLife.InventorySlot;

public class KarottenSeed extends Item{
    private TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Outdoor_Decor_Free.png")), 105, 105);
    private int itemCounter = 0;
    private Animation<TextureRegion> itemAnimation;
    private float stateTime = 0;
    private Vector2 cords = new Vector2();
    public KarottenSeed() {
        itemAnimation = new Animation<>(0.1f, itemSpriteSheet[0][4]);
    }
    @Override
    public void addItemCounter(int anzahl){
        itemCounter += anzahl;
    }
    @Override
    public int getItemCounter(){
        return itemCounter;
    }


    @Override
    public void draw(SpriteBatch batch, InventorySlot[] inventory, Vector2 touchPosition){
        stateTime += Gdx.graphics.getDeltaTime();
        for (InventorySlot invSlot : inventory) {
            if(!invSlot.getIsUsed() || invSlot.getItem() instanceof KarottenSeed){
                if(touchPosition.x == 0 && touchPosition.y == 0){cords.set(invSlot.getCords().x+25,invSlot.getCords().y+30);}else{cords = touchPosition;}
                invSlot.addItem(this);
                batch.draw(itemAnimation.getKeyFrame(stateTime,true),cords.x,cords.y);
                break;
            }
        }
    }
}
