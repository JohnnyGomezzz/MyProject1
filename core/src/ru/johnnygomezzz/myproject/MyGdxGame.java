package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    TextureAtlas mainAtlas;

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
        cannonAnimation = new NewAnimation(mainAtlas.findRegion("cannon"), Animation.PlayMode.NORMAL,
                1, 1, 25);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);
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
            cannonSprite.draw(batch);
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
            explosions.add(new Explosion(mainAtlas.findRegion("explosion-green"),
                    Animation.PlayMode.NORMAL, 5, 4,16, "laser-explosion.mp3"));
        }

        cannonSprite = new Sprite(cannonAnimation.getRegion());
        Vector2 cannonPosition = new Vector2(11, cannonSprite.getRegionHeight() - 44    );
        cannonSprite.setOrigin(cannonPosition.x, cannonPosition.y);

        batch.begin();
        cannonSprite.setPosition(Gdx.graphics.getWidth() / 2f - cannonPosition.x, 15);
        cannonSprite.setRotation(getAngle(new Vector2(Gdx.graphics.getWidth() / 2f - cannonPosition.x, 15)));
        cannonSprite.setScale(2, 2);
//        cannonSprite.draw(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
