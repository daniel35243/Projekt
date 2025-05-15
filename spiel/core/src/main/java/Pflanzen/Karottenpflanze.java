package Pflanzen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Karottenpflanze extends Pflanze{
    private TextureRegion[][] pflanzenSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Items.png")), 16, 16);
    private Animation<TextureRegion>[] pflanzenAnimation = new Animation[4];
    private Vector2 cords = new Vector2();
    private int plantHour;
    private int plantMinute;
    private int currentHour;

    public Karottenpflanze(float x, float y, int plantHour, int plantMinute){
        this.plantHour = plantHour;
        this.plantMinute = plantMinute;
        cords.set(x,y);
        pflanzenAnimation[0] = new Animation<>(0f, pflanzenSpriteSheet[1][3]);
        pflanzenAnimation[1] = new Animation<>(0f, pflanzenSpriteSheet[1][4]);
        pflanzenAnimation[2] = new Animation<>(0f, pflanzenSpriteSheet[2][3]);
        pflanzenAnimation[3] = new Animation<>(0f, pflanzenSpriteSheet[2][4]);
    }

    @Override
    public void draw(int hour, int minute, SpriteBatch batch){
        currentHour = hour;
        if(hour < plantHour){
            currentHour += 24;
        }
        if(currentHour >= plantHour + 3 && minute >= plantMinute){
            batch.draw(pflanzenAnimation[3].getKeyFrame(0,true),cords.x,cords.y);
        }else if(currentHour >= plantHour + 2 && minute >= plantMinute){
            batch.draw(pflanzenAnimation[2].getKeyFrame(0,true),cords.x,cords.y);
        }else if(currentHour >= plantHour + 1 && minute >= plantMinute){
            batch.draw(pflanzenAnimation[1].getKeyFrame(0,true),cords.x,cords.y);
        }else if(currentHour >= plantHour  && minute >= plantMinute){
            batch.draw(pflanzenAnimation[0].getKeyFrame(0,true),cords.x,cords.y);
        }
    }
}
