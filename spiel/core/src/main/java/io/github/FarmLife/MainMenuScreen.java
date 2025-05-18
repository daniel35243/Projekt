package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private boolean displayButton = true;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background = new Texture(Gdx.files.internal("MainMenuBackgroundPicture.PNG"));
        Image bgImage = new Image(background);
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        // Eigene Fonts laden
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P-Regular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter titleParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParam.size = 100;
        BitmapFont titleFont = generator.generateFont(titleParam);

        FreeTypeFontGenerator.FreeTypeFontParameter buttonParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParam.size = 50;
        BitmapFont buttonFont = generator.generateFont(buttonParam);

        generator.dispose();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Eigene Button-Grafiken laden
        Texture buttonUpTex = new Texture(Gdx.files.internal("ButtonUp.PNG"));
        Texture buttonDownTex = new Texture(Gdx.files.internal("ButtonDown.PNG"));
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTex);
        Drawable buttonDown = new TextureRegionDrawable(buttonDownTex);

        // Custom ButtonStyle mit eigener Font + Grafiken
        TextButton.TextButtonStyle customStyle = new TextButton.TextButtonStyle();
        customStyle.font = buttonFont;
        customStyle.up = buttonUp;
        customStyle.down = buttonDown;

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Titel mit eigener Font
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = titleFont;
        Label title = new Label("Main Menu", labelStyle);

        // Buttons mit Style
        TextButton startButton = new TextButton("Spiel Starten", customStyle);
        TextButton exitButton = new TextButton("Spiel Beenden", customStyle);
        TextButton MMbg = new TextButton("Main Menu", customStyle);

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (displayButton) {
                    displayButton = false;
                    game.setScreen(new GameScreen());
                    System.out.println("App Spiel Starten Button gedrückt");
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (displayButton) {
                    displayButton = false;
                    Gdx.app.exit();
                    System.out.println("App Beenden Button gedrückt");
                }
            }
        });


        table.add(MMbg).size(1200, 160).padBottom(50).row();
        table.add(startButton).size(800, 300).padBottom(20).row();
        table.add(exitButton).size(800, 300);
    }






    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        background.dispose();


    }
}
