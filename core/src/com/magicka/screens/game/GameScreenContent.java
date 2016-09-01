package com.magicka.screens.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.model.Mage;

public class GameScreenContent {

    private Mage mageOne;
    private Mage mageTwo;

    private ShapeRenderer shapeRenderer;
    private PlayerConfiguration playerOneConfiguration;
    private PlayerConfiguration playerTwoConfiguration;

    private static final float HEALTH_BAR_X_P1 = 30;
    private static final float HEALTH_BAR_X_P2 = 1050;
    private static final float HEALTH_BAR_Y = 720;
    private static final float HEALTH_BAR_HEIGHT = 25;

    public GameScreenContent() {
        this.initializePlayersConfigurations();
        this.initializeObjects();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update(float delta) {
        mageOne.update(delta);
        mageTwo.update(delta);

        mageOne.updateIfDamagedBy(mageTwo);
        mageTwo.updateIfDamagedBy(mageOne);
    }

    public void render(SpriteBatch batcher) {
        mageOne.render(batcher);
        mageTwo.render(batcher);

        batcher.end();
        drawHealthBars();
        batcher.begin();
    }

    private void initializeObjects() {
        this.mageOne = new Mage(this.playerOneConfiguration, 0, 0);
        this.mageTwo = new Mage(this.playerTwoConfiguration, 500, 500);
    }

    private void initializePlayersConfigurations() {
        this.playerOneConfiguration = new PlayerConfiguration();
        this.playerTwoConfiguration = new PlayerConfiguration();
        this.initializePlayerOneConfiguration();
        this.initializePlayerTwoConfiguration();
    }

    private void initializePlayerOneConfiguration() {
        this.playerOneConfiguration.setUpKey(Input.Keys.W);
        this.playerOneConfiguration.setDownKey(Input.Keys.S);
        this.playerOneConfiguration.setLeftKey(Input.Keys.A);
        this.playerOneConfiguration.setRightKey(Input.Keys.D);
        this.playerOneConfiguration.setFireKey(Input.Keys.SPACE);
    }

    private void initializePlayerTwoConfiguration() {
        this.playerTwoConfiguration.setUpKey(Input.Keys.UP);
        this.playerTwoConfiguration.setDownKey(Input.Keys.DOWN);
        this.playerTwoConfiguration.setLeftKey(Input.Keys.LEFT);
        this.playerTwoConfiguration.setRightKey(Input.Keys.RIGHT);
        this.playerTwoConfiguration.setFireKey(Input.Keys.CONTROL_RIGHT);
    }

    private void drawHealthBars() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawHealthBarForMage(mageOne, HEALTH_BAR_X_P1);
        drawHealthBarForMage(mageTwo, HEALTH_BAR_X_P2);
        shapeRenderer.end();
    }

    private void drawHealthBarForMage(Mage mage, float x_coordinate) {
        float health_bar_width = mage.getHealth() * 3;
        float missing_health = (100 - mage.getHealth()) * 3;

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x_coordinate, HEALTH_BAR_Y, health_bar_width, HEALTH_BAR_HEIGHT);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x_coordinate + health_bar_width, HEALTH_BAR_Y, missing_health, HEALTH_BAR_HEIGHT);
    }
}
