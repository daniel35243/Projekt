package io.github.FarmLife;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
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

    public Player(){
        playerSpriteSheet = new Texture(Gdx.files.internal("PixelMapPNGs/Player/Player.png"));
        playerFrames = TextureRegion.split(playerSpriteSheet,213,213);
        walkUp = new TextureRegion[6];
        walkDown = new TextureRegion[6];
        walkLeft = new TextureRegion[6];
        walkRight = new TextureRegion[6];
        stillRight = new TextureRegion[1];
        stillLeft = new TextureRegion[1];
        stillRight[0] = playerFrames[1][1];
        stillLeft[0] = playerFrames[1][1];
        stillUpAnimation = new Animation<>(0.1f,playerFrames[2][1]);
        stillDownAnimation = new Animation<>(0.1f,playerFrames[0][1]);
        stillLeftAnimation = new Animation<>(0.1f,stillLeft);
        stillRightAnimation = new Animation<>(0.1f,stillRight);

        stateTime = 0;
        Right();
        Up();
        Left();
        Down();
        flipped = false;

    }

    public void Up(){
        for(int i = 0; i < 6; i++){
            walkUp[i] = playerFrames[5][i];
        }
        walkUpAnimation = new Animation<>(0.1f,walkUp);
        walkUpAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void Down(){
        for(int i = 0; i < 6; i++){
            walkDown[i] = playerFrames[3][i];
        }
        walkDownAnimation = new Animation<>(0.1f,walkDown);
        walkDownAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void Left(){
        for (int i = 0; i < 6; i++) {
            walkLeft[i] = playerFrames[4][i];
        }
        walkLeftAnimation = new Animation<>(0.1f,walkLeft);
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    public void Right(){
        for (int i = 0; i < 6; i++){
            walkRight[i] = playerFrames[4][i];
        }
        walkRightAnimation = new Animation<>(0.1f,walkRight);
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }


    public void drawUP(SpriteBatch batch,boolean still){

        stateTime += Gdx.graphics.getDeltaTime();

        if(!still) {
            batch.begin();
            TextureRegion frame = walkUpAnimation.getKeyFrame(stateTime, true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2- 106.5f, Gdx.graphics.getHeight() / 2 - 106.5f);
            batch.end();
        }else{
            batch.begin();
            TextureRegion frame = stillUpAnimation.getKeyFrame(stateTime,true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2- 106.5f, Gdx.graphics.getHeight() / 2 - 106.5f);
            batch.end();
        }


    }

    public void drawDOWN(SpriteBatch batch,boolean still){

        stateTime += Gdx.graphics.getDeltaTime();

        if(!still) {
            batch.begin();
            TextureRegion frame = walkDownAnimation.getKeyFrame(stateTime, true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2 - 106.5f, Gdx.graphics.getHeight() / 2- 106.5f);
            batch.end();
        }else{
            batch.begin();
            TextureRegion frame = stillDownAnimation.getKeyFrame(stateTime,true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2 - 106.5f, Gdx.graphics.getHeight() / 2 - 106.5f);
            batch.end();
        }


    }

    public void drawRIGHT(SpriteBatch batch,boolean still){
        stateTime += Gdx.graphics.getDeltaTime();

        if(!still) {
            batch.begin();
            TextureRegion frame = walkRightAnimation.getKeyFrame(stateTime, true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2 - 106.5f, Gdx.graphics.getHeight() / 2 - 106.5f);
            batch.end();
        }else{
            batch.begin();
            TextureRegion frame = stillRightAnimation.getKeyFrame(stateTime,true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2 - 106.5f, Gdx.graphics.getHeight() / 2 - 106.5f);
            batch.end();
        }




    }

    public void drawLEFT(SpriteBatch batch,boolean still){
        stateTime += Gdx.graphics.getDeltaTime();



        if(!still) {
            for (int i = 0; i < 6; i++) {
                walkLeft[i].flip(true, false);
            }
            batch.begin();
            TextureRegion frame = walkLeftAnimation.getKeyFrame(stateTime, true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2 - 106.5f, Gdx.graphics.getHeight() / 2- 106.5f);
            batch.end();
            for (int i = 0; i < 6; i++) {
                walkLeft[i].flip(true, false);
            }
        }else{
            stillLeft[0].flip(true,false);
            batch.begin();
            TextureRegion frame = stillLeftAnimation.getKeyFrame(stateTime,true);
            batch.draw(frame, Gdx.graphics.getWidth() / 2- 106.5f, Gdx.graphics.getHeight() / 2- 106.5f);
            batch.end();
            stillLeft[0].flip(true,false);
        }



    }



}
