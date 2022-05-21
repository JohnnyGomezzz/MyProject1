package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class BigAlien extends BaseAlien {
    private List<BaseAlien> turrets;

    public BigAlien(String name, float speed, float health) {
        super(name, speed, health);

        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
        Vector2 tmpPos = new Vector2(position.x + 100.0f, position.y + (skin.getHeight() - 100.0f));

        turrets.add(new BaseAlien("alien-pink-small", speed, 10));
        turrets.add(new BaseAlien("alien-pink-small", speed, 10));

    }
}
