package io.github.FarmLife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

import Database.FeldByCord;
import Items.Item;
import Items.Karotte;
import Items.KarottenSeed;
import Items.Weizen;
import Pflanzen.Karottenpflanze;
import Pflanzen.Pflanze;
import Pflanzen.Weizenpflanze;

public class FeldSlot {

    private Vector2 cords;
    private int id_feld;
    private Game game;
    private Pflanze pflanze;
    private Rectangle feldRect = new Rectangle();
    private Random random = new Random();
    private int karottenIndex;
    private int weizenIndex;
    private int nullIndex;
    private ArrayList<InventorySlot> inventoryArrayList = new ArrayList<>();
    private boolean karottenIndexBoolean = false;
    private boolean weizenIndexBoolean = false;

    public FeldSlot(int id_feld, Main game) {
        this.game = game;
        FeldByCord feld = game.db.getFeldByCord(id_feld);
        System.out.println(id_feld + ": " + feld.feld_x + " " + feld.feld_y);
        cords = new Vector2(feld.feld_x, feld.feld_y);
        feldRect.set(cords.x,cords.y,32,32);
    }



    public Vector2 getCords() {
        return cords;
    }



    public void harvest(Vector3 touchPosMap, Inventory inventory,Boolean dragging,Player player){
        inventoryArrayList.clear();
        karottenIndexBoolean = false;
        weizenIndexBoolean = false;
        if(pflanze != null && pflanze.getStage() == 3 && feldRect.contains(touchPosMap.x,touchPosMap.y) && Gdx.input.isTouched() && !dragging){
            inventory.addItem(pflanze.getFinalProduct(),random.nextInt(3)+3);
            this.setPflanze(null);
            player.addXp(random.nextInt(11) + 30);
        }

    }

    public void setPflanze(Pflanze pflanze) {
        this.pflanze = pflanze;
    }

    public Pflanze getPflanze() {
        return this.pflanze;
    }
}
