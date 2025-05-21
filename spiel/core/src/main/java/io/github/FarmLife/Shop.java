package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

import Items.KarottenSeed;
import Items.WeizenSeed;
import Items.Weizen;
import Items.Karotte;

public class Shop {

    private Stage uiStage;
    private Skin skin;
    private BitmapFont buttonFont;
    private TextButton.TextButtonStyle Normalbutton;
    private TextButton.TextButtonStyle BigStyle;
    private TextButton.TextButtonStyle TitleStyle;

    private Window.WindowStyle windowStyle;
    private Image karotteImg, weizenImg;
    private Image karottenSamenImg, weizenSamenImg;
    private boolean shopOpened;
    private Player player;
    private InventorySlot[] inventory;
    private ArrayList<InventorySlot> inventorySlotArrayList = new ArrayList<>();
    private int index;
    private int nullIndex;
    private boolean indexBoolean;

    public Shop(){
        index = 0;
        nullIndex = 0;
        indexBoolean = false;
    }
    private void initWindowStyle() {
        shopOpened = false;
        BitmapFont windowFont = new BitmapFont();
        Texture windowTex = new Texture(Gdx.files.internal("window_bg.png"));
        Drawable windowBackground = new TextureRegionDrawable(new TextureRegion(windowTex));


        windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = windowFont;
        windowStyle.background = windowBackground;
    }

    private void initFontStyle() {
        // Font Laden
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParam.size = 33;
        buttonFont = generator.generateFont(fontParam);

        FreeTypeFontGenerator.FreeTypeFontParameter buttonParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParam.size = 30;
        BitmapFont titleFont = generator.generateFont(buttonParam);
        generator.dispose();




        // Normal Button Laden
        Texture buttonUpTex = new Texture(Gdx.files.internal("ButtonUp.png"));
        Texture buttonDownTex = new Texture(Gdx.files.internal("ButtonDown.png"));
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTex);
        Drawable buttonDown = new TextureRegionDrawable(buttonDownTex);

        Normalbutton = new TextButton.TextButtonStyle();
        Normalbutton.font = buttonFont;
        Normalbutton.up = buttonUp;
        Normalbutton.down = buttonDown;


        // Big Button Laden
        buttonUpTex = new Texture(Gdx.files.internal("BigButtonUp.png"));
        buttonDownTex = new Texture(Gdx.files.internal("BigButtonDown.png"));
        Drawable buttonUpx = new TextureRegionDrawable(buttonUpTex);
        Drawable buttonDownx = new TextureRegionDrawable(buttonDownTex);

        BigStyle = new TextButton.TextButtonStyle();
        BigStyle.font = buttonFont;
        BigStyle.up = buttonUpx;
        BigStyle.down = buttonDownx;


        // Titel Button Laden
        buttonUpTex = new Texture(Gdx.files.internal("Topbutton.png"));
        buttonDownTex = new Texture(Gdx.files.internal("Topbutton.png"));
        Drawable buttonUpxx = new TextureRegionDrawable(buttonUpTex);
        Drawable buttonDownxx = new TextureRegionDrawable(buttonDownTex);


        TitleStyle = new TextButton.TextButtonStyle();
        TitleStyle.font = titleFont;
        TitleStyle.up = buttonUpxx;
        TitleStyle.down = buttonDownxx;
    }


    private Window mainMenuWindow, subMenuWindow;

    public void render() {
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
    }
    private void showMainMenu(InventorySlot[] inventory, Player player) {
        if (mainMenuWindow != null) mainMenuWindow.remove();

        if (skin == null) {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        }
        initFontStyle();

        mainMenuWindow = new Window("Trader", windowStyle);
        mainMenuWindow.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        mainMenuWindow.setPosition((Gdx.graphics.getWidth() - mainMenuWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - mainMenuWindow.getHeight()) / 2f + 100f);

        Table content = new Table();
        content.pad(10);

        // Buttons werden erstellt
        TextButton shopButton = new TextButton("Kaufen", BigStyle);
        TextButton sellerButton = new TextButton("Verkaufen", BigStyle);
        TextButton exitButton = new TextButton("Schließen", Normalbutton);


        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showShopMenu("Kaufen");
            }
        });

        sellerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSellerMenu("Verkaufen");
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenuWindow.remove();
                shopOpened = false;
            }
        });

        content.add(shopButton).size(390, 600).padRight(20f);
        content.add(sellerButton).size(390, 600).padRight(20f);
        content.add(exitButton).size(400, 210);

        mainMenuWindow.add(content);
        uiStage.addActor(mainMenuWindow);
    }

    private void showShopMenu(String title) {
        if (subMenuWindow != null) subMenuWindow.remove();

        if (skin == null) {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        }
        initFontStyle();

        subMenuWindow = new Window(title, windowStyle);
        subMenuWindow.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        subMenuWindow.setPosition((Gdx.graphics.getWidth() - subMenuWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - subMenuWindow.getHeight()) / 2f + 100f);


        if (karotteImg != null) karotteImg.remove();
        if (weizenImg != null) weizenImg.remove();

        Table content = new Table();
        TextButton Laden = new TextButton("Kaufen", TitleStyle);
        TextButton KarottenSeeds = new TextButton("\n\n\n\n\n\nKarotten Samen\n\n2$", BigStyle);
        TextButton WeizenSeeds = new TextButton("\n\n\n\n\n\nWeizen\nSamen\n\n4$", BigStyle);
        TextButton back = new TextButton("Zurück", Normalbutton);

        Laden.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                karottenSamenImg.toFront();
                weizenSamenImg.toFront();
            }
        });

        KarottenSeeds.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                karottenSamenImg.toFront();
                weizenSamenImg.toFront();
                if (player.getCoins() >= 2) {
                    indexBoolean = false;
                    inventorySlotArrayList.clear();
                    for (InventorySlot invSlot : inventory) {
                        inventorySlotArrayList.add(invSlot);
                        if (invSlot.getItem() instanceof KarottenSeed) {
                            index = inventorySlotArrayList.indexOf(invSlot);
                            indexBoolean = true;
                        } else if (!indexBoolean) {
                            nullIndex = inventorySlotArrayList.indexOf(invSlot);
                        }
                    }
                    if (indexBoolean) {
                        inventory[index].addItem(new KarottenSeed(), 1);
                    } else {
                        inventory[nullIndex].addItem(new KarottenSeed(), 1);
                    }
                    player.removeCoins(2);
                }
            }
        });

        WeizenSeeds.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                karottenSamenImg.toFront();
                weizenSamenImg.toFront();
                if (player.getCoins() >= 4) {
                    indexBoolean = false;
                    inventorySlotArrayList.clear();
                    for (InventorySlot invSlot : inventory) {
                        inventorySlotArrayList.add(invSlot);
                        if (invSlot.getItem() instanceof WeizenSeed) {
                            index = inventorySlotArrayList.indexOf(invSlot);
                            indexBoolean = true;
                        } else if (!indexBoolean) {
                            nullIndex = inventorySlotArrayList.indexOf(invSlot);
                        }
                    }
                    if (indexBoolean) {
                        inventory[index].addItem(new WeizenSeed(), 1);
                    } else {
                        inventory[nullIndex].addItem(new WeizenSeed(), 1);
                    }
                    player.removeCoins(4);
                }
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                subMenuWindow.remove();
                weizenSamenImg.remove();
                karottenSamenImg.remove();
            }
        });

        KarottenSeeds.getLabel().setWrap(true);
        WeizenSeeds.getLabel().setWrap(true);

        content.add(Laden).size(320, 70).padRight(30f).padLeft(10f);
        content.add(KarottenSeeds).size(400, 500).padRight(30f);
        content.add(WeizenSeeds).size(400, 500).padRight(40f);
        content.add(back).size(400, 210).row();


        subMenuWindow.add(content);
        uiStage.addActor(subMenuWindow);

        karottenSamenImg = new Image(new Texture(Gdx.files.internal("karotten_samen.png")));
        karottenSamenImg.setSize(180, 180);
        karottenSamenImg.setPosition(850, 630);
        uiStage.addActor(karottenSamenImg);
        karottenSamenImg.setTouchable(Touchable.disabled);

        weizenSamenImg = new Image(new Texture(Gdx.files.internal("weizen_samen.png")));
        weizenSamenImg.setSize(180, 180);
        weizenSamenImg.setPosition(1280, 630);
        uiStage.addActor(weizenSamenImg);
        weizenSamenImg.setTouchable(Touchable.disabled);

        karottenSamenImg.toFront();
        weizenSamenImg.toFront();
    }

    private void showSellerMenu(String title) {
        if (subMenuWindow != null) subMenuWindow.remove();

        if (skin == null) {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        }
        initFontStyle();

        subMenuWindow = new Window(title, windowStyle);
        subMenuWindow.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        subMenuWindow.setPosition((Gdx.graphics.getWidth() - subMenuWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - subMenuWindow.getHeight()) / 2f + 100f);


        if (karottenSamenImg != null) karottenSamenImg.remove();
        if (weizenSamenImg != null) weizenSamenImg.remove();

        Table content = new Table();
        TextButton Karotte = new TextButton("\n\n\n\n\n\nKarotte\n\n1$", BigStyle);
        TextButton Weizen = new TextButton("\n\n\n\n\n\nWeizen\n\n3$", BigStyle);
        TextButton Markt = new TextButton("Verkaufen", TitleStyle);
        TextButton back = new TextButton("Zurück", Normalbutton);

        Markt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                weizenImg.toFront();
                karotteImg.toFront();
            }
        });
        Karotte.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                karotteImg.toFront();
                weizenImg.toFront();
                for(InventorySlot invSlot : inventory){
                    if(invSlot.getItem() instanceof Karotte){
                        invSlot.removeItem(1);
                        player.addCoins(1);
                    }
                }
            }
        });

        Weizen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                karotteImg.toFront();
                weizenImg.toFront();
                for(InventorySlot invSlot : inventory){
                    if(invSlot.getItem() instanceof Weizen){
                        invSlot.removeItem(1);
                        player.addCoins(3);
                    }
                }
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                subMenuWindow.remove();
                karotteImg.remove();
                weizenImg.remove();
            }
        });

        content.add(Markt).size(320, 70).padRight(30f).padLeft(10f);
        content.add(Karotte).size(400, 500).padRight(30f);
        content.add(Weizen).size(400, 500).padRight(40f);
        content.add(back).size(400, 210).row();

        subMenuWindow.add(content);
        uiStage.addActor(subMenuWindow);

        karotteImg = new Image(new Texture(Gdx.files.internal("karotte.png")));
        karotteImg.setSize(180, 180);
        karotteImg.setPosition(850, 630);
        uiStage.addActor(karotteImg);
        karotteImg.setTouchable(Touchable.disabled);


        weizenImg = new Image(new Texture(Gdx.files.internal("weizen.png")));
        weizenImg.setSize(180, 180);
        weizenImg.setPosition(1300, 630);
        uiStage.addActor(weizenImg);
        weizenImg.setTouchable(Touchable.disabled);


        karotteImg.toFront();
        weizenImg.toFront();
    }

    public void show() {

        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(uiStage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        initFontStyle();
        initWindowStyle();

        TextButton openMenu = new TextButton("Händler", Normalbutton);
        openMenu.getLabel().setSize(500, 300);
        openMenu.setScale(10f);
        openMenu.setPosition(1950, 700);
        openMenu.setVisible(true);
        openMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMainMenu(inventory, player);
                shopOpened = true;
            }
        });
        uiStage.addActor(openMenu);

    }

    public void dispose() {
        uiStage.dispose();
        skin.dispose();
        buttonFont.dispose();
        if (mainMenuWindow != null) mainMenuWindow.remove();
        if (subMenuWindow != null) subMenuWindow.remove();
        if (skin != null) skin.dispose();

    }

    public boolean getShopOpened() {
        return shopOpened;
    }

    public void setPlayerInventory(Player player, InventorySlot[] inventory){
        this.player = player;
        this.inventory = inventory;
    }

    public Player getPlayer(){
        return player;
    }
    public InventorySlot[] getInventory(){
        return inventory;
    }

}
