package com.magicka.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.magicka.GameObject;

public class Fire extends Sprite implements GameObject {

    private Vector2 previousPosition;
    private float speed;
    private boolean used;

    public Fire(Texture texture, float x, float y) {
        super(texture);
        this.setPosition(x,y);
        this.speed = 10;
        this.used = false;
    }

    @Override
    public void update(float delta) {
        this.translate(1 * speed * 1, 1 * speed * 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(used)
            this.draw(batch);
    }

    public void updateCoordinates(float x, float y) {
        this.setPosition(x, y);
    }

    public void setUsed() {
        this.used = !this.used;
    }

    public boolean isUsed() {
        return used;
    }

}
