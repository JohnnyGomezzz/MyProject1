package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
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

    @Override
    public void create() {
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
        yPos = Gdx.graphics.getHeight() - 100;
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);
        boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
            shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
            shapeRenderer.circle(getPosition().x, getPosition().y, 7);
        shapeRenderer.end();

        alienSprite = new Sprite(mainAtlas.findRegion("alien-pink"));
        Vector2 alienSpriteOrigin = new Vector2(alienSprite.getRegionWidth() / 2f, alienSprite.getRegionHeight() / 2f);
        alienSprite.setOrigin(alienSpriteOrigin.x, alienSpriteOrigin.y);
        yPos -= 0.3f;
        batch.begin();
        Vector2 alienPosition = new Vector2(200, yPos);
        alienSprite.setPosition(alienPosition.x - alienSpriteOrigin.x, alienPosition.y - alienSpriteOrigin.y);
        alienSprite.setRotation(getAngle(alienPosition) - 180);
        alienSprite.draw(batch);
        batch.end();

        if ((fire & !cannonAnimation.isFinished()) || (!fire & cannonAnimation.isFinished())) {
            cannonAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (fire & cannonAnimation.isFinished()) {
            cannonAnimation.resetTime();
            explosions.add(new Explosion(mainAtlas.findRegion("explosion-green"),
                    Animation.PlayMode.NORMAL, 5, 4,16, "laser-explosion.mp3"));
            if (alienSprite.getBoundingRectangle().contains(getPosition())) {
                xPos = MathUtils.random(0, Gdx.graphics.getWidth() - alienSprite.getWidth());
                yPos = Gdx.graphics.getHeight() + 100;
                count++;
            }
        }
        Gdx.graphics.setTitle("Подбито: " + String.valueOf(count));

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(alienSprite.getBoundingRectangle().x, alienSprite.getBoundingRectangle().y,
                alienSprite.getBoundingRectangle().getWidth(), alienSprite.getBoundingRectangle().getHeight());
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

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        deploySound.dispose();
    }
}
