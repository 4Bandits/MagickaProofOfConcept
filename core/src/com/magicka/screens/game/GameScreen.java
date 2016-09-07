package com.magicka.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.magicka.Magicka;
import com.magicka.helpers.TextureManager;

public class GameScreen extends ScreenAdapter {

    private Magicka game;
    private OrthographicCamera camera;
    private GameScreenContent content;

    public GameScreen(Magicka game, TextureManager textureManager) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.game = game;
        this.content = new GameScreenContent(textureManager);
        this.initializeCamera();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update(delta);
        this.draw();
    }

    private void update(float delta) {
        this.camera.update();
        this.content.update(delta);
    }

    private void draw() {
        this.game.batch.begin();
        this.content.render(this.game.batch);
        this.game.batch.end();
    }

    private void initializeCamera() {
        this.camera = new OrthographicCamera(Magicka.SCREEN_WIDTH, Magicka.SCREEN_HEIGHT);
    }
}
