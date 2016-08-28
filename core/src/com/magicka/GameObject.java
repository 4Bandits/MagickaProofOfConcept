package com.magicka;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameObject {

    public void update(float delta);

    public void render(SpriteBatch batch);

}
