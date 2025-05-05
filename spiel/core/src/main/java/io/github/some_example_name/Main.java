package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    Rectangle rec;
    OrthographicCamera cameraWelt;
    OrthographicCamera cameraHUD;
    Map map;
    float screenWidth;
    float screenHeight;
    Joystick joystick;
    Vector2 touchPos;
    ShapeRenderer shapeRendererHUD;



    @Override
    public void create() {
        shapeRendererHUD = new ShapeRenderer();

        touchPos = new Vector2(0,0);

        map = new Map();

        joystick = new Joystick(new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));

        cameraHUD = new OrthographicCamera(100,100);
        cameraHUD.position.set(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5,0);
        cameraHUD.zoom = 1f;
        cameraHUD.update();

        rec = new Rectangle(
            cameraHUD.position.x - cameraHUD.viewportWidth * 0.5f * cameraHUD.zoom,
            cameraHUD.position.y - cameraHUD.viewportHeight * 0.5f * cameraHUD.zoom,
            cameraHUD.viewportWidth,
            cameraHUD.viewportHeight
        );



        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();
        cameraWelt.position.set(800,800,0);
        cameraWelt.zoom = 0.2f;
    }

    @Override
    public void render() {

        cameraHUD.update();

        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render(cameraWelt);
        cameraWelt.update();


        if(Gdx.input.isTouched()){
            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.graphics.getHeight() - Gdx.input.getY();
        }else{
            touchPos.x = 0;
            touchPos.y = 0;
        }


        shapeRendererHUD.setProjectionMatrix(cameraHUD.combined);
        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Filled);
        joystick.moveJoystick(touchPos);


        cameraHUD.update();



        shapeRendererHUD.end();

    }

    @Override
    public void dispose() {

    }

}
