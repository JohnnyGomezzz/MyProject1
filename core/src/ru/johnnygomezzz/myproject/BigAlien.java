package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.johnnygomezzz.myproject.screens.GameProcess;

public class BigAlien extends BaseAlien {
    private List<BaseAlien> turrets;
    private int turretToDamage;
    private ShapeRenderer shapeRenderer;
    private Texture texture;

    public BigAlien(NewAnimation animation, float speed, float health, float positionCorrectionX, float positionCorrectionY) {
        super(animation, speed, health, positionCorrectionX, positionCorrectionY);

        turrets = new ArrayList<>();
        turretToDamage = -1;
        shapeRenderer = new ShapeRenderer();
        texture = new Texture("alien-small.png");

        turrets.add(new BaseAlien(new NewAnimation(new TextureRegion(texture), Animation.PlayMode.NORMAL,
                3, 2, 16), speed, 10, 17, 110));
        turrets.add(new BaseAlien(new NewAnimation(new TextureRegion(texture), Animation.PlayMode.NORMAL,
                3, 2, 16), speed, 10, 70, 135));
        turrets.add(new BaseAlien(new NewAnimation(new TextureRegion(texture), Animation.PlayMode.NORMAL,
                3, 2, 16), speed, 10, 125, 113));
        for (int i = 0; i < turrets.size(); i++) {
            Vector2 turretPosition = new Vector2(position.x + turrets.get(i).getPositionCorrectionX(),
                position.y + (skin.getHeight() - turrets.get(i).getPositionCorrectionY()));
            turrets.get(i).setPosition(turretPosition);
            turrets.get(i).setDamage(1f);
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i = 0; i < turrets.size(); i++) {
            turrets.get(i).draw(batch);
        }
    }

    @Override
    public boolean step() {
        super.step();
        for (int i = 0; i < turrets.size(); i++) {
            if (turrets.get(i).step()) {
                GameProcess.life -= turrets.get(i).getDamage();
//                music.play();
            }
            turrets.get(i).setRotate(new Vector2(position.x + turrets.get(i).getPositionCorrectionX(),
                    position.y + (skin.getHeight() - turrets.get(i).getPositionCorrectionY())),
                    new Vector2(Gdx.graphics.getWidth() / 2f,0), 180);
        }
        return false;
    }

    @Override
    public boolean isHit(Vector2 position) {
        for (int i = 0; i < turrets.size(); i++) {
            if (turrets.get(i).isHit(position)) {
                turretToDamage = i;
                return true;
            }
        }
        return super.isHit(position);
    }

    @Override
    public float damage(float damage) {
        if (turretToDamage >= 0) {
            if (turrets.get(turretToDamage).damage(damage) < 0) {
                turrets.remove(turretToDamage);
            }
            turretToDamage = -1;
            return health;
        } else {
            return super.damage(damage);
        }
    }
}
