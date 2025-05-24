package Pflanzen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import Items.Item;

public abstract class Pflanze {

    protected Animation<TextureRegion>[] pflanzenAnimation;
    private Vector2 cords;
    private int plantHour;
    private int plantMinute;
    private int currentHour;
    private int stage = 0;
    private Item finalProduct;
    private int growthTime;
    public Pflanze(Item finalProduct, int plantHour, int plantMinute,Vector2 cords,int growthTime){
        this.finalProduct = finalProduct;
        this.plantHour = plantHour;
        this.plantMinute = plantMinute;
        this.cords = cords;
        this.growthTime = growthTime;
        pflanzenAnimation = new Animation[4];
        loadAnimation();
    }
    protected abstract void loadAnimation();
    public void draw(int hour, int minute, SpriteBatch batch){
        currentHour = hour;
        if(hour < plantHour){
            currentHour += 24;
        }
        if((currentHour > plantHour && minute >= plantMinute && stage < 3) || (currentHour > plantHour + growthTime && stage < 3)){
            stage++;
            plantHour++;
            System.out.println(stage);
        }
        batch.draw(pflanzenAnimation[stage].getKeyFrame(0,true),cords.x,cords.y);
    }
    public int getStage(){
        return stage;
    }
    public Item getFinalProduct(){return finalProduct;}
}
