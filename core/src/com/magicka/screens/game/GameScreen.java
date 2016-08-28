package com.magicka.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.magicka.Magicka;

public class GameScreen extends ScreenAdapter {

    private Magicka game;
    private OrthographicCamera camera;
    private GameContent gameContent;

    public GameScreen(Magicka game) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.game = game;
        this.gameContent = new GameContent();
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
        this.gameContent.update(delta);
    }

    private void draw() {
        this.game.batcher.begin();
        this.gameContent.render(this.game.batcher);
        this.game.batcher.end();
    }

    private void initializeCamera() {
        this.camera = new OrthographicCamera(Magicka.SCREEN_WIDTH, Magicka.SCREEN_HEIGHT);
    }
}
