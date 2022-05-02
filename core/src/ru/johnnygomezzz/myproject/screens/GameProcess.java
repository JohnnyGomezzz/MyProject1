package ru.johnnygomezzz.myproject.screens;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import ru.johnnygomezzz.myproject.Explosion;
import ru.johnnygomezzz.myproject.NewAnimation;


public class GameProcess implements Screen, InputProcessor {
    final Game game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    NewAnimation bunkerAnimation;
    NewAnimation bunkerBaseAnimation;
    NewAnimation cannonAnimation;
    List<Explosion> explosions;
    Sprite cannonSprite;
    Sprite bunkerSprite;
    Sprite bunkerBaseSprite;
    Sprite alienSprite;
    TextureAtlas mainAtlas;
    Music deploySound;
    float xPos;
    float yPos;
    int count;


    public GameProcess(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mainAtlas = new TextureAtlas("atlas/main.atlas");
        explosions = new ArrayList<>();
        bunkerAnimation = new NewAnimation(mainAtlas.findRegion("Bunker"), Animation.PlayMode.NORMAL,
                4, 4, 5);
        bunkerBaseAnimation = new NewAnimation(mainAtlas.findRegion("Bunker_base"), Animation.PlayMode.LOOP,
                2, 1, 4);
        cannonAnimation = new NewAnimation(mainAtlas.findRegion("cannon-anim"), Animation.PlayMode.LOOP_RANDOM,
                4, 1, 30);
        deploySound = Gdx.audio.newMusic(Gdx.files.internal("deployment1.mp3"));

        xPos = MathUtils.random(0, Gdx.graphics.getWidth());
        yPos = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        boolean fire = false;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            fire = true;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
        shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
        shapeRenderer.circle(getPosition().x, getPosition().y, 7);
        shapeRenderer.end();

        bunkerSprite = new Sprite(bunkerAnimation.getRegion());
        Vector2 bunkerPosition = new Vector2(33, bunkerSprite.getRegionHeight() - 44);
        batch.begin();
        bunkerSprite.setPosition(Gdx.graphics.getWidth() / 2f - bunkerPosition.x, 22);
        bunkerSprite.setScale(2, 2);
        batch.end();

        bunkerBaseSprite = new Sprite(bunkerBaseAnimation.getRegion());
        Vector2 bunkerBasePosition = new Vector2(33, bunkerBaseSprite.getRegionHeight() - 44);
        batch.begin();
        bunkerBaseSprite.setPosition(Gdx.graphics.getWidth() / 2f - bunkerBasePosition.x, 22);
        bunkerBaseSprite.setScale(2, 2);
        batch.end();

        cannonSprite = new Sprite(cannonAnimation.getRegion());
        Vector2 cannonPosition = new Vector2(11, cannonSprite.getRegionHeight() - 44);
        cannonSprite.setOrigin(cannonPosition.x, cannonPosition.y);
        batch.begin();
        cannonSprite.setPosition(Gdx.graphics.getWidth() / 2f - cannonPosition.x, 15);
        cannonSprite.setRotation(getAngle(new Vector2(Gdx.graphics.getWidth() / 2f - cannonPosition.x, 15)));
        cannonSprite.setScale(2, 2);
        batch.end();

        createAlien(3);

        if ((fire & !cannonAnimation.isFinished()) || (!fire & cannonAnimation.isFinished())) {
            cannonAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (!fire & !cannonAnimation.isFinished()) {
            cannonAnimation.resetTime();
        }
        if (fire & cannonAnimation.isFinished()) {
            cannonAnimation.resetTime();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(Gdx.graphics.getWidth() / 2f, 15, getPosition().x, getPosition().y,
                    Color.WHITE, Color.OLIVE);
            shapeRenderer.end();
            explosions.add(new Explosion(mainAtlas.findRegion("explosion-green"),
                    Animation.PlayMode.NORMAL, 5, 4, 16, "laser-explosion.mp3"));
            if (alienSprite.getBoundingRectangle().contains(getPosition())) {
                xPos = MathUtils.random(0, Gdx.graphics.getWidth() - alienSprite.getWidth());
                yPos = Gdx.graphics.getHeight();
                count++;
            }
        }
        Gdx.graphics.setTitle("Подбито: " + count);

        batch.begin();
        if (!bunkerAnimation.isFinished()) {
            deploySound.play();
            bunkerAnimation.setTime(Gdx.graphics.getDeltaTime());
            bunkerSprite.draw(batch);
        } else {
            bunkerBaseAnimation.setTime(Gdx.graphics.getDeltaTime());
            cannonSprite.draw(batch);
            bunkerBaseSprite.draw(batch);
            deploySound.stop();
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setTime(Gdx.graphics.getDeltaTime());
            Vector2 tVec = explosions.get(i).getExplosionPosition();
            batch.draw(explosions.get(i).getRegion(), tVec.x, tVec.y);

            if (explosions.get(i).isFinished()) {
                explosions.remove(i);
            }
        }
        batch.end();
    }

    public void createAlien(int quantity) {

        alienSprite = new Sprite(mainAtlas.findRegion("alien-pink"));
        Vector2 alienSpriteOrigin = new Vector2(alienSprite.getRegionWidth() / 2f, alienSprite.getRegionHeight() / 2f);
        alienSprite.setOrigin(alienSpriteOrigin.x, alienSpriteOrigin.y);
        yPos -= 0.5f;

        for (int i = 0; i < quantity; i++) {
            batch.begin();
            Vector2 alienPosition = new Vector2(xPos, yPos);
            alienSprite.setPosition(alienPosition.x - alienSpriteOrigin.x, alienPosition.y - alienSpriteOrigin.y);
            alienSprite.setRotation(getAngle(alienPosition) - 180);
            alienSprite.draw(batch);
            batch.end();
        }

        if (yPos == 0) {
            game.setScreen(new MainMenu(game));
        }
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
        return false;
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
