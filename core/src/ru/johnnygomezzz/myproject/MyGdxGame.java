package ru.johnnygomezzz.myproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	NewAnimation turretAnimation;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		turretAnimation = new NewAnimation("turret-sprites-deployment.png", Animation.PlayMode.NORMAL,
				8, 1, 5);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.DARK_GRAY);

		batch.begin();
		turretAnimation.setTime(Gdx.graphics.getDeltaTime());
		batch.draw(turretAnimation.getRegion(), 0, 0);
		batch.end();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) turretAnimation.resetTime();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
