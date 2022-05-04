package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.Game;

import ru.johnnygomezzz.myproject.screens.MainMenu;

public class MyGdxGame extends Game {

    @Override
    public void create() {
        this.setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
