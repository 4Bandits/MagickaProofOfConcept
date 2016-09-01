package com.magicka.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
    private int health;
    private Fire fire;
    private Rectangle bounds;
    private float cooldown = 2;
    private float timeSinceLastCast = 0;

    public Mage(PlayerConfiguration configuration, float x, float y) {
        this.health = 100;
        this.coordinates = new Vector2(x, y);
        this.configuration = configuration;
        this.sprite = new Texture("Mage.png");
        this.fire = new Fire(x, y);
        this.bounds = new Rectangle(x, y, this.sprite.getWidth(), this.sprite.getHeight());
    }

    @Override
    public void update(float delta) {
        timeSinceLastCast += Gdx.graphics.getRawDeltaTime();
        if(timeSinceLastCast > cooldown){
            this.fire = new Fire(this.coordinates.x, this.coordinates.y);
            this.timeSinceLastCast = 0;
        }

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
        if(KeyPressedHelper.isKeyJustPressed(this.configuration.getFireKey())) {
            this.fire.setUsed();
        }

        if(this.fire.isUsed()) {
            this.fire.update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(this.sprite, this.coordinates.x, this.coordinates.y);
        this.fire.render(batch);
    }

    public int getHealth() {
        return health;
    }

    private void moveImageLeft() {
        this.coordinates.add(-5, 0);
        this.bounds.setPosition(coordinates.x, coordinates.y);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(coordinates.x, coordinates.y);
        }
    }

    private void moveImageRight() {
        this.coordinates.add(5, 0);
        this.bounds.setPosition(coordinates.x, coordinates.y);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(coordinates.x, coordinates.y);
        }
    }

    private void moveImageDown() {
        this.coordinates.add(0, -5);
        this.bounds.setPosition(coordinates.x, coordinates.y);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(coordinates.x, coordinates.y);
        }
    }

    private void moveImageUp() {
        this.coordinates.add(0, 5);
        this.bounds.setPosition(coordinates.x, coordinates.y);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(coordinates.x, coordinates.y);
        }
    }

    public void updateIfDamagedBy(Mage otherMage) {
        if (otherMage.attackIsAt(this.bounds)) {
            if(this.health > 2)
                this.health -= 2;
            else
                this.health = 0;
        }
    }

    private boolean attackIsAt(Rectangle bounds) {
        return this.fire.isAt(bounds);
    }
}
