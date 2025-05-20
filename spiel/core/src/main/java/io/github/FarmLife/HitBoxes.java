package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;

public class HitBoxes {
    public void draw(ShapeRenderer shapeRendererMap, ShapeRenderer shapeRendererHUD, Map map, Vector2 cameraWeltPosition) {
        //Map Border
        shapeRendererMap.begin(ShapeRenderer.ShapeType.Line);
        shapeRendererMap.setColor(Color.BLACK);
        shapeRendererMap.polygon(map.getMapBorderPolygon().getTransformedVertices());
        for (PolygonMapObject object : map.getObjectBorderLayer().getByType(PolygonMapObject.class)) {
            shapeRendererMap.polygon(object.getPolygon().getTransformedVertices());
        }
        //Anfangsfeld
        shapeRendererMap.rect(880,688,32,32);
        shapeRendererMap.rect(944,688,32,32);
        shapeRendererMap.rect(880,624,32,32);
        shapeRendererMap.rect(944,624,32,32);

        //Fortgeschrittenenfeld
        shapeRendererMap.rect(736,656,32,32);
        shapeRendererMap.rect(672,656,32,32);
        shapeRendererMap.rect(608,656,32,32);

        shapeRendererMap.rect(736,592,32,32);
        shapeRendererMap.rect(672,592,32,32);
        shapeRendererMap.rect(608,592,32,32);
        shapeRendererMap.end();
        //Player HITBOX
        shapeRendererHUD.begin(ShapeRenderer.ShapeType.Line);
        shapeRendererHUD.setColor(Color.RED);
        shapeRendererHUD.rect(Gdx.graphics.getWidth() / 2 - 60, Gdx.graphics.getHeight() / 2 - 60, 110, 160);
        shapeRendererHUD.setColor(Color.YELLOW);
        shapeRendererHUD.rect(Gdx.graphics.getWidth() / 2 - 60, Gdx.graphics.getHeight() / 2 - 60, 110, 30);


        //Joystick HITBOX
        shapeRendererHUD.setColor(Color.BLUE);
        shapeRendererHUD.rect(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        shapeRendererHUD.end();

        //Player Fußpunkt
        shapeRendererMap.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererMap.setColor(Color.WHITE);
        shapeRendererMap.circle(cameraWeltPosition.x, cameraWeltPosition.y - 8, 1);
        shapeRendererMap.end();
    }
}
