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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Items.Item;
import Items.Karotte;
import Items.KarottenSeed;
import Items.Weizen;
import Items.WeizenSeed;


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
    InventorySlot[] inventory = new InventorySlot[5];
    FeldSlot[] feld = new FeldSlot[10];
    SpriteBatch inventorySpriteBatch;


    HitBoxes hitBoxes = new HitBoxes();

    Item selectedItem = null;
    InventorySlot draggedSlot = null;
    boolean dragging = false;
    int numberAnimation = 0;
    FeldSlot[] feldAnfänger = new FeldSlot[4];
    FeldSlot[] feldFortgeschritten = new FeldSlot[6];

    @Override
    public void show() {
        player = new Player();
        playerSpriteBatch = new SpriteBatch();
        inventorySpriteBatch = new SpriteBatch();

        shapeRendererMap = new ShapeRenderer();
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

        int counterInventorySlots = 0;
        for(int j = 180; j <= 900; j += 180) {
            inventory[counterInventorySlots] = new InventorySlot(Gdx.graphics.getWidth() - j);
            counterInventorySlots++;
        }
        inventory[0].addItem(new Karotte(),1);
        inventory[1].addItem(new Weizen(),1);
        inventory[2].addItem(new WeizenSeed(),1);
        inventory[3].addItem(new KarottenSeed(),1);
        inventory[4].addItem(new Karotte(),1);

        int anfängerFeld = 0;
//        for(){
//            for(){
//                feldAnfänger[anfängerFeld] = new FeldSlot();
//                anfängerFeld++;
//
//            }
//        }
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


        hitBoxes.draw(shapeRendererMap,shapeRendererHUD,map,cameraWeltPosition);


        //Erkennt wenn Bildschirm TOUCHED
        if(Gdx.input.isTouched()){
            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.graphics.getHeight() - Gdx.input.getY();
        }else{
            touchPos.x = 0;
            touchPos.y = 0;
        }





        //Joystick
        joystick.moveJoystick(touchPos,new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));
        cameraHUD.update();





        inventorySpriteBatch.begin();



        //Inventory
        for (InventorySlot invSlot:inventory) {
            Rectangle slotRect = new Rectangle(invSlot.getCords().x,invSlot.getCords().y, 170,170);

            if(Gdx.input.isTouched() && slotRect.contains(touchPos.x,touchPos.y) && invSlot.getIsUsed() && selectedItem == null) {
                selectedItem = invSlot.getItem();
                draggedSlot = invSlot;
                dragging = true;
                invSlot.setInventorySlotClickedTrue(inventory);
            }
            if(invSlot.getInventorySlotClicked()){numberAnimation = 1;}else{numberAnimation = 0;}
            invSlot.drawSlot(inventorySpriteBatch,numberAnimation);

            if(invSlot.getIsUsed() && invSlot != draggedSlot) {
                invSlot.getItem().drawInSlot(inventorySpriteBatch,new Vector2(invSlot.getCords().x+25,invSlot.getCords().y+30),fpsFont);
            }
            if(dragging && selectedItem != null){
                selectedItem.drawClicked(inventorySpriteBatch, touchPos, fpsFont, invSlot);
            }
            if(dragging && !Gdx.input.isTouched()){
                dragging = false;
                if(draggedSlot != null) draggedSlot.setInventorySlotClickedFalse();
                selectedItem = null;
                draggedSlot = null;
                invSlot.removeItem();
            }
        }


        inventorySpriteBatch.end();


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
