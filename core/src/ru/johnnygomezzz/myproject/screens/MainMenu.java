package ru.johnnygomezzz.myproject.screens;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen, InputProcessor {
    private final Game game;
    private SpriteBatch batch;
    private Texture background;
    private Sprite gameTitle;
    private Sprite buttonPlay;
    private Sprite buttonPlayHighlighted;
    private Sprite buttonExit;
    private Sprite buttonExitHighlighted;
    private TextureAtlas mainAtlas;

    private boolean play;
    private boolean exit;

    private Vector2 cursorVec;
    private Rectangle rectanglePlay;
    private Rectangle rectangleExit;

    private OrthographicCamera camera;
    private Viewport viewport;

    public MainMenu(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();

        background = new Texture("background_earth.jpg");

        mainAtlas = new TextureAtlas("atlas/main.atlas");

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new StretchViewport(camera.viewportWidth, camera.viewportHeight, camera);
        camera.update();

        gameTitle = mainAtlas.createSprite("title");
        gameTitle.setPosition(Gdx.graphics.getWidth() / 2f - gameTitle.getWidth() / 2f, Gdx.graphics.getHeight() - gameTitle.getHeight() - 50);

        buttonPlay = mainAtlas.createSprite("buttons-play");
        buttonPlay.setPosition(viewport.getWorldWidth() / 3f, viewport.getWorldHeight() - viewport.getWorldHeight() / 2f);
        buttonPlayHighlighted = mainAtlas.createSprite("buttons-play-light");
        buttonPlayHighlighted.setPosition(buttonPlay.getX(), buttonPlay.getY());

        buttonExit = mainAtlas.createSprite("buttons-exit");
        buttonExit.setPosition(viewport.getWorldWidth() / 3f, viewport.getWorldHeight() - viewport.getWorldHeight() / 2f - 100);
        buttonExitHighlighted = mainAtlas.createSprite("buttons-exit-light");
        buttonExitHighlighted.setPosition(buttonExit.getX(), buttonExit.getY());

        rectanglePlay = buttonPlay.getBoundingRectangle();
        rectangleExit = buttonExit.getBoundingRectangle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        gameTitle.draw(batch);
        if (play) {
            buttonPlayHighlighted.draw(batch);
        } else {
            buttonPlay.draw(batch);
        }
        if (exit) {
            buttonExitHighlighted.draw(batch);
        } else {
            buttonExit.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
        camera.update();
        viewport.update(width, height);
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
        batch.dispose();
        mainAtlas.dispose();
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
        if (rectanglePlay.contains(cursorVec)) {
            dispose();
            game.setScreen(new GameProcess(game));
        }

        if (rectangleExit.contains(cursorVec)) {
            dispose();
            Gdx.app.exit();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        cursorVec = getCursorPosition();
        play = false;
        exit = false;

        if (rectanglePlay.contains(cursorVec)) {
            play = true;
        }
        if (rectangleExit.contains(cursorVec)) {
            exit = true;
        }
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
