package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        skin = new Skin(Gdx.files.internal("uiskin.json"));


        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Main Menu", skin);
        title.setFontScale(6f); // Überschrift größer

        TextButton startButton = new TextButton("Spiel Starten", skin);
        TextButton exitButton = new TextButton("Spiel Beenden", skin);


        startButton.getLabel().setFontScale(3f);
        exitButton.getLabel().setFontScale(3f);


        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (displayButton) {
                    displayButton = false;
                    game.setScreen((new GameScreen()));
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

        // Aufbau des Menüs
        table.add(title).padBottom(50).row();
        table.add(startButton).size(400, 120).padBottom(30).row();
        table.add(exitButton).size(400, 120);
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
