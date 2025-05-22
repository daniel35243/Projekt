package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Map {
    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;
    private MapObjects mapBorderObject;
    private PolygonMapObject mapBorder;
    private Polygon mapBorderPolygon;
    private int[] startLayers;
    private MapObjects objectBorderLayer;
    private MapObjects advancedFeldBorderLayer;
    private PolygonMapObject advancedFeldBorder;
    private Polygon advancedFeldBorderPolygon;
    private boolean isCollidingX;
    private boolean isCollidingY;
    private Vector2 newPosition = new Vector2();
    private Vector2 newXPosition = new Vector2();
    private Vector2 newYPosition = new Vector2();


    //Konstruktor der Map
    public Map(){
        map = new TmxMapLoader().load("map.tmx");  //Map wird als TiledMap geladen
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);   //Map wird in einen Renderer hinzugefügt

        mapBorderObject = map.getLayers().get("Map - Border").getObjects();      //Zieht den MapBorder,
        objectBorderLayer = map.getLayers().get("Objekte - Border").getObjects();         //Objekete, AdvancedFeld
        advancedFeldBorderLayer = map.getLayers().get("AdvancedFeld - Border").getObjects();  //Layer aus der Map


        mapBorder = (PolygonMapObject) mapBorderObject.get(0);     //Aus dem MapBorder  und advancedFeldBorder
        advancedFeldBorder = (PolygonMapObject) advancedFeldBorderLayer.get(0); //Layer wird ein PolygonMapObject erstellt

        startLayers = new int[]{0,1,2,3,4,5,6,7};       //Integer Array mit den indexen die zuerst geladen werden


        isCollidingX = false; //Booleans für spätere
        isCollidingY = false; //Verwendung der Borders
    }


    public void renderFirst(OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(startLayers);
    }
    public void renderPlayer( Player player , SpriteBatch playerSpriteBatch,Joystick joystick, Vector2 cameraWeltPosition){
        playerSpriteBatch.begin();
        player.draw(playerSpriteBatch, joystick.getStillAnimation(), joystick.getPlayerDirection(), cameraWeltPosition);
        playerSpriteBatch.end();
    }
    public void renderLast(){
        tiledMapRenderer.render(new int[]{9});
    }
    public void renderLock(Player player){
        if(player.getLevel() < 5) {
            tiledMapRenderer.render(new int[]{10});
        }
    }


    public Vector2 mapBorder(Joystick joystick, Vector2 cameraWeltPosition, Player player){
        //Berechnet zukünftige Position
        newPosition.set(cameraWeltPosition.x ,  cameraWeltPosition.y);
        newPosition.x += joystick.getCameraWeltPosition().x * 40f * Gdx.graphics.getDeltaTime();
        newPosition.y += joystick.getCameraWeltPosition().y * 40f * Gdx.graphics.getDeltaTime();
        newXPosition.set(newPosition.x, cameraWeltPosition.y );
        newYPosition.set(cameraWeltPosition.x, newPosition.y);


        advancedFeldBorderPolygon = advancedFeldBorder.getPolygon();  //Polygons aus den Layers erstellen
        mapBorderPolygon = mapBorder.getPolygon();

        isCollidingX = false;
        isCollidingY = false;

        //Schaut in Liste mit Objekten
        for(PolygonMapObject object : objectBorderLayer.getByType(PolygonMapObject.class)) {
            Polygon objectBorder = object.getPolygon();

            if (objectBorder.contains(newXPosition.x, newXPosition.y)) {
                isCollidingX = true;
            }
            if(objectBorder.contains(newYPosition.x, newYPosition.y - 8)){
                isCollidingY = true;
            }
        }

        //Prüft ob der Spieler gegen Objekte in der Liste läuft oder die Map Border verlässt
        if (!isCollidingX && mapBorderPolygon.contains(newXPosition.x, newXPosition.y) && (player.getLevel() >= 5 ||
            !advancedFeldBorderPolygon.contains(newXPosition.x, newXPosition.y))) {
            cameraWeltPosition.x = newXPosition.x;
        }
        if (!isCollidingY && mapBorderPolygon.contains(newYPosition.x, newYPosition.y) && (player.getLevel() >= 5 ||
            !advancedFeldBorderPolygon.contains(newYPosition.x, newYPosition.y))) {
            cameraWeltPosition.y = newYPosition.y ;
        }
        return cameraWeltPosition;
    }




    public Polygon getMapBorderPolygon(){
        return mapBorderPolygon;
    }
    public MapObjects getObjectBorderLayer(){
        return objectBorderLayer;
    }
    public void dispose(){
        map.dispose();
    }
}
