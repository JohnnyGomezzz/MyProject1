package ru.johnnygomezzz.myproject;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BigAlien extends BaseAlien {
    private List<BaseAlien> turrets;
    private int turretToDamage;
    private ShapeRenderer shapeRenderer;

    public BigAlien(String name, float speed, float health) {
        super(name, speed, health);

        turrets = new ArrayList<>();
        turretToDamage = 0;
        shapeRenderer = new ShapeRenderer();

        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
        Vector2 position0 = new Vector2(position.x + 22.0f, position.y + (skin.getHeight() - 100.0f));
        turrets.get(0).setPosition(position0);

        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
        Vector2 position1 = new Vector2(position.x + 75.0f, position.y + (skin.getHeight() - 125.0f));
        turrets.get(1).setPosition(position1);

        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
        Vector2 position2 = new Vector2(position.x + 130.0f, position.y + (skin.getHeight() - 103.0f));
        turrets.get(2).setPosition(position2);

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
            turrets.get(i).getSkin().setRotation(getAngle(new Vector2(getPosition().x + 22, position.y + (skin.getHeight() - 100.0f))) - 180);

//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            shapeRenderer.line(turrets.get(i).getPosition().x, turrets.get(i).getPosition().y, getCursorPosition().x, getCursorPosition().y,
//                    Color.WHITE, Color.OLIVE);
//            shapeRenderer.end();
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
