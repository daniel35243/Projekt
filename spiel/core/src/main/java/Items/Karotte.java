package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.FarmLife.InventorySlot;

public class Karotte extends Item{

    public Karotte() {}

    @Override
    public void loadAnimation() {
        TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/ItemsZoomed.png")), 96, 96);
        itemAnimation = new Animation<>(0f, itemSpriteSheet[3][4]);
    }


}
