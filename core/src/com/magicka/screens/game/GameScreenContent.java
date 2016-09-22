package com.magicka.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.helpers.TextureManager;
import com.magicka.model.Mage;
import com.magicka.server.Server;

public class GameScreenContent {

    private Server server;
    private Mage mainPlayer;

    private ShapeRenderer shapeRenderer;

    private static final float HEALTH_BAR_X_P1 = 30;
    private static final float HEALTH_BAR_Y = 720;
    private static final float HEALTH_BAR_HEIGHT = 25;

    public GameScreenContent() {
        TextureManager.getInstance();
        this.mainPlayer= null;
        this.initializeServer();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void setMainPlayer(Mage mainPlayer){
        this.mainPlayer = mainPlayer;
    }

    public void update(float delta) {
        //this.updateServer(delta);
        if(this.isMainPlayer()) {
            mainPlayer.update(delta);
            mainPlayer.updateIfDamagedByAnyOf(this.server.getPlayers());
        }
    }

    public void render(SpriteBatch batch) {
        if(this.isMainPlayer()) mainPlayer.render(batch);
        this.server.getPlayers().forEach(mage -> mage.render(batch));
        batch.end();
        //drawHealthBars();
        batch.begin();
    }

    private void initializeServer() {
        this.server= new Server(this);
    }

    private void drawHealthBars() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawHealthBarForMage(mainPlayer, HEALTH_BAR_X_P1);
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

    public boolean isMainPlayer(){
        return mainPlayer != null;
    }
}
