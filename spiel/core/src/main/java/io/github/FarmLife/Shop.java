package io.github.FarmLife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Shop {

    private Stage uiStage;
    private Skin skin;
    private Window mainMenuWindow, subMenuWindow;
    private Stage stage;


    public void render() {
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
    }
    private void showMainMenu() {
        if (mainMenuWindow != null) mainMenuWindow.remove();

        mainMenuWindow = new Window("Trader", skin);
        mainMenuWindow.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        mainMenuWindow.setPosition((Gdx.graphics.getWidth() - mainMenuWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - mainMenuWindow.getHeight()) / 2f + 100f);

        Table content = new Table();
        content.pad(10);

        TextButton shopButton = new TextButton("Shop", skin);
        shopButton.setSize(200, 200);
        TextButton sellerButton = new TextButton("Verkäufer", skin);
        sellerButton.setSize(200, 200);
        TextButton exitButton = new TextButton("Schließen", skin);
        exitButton.setSize(200, 200);

        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showShopMenu("Shop");
            }
        });

        sellerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSellerMenu("Verkäufer");
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenuWindow.remove();
            }
        });

        content.add(shopButton).pad(10).row();
        content.add(sellerButton).pad(10).row();
        content.add(exitButton).pad(10);

        mainMenuWindow.add(content);
        uiStage.addActor(mainMenuWindow);
    }

    private void showShopMenu(String title) {
        if (subMenuWindow != null) subMenuWindow.remove();

        subMenuWindow = new Window(title, skin);
        subMenuWindow.setSize(Gdx.graphics.getWidth() * 0.6f, Gdx.graphics.getHeight() * 0.6f);
        subMenuWindow.setPosition((Gdx.graphics.getWidth() - subMenuWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - subMenuWindow.getHeight()) / 2f);

        Table content = new Table();
        TextButton KarottenSeeds = new TextButton("Karotten Seeds Kaufen", skin);
        TextButton WeizenSeeds = new TextButton("Weizen Seeds Kaufen", skin);
        TextButton back = new TextButton("Zurück", skin);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                subMenuWindow.remove();
            }
        });

        content.add(KarottenSeeds).pad(10).row();
        content.add(WeizenSeeds).pad(10).row();
        content.add(back).pad(10);

        subMenuWindow.add(content);
        uiStage.addActor(subMenuWindow);
    }

    private void showSellerMenu(String title) {
        if (subMenuWindow != null) subMenuWindow.remove();

        subMenuWindow = new Window(title, skin);
        subMenuWindow.setSize(Gdx.graphics.getWidth() * 0.6f, Gdx.graphics.getHeight() * 0.6f);
        subMenuWindow.setPosition((Gdx.graphics.getWidth() - subMenuWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - subMenuWindow.getHeight()) / 2f);

        Table content = new Table();
        TextButton Karotte = new TextButton("Karotte Verkaufen", skin);
        TextButton Weizen = new TextButton("Weizen Verkaufen", skin);
        TextButton back = new TextButton("Zurück", skin);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                subMenuWindow.remove();
            }
        });

        content.add(Karotte).pad(10).row();
        content.add(Weizen).pad(10).row();
        content.add(back).pad(10);

        subMenuWindow.add(content);
        uiStage.addActor(subMenuWindow);
    }

    public void show() {
        TextButton openMenu = new TextButton("Händler", skin);
        openMenu.getLabel().setSize(500, 300);
        openMenu.setScale(10f);
        openMenu.setPosition(1800, 800);
        openMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMainMenu();
            }
        });
        uiStage.addActor(openMenu);


    }

    public void dispose() {
        uiStage.dispose();
        skin.dispose();
    }

}
