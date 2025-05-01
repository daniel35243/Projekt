package io.github.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

import farmlife.screens.Splash;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new Splash());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
