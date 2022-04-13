package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import jdk.internal.vm.compiler.word.Pointer;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float cRed;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		cRed = 0;
	}

	@Override
	public void render () {
		ScreenUtils.clear(cRed, 0, 0, 1);

		if (Gdx.input.isTouched()) cRed += 0.1f;
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) Gdx.app.exit();

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
