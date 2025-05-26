package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

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
    private Skin skin;
    private Image background;


    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        background = new Image(new Texture(Gdx.files.internal("MainMenuBackgroundPicture.png")));
        background.setFillParent(true);
        stage.addActor(background);


        joystick = new Joystick(new Vector2(Gdx.graphics.getWidth()/5*4,Gdx.graphics.getHeight()/5*4));
        player = new Player();
        playerSpriteBatch = new SpriteBatch();
        map = new Map();
        shapeRendererMap = new ShapeRenderer();
        cameraWelt = new OrthographicCamera();
        cameraWelt.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cameraWelt.update();
        cameraWelt.zoom = 0.6f;
        startGame = false;

        Gdx.input.setInputProcessor(stage);


        // Eigene Fonts laden
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontRegular.ttf"));


        FreeTypeFontGenerator.FreeTypeFontParameter titleParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParam.size = 75;
        BitmapFont titleFont = generator.generateFont(titleParam);

        FreeTypeFontGenerator.FreeTypeFontParameter buttonParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParam.size = 52;
        BitmapFont buttonFont = generator.generateFont(buttonParam);

        generator.dispose();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Eigene Button-Grafiken laden
        Texture buttonUpTex = new Texture(Gdx.files.internal("ButtonUp.png"));
        Texture buttonDownTex = new Texture(Gdx.files.internal("ButtonDown.png"));
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTex);
        Drawable buttonDown = new TextureRegionDrawable(buttonDownTex);

        // Custom ButtonStyle mit eigener Font + Grafiken
        TextButton.TextButtonStyle Normalbutton = new TextButton.TextButtonStyle();
        Normalbutton.font = buttonFont;
        Normalbutton.up = buttonUp;
        Normalbutton.down = buttonDown;

        // Eigene Button-Grafiken laden
        buttonUpTex = new Texture(Gdx.files.internal("Topbutton.png"));
        buttonDownTex = new Texture(Gdx.files.internal("Topbutton.png"));
        Drawable buttonUpx = new TextureRegionDrawable(buttonUpTex);
        Drawable buttonDownx = new TextureRegionDrawable(buttonDownTex);

        // Custom ButtonStyle mit eigener Font + Grafiken
        TextButton.TextButtonStyle TitleStyle = new TextButton.TextButtonStyle();
        TitleStyle.font = titleFont;
        TitleStyle.up = buttonUpx;
        TitleStyle.down = buttonDownx;

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Titel mit eigener Font
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = titleFont;
        Label title = new Label("Hauptmenü", labelStyle);

        // Buttons mit Style
        TextButton startButton = new TextButton("Spiel Starten", Normalbutton);
        TextButton exitButton = new TextButton("Spiel Beenden", Normalbutton);
        TextButton TitleButton = new TextButton("Hauptmenü", TitleStyle);

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (displayButton) {
                    displayButton = false;
                    startGame = true;
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (displayButton) {
                    displayButton = false;
                    Gdx.app.exit();
                }
            }
        });


        table.add(TitleButton).size(1400, 180).padBottom(70).row();
        table.add(startButton).size(840, 230).padBottom(30).row();
        table.add(exitButton).size(840, 230);
    }






    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(0.0f,149/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playerSpriteBatch.setProjectionMatrix(cameraWelt.combined);
        shapeRendererMap.setProjectionMatrix(cameraWelt.combined);
        map.renderFirst(cameraWelt);
        map.renderPlayer(player,playerSpriteBatch,joystick,new Vector2(800,800));
        map.renderLast();
        map.renderLock(player);
        cameraWelt.position.set(800,800, 0);

        if (startGame) {
            stage.getRoot().setVisible(false);
            cameraWelt.zoom = Math.max(0.15f, cameraWelt.zoom - delta * 1.2f);
            if (cameraWelt.zoom <= 0.15f) {
                game.setScreen(new GameScreen(map));
            }
        }
        cameraWelt.update();
        stage.act();
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        map.dispose();

    }
}
