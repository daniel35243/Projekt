package io.github.FarmLife;

import com.badlogic.gdx.Game;

public class Main extends Game {
    public final inventarLogik db;

    public Main(inventarLogik db) {
        this.db = db;
    }

    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}

