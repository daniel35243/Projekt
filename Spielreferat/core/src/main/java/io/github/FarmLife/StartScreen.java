package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen implements Screen {

    private final Game game;
    private SpriteBatch batch; // wird verwendet, um 2D-Grafik zu zeichnen
    private Texture logo;
    private float stateTime = 0f;

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch(); // wird verwendet, um 2D-Grafik zu zeichnen
        logo = new Texture("FarmLifeLogo.png"); // Hier wird die Logo-Textur geladen
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        float alpha;
        if (stateTime < 1f) { // Logo erscheint 1 Sekunde lang
            alpha = stateTime;
        } else if (stateTime < 3f) { // Logo bleibt für 2 Sekunden lang
            alpha = 1f;
        } else if (stateTime < 4f) {
            alpha = 1f - ((stateTime - 3f)); // Logo verschwindet 1 Sekunde lang
        } else {
            game.setScreen(new MainMenuScreen(game)); // Der Screen wird auf die MainMenuScreen gesetzt
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1); // Hintergrundfarbe auf Schwarz
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth(); // Abfrage der Bildschirmbreite
        float screenHeight = Gdx.graphics.getHeight(); // Abfrage der Bildschirmhöhe
        float logoWidth = logo.getWidth(); //Abfrage der Logobreite
        float logoHeight = logo.getHeight(); //Abfrage der Logohöhe

        float scale = Math.min(screenWidth / logoWidth, screenHeight / logoHeight);
        float scaledWidth = logoWidth * scale; // Skalierung der Logobreite
        float scaledHeight = logoHeight * scale; // Skalierung der Logohöhe

        float x = (screenWidth - scaledWidth) / 2f;
        float y = (screenHeight - scaledHeight) / 2f; // Zentrierung des Logos im Bildschirm

        batch.begin(); // Startet Zeichenbefehl
        batch.setColor(1, 1, 1, alpha); // Logo Transparenz
        batch.draw(logo, x, y, scaledWidth, scaledHeight); // Zeichnet das bild in der Mitte des Bildschirms
        batch.setColor(1, 1, 1, 1); // Farbe auf vollständig sichtbar
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        game.dispose();
        batch.dispose();
        logo.dispose();

    }
}
