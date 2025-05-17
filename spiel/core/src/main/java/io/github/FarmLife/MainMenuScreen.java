package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
                        game.setScreen((new GameScreen()));
                        System.out.println("App Spiel Starten Button gedrückt");
                        System.out.println(displayButton);
                    }
                }
            });

            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (displayButton) {
                        displayButton = false;
                        Gdx.app.exit();
                        System.out.println("App Beenden Button gedrückt");
                    }
                }
            });

        //Layout der Buttons
        table.add(title).padBottom(60).row();
        table.add(startButton).width(300).height(100).padBottom(30).row();
        table.add(exitButton).width(300).height(100);
    }

    @Override public void show() {
        InventorySlotDB inventory = ((Main) game).db.getInventorySlot(1);
        Gdx.app.log("Inventar", "Slot " +  inventory.slot +  " = " + inventory.item + " (" + inventory.anzahl + ")");

        FeldByCord feld = ((Main) game).db.getFeldByCord(1);
        Gdx.app.log("Feld " + feld.feldID, " = " + feld.item + " = " + feld.Wachsstufe + " = " + feld.feld_x + " = " + feld.feld_y);
    }
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        game.dispose();
        stage.dispose();
    }
}
