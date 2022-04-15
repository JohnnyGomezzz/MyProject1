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
	NewAnimation bodyAnimation;
	NewAnimation headAnimation;
	boolean begin;

	@Override
	public void create () {
		batch = new SpriteBatch();
		turretAnimation = new NewAnimation("turret-sprites-deployment.png", Animation.PlayMode.NORMAL,
				8, 1, 5);
		bodyAnimation = new NewAnimation("turret-sprites-body.png", Animation.PlayMode.LOOP,
				2, 1, 5);
		headAnimation = new NewAnimation("turret-sprites-head-shot-idle.png", Animation.PlayMode.LOOP,
				5, 1, 16);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.DARK_GRAY);

		if (begin) {
			batch.begin();
			if (!turretAnimation.isFinished()) {
				turretAnimation.setTime(Gdx.graphics.getDeltaTime());
				batch.draw(turretAnimation.getRegion(), 0, 0);
			} else {
				bodyAnimation.setTime(Gdx.graphics.getDeltaTime());
				batch.draw(bodyAnimation.getRegion(), 0, 0);
				batch.draw(headAnimation.getRegion(), -4, 11, 22, 15,
						headAnimation.getRegion().getRegionWidth(), headAnimation.getRegion().getRegionHeight(),
						1, 1, 270, false);
			}
			batch.end();
		}
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) begin = true;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
