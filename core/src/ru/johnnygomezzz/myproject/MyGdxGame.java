package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    NewAnimation turretAnimation;
    NewAnimation bodyAnimation;
    NewAnimation headAnimation;
    boolean begin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        turretAnimation = new NewAnimation("turret-sprites-deployment.png", Animation.PlayMode.NORMAL,
                8, 1, 5);
        bodyAnimation = new NewAnimation("turret-sprites-body.png", Animation.PlayMode.LOOP,
                2, 1, 5);
        headAnimation = new NewAnimation("turret-sprites-head-shot-idle.png", Animation.PlayMode.NORMAL,
                5, 1, 30);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);
        float rotation = 360 - MathUtils.atan2(getPosition().x - 25, getPosition().y - 34) * MathUtils.radiansToDegrees;
        boolean fire = false;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) fire = true;

        batch.begin();
        if (!turretAnimation.isFinished()) {
            turretAnimation.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(turretAnimation.getRegion(), 0, 0);
        } else {
            bodyAnimation.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(bodyAnimation.getRegion(), 0, 0);
            batch.draw(headAnimation.getRegion(), 11, 12, 14, 22,
                    headAnimation.getRegion().getRegionWidth(), headAnimation.getRegion().getRegionHeight(),
                    1, 1, rotation, false);
        }
        batch.end();

        if ((fire & !headAnimation.isFinished()) || (!fire & headAnimation.isFinished())) {
            headAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (fire & headAnimation.isFinished()) headAnimation.resetTime();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
