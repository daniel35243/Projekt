package Pflanzen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import Items.Karotte;
import Items.Weizen;

public class Weizenpflanze extends Pflanze{

    public Weizenpflanze(float x, float y, int plantHour, int plantMinute){
        super(new Weizen(),plantHour,plantMinute, new Vector2(x,y),4);
    }

    @Override
    protected void loadAnimation() {
        TextureRegion[][] pflanzenSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Items.png")), 16, 16);
        pflanzenAnimation[0] = new Animation<>(0f, pflanzenSpriteSheet[1][5]);
        pflanzenAnimation[1] = new Animation<>(0f, pflanzenSpriteSheet[1][6]);
        pflanzenAnimation[2] = new Animation<>(0f, pflanzenSpriteSheet[2][5]);
        pflanzenAnimation[3] = new Animation<>(0f, pflanzenSpriteSheet[2][6]);
    }


}
