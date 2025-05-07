package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private Texture logo;
    private float stateTime = 0f;

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        logo = new Texture("FarmLifeLogo.png");
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        float alpha;
        if (stateTime < 1f) {
            alpha = stateTime / 1f;
        } else if (stateTime < 3f) {
            alpha = 1f;
        } else if (stateTime < 4f) {
            alpha = 1f - ((stateTime - 3f) / 1f);
        } else {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();


        float scale = Math.min(screenWidth / logoWidth, screenHeight / logoHeight);

        float scaledWidth = logoWidth * scale;
        float scaledHeight = logoHeight * scale;

        float x = (screenWidth - scaledWidth) / 2f;
        float y = (screenHeight - scaledHeight) / 2f;

        batch.begin();
        batch.setColor(1, 1, 1, alpha);
        batch.draw(logo, x, y, scaledWidth, scaledHeight);
        batch.setColor(1, 1, 1, 1);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        logo.dispose();
    }
}
