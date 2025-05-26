package Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import Pflanzen.Karottenpflanze;
import io.github.FarmLife.InventorySlot;

public class KarottenSeed extends Seed{


    public KarottenSeed() {
        super(new Karottenpflanze());
    }

    @Override
    public void loadAnimation() {
        TextureRegion[][] itemSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/ItemsZoomed.png")), 96, 96);
        itemAnimation = new Animation<>(0.1f, itemSpriteSheet[0][4]);
    }

}
