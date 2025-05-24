package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import org.w3c.dom.css.Rect;

import Items.Item;
import Items.KarottenSeed;
import Items.WeizenSeed;
import Pflanzen.Karottenpflanze;
import Pflanzen.Weizenpflanze;

public class Inventory {
    private Item selectedItem;
    private InventorySlot draggedSlot;
    private boolean dragging;
    private int numberAnimation;
    private InventorySlot[] inventory;
    private Rectangle invSlotRect;
    int counterInventorySlots;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    boolean found;
    int nullSlot;
    public Inventory(BitmapFont font){
        this.font = font;
        selectedItem = null;
        draggedSlot = null;
        dragging = false;
        numberAnimation = 0;
        inventory = new InventorySlot[5];
        invSlotRect = new Rectangle();
        counterInventorySlots = 0;
        for(int j = 200; j <= 1000; j += 200) {
            inventory[counterInventorySlots] = new InventorySlot(Gdx.graphics.getWidth() - j);
            counterInventorySlots++;
        }
    }

    public void addItem(Item item, int anzahl){
        found = false;
        nullSlot = -1;
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i].getItem() != null && inventory[i].getItem().getClass() == item.getClass()){
                inventory[i].addItem(item, anzahl);
                found = true;
            }else if(!inventory[i].getIsUsed() && nullSlot == -1){
                nullSlot = i;
            }

        }
        if (!found && nullSlot != -1) {
            inventory[nullSlot].addItem(item, anzahl);
        }
    }
    public void removeItem(Item item, int anzahl){
        found = false;
        nullSlot = -1;
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i].getItem() != null && inventory[i].getItem().getClass() == item.getClass()){
                inventory[i].removeItem(anzahl);
                found = true;
            }else if(!inventory[i].getIsUsed() && nullSlot == -1){
                nullSlot = i;
            }

        }
        if (!found && nullSlot != -1) {
            inventory[nullSlot].removeItem(anzahl);
        }
    }
    public void render(Vector2 touchPosHUD, FeldSlot[][] felder, Rectangle feldRect, Shop shop, Player player, Vector3 touchPosMap, Clock clock, SpriteBatch inventorySpriteBatch){
        for (InventorySlot invSlotTouched:inventory) {
            Rectangle slotRect = new Rectangle(invSlotTouched.getCords().x,invSlotTouched.getCords().y, 170,170);

            if(Gdx.input.isTouched() && slotRect.contains(touchPosHUD.x,touchPosHUD.y) && invSlotTouched.getIsUsed() && !dragging) {
                selectedItem = invSlotTouched.getItem();
                draggedSlot = invSlotTouched;
                dragging = true;
                invSlotTouched.setInventorySlotClickedTrue(inventory);
            }
            if(dragging && !Gdx.input.isTouched()){
                boolean swapped = false;
                for(InventorySlot invSlotHotSwap : inventory) {
                    invSlotRect.set(invSlotHotSwap.getCords().x,invSlotHotSwap.getCords().y,170,170);
                    if(invSlotRect.contains(touchPosHUD.x,touchPosHUD.y) && invSlotHotSwap != draggedSlot) {

                        Item draggedSlotItem = draggedSlot.getItem();
                        int draggedSlotCounter = draggedSlot.getItem().getItemCounter();

                        if (invSlotHotSwap.getIsUsed()) {
                            Item invSlotHotSwapItem = invSlotHotSwap.getItem();
                            int invSlotHotSwapCounter = invSlotHotSwap.getItem().getItemCounter();

                            draggedSlot.setItem(invSlotHotSwapItem);
                            draggedSlot.getItem().setItemCounter(invSlotHotSwapCounter);

                        } else {
                            draggedSlot.removeItem(draggedSlotCounter);
                        }
                        invSlotHotSwap.setItem(draggedSlotItem);
                        invSlotHotSwap.getItem().setItemCounter(draggedSlotCounter);
                        System.out.println(invSlotHotSwap.getItem() + " " + invSlotHotSwap.getItem().getItemCounter());
                        swapped = true;
                        break;
                    }
                }
                if(!swapped) {
                    for(int feldSlotCounter = 0; feldSlotCounter < felder.length; feldSlotCounter++) {
                        for(FeldSlot feldSlot : felder[feldSlotCounter]) {
                            if((feldSlotCounter == felder.length -1 && player.getLevel() >= 5)|| feldSlotCounter < felder.length -1){
                                feldRect.set(feldSlot.getCords().x, feldSlot.getCords().y, 32, 32);
                                if (draggedSlot != null && selectedItem != null && feldRect.contains(touchPosMap.x, touchPosMap.y) && feldSlot.getPflanze() == null) {
                                    if (selectedItem instanceof KarottenSeed && !shop.getShopOpened()) {
                                        feldSlot.setPflanze(new Karottenpflanze(feldSlot.getCords().x + 8, feldSlot.getCords().y + 8, clock.getHour(), clock.getMinute()));
                                        draggedSlot.removeItem(1);
                                    } else if (selectedItem instanceof WeizenSeed && !shop.getShopOpened()) {
                                        feldSlot.setPflanze(new Weizenpflanze(feldSlot.getCords().x + 8, feldSlot.getCords().y + 8, clock.getHour(), clock.getMinute()));
                                        draggedSlot.removeItem(1);
                                    }
                                }
                            }
                        }
                    }
                }

                selectedItem = null;
                dragging = false;
                if(draggedSlot != null) draggedSlot.setInventorySlotClickedFalse();
                draggedSlot = null;
                break;
            }
        }
        draw(inventorySpriteBatch, touchPosHUD);
    }
    public void draw(SpriteBatch inventorySpriteBatch, Vector2 touchPosHUD){
        for(InventorySlot invSlot:inventory) {
            if(invSlot == draggedSlot && dragging) {
                numberAnimation = 1;
            }else{
                numberAnimation = 0;
            }

            invSlot.drawSlot(inventorySpriteBatch,numberAnimation);

            if(invSlot.getIsUsed() && invSlot != draggedSlot) {
                invSlot.getItem().drawInSlot(inventorySpriteBatch,new Vector2(invSlot.getCords().x+25,invSlot.getCords().y+30),font);
            }
        }
        if(dragging && selectedItem != null && draggedSlot != null){
            selectedItem.drawClicked(inventorySpriteBatch, touchPosHUD, font, draggedSlot);
        }
    }

    public boolean getDragging(){
        return dragging;
    }
    public Item getSelectedItem(){
        return selectedItem;
    }
    public InventorySlot[] getInventory(){
        return inventory;
    }

}
