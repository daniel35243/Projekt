package io.github.FarmLife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameScreen implements Screen {
    OrthographicCamera cameraWelt;
    OrthographicCamera cameraHUD;
    Map map;
    Joystick joystick;
    Vector2 touchPos;
    ShapeRenderer shapeRendererHUD;
    Vector2 cameraWeltPosition;
    BitmapFont fpsFont;
    SpriteBatch fps;
    Player player;
    SpriteBatch playerSpriteBatch;
    Viewport hudViewport;
    float nightFaktor;
    boolean nightFaktorNull;
    int framecounter;
    Vector2 zwischenspeicherCameraWeltPosition;
    ShapeRenderer shapeRendererMap;

    @Override
    public void show() {
        player = new Player();
        playerSpriteBatch = new SpriteBatch();

        shapeRendererHUD = new ShapeRenderer();

        touchPos = new Vector2(0,0);

        map = new Map();

        joystick = new Joystick(new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));

        cameraHUD = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraHUD.position.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,0);
        cameraHUD.update();
        hudViewport = new ScreenViewport(cameraHUD);

        cameraWeltPosition = new Vector2(800,800);
        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();

        cameraWelt.zoom = 0.15f;

        fpsFont = new BitmapFont();
        fps = new SpriteBatch();

        nightFaktor = 0.8f;
        nightFaktorNull = false;
        framecounter = 0;
        zwischenspeicherCameraWeltPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
        framecounter++;
        //Einstellungen
        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRendererHUD.setProjectionMatrix(cameraHUD.combined);
        playerSpriteBatch.setProjectionMatrix(cameraWelt.combined);

        //Map
        cameraWeltPosition.set(map.mapBorder(joystick,cameraWeltPosition));
        map.render(cameraWelt,player,playerSpriteBatch,joystick,cameraWeltPosition);
        cameraWelt.position.set(cameraWeltPosition, 0);
        cameraWelt.update();


        //Erkennt wenn Bildschirm TOUCHED
        if(Gdx.input.isTouched()){
            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.graphics.getHeight() - Gdx.input.getY();
        }else{
            touchPos.x = 0;
            touchPos.y = 0;
        }


        //Player HITBOX
        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Line);
        shapeRendererHUD.setColor(Color.RED);
        shapeRendererHUD.rect(Gdx.graphics.getWidth() / 2 - 60,Gdx.graphics.getHeight() / 2 - 60,110,160);
        shapeRendererHUD.setColor(Color.YELLOW);
        shapeRendererHUD.rect(Gdx.graphics.getWidth() / 2 - 60,Gdx.graphics.getHeight() / 2 - 60,110,30);


        //Joystick HITBOX
        shapeRendererHUD.setColor(Color.BLUE);
        shapeRendererHUD.rect(0,0,Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight());
        shapeRendererHUD.end();


        //Joystick
        joystick.moveJoystick(touchPos,player,playerSpriteBatch,new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));
        cameraHUD.update();

        //TAG/NACHT
        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererHUD.setColor(new Color(30/255f,30/255f,30/255f,0));
        shapeRendererHUD.rect(0,0,Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
        shapeRendererHUD.end();

        //Map Border
        shapeRendererMap.begin(ShapeRenderer.ShapeType.Line);
        shapeRendererMap.setColor(Color.BLACK);
        shapeRendererMap.polygon(map.getMapBorderPolygon().getTransformedVertices());
        for(RectangleMapObject object : map.getObjectBorderLayer().getByType(RectangleMapObject.class)) {
            shapeRendererMap.rect(object.getRectangle().x, object.getRectangle().y, object.getRectangle().width, object.getRectangle().height);
        }
        shapeRendererMap.end();

        if(framecounter == 30) {
            if (nightFaktorNull) {
                nightFaktor += 0.0133f;
                if (nightFaktor >= 0.8) {
                    nightFaktorNull = false;
                }
            } else {
                nightFaktor -= 0.0133f;
                if (nightFaktor <= 0) {
                    nightFaktorNull = true;
                }
            }
            framecounter = 0;
        }

        //FPS
        fpsFont.getData().setScale(3f);
        fps.begin();
        fpsFont.draw(fps, "Fps: " + (Gdx.graphics.getFramesPerSecond()), 20, Gdx.graphics.getHeight()-20);
        fps.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        playerSpriteBatch.dispose();
        fpsFont.dispose();
        fps.dispose();
        shapeRendererHUD.dispose();
        shapeRendererMap.dispose();
        fps.dispose();
        fpsFont.dispose();
        shapeRendererHUD.dispose();
        shapeRendererMap.dispose();
        fps.dispose();
        fpsFont.dispose();
        shapeRendererHUD.dispose();
        shapeRendererMap.dispose();
        map.dispose();
    }



}
