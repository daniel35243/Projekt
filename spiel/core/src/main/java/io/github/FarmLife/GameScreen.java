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
    Rectangle invSlotRect = new Rectangle();
    private Shop shop;

    @Override
    public void show() {
        shop = new Shop();
        shop.show();

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
        hudViewport = new ScreenViewport(cameraHUD);

        cameraWeltPosition = new Vector2(800,800);
        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();

        cameraWelt.zoom = 0.15f;

        fpsFont = new BitmapFont();
        fps = new SpriteBatch();


        framecounter = 0;
        zwischenspeicherCameraWeltPosition = new Vector2();

        int counterInventorySlots = 0;
        for(int j = 200; j <= 1000; j += 200) {
            inventory[counterInventorySlots] = new InventorySlot(Gdx.graphics.getWidth() - j);
            counterInventorySlots++;
        }

//        for(i = 0; i < inventory.length; i++) {
//            InventorySlotDB inventar = ((Main) game).db.getInventorySlot(i);
//        }


        Main game = (Main) Gdx.app.getApplicationListener();
        for(int i = 0; i < 4; i++) {
            feldAnfänger[i] = new FeldSlot(i+1,game);
        }

//        inventory[2].addItem(new KarottenSeed(), 20);

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
        cameraWeltPosition.set(map.mapBorder(joystick,cameraWeltPosition));
        map.renderFirst(cameraWelt);
        cameraWelt.position.set(cameraWeltPosition, 0);
        cameraWelt.update();

        //Hitboxes
        hitBoxes.draw(shapeRendererMap,shapeRendererHUD,map,cameraWeltPosition);


        //Erkennt wenn Bildschirm TOUCHED
        touchPosHUD.x = Gdx.input.getX();
        touchPosHUD.y = Gdx.graphics.getHeight() - Gdx.input.getY();

        touchPosMap.set(Gdx.input.getX(),Gdx.input.getY(),0);
        cameraWelt.unproject(touchPosMap);


        inventorySpriteBatch.begin();
        //Inventory
        for (InventorySlot invSlotTouched:inventory) {
            Rectangle slotRect = new Rectangle(invSlotTouched.getCords().x,invSlotTouched.getCords().y, 170,170);

            if(Gdx.input.isTouched() && slotRect.contains(touchPosHUD.x,touchPosHUD.y) && invSlotTouched.getIsUsed() && !dragging) {
                selectedItem = invSlotTouched.getItem();
                draggedSlot = invSlotTouched;
                dragging = true;
                invSlotTouched.setInventorySlotClickedTrue(inventory);
            }
            if(dragging && !Gdx.input.isTouched()){
                boolean swapped = false;
                for(InventorySlot invSlotHotSwap : inventory) {
                    invSlotRect.set(invSlotHotSwap.getCords().x,invSlotHotSwap.getCords().y,170,170);
                    if(invSlotRect.contains(touchPosHUD.x,touchPosHUD.y) && invSlotHotSwap != draggedSlot) {

                        Item draggedSlotItem = draggedSlot.getItem();
                        int draggedSlotCounter = draggedSlot.getItem().getItemCounter();

                        if (invSlotHotSwap.getIsUsed()) {
                            Item invSlotHotSwapItem = invSlotHotSwap.getItem();
                            int invSlotHotSwapCounter = invSlotHotSwap.getItem().getItemCounter();

                            draggedSlot.setItem(invSlotHotSwapItem);
                            draggedSlot.getItem().setItemCounter(invSlotHotSwapCounter);

                        } else {
                            draggedSlot.removeItem(draggedSlotCounter);
                        }
                        invSlotHotSwap.setItem(draggedSlotItem);
                        invSlotHotSwap.getItem().setItemCounter(draggedSlotCounter);
                        System.out.println(invSlotHotSwap.getItem() + " " + invSlotHotSwap.getItem().getItemCounter());
                        swapped = true;
                        break;
                    }
                }
                if(!swapped) {
                    for(FeldSlot feldSlot : feldAnfänger) {
                        feldRect.set(feldSlot.getCords().x,feldSlot.getCords().y,32,32);
                        if (draggedSlot != null && selectedItem != null && feldRect.contains(touchPosMap.x,touchPosMap.y) && feldSlot.getPflanze() == null) {
                            if (selectedItem instanceof KarottenSeed && !shop.getShopOpened()) {
                                feldSlot.setPflanze(new Karottenpflanze(feldSlot.getCords().x + 8, feldSlot.getCords().y + 8, clock.getHour(), clock.getMinute()));
                                draggedSlot.removeItem(1);
                            } else if (selectedItem instanceof WeizenSeed && !shop.getShopOpened()) {
                                feldSlot.setPflanze(new Weizenpflanze(feldSlot.getCords().x + 8, feldSlot.getCords().y + 8, clock.getHour(), clock.getMinute()));
                                draggedSlot.removeItem(1);
                            }
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
        inventorySpriteBatch.end();

        playerSpriteBatch.begin();
        for(FeldSlot feldSlot : feldAnfänger) {
            if(feldSlot.getPflanze() != null) {
                feldSlot.getPflanze().draw(clock.getHour(), clock.getMinute(), playerSpriteBatch);
            }
        }
        playerSpriteBatch.end();

        map.renderPlayer( player , playerSpriteBatch,joystick,cameraWeltPosition);
        map.renderLast();

        clock.TagNacht(shapeRendererHUD);

        player.drawLevel(inventorySpriteBatch);
        player.drawCoins(inventorySpriteBatch);

        //Joystick
        joystick.moveJoystick(touchPosHUD, new Vector2(Gdx.graphics.getWidth() / 5 * 4, Gdx.graphics.getHeight() / 5 * 4),dragging,shop.getShopOpened());
        cameraHUD.update();

        if(!shop.getShopOpened()) {
            for (FeldSlot feld : feldAnfänger) {
                feld.harvest(touchPosMap, inventory, dragging, player);
            }
        }

        inventorySpriteBatch.begin();
        for(InventorySlot invSlot:inventory) {
            if(invSlot == draggedSlot && dragging) {
                numberAnimation = 1;
            }else{
                numberAnimation = 0;
            }

            invSlot.drawSlot(inventorySpriteBatch,numberAnimation);

            if(invSlot.getIsUsed() && invSlot != draggedSlot) {
                invSlot.getItem().drawInSlot(inventorySpriteBatch,new Vector2(invSlot.getCords().x+25,invSlot.getCords().y+30),fpsFont);
            }
        }
        //UHR
        clock.draw(inventorySpriteBatch);

        if(dragging && selectedItem != null && draggedSlot != null){
            selectedItem.drawClicked(inventorySpriteBatch, touchPosHUD, fpsFont, draggedSlot);
        }

        inventorySpriteBatch.end();

        shop.setPlayerInventory(player,inventory);
        shop.render();
        player = shop.getPlayer();
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
