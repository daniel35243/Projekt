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
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Database.InventorySlotDB;
import Items.Item;
import Items.Karotte;
import Items.KarottenSeed;
import Items.Weizen;
import Items.WeizenSeed;
import Pflanzen.Karottenpflanze;
import Pflanzen.Weizenpflanze;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameScreen implements Screen {
    private OrthographicCamera cameraWelt;
    private OrthographicCamera cameraHUD;
    private Map map;
    private Joystick joystick;
    private Vector2 touchPosHUD;
    private Vector3 touchPosMap = new Vector3();
    private ShapeRenderer shapeRendererHUD;
    private Vector2 cameraWeltPosition;
    private BitmapFont fpsFont;
    private SpriteBatch fps;
    private Player player;
    private SpriteBatch playerSpriteBatch;
    private ShapeRenderer shapeRendererMap;
    private SpriteBatch inventorySpriteBatch;
    private HitBoxes hitBoxes = new HitBoxes();
    private final FeldSlot[][] felder = new FeldSlot[2][];
    private Clock clock = new Clock();
    private Rectangle feldRect = new Rectangle();
    private Shop shop;
    private Inventory inventory;

    @Override
    public void show() {
        felder[0] = new FeldSlot[4];
        felder[1] = new FeldSlot[6];


        player = new Player();
        playerSpriteBatch = new SpriteBatch();
        inventorySpriteBatch = new SpriteBatch();

        shapeRendererMap = new ShapeRenderer();
        shapeRendererHUD = new ShapeRenderer();

        touchPosHUD = new Vector2(0, 0);

        map = new Map();

        joystick = new Joystick(new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));

        cameraHUD = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraHUD.position.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,0);
        cameraHUD.update();

        cameraWeltPosition = new Vector2(800,800);
        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();

        cameraWelt.zoom = 0.15f;

        fpsFont = new BitmapFont();
        fps = new SpriteBatch();
        inventory = new Inventory(fpsFont);




        Main game = (Main) Gdx.app.getApplicationListener();
        for(int i = 0; i < felder[0].length; i++) {
            felder[0][i] = new FeldSlot(i+1,game);
        }
        for(int i = 10; i < felder[1].length + 10; i++) {
            felder[1][i-10] = new FeldSlot(i , game);
        }

        shop = new Shop();
        shop.show(cameraWelt);
    }

    @Override
    public void render(float delta) {
        //Einstellungen
        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRendererHUD.setProjectionMatrix(cameraHUD.combined);
        playerSpriteBatch.setProjectionMatrix(cameraWelt.combined);
        inventorySpriteBatch.setProjectionMatrix(cameraHUD.combined);
        shapeRendererMap.setProjectionMatrix(cameraWelt.combined);


        //Map
        cameraWeltPosition.set(map.mapBorder(joystick,cameraWeltPosition,player));
        map.renderFirst(cameraWelt);
        cameraWelt.position.set(cameraWeltPosition, 0);
        cameraWelt.update();

        //Hitboxes
        //hitBoxes.draw(shapeRendererMap,shapeRendererHUD,map,cameraWeltPosition);


        //Erkennt wenn Bildschirm TOUCHED
        touchPosHUD.x = Gdx.input.getX();
        touchPosHUD.y = Gdx.graphics.getHeight() - Gdx.input.getY();

        touchPosMap.set(Gdx.input.getX(),Gdx.input.getY(),0);
        cameraWelt.unproject(touchPosMap);


        playerSpriteBatch.begin();
        for (FeldSlot[] feldSlots : felder) {
            for (FeldSlot feldSlot : feldSlots) {
                if (feldSlot.getPflanze() != null) {
                    feldSlot.getPflanze().draw(clock.getHour(), clock.getMinute(), playerSpriteBatch);
                }
            }
        }
        playerSpriteBatch.end();

        map.renderPlayer( player , playerSpriteBatch,joystick,cameraWeltPosition);
        map.renderLast();
        map.renderLock(player);

        clock.TagNacht(shapeRendererHUD);

        player.drawLevel(inventorySpriteBatch);
        player.drawCoins(inventorySpriteBatch);

        //Joystick
        joystick.moveJoystick(touchPosHUD, new Vector2(Gdx.graphics.getWidth() / 5 * 4, Gdx.graphics.getHeight() / 5 * 4),inventory.getDragging(),shop.getShopOpened());
        cameraHUD.update();

        if(!shop.getShopOpened()) {
            for(FeldSlot[] feldSlots : felder) {
                for (FeldSlot feld : feldSlots) {
                    feld.harvest(touchPosMap, inventory, inventory.getDragging(), player);
                }
            }
        }

        inventorySpriteBatch.begin();
        //UHR
        clock.draw(inventorySpriteBatch);

        inventory.render( touchPosHUD,felder,feldRect,shop,player,touchPosMap,clock,inventorySpriteBatch);

        inventorySpriteBatch.end();


        shop.setPlayerInventoryClock(player,inventory,clock);
        shop.render(new Rectangle(595,860,75,50),cameraWeltPosition);
        if(shop.getDrawSleep()){
            player.drawSleep(shop, shapeRendererHUD);
        }
        player = shop.getPlayer();
        clock = shop.getClock();
        inventory = shop.getInventory();



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
        shop.dispose();
    }



}
