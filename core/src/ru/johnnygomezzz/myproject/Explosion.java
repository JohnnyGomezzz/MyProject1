package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static ru.johnnygomezzz.myproject.GfxUtils.*;

public class Explosion {
    private NewAnimation animation;
    private float time;
    Music music;
    private Vector2 position;

    public Explosion(String texture, Animation.PlayMode mode, int cols, int rows, int fps, String musicName) {
        animation = new NewAnimation(texture, mode, cols, rows, fps);
        time = 0;
        music = Gdx.audio.newMusic(Gdx.files.internal(musicName));
        music.play();
        position = getPosition(animation.getRegion().getRegionWidth(), animation.getRegion().getRegionHeight());
    }

    public void setTime(float dTime) {
        animation.setTime(dTime);
        if (animation.isFinished()) {
            music.dispose();
        }
    }

    public Vector2 getExplosionPosition() {
        return position;
    }

    public TextureRegion getRegion() {
        return animation.getRegion();
    }

    public boolean isFinished() {
        return animation.isFinished();
    }
}
