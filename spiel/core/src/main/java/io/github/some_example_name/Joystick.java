package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;
import java.awt.Shape;

public class Joystick{
    private int radiusSmallCircle;
    private int radiusBigCircle;
    private boolean isPressed;
    private boolean inputJoystick;
    private Vector2 bigCircleCords;
    private Vector2 smallCircleCords;
    ShapeRenderer shapeRendererJoystick;
    float horizontalMovement;
    float verticalMovement;
    public Joystick(Vector2 cords){
        horizontalMovement = 0;
        verticalMovement = 0;
        radiusSmallCircle = 60;
        radiusBigCircle = 150;
        bigCircleCords = new Vector2(cords.x/4.5f,cords.y/4.5f);

        smallCircleCords = bigCircleCords;
        inputJoystick = false;
        isPressed = false;
        shapeRendererJoystick = new ShapeRenderer();
    }

    public void moveJoystick(Vector2 touchPosition){

        inputJoystick = touchPosition.dst(bigCircleCords) <= radiusBigCircle;
        if(Gdx.input.justTouched() && inputJoystick) {
            isPressed = true;

        }else if(!Gdx.input.isTouched()){
            isPressed = false;
        }

        if(isPressed) {
            if(inputJoystick){
                smallCircleCords = touchPosition;

            }else{
                float angle = MathUtils.atan2(touchPosition.y - bigCircleCords.y,touchPosition.x - bigCircleCords.x);

                smallCircleCords.x = (float) (bigCircleCords.x + MathUtils.cos(angle) * radiusBigCircle);
                smallCircleCords.y = (float) (bigCircleCords.y + MathUtils.sin(angle) * radiusBigCircle);
            }
        }else{
            smallCircleCords = bigCircleCords;
        }
        draw();
    }
    public void draw(){

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        shapeRendererJoystick.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererJoystick.setColor(new Color(80/255f,80/255f,80/255f,0.8f));
        shapeRendererJoystick.circle(bigCircleCords.x, bigCircleCords.y, radiusBigCircle);

        shapeRendererJoystick.setColor(Color.WHITE);
        shapeRendererJoystick.circle(smallCircleCords.x,smallCircleCords.y,radiusSmallCircle);
        shapeRendererJoystick.end();
    }

}
