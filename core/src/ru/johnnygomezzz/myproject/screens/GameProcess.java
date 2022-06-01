package ru.johnnygomezzz.myproject.screens;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import java.util.ListIterator;

import ru.johnnygomezzz.myproject.BaseAlien;
import ru.johnnygomezzz.myproject.BigAlien;
import ru.johnnygomezzz.myproject.Explosion;
import ru.johnnygomezzz.myproject.NewAnimation;


public class GameProcess implements Screen, InputProcessor {
    private final Game game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private NewAnimation bunkerAnimation;
    private NewAnimation bunkerBaseAnimation;
    private NewAnimation cannonAnimation;
    private List<Explosion> explosions;
    private Sprite cannonSprite;
    private Sprite bunkerSprite;
    private Sprite bunkerBaseSprite;
    private Texture background;
    public static TextureAtlas mainAtlas;
    private Music deploySound;
    private int count;
    private int aliensOnScreen;
    private int aliensTotal;
    private List<BigAlien> aliensList;
    private float alienTime;
    private float alienTimeCounter;
    private float damage;


    public GameProcess(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        aliensList = new ArrayList<>();
        aliensOnScreen = 3;
        alienTime = 1;
        alienTimeCounter = 0;
        aliensTotal = 10;
        damage = 1;

        batch = new SpriteBatch();
        background = new Texture("background_game.png");
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
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
        }

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        boolean fire = false;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            fire = true;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(getCursorPosition().x - 10, getCursorPosition().y, getCursorPosition().x + 10, getCursorPosition().y);
        shapeRenderer.line(getCursorPosition().x, getCursorPosition().y - 10, getCursorPosition().x, getCursorPosition().y + 10);
        shapeRenderer.circle(getCursorPosition().x, getCursorPosition().y, 7);
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
        cannonSprite.setRotation(getAngleToCursorFrom(new Vector2(Gdx.graphics.getWidth() / 2f - cannonPosition.x, 15)));
        cannonSprite.setScale(2, 2);
        batch.end();

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

        ListIterator<BigAlien> iterator1 = aliensList.listIterator();
        while (iterator1.hasNext()) {
            BaseAlien alien = iterator1.next();
            alien.step();
            alien.draw(batch);
        }

        ListIterator<Explosion> iterator = explosions.listIterator();
        while (iterator.hasNext()) {
            Explosion ex = iterator.next();
            ex.setTime(Gdx.graphics.getDeltaTime());
            if (ex.isFinished()) {
                ex.dispose();
                iterator.remove();
            } else {
                batch.draw(ex.getRegion(), ex.getPos().x, ex.getPos().y);
            }
        }
        batch.end();

        if ((fire & !cannonAnimation.isFinished()) || (!fire & cannonAnimation.isFinished())) {
            cannonAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (fire & cannonAnimation.isFinished()) {
            cannonAnimation.resetTime();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(Gdx.graphics.getWidth() / 2f, 15, getCursorPosition().x, getCursorPosition().y,
                    Color.WHITE, Color.OLIVE);
            shapeRenderer.end();

            explosions.add(new Explosion(mainAtlas.findRegion("explosion-green"),
                    Animation.PlayMode.NORMAL, 5, 4, 16, "laser-explosion.mp3", damage));
            iterator1 = aliensList.listIterator(aliensList.size());
            while (iterator1.hasPrevious()) {
                BaseAlien alien = iterator1.previous();
                if (alien.isHit(getCursorPosition())) {
                    if (alien.damage(explosions.get(explosions.size() - 1).getDamage()) < 0) {
                        iterator1.remove();
                        explosions.get(explosions.size() - 1).setDamage(0);
                        count++;
                        break;
                    }
                }
            }
        }
        alienTimeCounter += Gdx.graphics.getDeltaTime();
        float alienSpeed = MathUtils.random(0.3f, 0.5f);

       // while (aliensTotal >= 0) {
            if (alienTimeCounter > alienTime && aliensList.size() < aliensOnScreen) {
                alienTimeCounter = 0;
                aliensList.add(new BigAlien("alien-boss1", alienSpeed, 10,
                        0, 0, MathUtils.random(0.5f, 1f)));
                aliensTotal--;
            }
       // }

        Gdx.graphics.setTitle("Подбито: " + count + " Осталось врагов: " + aliensTotal);
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
