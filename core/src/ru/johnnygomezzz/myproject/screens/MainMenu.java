package ru.johnnygomezzz.myproject.screens;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen, InputProcessor {
    final Game game;
    SpriteBatch batch;
    Sprite buttonPlay;
    Sprite buttonExit;
    TextureAtlas mainAtlas;

    public MainMenu(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);

        mainAtlas = new TextureAtlas("atlas/main.atlas");

        buttonPlay = new Sprite(mainAtlas.findRegion("buttons-play"));
        buttonPlay.setPosition(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3f);

        buttonExit = new Sprite(mainAtlas.findRegion("buttons-exit"));
        buttonExit.setPosition(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3f - 100);

        batch.begin();
        buttonPlay.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 cursorVec = getPosition();
        Rectangle rectanglePlay = buttonPlay.getBoundingRectangle();
        Rectangle rectangleExit = buttonExit.getBoundingRectangle();

        if (rectanglePlay.contains(cursorVec)) {
            dispose();
            game.setScreen(new GameProcess(game));
        }

        if (rectangleExit.contains(cursorVec)) {
            dispose();
            Gdx.app.exit();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
