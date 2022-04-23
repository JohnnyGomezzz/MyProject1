package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        explosions = new ArrayList<>();
        bunkerAnimation = new NewAnimation("bunker-cannon.png", Animation.PlayMode.NORMAL,
                4, 4, 5);
        bunkerBaseAnimation = new NewAnimation("Bunker_base.png", Animation.PlayMode.LOOP,
                2, 1, 5);
        cannonAnimation = new NewAnimation("cannon.png", Animation.PlayMode.NORMAL,
                1, 1, 25);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);
        float rotation = 360 - MathUtils.atan2(getPosition().x - 320, getPosition().y - 22) * MathUtils.radiansToDegrees;
        boolean fire = false;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) fire = true;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
            shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
            shapeRenderer.circle(getPosition().x, getPosition().y, 7);
        shapeRenderer.end();

        batch.begin();
        if (!bunkerAnimation.isFinished()) {
            bunkerAnimation.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(bunkerAnimation.getRegion(), Gdx.graphics.getWidth() / 2f - (bunkerAnimation.getRegion().getRegionWidth()), 0,
                    bunkerAnimation.getRegion().getRegionWidth() * 2, bunkerAnimation.getRegion().getRegionHeight() * 2);
        } else {
            bunkerBaseAnimation.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(cannonAnimation.getRegion(), Gdx.graphics.getWidth() / 2f - (bunkerAnimation.getRegion().getRegionWidth() / 2f + 13),
                    0, 46, 11, cannonAnimation.getRegion().getRegionHeight(), cannonAnimation.getRegion().getRegionWidth(),
                    2, 2, rotation - 90, false);
            batch.draw(bunkerBaseAnimation.getRegion(), Gdx.graphics.getWidth() / 2f - (bunkerAnimation.getRegion().getRegionWidth()), 0,
                    bunkerAnimation.getRegion().getRegionWidth() * 2, bunkerAnimation.getRegion().getRegionHeight() * 2);
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

        if ((fire & !cannonAnimation.isFinished()) || (!fire & cannonAnimation.isFinished())) {
            cannonAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (fire & cannonAnimation.isFinished()) {
            cannonAnimation.resetTime();
            explosions.add(new Explosion("explosion-green.png",
                    Animation.PlayMode.NORMAL, 5, 4,16, "laser-explosion.mp3"));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
