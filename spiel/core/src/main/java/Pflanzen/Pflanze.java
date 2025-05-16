package Pflanzen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Pflanze {
    private TextureRegion[][] pflanzenSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Items.png")), 16, 16);
    private Animation<TextureRegion> pflanzenAnimation;
    private Vector2 cords = new Vector2();
    private int plantHour;
    private int plantMinute;
    private int currentHour;
    private int stage = 0;
    public Pflanze(){

    }
    public void draw(int hour, int minute, SpriteBatch batch){}
    public int getStage(){
        return stage;
    }
}
