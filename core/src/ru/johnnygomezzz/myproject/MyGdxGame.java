package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static ru.johnnygomezzz.myproject.GfxUtils.getBadLogicSmile;
import static ru.johnnygomezzz.myproject.GfxUtils.getCenterPosition;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = getBadLogicSmile();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);

        batch.begin();
        batch.draw(img, getCenterPosition().x, getCenterPosition().y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
