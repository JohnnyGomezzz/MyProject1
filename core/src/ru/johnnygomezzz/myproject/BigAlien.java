package ru.johnnygomezzz.myproject;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BigAlien extends BaseAlien {
    private List<BaseAlien> turrets;
    private int turretToDamage;
    private ShapeRenderer shapeRenderer;

    public BigAlien(String name, float speed, float health, float positionCorrectionX, float positionCorrectionY) {
        super(name, speed, health, positionCorrectionX, positionCorrectionY);

        turrets = new ArrayList<>();
        turretToDamage = 0;
        shapeRenderer = new ShapeRenderer();

        turrets.add(new BaseAlien("alien-pink-small", speed, 10, 22, 100));
        turrets.add(new BaseAlien("alien-pink-small", speed, 10, 75, 125));
        turrets.add(new BaseAlien("alien-pink-small", speed, 10, 130, 103));
        for (int i = 0; i < turrets.size(); i++) {
            Vector2 turretPosition = new Vector2(position.x + turrets.get(i).getPositionCorrectionX(),
                position.y + (skin.getHeight() - turrets.get(i).getPositionCorrectionY()));
            turrets.get(i).setPosition(turretPosition);
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
    public void step() {
        super.step();
        for (int i = 0; i < turrets.size(); i++) {
            turrets.get(i).step();
            turrets.get(i).setRotate(new Vector2(position.x + turrets.get(i).getPositionCorrectionX(),
                    position.y + (skin.getHeight() - turrets.get(i).getPositionCorrectionY())), 180);
        }
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
