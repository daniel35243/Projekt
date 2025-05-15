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
    private String daytimeEmoji;
    BitmapFont  font;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("clockFont.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public Clock(){
        stateTime = 0;
        hour = 6;
        minute = 0;
        dayCounter = 1;
        ausgabeClock = "";
        daytimeEmoji = "☀️";
        parameter.size = 50;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "☀️🌙";
        font = generator.generateFont(parameter);
        isDay = true;
        isNight = false;
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

    public String setDaytimeEmoji(){
        if(hour >= 6 && hour <= 18){
            daytimeEmoji = "☀️";
            isDay = true;
            isNight = false;
        }else if(hour >= 19 && hour <= 5) {
            daytimeEmoji = "🌙";
            isDay = false;
            isNight = true;
        }
        return daytimeEmoji;
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
        ausgabeClock += " Uhr " + setDaytimeEmoji();
        return ausgabeClock;
    }



    public void draw(SpriteBatch batch){
        tick();
        font.draw(batch, dayCounter + ". TAG", Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 50);
        font.draw(batch, setAusgabeClock() , Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 100);
    }
    public int getHour(){
        return hour;
    }
    public int getMinute() {
        return minute;
    }
}
