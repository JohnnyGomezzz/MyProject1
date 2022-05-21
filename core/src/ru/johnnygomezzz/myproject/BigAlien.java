package ru.johnnygomezzz.myproject;

import java.util.List;

public class BigAlien extends BaseAlien {
    private List<BaseAlien> turrets;

    public BigAlien(String name, float speed, float health) {
        super(name, speed, health);

        turrets.add(new BaseAlien("alien-pink", speed, 10));
        turrets.add(new BaseAlien("alien-pink", speed, 10));
        turrets.add(new BaseAlien("alien-pink", speed, 10));

    }
}
