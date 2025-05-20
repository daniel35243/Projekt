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
        cords = new Vector2(feld.feld_x, feld.feld_y);
        feldRect.set(cords.x,cords.y,32,32);
    }



    public Vector2 getCords() {
        return cords;
    }



    public void harvest(Vector3 touchPosMap, InventorySlot[] inventory,Boolean dragging,Player player){
        inventoryArrayList.clear();
        karottenIndexBoolean = false;
        weizenIndexBoolean = false;
        if(pflanze != null && pflanze.getStage() == 3 && feldRect.contains(touchPosMap.x,touchPosMap.y) && Gdx.input.isTouched() && !dragging){
            for(InventorySlot invSlot:inventory) {
                inventoryArrayList.add(invSlot);
                if(invSlot.getItem() != null) {

                    if (invSlot.getItem().getClass() == Karotte.class) {
                        karottenIndex = inventoryArrayList.indexOf(invSlot);
                        karottenIndexBoolean = true;
                    } else if(!karottenIndexBoolean){
                        karottenIndex = 10;
                    }
                    if (invSlot.getItem().getClass() == Weizen.class) {
                        weizenIndex = inventoryArrayList.indexOf(invSlot);
                        weizenIndexBoolean = true;
                    } else if(!weizenIndexBoolean){
                        weizenIndex = 10;
                    }
                } else {
                    nullIndex = inventoryArrayList.indexOf(invSlot);
                }
            }
                if(pflanze.getClass() == Karottenpflanze.class){
                    if(karottenIndex != 10) {
                        inventory[karottenIndex].addItem(new Karotte(), random.nextInt(4) + 2);
                    }else{
                        inventory[nullIndex].addItem(new Karotte(), random.nextInt(4) + 2);
                    }
                }
                if(pflanze.getClass() == Weizenpflanze.class) {
                    if (weizenIndex != 10) {
                        inventory[weizenIndex].addItem(new Weizen(), random.nextInt(4) + 2);
                    }else{
                        inventory[nullIndex].addItem(new Weizen(), random.nextInt(4) + 2);
                    }
                }
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
