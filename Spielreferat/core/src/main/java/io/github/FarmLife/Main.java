package io.github.FarmLife;

import com.badlogic.gdx.Game;
import Database.DatabaseLogik;

public class Main extends Game {
    public final DatabaseLogik db; // Hier wird die Datenbankinstanz gespeichert

    public Main(DatabaseLogik db) {
        this.db = db; // Hier wird die Datenbankinstanz übergeben
    }

    @Override
    public void create() {
        setScreen(new StartScreen(this)); // Hier wird bei dem Starten des Spiels die StartScreen angezeigt
    }

    @Override
    public void dispose() {
        super.dispose(); // Hier werden alle Ressourcen freigegeben
    }
}

