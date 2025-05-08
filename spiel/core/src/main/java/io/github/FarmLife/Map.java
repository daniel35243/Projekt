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
        mapBorderObject = map.getLayers().get("Map Border").getObjects();
        mapBorder = (PolygonMapObject) mapBorderObject.get(0);
        playerHitboxCords = new float[]{
            Gdx.graphics.getWidth() / 2 - 60,Gdx.graphics.getHeight() / 2 - 60,
            Gdx.graphics.getWidth() / 2 + 50,Gdx.graphics.getHeight() / 2 - 60,
            Gdx.graphics.getWidth() / 2 + 50,Gdx.graphics.getHeight() / 2 + 50,
            Gdx.graphics.getWidth() / 2 - 60,Gdx.graphics.getHeight() / 2 + 50,

        };
        playerHitbox = new Polygon(playerHitboxCords);
        mapBorderPolygon = mapBorder.getPolygon();
    }

    public void render(OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public Vector2 mapBorder(Joystick joystick, Vector2 vector){
        if(!Intersector.overlapConvexPolygons(mapBorderPolygon,playerHitbox)){
            vector.y -= joystick.getCameraWeltPosition().y * 1.55f;
            vector.x -= joystick.getCameraWeltPosition().x * 1.55f;
        }else{
            vector.y = joystick.getCameraWeltPosition().y;
            vector.x = joystick.getCameraWeltPosition().x;
        }
        return vector;
    }
}
