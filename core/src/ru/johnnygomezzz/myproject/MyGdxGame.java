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
    NewAnimation turretAnimation;
    NewAnimation bodyAnimation;
    NewAnimation headAnimation;
    List<Explosion> explosions;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        explosions = new ArrayList<>();
        turretAnimation = new NewAnimation("turret-sprites-deployment.png", Animation.PlayMode.NORMAL,
                8, 1, 5);
        bodyAnimation = new NewAnimation("turret-sprites-body.png", Animation.PlayMode.LOOP,
                2, 1, 5);
        headAnimation = new NewAnimation("turret-sprites-head-shot-idle.png", Animation.PlayMode.NORMAL,
                5, 1, 25);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);
        float rotation = 360 - MathUtils.atan2(getPosition().x - 25, getPosition().y - 34) * MathUtils.radiansToDegrees;
        boolean fire = false;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) fire = true;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
            shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
            shapeRenderer.circle(getPosition().x, getPosition().y, 7);
        shapeRenderer.end();

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
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setTime(Gdx.graphics.getDeltaTime());
            Vector2 tVec = explosions.get(i).getExplosionPosition();
            batch.draw(explosions.get(i).getRegion(), tVec.x, tVec.y);
            if (explosions.get(i).isFinished()) {
                explosions.remove(i);
            }
        }
        batch.end();

        if ((fire & !headAnimation.isFinished()) || (!fire & headAnimation.isFinished())) {
            headAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (fire & headAnimation.isFinished()) {
            headAnimation.resetTime();
            explosions.add(new Explosion("79a0eceb71bb6a9939df582317038b81.png",
                    Animation.PlayMode.NORMAL, 4, 4,16, "370b925a30aca01.mp3"));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
