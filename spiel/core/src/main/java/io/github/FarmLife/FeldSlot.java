package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

import Database.FeldByCord;
import Pflanzen.Pflanze;

public class FeldSlot {
    private boolean isTouched;
    private Vector2 cords;
    private int Wachsstufe;
    private String Item;
    private int id_feld;
    private Game game;
    private Pflanze pflanze;

    public FeldSlot(int id_feld, Main game) {
        this.game = game;
        FeldByCord feld = game.db.getFeldByCord(id_feld);
        cords = new Vector2(feld.feld_x, feld.feld_y);
        Wachsstufe = feld.Wachsstufe;
        Item = feld.item;
        isTouched = false;
    }

    public void setTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }

    public boolean getIsTouched() {
        return isTouched;
    }

    public Vector2 getCords() {
        return cords;
    }

    public int getWachsstufe() {
        return this.Wachsstufe;
    }

    public String getItem() {
        return this.Item;

    }

    public void setPflanze(Pflanze pflanze) {
        this.pflanze = pflanze;
    }

    public Pflanze getPflanze() {
        return this.pflanze;
    }
}
