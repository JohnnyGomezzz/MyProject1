package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BigAlien extends BaseAlien {
    private List<BaseAlien> turrets;

    public BigAlien(String name, float speed, float health) {
        super(name, speed, health);

        turrets = new ArrayList<>();
        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
        Vector2 tmpPos = new Vector2(position.x + 25.0f, position.y + (skin.getHeight() - 96.0f));
        turrets.get(turrets.size() - 1).setPosition(tmpPos);

//        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
//        turrets.add(new BaseAlien("alien-pink-small", speed, 10));

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
        }
    }
}
