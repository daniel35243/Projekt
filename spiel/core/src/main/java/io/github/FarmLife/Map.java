package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
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
    private Polygon playerHitbox;
    private float[] playerHitboxCords;
    int[] startLayers;
    int[] objectLayer;
    public Map(){
        map = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        mapBorderObject = map.getLayers().get("MapBorder").getObjects();
        mapBorder = (PolygonMapObject) mapBorderObject.get(0);
        startLayers = new int[]{0,1,2,3,4,5,6,7};
    }

    public void renderStart(OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(startLayers);
    }
    public void renderObjects(OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(new int[]{8});
    }

    public Vector2 mapBorder(Joystick joystick, Vector2 cameraWeltPosition){
        Vector2 newPosition = new Vector2(cameraWeltPosition);
        newPosition.x += joystick.getCameraWeltPosition().x*1.55f;
        newPosition.y += joystick.getCameraWeltPosition().y*1.55f;


        playerHitboxCords = new float[]{
            newPosition.x - 60,newPosition.y - 60,
            newPosition.x + 50,newPosition.y - 60,
            newPosition.x + 50,newPosition.y - 30,
            newPosition.x - 60,newPosition.y - 30,
        };



        playerHitbox = new Polygon(playerHitboxCords);
        mapBorderPolygon = mapBorder.getPolygon();

        Vector2 newXPosition = new Vector2(newPosition.x, cameraWeltPosition.y);
        Vector2 newYPosition = new Vector2(cameraWeltPosition.x, newPosition.y);
        if (mapBorderPolygon.contains(newXPosition.x, newXPosition.y)) {
            cameraWeltPosition.x = newXPosition.x;
        }
        if (mapBorderPolygon.contains(newYPosition.x, newYPosition.y)) {
            cameraWeltPosition.y = newYPosition.y;
        }
        return cameraWeltPosition;

    }
    public Polygon getMapBorderPolygon(){
        return mapBorderPolygon;
    }
    public void dispose(){
        map.dispose();
    }
}
