package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    PolygonMapObject mapBorder;
    Polygon mapBorderPolygon;
    Polygon playerHitbox;
    float[] playerHitboxCords;
    public Map(){
        map = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        mapBorderObject = map.getLayers().get("MapBorder").getObjects();
        mapBorder = (PolygonMapObject) mapBorderObject.get(0);
    }

    public void render(OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public Vector2 mapBorder(Joystick joystick, Vector2 camerWeltPosition){
        Vector2 newPosition = new Vector2(camerWeltPosition);
        newPosition.x += joystick.getCameraWeltPosition().x*1.55f;
        newPosition.y += joystick.getCameraWeltPosition().y*1.55f;

        playerHitboxCords = new float[]{
            newPosition.x - 60,newPosition.y - 60,
            newPosition.x + 50,newPosition.y - 60,
            newPosition.x - 60,newPosition.y - 30,
            newPosition.x + 50,newPosition.y - 30,
        };


        playerHitbox = new Polygon(playerHitboxCords);
        mapBorderPolygon = mapBorder.getPolygon();

        if(!Intersector.overlapConvexPolygons(mapBorderPolygon,playerHitbox)){
            System.out.println("TEST");
            return camerWeltPosition;
        }else{
            return newPosition;
        }

    }
    public Polygon getMapBorderPolygon(){
        return mapBorderPolygon;
    }
}
