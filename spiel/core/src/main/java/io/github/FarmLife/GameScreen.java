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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import Database.InventorySlotDB;
import Items.Item;
import Items.Karotte;
import Items.KarottenSeed;
import Items.Weizen;
import Items.WeizenSeed;
import Pflanzen.Karottenpflanze;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameScreen implements Screen {
    OrthographicCamera cameraWelt;
    OrthographicCamera cameraHUD;
    Map map;
    Joystick joystick;
    Vector2 touchPosHUD;
    Vector3 touchPosMap = new Vector3();
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
    InventorySlot[] inventory = new InventorySlot[5];
    SpriteBatch inventorySpriteBatch;
    HitBoxes hitBoxes = new HitBoxes();
    Item selectedItem = null;
    InventorySlot draggedSlot = null;
    boolean dragging = false;
    int numberAnimation = 0;
    FeldSlot[] feldAnfänger = new FeldSlot[4];
    FeldSlot[] feldFortgeschritten = new FeldSlot[6];
    Clock clock = new Clock();
    Rectangle feldRect = new Rectangle();
    private Skin skin;
    private boolean showButton = true;

    private TextButton shopButton;
    private Window shopWindow;

    private Stage stage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        player = new Player();
        playerSpriteBatch = new SpriteBatch();
        inventorySpriteBatch = new SpriteBatch();

        shapeRendererMap = new ShapeRenderer();
        shapeRendererHUD = new ShapeRenderer();

        touchPosHUD = new Vector2(0,0);

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

        int counterInventorySlots = 0;
        for(int j = 200; j <= 1000; j += 200) {
            inventory[counterInventorySlots] = new InventorySlot(Gdx.graphics.getWidth() - j);
            counterInventorySlots++;
        }
        for(int i = 0; i < 5; i++) {
            InventorySlotDB inventar = ((Main) Gdx.app.getApplicationListener()).db.getInventorySlot(i+1);
            inventory[i].addItem(inventar.item, inventar.anzahl);
        }


        Main game = (Main) Gdx.app.getApplicationListener();
        for(int i = 0; i < 4; i++) {
            feldAnfänger[i] = new FeldSlot(i+1,game);
        }

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Shop-Button erstellen
        shopButton = new TextButton("Shop öffnen", skin);
        shopButton.setPosition(1900, 600);
        shopButton.setSize(400, 150);
        shopButton.getLabel().setFontScale(4f);
        stage.addActor(shopButton);

        // Shop-Fenster erstellen
        shopWindow = new Window("Shop", skin);
        shopWindow.setSize(300, 250);
        shopWindow.setPosition(200, 200); // Position anpassen
        shopWindow.setVisible(false); // Anfangs versteckt

        // Drei Buttons im Shop-Fenster
        TextButton item1Button = new TextButton("Item 1 kaufen", skin);
        TextButton item2Button = new TextButton("Item 2 kaufen", skin);
        TextButton item3Button = new TextButton("Item 3 kaufen", skin);

        shopWindow.add(item1Button).pad(10).row();
        shopWindow.add(item2Button).pad(10).row();
        shopWindow.add(item3Button).pad(10).row();

        stage.addActor(shopWindow);

        // Listener: Fenster anzeigen beim Klick
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopWindow.setVisible(true);
            }
        });


    }

    @Override
    public void render(float delta) {
        delta = Gdx.graphics.getDeltaTime();

        //Einstellungen
        framecounter++;
        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRendererHUD.setProjectionMatrix(cameraHUD.combined);
        playerSpriteBatch.setProjectionMatrix(cameraWelt.combined);
        inventorySpriteBatch.setProjectionMatrix(cameraHUD.combined);
        shapeRendererMap.setProjectionMatrix(cameraWelt.combined);

//        //TAG/NACHT
//        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRendererHUD.setColor(new Color(30/255f,30/255f,30/255f,0));
//        shapeRendererHUD.rect(0,0,Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
//        shapeRendererHUD.end();

        //Map
        cameraWeltPosition.set(map.mapBorder(joystick,cameraWeltPosition));
        map.render(cameraWelt,player,playerSpriteBatch,joystick,cameraWeltPosition);
        cameraWelt.position.set(cameraWeltPosition, 0);
        cameraWelt.update();

        //Hitboxes
//        hitBoxes.draw(shapeRendererMap,shapeRendererHUD,map,cameraWeltPosition);


        //Erkennt wenn Bildschirm TOUCHED
        if(Gdx.input.isTouched()){
            touchPosHUD.x = Gdx.input.getX();
            touchPosHUD.y = Gdx.graphics.getHeight() - Gdx.input.getY();
        }else{
            touchPosHUD.x = 0;
            touchPosHUD.y = 0;
        }
        touchPosMap.set(Gdx.input.getX(),Gdx.input.getY(),0);
        cameraWelt.unproject(touchPosMap);


        stage.act(delta);
        stage.draw();


        //Joystick
        joystick.moveJoystick(touchPosHUD, new Vector2(Gdx.graphics.getWidth() / 5 * 4, Gdx.graphics.getHeight() / 5 * 4),dragging);
        cameraHUD.update();


        inventorySpriteBatch.begin();
        //UHR
        clock.draw(inventorySpriteBatch);


        //Inventory
        for (InventorySlot invSlot:inventory) {
            Rectangle slotRect = new Rectangle(invSlot.getCords().x,invSlot.getCords().y, 170,170);

            if(Gdx.input.isTouched() && slotRect.contains(touchPosHUD.x,touchPosHUD.y) && invSlot.getIsUsed() && !dragging) {
                selectedItem = invSlot.getItem();
                draggedSlot = invSlot;
                dragging = true;
                invSlot.setInventorySlotClickedTrue(inventory);
            }
            if(invSlot == draggedSlot && dragging) {
                numberAnimation = 1;
            }else{
                numberAnimation = 0;
            }

            invSlot.drawSlot(inventorySpriteBatch,numberAnimation);

            if(invSlot.getIsUsed() && invSlot != draggedSlot) {
                invSlot.getItem().drawInSlot(inventorySpriteBatch,new Vector2(invSlot.getCords().x+25,invSlot.getCords().y+30),fpsFont);
            }

            if(dragging && !Gdx.input.isTouched()){
                for(FeldSlot feldSlot : feldAnfänger) {
                    feldRect.set(feldSlot.getCords().x,feldSlot.getCords().y,32,32);
                    if (draggedSlot != null && selectedItem != null && feldRect.contains(touchPosMap.x,touchPosMap.y)) {

                        if(selectedItem instanceof KarottenSeed){
                          feldSlot.setPflanze(new Karottenpflanze(feldSlot.getCords().x + 8, feldSlot.getCords().y + 8, clock.getHour(), clock.getMinute()));
                            draggedSlot.removeItem();
                        }

                    }
                }
                selectedItem = null;
                dragging = false;
                if(draggedSlot != null) draggedSlot.setInventorySlotClickedFalse();
                draggedSlot = null;
                break;
            }
        }
        if(dragging && selectedItem != null && draggedSlot != null){
            selectedItem.drawClicked(inventorySpriteBatch, touchPosHUD, fpsFont, draggedSlot);
        }
        inventorySpriteBatch.end();

        playerSpriteBatch.begin();
        for(FeldSlot feldSlot : feldAnfänger) {
            if(feldSlot.getPflanze() != null) {
                feldSlot.getPflanze().draw(clock.getHour(), clock.getMinute(), playerSpriteBatch);
            }
        }
        playerSpriteBatch.end();






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
