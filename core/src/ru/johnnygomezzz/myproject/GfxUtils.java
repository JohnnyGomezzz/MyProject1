package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GfxUtils {
    static float x;
    static float y;
    static float xCenter;
    static float yCenter;
    static Texture badLogicSmile;

    public static Vector2 getPosition() {
        x = Gdx.input.getX();
        y = Gdx.graphics.getHeight() - Gdx.input.getY();
        return new Vector2(x, y);
    }

    public static Vector2 getCenterPosition() {
        xCenter = Gdx.input.getX() - (badLogicSmile.getWidth() / 2f);
        yCenter = (Gdx.graphics.getHeight() - Gdx.input.getY()) - (badLogicSmile.getHeight() / 2f);
        return new Vector2(xCenter, yCenter);
    }

    public static Texture getBadLogicSmile() {
        badLogicSmile = new Texture("badlogic.jpg");
        return badLogicSmile;
    }
}
