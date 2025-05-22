package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Clock {
    private float stateTime;
    private int hour;
    private int minute;
    private boolean isDay;
    private boolean isNight;
    private int dayCounter;
    private String ausgabeClock;
    private String daytimeEmoji;
    private BitmapFont  font;
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("clockFont.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    float alpha = 0f;

    public Clock(){
        stateTime = 0;
        hour = 12;
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
        if(hour >= 4 && hour < 20){
            daytimeEmoji = "☀️";
            isDay = true;
            isNight = false;
        }else {
            daytimeEmoji = "🌙";
            isDay = false;
            isNight = true;
        }
        return daytimeEmoji;
    }

    public String setAusgabeClock(){
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

    public void TagNacht(ShapeRenderer shapeRendererHUD){
        if(isDay && alpha > 0){
            alpha -= 0.05f * Gdx.graphics.getDeltaTime();
        } else if (isNight && alpha < 0.65) {
            alpha += 0.05f * Gdx.graphics.getDeltaTime();
        }
        if(alpha > 0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRendererHUD.begin(ShapeRenderer.ShapeType.Filled);
            shapeRendererHUD.setColor(new Color(0, 0, 33 / 255f, alpha));
            shapeRendererHUD.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRendererHUD.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    public int getHour(){
        return hour;
    }
    public int getMinute() {
        return minute;
    }

    public void setDay(){
        if(hour < 24 && hour >= 20){
            dayCounter++;
        }
        isDay = true;
        isNight = false;
        hour = 4;
        minute = 0;
    }
}
