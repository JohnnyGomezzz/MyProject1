package ru.johnnygomezzz.myproject;

import java.util.List;

public class BigAlien extends BossAlien{
    private List<BossAlien> turrets;

    public BigAlien(String name, float speed, float health) {
        super(name, speed, health);

    }
}
