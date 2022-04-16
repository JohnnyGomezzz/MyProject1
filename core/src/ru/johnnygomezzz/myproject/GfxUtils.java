package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GfxUtils {
    static float x;
    static float y;

    public static Vector2 getPosition() {
        x = Gdx.input.getX();
        y = Gdx.graphics.getHeight() - Gdx.input.getY();
        return new Vector2(x, y);
    }
}
