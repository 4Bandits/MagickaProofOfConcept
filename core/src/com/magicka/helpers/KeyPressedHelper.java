package com.magicka.helpers;

import com.badlogic.gdx.Gdx;

public class KeyPressedHelper {

    public static boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
}
