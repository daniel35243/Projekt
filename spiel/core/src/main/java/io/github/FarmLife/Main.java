package io.github.FarmLife;

import static jdk.internal.misc.OSEnvironment.initialize;

import com.badlogic.gdx.Game;

import Database.DatabaseLogik;

public class Main extends Game {
    public final DatabaseLogik db;


    public Main(DatabaseLogik db) {
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

