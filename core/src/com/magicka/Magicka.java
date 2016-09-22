package com.magicka;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.magicka.helpers.TextureManager;
import com.magicka.screens.game.GameScreen;

public class Magicka extends Game {

	public static final int SCREEN_WIDTH = 1376;
	public static final int SCREEN_HEIGHT = 768;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
