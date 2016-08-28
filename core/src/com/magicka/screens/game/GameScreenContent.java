package com.magicka.screens.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.magicka.GameObject;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.model.Mage;

import java.util.ArrayList;
import java.util.List;

public class GameScreenContent {

    private List<GameObject> objects;
    private PlayerConfiguration playerOneConfiguration;
    private PlayerConfiguration playerTwoConfiguration;

    public GameScreenContent() {
        this.initializePlayersConfigurations();
        this.initializeObjects();
    }

    public void update(float delta) {
        for (GameObject object : this.objects) {
            object.update(delta);
        }
    }

    public void render(SpriteBatch batcher) {
        for (GameObject object : this.objects) {
            object.render(batcher);
        }
    }

    private void initializeObjects() {
        this.objects = new ArrayList<GameObject>();
        this.objects.add(new Mage(this.playerOneConfiguration));
        this.objects.add(new Mage(this.playerTwoConfiguration));
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
    }

    private void initializePlayerTwoConfiguration() {
        this.playerTwoConfiguration.setUpKey(Input.Keys.UP);
        this.playerTwoConfiguration.setDownKey(Input.Keys.DOWN);
        this.playerTwoConfiguration.setLeftKey(Input.Keys.LEFT);
        this.playerTwoConfiguration.setRightKey(Input.Keys.RIGHT);
    }
}
