package io.github.FarmLife;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private float xpMultiplier;
    private int level;
    private float xp;
    private int coins;
    private Texture levelBarStructure = new Texture(Gdx.files.internal("LevelBarStructure.png"));
    private Texture coinBarStructure = new Texture(Gdx.files.internal("CoinBarStructure.png"));
    private Texture coinTexture = new Texture(Gdx.files.internal("Coin.png"));
    private Sprite[] levelBarXP = new Sprite[3];
    private Texture playerSpriteSheet;
    private TextureRegion[][] playerFrames;
    private TextureRegion[] walkUp;
    private TextureRegion[] walkLeft;
    private TextureRegion[] walkDown;
    private TextureRegion[] walkRight;
    private TextureRegion[] stillRight;
    private TextureRegion[] stillLeft;
    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> stillUpAnimation;
    private Animation<TextureRegion> stillDownAnimation;
    private Animation<TextureRegion> stillRightAnimation;
    private Animation<TextureRegion> stillLeftAnimation;
    private float stateTime;
    private boolean flipped;
    private BitmapFont font;
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("clockFont.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private float stateTimeSleep = 0;
    private float alphaSleep = 0;
    private boolean fadeUp = true;
    private float sleepDelay = 0f;
    public Player() {
        xpMultiplier = 1;
        level = 4;
        xp = 350;
        coins = 50000;

        levelBarXP[0] = new Sprite(new Texture(Gdx.files.internal("LevelBarXPStart.png")));
        levelBarXP[1] = new Sprite(new Texture(Gdx.files.internal("LevelBarXPMitte.png")));
        levelBarXP[2] = new Sprite(new Texture(Gdx.files.internal("LevelBarXPEnde.png")));



        parameter.size = 40;
        font = generator.generateFont(parameter);

        levelBarXP[0].setPosition(190, Gdx.graphics.getHeight()-188);
        levelBarXP[1].setPosition(190 + levelBarXP[0].getWidth(), Gdx.graphics.getHeight()-188);


        playerSpriteSheet = new Texture(Gdx.files.internal("PixelMapPNGs/Player/Player.png"));
        playerFrames = TextureRegion.split(playerSpriteSheet, 32, 32);
        walkUp = new TextureRegion[6];
        walkDown = new TextureRegion[6];
        walkLeft = new TextureRegion[6];
        walkRight = new TextureRegion[6];
        stillRight = new TextureRegion[1];
        stillLeft = new TextureRegion[1];
        stillRight[0] = playerFrames[1][1];
        stillLeft[0] = playerFrames[1][1];
        stillUpAnimation = new Animation<>(0.1f, playerFrames[2][1]);
        stillDownAnimation = new Animation<>(0.1f, playerFrames[0][1]);
        stillLeftAnimation = new Animation<>(0.1f, stillLeft);
        stillRightAnimation = new Animation<>(0.1f, stillRight);

        stateTime = 0;
        Right();
        Up();
        Left();
        Down();
        flipped = false;

    }
    public void drawLevel(SpriteBatch spriteBatch){
        if(xp > 365){
            addLevel();
            xp = 0;
        }
        levelBarXP[1].setSize(xp, 42);
        levelBarXP[2].setPosition(190 + levelBarXP[0].getWidth() + levelBarXP[1].getWidth(), Gdx.graphics.getHeight() - 188);

        spriteBatch.begin();
        spriteBatch.draw(levelBarStructure, 5, Gdx.graphics.getHeight() - 220);
        levelBarXP[0].draw(spriteBatch);
        levelBarXP[1].draw(spriteBatch);
        levelBarXP[2].draw(spriteBatch);
        font.draw(spriteBatch, "LvL: " + level, 50, Gdx.graphics.getHeight() - 155);
        spriteBatch.end();
    }
    public void drawCoins(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(coinBarStructure, 5, Gdx.graphics.getHeight() - 320);
        spriteBatch.draw(coinTexture, 150, Gdx.graphics.getHeight() - 295);
        font.draw(spriteBatch, Integer.toString(coins),100 - Integer.toString(coins).length() * 12,Gdx.graphics.getHeight() - 255);
        spriteBatch.end();
    }

    public void addLevel(){
        level++;
        xpMultiplier *= 0.75f;
    }
    public int getLevel(){
        return level;
    }
    public void addXp(float xp){
        this.xp += xp * xpMultiplier;
    }
    public float getXp() {
        return xp;
    }
    public void setXp(int xp){
        this.xp = xp;
    }
    public void addCoins(int coins){
        if(coins <= 9999) {
            this.coins += coins;
        }
    }
    public int getCoins() {
        return coins;
    }
    public void removeCoins(int coins){
        if(coins <= this.coins)this.coins -= coins;
    }

    public void Up() {
        for (int i = 0; i < 6; i++) {
            walkUp[i] = playerFrames[5][i];
        }
        walkUpAnimation = new Animation<>(0.1f, walkUp);
        walkUpAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void Down() {
        for (int i = 0; i < 6; i++) {
            walkDown[i] = playerFrames[3][i];
        }
        walkDownAnimation = new Animation<>(0.1f, walkDown);
        walkDownAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void Left() {
        for (int i = 0; i < 6; i++) {
            walkLeft[i] = playerFrames[4][i];
        }
        walkLeftAnimation = new Animation<>(0.1f, walkLeft);
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void Right() {
        for (int i = 0; i < 6; i++) {
            walkRight[i] = playerFrames[4][i];
        }
        walkRightAnimation = new Animation<>(0.1f, walkRight);
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw(SpriteBatch batch, boolean still, char direction, Vector2 cords) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = new TextureRegion();
        switch (direction) {
            case 'r':
                if (!still) {
                    frame = walkRightAnimation.getKeyFrame(stateTime, true);
                } else {
                    frame = stillRightAnimation.getKeyFrame(stateTime, true);
                }
                break;
            case 'l':
                if (!still) {
                    frame = walkLeftAnimation.getKeyFrame(stateTime, true);
                } else {
                    frame = stillLeftAnimation.getKeyFrame(stateTime, true);
                }
                break;
            case 'u':
                if (!still) {
                    frame = walkUpAnimation.getKeyFrame(stateTime, true);
                } else {
                    frame = stillUpAnimation.getKeyFrame(stateTime, true);
                }
                break;
            case 'd':
                if (!still) {
                    frame = walkDownAnimation.getKeyFrame(stateTime, true);
                } else {
                    frame = stillDownAnimation.getKeyFrame(stateTime, true);
                }
                break;
        }
        if (direction == 'l') {
            for (int i = 0; i < 6; i++) {
                walkLeft[i].flip(true, false);
            }
            stillLeft[0].flip(true, false);
        }

        batch.draw(frame, cords.x - 16,cords.y - 16);

        if (direction == 'l') {
            for (int i = 0; i < 6; i++) {
                walkLeft[i].flip(true, false);
            }
            stillLeft[0].flip(true, false);
        }


    }
    public void drawSleep(Shop shop, ShapeRenderer shapeRendererHUD){
        if(fadeUp){
            alphaSleep += Gdx.graphics.getDeltaTime();
            if(alphaSleep >= 1f){
                alphaSleep = 1f;
                fadeUp = false;
                sleepDelay = 1f;
            }
        }else{
            if(sleepDelay > 0){
                sleepDelay -= Gdx.graphics.getDeltaTime();
            }else{
                alphaSleep -= Gdx.graphics.getDeltaTime();
                if(alphaSleep <= 0){
                    alphaSleep = 0;
                    fadeUp = true;
                    shop.setDrawSleep(false);
                }
            }
        }


        if(alphaSleep > 0f) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRendererHUD.begin(ShapeRenderer.ShapeType.Filled);
            shapeRendererHUD.setColor(new Color(0, 0, 0, 1f));
            shapeRendererHUD.setColor(new Color(0, 0, 0, alphaSleep));
            shapeRendererHUD.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRendererHUD.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}

