package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.FarmLife.InventorySlot;

public class KarottenSeed extends Item{
    private TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/ItemsZoomed.png")), 105, 105);
    private int itemCounter = 0;
    private TextureRegion[] itemFrames = new TextureRegion[4];
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
    public int getItemCounter() {
        return itemCounter;
    }


    @Override
    public void drawInSlot(SpriteBatch batch, Vector2 cords, BitmapFont font){
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(itemAnimation.getKeyFrame(stateTime, true), cords.x, cords.y);
        font.draw(batch, Integer.toString(itemCounter), cords.x + 90, cords.y + 25);
    }
    @Override
    public void drawClicked(SpriteBatch batch,  Vector2 touchPosition, BitmapFont font, InventorySlot invSlot){
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(itemAnimation.getKeyFrame(stateTime, true), touchPosition.x, touchPosition.y);
        font.draw(batch, Integer.toString(itemCounter), invSlot.getCords().x + 100, invSlot.getCords().y);
    }
}
