package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GfxUtils {
    static float x;
    static float y;

    public static float getAngle(Vector2 pos) {
        return  360 - MathUtils.atan2(getPosition().x - pos.x, getPosition().y - pos.y) * MathUtils.radiansToDegrees;
    }

    public static Vector2 getPosition() {
        x = Gdx.input.getX();
        y = Gdx.graphics.getHeight() - Gdx.input.getY();
        return new Vector2(x, y);
    }

    public static Vector2 getPosition(int width, int height) {
        x = Gdx.input.getX();
        y = Gdx.graphics.getHeight() - Gdx.input.getY();
        x -= width / 2.0f;
        y -= height / 2.0f;
        return new Vector2(x, y);
    }
}
