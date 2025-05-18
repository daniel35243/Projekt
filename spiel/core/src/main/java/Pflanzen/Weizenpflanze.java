package Pflanzen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Weizenpflanze extends Pflanze{
    private TextureRegion[][] pflanzenSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal("PixelMapPNGs/Outdoor decoration/Items.png")), 16, 16);
    private Animation<TextureRegion>[] pflanzenAnimation = new Animation[4];
    private Vector2 cords = new Vector2();
    private int plantHour;
    private int plantMinute;
    private int currentHour;
    private int stage = 0;

    public Weizenpflanze(float x, float y, int plantHour, int plantMinute){
        this.plantHour = plantHour;
        this.plantMinute = plantMinute;
        cords.set(x,y);
        pflanzenAnimation[0] = new Animation<>(0f, pflanzenSpriteSheet[1][5]);
        pflanzenAnimation[1] = new Animation<>(0f, pflanzenSpriteSheet[1][6]);
        pflanzenAnimation[2] = new Animation<>(0f, pflanzenSpriteSheet[2][5]);
        pflanzenAnimation[3] = new Animation<>(0f, pflanzenSpriteSheet[2][6]);
    }

    @Override
    public void draw(int hour, int minute, SpriteBatch batch){
        currentHour = hour;
        if(hour < plantHour){
            currentHour += 24;
        }
        if(currentHour > plantHour && minute >= plantMinute && stage < 3){
            stage++;
            plantHour++;
            System.out.println(stage);
        }
        batch.draw(pflanzenAnimation[stage].getKeyFrame(0,true),cords.x,cords.y);
    }
    @Override
    public int getStage(){
        return stage;
    }

}
