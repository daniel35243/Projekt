package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;

public class Joystick{
    private int radiusSmallCircle;
    private int radiusBigCircle;
    private boolean isPressed;
    private boolean inputJoystick;
    private Vector2 bigCircleCords;
    private Vector2 smallCircleCords;
    private ShapeRenderer shapeRendererJoystick;
    private Vector2 cameraWeltPosition;
    private Vector2 direction;
    private char playerDirection;
    private boolean neuePositionJoystick;
    private boolean stillAnimation;

    public Joystick(Vector2 cords){
        cameraWeltPosition = new Vector2(800,800);

        radiusSmallCircle = 60;
        radiusBigCircle = 150;
        bigCircleCords = new Vector2(cords.x/20*3f,cords.y/10*3f);

        smallCircleCords = bigCircleCords;
        inputJoystick = false;
        isPressed = false;
        shapeRendererJoystick = new ShapeRenderer();
        playerDirection = 'r';
        neuePositionJoystick = false;
        direction  = new Vector2();
        stillAnimation = true;
    }

    public void moveJoystick(Vector2 touchPosition, Vector2 cords){

        //Erkennt, wenn in Joystick getouched wird
        inputJoystick = touchPosition.dst(bigCircleCords) <= radiusBigCircle;

        //Erkennt ob gerade Joystick getouched wird
        if(Gdx.input.justTouched() && inputJoystick) {
            isPressed = true;
        }else if(!Gdx.input.isTouched()){
            isPressed = false;
            bigCircleCords.set(new Vector2(cords.x/20*3f,cords.y/10*3f));
            neuePositionJoystick = false;
        }else if(Gdx.input.isTouched() && touchPosition.x <= Gdx.graphics.getWidth()/2 && !neuePositionJoystick){
            bigCircleCords.set(touchPosition);
            neuePositionJoystick = true;
        }


        //Joystick-Logik
        if(isPressed) {

            //Wenn Joystick im großen Kreis gehalten wird
            if(inputJoystick){
                smallCircleCords = touchPosition;

            //Wenn man außerhalb des Joysticks geht
            }else{
                float angle = MathUtils.atan2(touchPosition.y - bigCircleCords.y,touchPosition.x - bigCircleCords.x);

                smallCircleCords.x = bigCircleCords.x + MathUtils.cos(angle) * radiusBigCircle;
                smallCircleCords.y = bigCircleCords.y + MathUtils.sin(angle) * radiusBigCircle;
            }

            //Führt Animation aus je nach Direction
            direction.set(smallCircleCords).sub(bigCircleCords);
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                if (direction.x > 0) {
                    playerDirection = 'r';
                } else {
                    playerDirection = 'l';
                }
            } else {
                if (direction.y > 0) {
                    playerDirection = 'u';
                } else {
                    playerDirection = 'd';
                }
            }
            stillAnimation = false;

        //Wenn Joystick nicht gehalten wird
        }else{
            smallCircleCords = bigCircleCords;
            stillAnimation = true;
        }



        //Bewegung
        cameraWeltPosition.set((smallCircleCords.x - bigCircleCords.x)/radiusBigCircle,(smallCircleCords.y-bigCircleCords.y)/radiusBigCircle);

        //Malt Joystick
        draw();
    }
    public void draw(){

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //Großer Kreis
        shapeRendererJoystick.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererJoystick.setColor(new Color(80/255f,80/255f,80/255f,0.8f));
        shapeRendererJoystick.circle(bigCircleCords.x, bigCircleCords.y, radiusBigCircle);

        //Kleiner Kreis
        shapeRendererJoystick.setColor(Color.WHITE);
        shapeRendererJoystick.circle(smallCircleCords.x,smallCircleCords.y,radiusSmallCircle);
        shapeRendererJoystick.end();
    }

    public Vector2 getCameraWeltPosition(){
        return cameraWeltPosition;
    }

    public char getPlayerDirection(){
        return playerDirection;
    }
    public boolean getStillAnimation(){
        return stillAnimation;
    }


}
