package com.magicka.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.magicka.GameObject;

public class Fire implements GameObject {

    private Vector2 coordinates;
    private Texture sprite;
    private float speed;
    private Rectangle bounds;
    private boolean used;

    public Fire(float x, float y) {
        this.coordinates = new Vector2(x, y);
        this.sprite = new Texture("Fire.png");
        this.speed = 10;
        this.bounds = new Rectangle(coordinates.x, this.coordinates.y, this.sprite.getWidth(), this.sprite.getHeight());
        this.used = false;
    }

    @Override
    public void update(float delta) {
        this.coordinates.add(1 * speed * 1, 1 * speed * 1);
        this.bounds.setPosition(this.coordinates);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(used)
            batch.draw(this.sprite, this.coordinates.x, this.coordinates.y);
    }

    public void updateCoordinates(float x, float y) {
        this.coordinates.set(x, y);
    }

    public void setUsed() {
        this.used = true;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isAt(Rectangle bounds) {
        return this.bounds.overlaps(bounds);
    }
}
