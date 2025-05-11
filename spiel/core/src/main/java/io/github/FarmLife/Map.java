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
    private RectangleMapObject objectBorder;
    boolean isCollidingX;
    boolean isCollidingY;
    public Map(){
        map = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        mapBorderObject = map.getLayers().get("MapBorder").getObjects();
        mapBorder = (PolygonMapObject) mapBorderObject.get(0);
        startLayers = new int[]{0,1,2,3,4,5,6,7};

        objectBorderLayer = map.getLayers().get("Objekte").getObjects();
        isCollidingX = false;
        isCollidingY = false;
    }


    public void render(OrthographicCamera camera, Player player , SpriteBatch playerSpriteBatch,Joystick joystick, Vector2 cameraWeltPosition){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(startLayers);
        playerSpriteBatch.begin();
        player.draw(playerSpriteBatch, joystick.getStillAnimation(), joystick.getPlayerDirection(), cameraWeltPosition);
        playerSpriteBatch.end();
        tiledMapRenderer.render(new int[]{9});
    }


    public Vector2 mapBorder(Joystick joystick, Vector2 cameraWeltPosition){
        Vector2 newPosition = new Vector2(cameraWeltPosition.x ,  cameraWeltPosition.y);
        newPosition.x += joystick.getCameraWeltPosition().x * 1.55f;
        newPosition.y += joystick.getCameraWeltPosition().y * 1.55f;

        Vector2 newXPosition = new Vector2(newPosition.x, cameraWeltPosition.y );
        Vector2 newYPosition = new Vector2(cameraWeltPosition.x, newPosition.y);

        mapBorderPolygon = mapBorder.getPolygon();

        for(RectangleMapObject object : objectBorderLayer.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            if (rectangle.contains(newXPosition.x, newXPosition.y)) {
                isCollidingX = true;
            }
            if(rectangle.contains(newYPosition.x, newYPosition.y - 8)){
                isCollidingY = true;
            }else {
                isCollidingX = false;
                isCollidingY = false;
            }
        }

        if (!isCollidingX && mapBorderPolygon.contains(newXPosition.x, newXPosition.y)) {
            cameraWeltPosition.x = newXPosition.x;
        }
        if (mapBorderPolygon.contains(newYPosition.x, newYPosition.y) && !isCollidingY) {
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
