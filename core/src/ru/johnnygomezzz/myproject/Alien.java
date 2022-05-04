package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static ru.johnnygomezzz.myproject.screens.GameProcess.*;

import ru.johnnygomezzz.myproject.screens.MainMenu;

public class Alien {
    private Game game;
    private Vector2 position;
    private float speed;
    private float health;
    private Sprite skin;
    private boolean isDamaged;

    public Alien() {
        game = new MyGdxGame();
        isDamaged = false;
        skin = mainAtlas.createSprite("alien-pink");
        position = new Vector2();
        position.x = MathUtils.random(0, Gdx.graphics.getWidth() - skin.getWidth());
        position.y = Gdx.graphics.getHeight();
        skin.setPosition(position.x, position.y);
        Vector2 skinOrigin = new Vector2(skin.getRegionWidth() / 2f, skin.getRegionHeight() / 2f);
        skin.setOrigin(skinOrigin.x, skinOrigin.y);

        speed = MathUtils.random(0.25f, 1.0f);
        health = 1;
    }

    public void draw(SpriteBatch batch) {
        if (isDamaged) {
            isDamaged = false;
            skin.setColor(Color.RED);
        } else {
            skin.setColor(Color.WHITE);
        }
        skin.draw(batch);
        if (skin.getHeight() / 2f == 0) {
            game.setScreen(new MainMenu(game));
        }
    }

    public void step() {
        position.y -= speed;
        skin.setPosition(position.x, position.y);
    }

    public float isDamaged(Vector2 pos, float damage) {
        Rectangle tRect = skin.getBoundingRectangle();
        if (tRect.contains(pos)) {
            isDamaged = true;
            health -= damage;
        }
        return health;
    }

    public void dispose() {

    }
}
