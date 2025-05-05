package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
    public Joystick(Vector2 cords){
        radiusSmallCircle = 40;
        radiusBigCircle = 100;
        bigCircleCords = new Vector2(cords.x/4,cords.y/4);

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
    }
    public void draw(){
        shapeRendererJoystick.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererJoystick.setColor(Color.BLACK);
        shapeRendererJoystick.circle(bigCircleCords.x, bigCircleCords.y, radiusBigCircle);


        shapeRendererJoystick.setColor(Color.WHITE);
        shapeRendererJoystick.circle(smallCircleCords.x,smallCircleCords.y,radiusSmallCircle);
        shapeRendererJoystick.end();

    }
    public Vector2 getBigCircleCords(){
        return bigCircleCords;
    }



}
