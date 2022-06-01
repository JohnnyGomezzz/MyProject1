package ru.johnnygomezzz.myproject;

import static ru.johnnygomezzz.myproject.screens.GameProcess.mainAtlas;
import static ru.johnnygomezzz.myproject.GfxUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BaseAlien {
    protected Vector2 position, origin, rotate;
    protected float health, speed, damage, positionCorrectionX, positionCorrectionY;
    protected Sprite skin;
    protected boolean isDamaged;

    public BaseAlien(String name, float speed, float health, float positionCorrectionX, float positionCorrectionY) {
        isDamaged = false;
        skin = mainAtlas.createSprite(name);
        position = new Vector2();
        position.x = (MathUtils.random(0, Gdx.graphics.getWidth() - skin.getWidth()));
        position.y = Gdx.graphics.getHeight();
        skin.setPosition(position.x, position.y);
        origin = new Vector2(skin.getRegionWidth() / 2.0f, skin.getRegionHeight() / 2.0f);
        skin.setOrigin(origin.x, origin.y);
        skin.setScale(1);

        this.speed = speed;
        this.health = health;
        this.positionCorrectionX = positionCorrectionX;
        this.positionCorrectionY = positionCorrectionY;
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

    public Sprite getSkin() {
        return skin;
    }

    public void setRotate(Vector2 pos, int angleCorrection) {
        skin.setRotation(getAngleToCursorFrom(pos) - angleCorrection);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getPositionCorrectionX() {
        return positionCorrectionX;
    }

    public void setPositionCorrectionX(float positionCorrectionX) {
        this.positionCorrectionX = positionCorrectionX;
    }

    public float getPositionCorrectionY() {
        return positionCorrectionY;
    }

    public void setPositionCorrectionY(float positionCorrectionY) {
        this.positionCorrectionY = positionCorrectionY;
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
