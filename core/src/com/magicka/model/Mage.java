package com.magicka.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.magicka.GameObject;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.helpers.KeyPressedHelper;

public class Mage implements GameObject {

    /*
    * A bad thing about this is that the Model is mixed with the behaviour on the screen.
    * TODO: We can try and split the concepts of Model and View.
    */

    private Vector2 coordinates;
    private Texture sprite;
    private PlayerConfiguration configuration;

    public Mage(PlayerConfiguration configuration) {
        this.coordinates = new Vector2(0, 0);
        this.sprite = new Texture("Mage.png");
        this.configuration = configuration;
    }

    @Override
    public void update(float delta) {
        if(KeyPressedHelper.isKeyPressed(this.configuration.getLeftKey())) {
            this.moveImageLeft();
        }
        if(KeyPressedHelper.isKeyPressed(this.configuration.getDownKey())) {
            this.moveImageDown();
        }
        if(KeyPressedHelper.isKeyPressed(this.configuration.getRightKey())) {
            this.moveImageRight();
        }
        if(KeyPressedHelper.isKeyPressed(this.configuration.getUpKey())) {
            this.moveImageUp();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(this.sprite, this.coordinates.x, this.coordinates.y);
    }

    private void moveImageLeft() {
        this.coordinates.add(-5, 0);
    }

    private void moveImageRight() {
        this.coordinates.add(5, 0);
    }

    private void moveImageDown() {
        this.coordinates.add(0, -5);
    }

    private void moveImageUp() {
        this.coordinates.add(0, 5);
    }

}
