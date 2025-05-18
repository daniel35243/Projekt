package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

import Database.FeldByCord;
import Database.InventorySlotDB;

public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage;
    private boolean displayButton = true;
    private Map map;
    private ShapeRenderer shapeRendererMap;
    private OrthographicCamera cameraWelt;
    boolean startGame;
    private SpriteBatch playerSpriteBatch;
    private Player player;
    private Joystick joystick;

    public MainMenuScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        //Skin laden
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        /*Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        skin.add("default", style);*/

        //Größere Schrift
        BitmapFont bigFont = new BitmapFont();
        bigFont.getData().setScale(8f);

        //Button Style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = bigFont;
        skin.add("default", buttonStyle);

        // Label Stil wird Vergrößert
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = bigFont;

        // UI Aufbau
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);


        Label title = new Label("Hauptmenü", titleStyle);
        TextButton startButton = new TextButton("Spiel starten", skin);
        TextButton exitButton = new TextButton("Beenden", skin);

        //Listener

            startButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (displayButton) {
                        displayButton = false;
                        startGame = true;
                    }
                }
            });

            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (displayButton) {
                        displayButton = false;
                        Gdx.app.exit();
                    }
                }
            });

        //Layout der Buttons
        table.add(title).padBottom(60).row();
        table.add(startButton).width(300).height(100).padBottom(30).row();
        table.add(exitButton).width(300).height(100);
    }

    @Override public void show() {
        joystick = new Joystick(new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));
        player = new Player();
        playerSpriteBatch = new SpriteBatch();
        map = new Map();
        shapeRendererMap = new ShapeRenderer();
        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();
        cameraWelt.zoom = 0.8f;
        startGame = false;



        InventorySlotDB inventory = ((Main) game).db.getInventorySlot(1);
        Gdx.app.log("Inventar", "Slot " +  inventory.slot +  " = " + inventory.item + " (" + inventory.anzahl + ")");

        FeldByCord feld = ((Main) game).db.getFeldByCord(1);
        Gdx.app.log("Feld " + feld.feldID, " = " + feld.item + " = " + feld.Wachsstufe + " = " + feld.feld_x + " = " + feld.feld_y);
    }
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playerSpriteBatch.setProjectionMatrix(cameraWelt.combined);
        shapeRendererMap.setProjectionMatrix(cameraWelt.combined);
        map.renderFirst(cameraWelt);
        map.renderPlayer(player,playerSpriteBatch,joystick,new Vector2(800,800));
        map.renderLast();
        cameraWelt.position.set(800,800, 0);

        if (startGame) {
            cameraWelt.zoom = Math.max(0.15f, cameraWelt.zoom - delta * 1.2f);
            if (cameraWelt.zoom <= 0.15f) {
                game.setScreen(new GameScreen());
            }
        }
        cameraWelt.update();
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {


    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }

    @Override public void dispose() {
        stage.dispose();
        playerSpriteBatch.dispose();
        shapeRendererMap.dispose();
        map.dispose();
    }
}
