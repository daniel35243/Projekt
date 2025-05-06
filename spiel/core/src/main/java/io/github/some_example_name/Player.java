package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    private Texture playerSpriteSheet;
    private TextureRegion[][] playerFrames;
    private TextureRegion[] walkUp;
    private TextureRegion[] walkLeft;
    private TextureRegion[] walkDown;
    private TextureRegion[] walkRight;
    private Animation<TextureRegion> walkUpAnimation;
    private float stateTime;
    public Player(){
        playerSpriteSheet = new Texture("PixelMapPNGs/Player/Player.png");
        playerFrames = TextureRegion.split(playerSpriteSheet,32,32);
        walkUp = new TextureRegion[6];
        stateTime = 0;
        Up();
    }
    public void Up(){
        for(int i = 0; i < 6; i++){
            walkUp[i] = playerFrames[5][i];
        }
        walkUpAnimation = new Animation<>(0.1f,walkUp);
        walkUpAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void drawUP(SpriteBatch batch, ShapeRenderer shapeRenderer){
        TextureRegion frame = walkUpAnimation.getKeyFrame(stateTime,true);
        batch.begin();
        batch.draw(frame, Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);


        shapeRenderer.rect(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f,32,32);
        shapeRenderer.end();
        batch.end();
    }

    public void updateUP(float delta){
        stateTime += delta;
    }


}
