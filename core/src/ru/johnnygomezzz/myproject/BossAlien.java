package ru.johnnygomezzz.myproject;

import static ru.johnnygomezzz.myproject.screens.GameProcess.mainAtlas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BossAlien {
    protected Vector2 position, origin;
    protected float health, speed, damage;
    protected Sprite skin;
    protected boolean isDamaged;

    public BossAlien(String name, float speed, float health) {
        isDamaged = false;
        skin = mainAtlas.createSprite(name);
        position = new Vector2();
        position.x = MathUtils.random(0, Gdx.graphics.getWidth() - skin.getWidth());
        position.y = Gdx.graphics.getHeight();
        skin.setPosition(position.x, position.y);
        origin = new Vector2(skin.getRegionWidth() / 2f, skin.getRegionHeight() / 2f);
        skin.setOrigin(origin.x, origin.y);
        skin.setScale(1);

        this.speed = speed;
        this.health = health;
    }

    public float getDamage() {
        return damage;
    }

    public float damage(float damage) {
        isDamaged = true;
        health -= damage;
        return health;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void step() {
        position.y -= speed;
        skin.setPosition(position.x, position.y);
    }

    public boolean isHit(Vector2 position) {
        Rectangle tRect = skin.getBoundingRectangle();
        return tRect.contains(position);
    }

    public void draw(SpriteBatch batch) {
        if (isDamaged) {
            isDamaged = false;
            skin.setColor(Color.RED);
        } else {
            skin.setColor(Color.WHITE);
        }
        skin.draw(batch);
    }
}
