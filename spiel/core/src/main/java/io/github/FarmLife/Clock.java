package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Clock {
    private float stateTime;
    private int hour;
    private int minute;
    private boolean isDay;
    private boolean isNight;
    private int dayCounter;
    private String ausgabeClock;
    private String ausgabeDay;
    private String daytimeEmoji;
    BitmapFont  Font;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("clockFont.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public Clock(){
        stateTime = 0;
        hour = 0;
        minute = 0;
        dayCounter = 1;
        ausgabeClock = "";
        ausgabeDay = "";
        daytimeEmoji = "☀️";
        parameter.size = 50;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "☀️🌙";
        Font = generator.generateFont(parameter);
    }

    public void tick(){
        stateTime += Gdx.graphics.getDeltaTime();
        if(stateTime >= 1){
            minute++;
            stateTime = 0;
        }
        if(minute >= 60){
            hour++;
            minute = 0;
        }
        if(hour >= 24){
            hour = 0;
            dayCounter++;
        }
    }

    public void setDaytimeEmoji(){
        if(hour >= 6 && hour <= 18){
            daytimeEmoji = "☀️";
            isDay = true;
            isNight = false;
        }else if(hour >= 19 && hour <= 5) {
            daytimeEmoji = "🌙";
            isDay = false;
            isNight = true;
        }
    }

    public String setAusgabeClock(){
        setDaytimeEmoji();
        ausgabeClock = "";
        if(hour < 10){
            ausgabeClock += "0" + hour;
        }else{
            ausgabeClock += hour;
        }
        ausgabeClock += " : ";
        if(minute < 10){
            ausgabeClock += "0" + minute;
        }else{
            ausgabeClock += minute;
        }
        ausgabeClock += " Uhr " + daytimeEmoji;
        return ausgabeClock;
    }



    public void draw(SpriteBatch batch, BitmapFont font){
        tick();
        Font.draw(batch, dayCounter + ". TAG", Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 50);
        Font.draw(batch, setAusgabeClock() , Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 100);
    }
}
