package io.github.FarmLife;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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

    public Player() {
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
}
