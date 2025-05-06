package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter{
    OrthographicCamera cameraWelt;
    OrthographicCamera cameraHUD;
    Map map;
    Joystick joystick;
    Vector2 touchPos;
    ShapeRenderer shapeRendererHUD;
    Vector2 cameraWeltPosition;
    BitmapFont font;
    SpriteBatch sp;
    float delta;
    Player player;
    SpriteBatch playerSpriteBatch;

    @Override
    public void create() {
        player = new Player();
        playerSpriteBatch = new SpriteBatch();

        shapeRendererHUD = new ShapeRenderer();

        touchPos = new Vector2(0,0);

        map = new Map();

        joystick = new Joystick(new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));

        cameraHUD = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraHUD.position.set(0,0,0);
        cameraHUD.zoom = 1f;
        cameraHUD.update();


        cameraWeltPosition = new Vector2(800,800);
        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();

        cameraWelt.zoom = 0.15f;

        font = new BitmapFont();

        sp = new SpriteBatch();
    }

    @Override
    public void render() {
        //Einstellungen
        delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRendererHUD.setProjectionMatrix(cameraHUD.combined);
        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Filled);
        playerSpriteBatch.setProjectionMatrix(cameraHUD.combined);

        //Map
        map.render(cameraWelt);
        cameraWelt.update();


        //Erkennt wenn Bildschirm TOUCHED
        if(Gdx.input.isTouched()){
            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.graphics.getHeight() - Gdx.input.getY();
        }else{
            touchPos.x = 0;
            touchPos.y = 0;
        }

        //Joystick
        joystick.moveJoystick(touchPos);
        shapeRendererHUD.end();


        //Player Animation
//        player.updateUP(delta);
        player.drawUP(playerSpriteBatch);



        //Player HITBOX
        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Line);
        shapeRendererHUD.setColor(Color.RED);
        shapeRendererHUD.rect(Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/3f,32,32);
        shapeRendererHUD.end();



        //MOVEMENT
        if(joystick.getCameraWeltPosition().x != 0) {
            cameraWeltPosition.x += joystick.getCameraWeltPosition().x * 1.55f;
        }
        if(joystick.getCameraWeltPosition().y != 0){
            cameraWeltPosition.y += joystick.getCameraWeltPosition().y * 1.55f;
        }
        cameraWelt.position.set(cameraWeltPosition, 0);
        cameraHUD.update();


        //FPS
        font.getData().setScale(3f);
        sp.begin();
        font.draw(sp, "Fps: " + (Gdx.graphics.getFramesPerSecond()), 20, Gdx.graphics.getHeight()-20);
        sp.end();

    }

    @Override
    public void dispose() {

    }



}
