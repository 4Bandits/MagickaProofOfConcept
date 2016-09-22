package com.magicka.helpers;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    private static TextureManager instance = null;

    private Texture mage;
    private Texture attack;

    protected TextureManager(){
        mage = new Texture("Mage.png");
        attack = new Texture("Fire.png");
    }

    public static TextureManager getInstance() {
        if(instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }


    public Texture getMage(){
        return mage;
    }

    public Texture getAttack(){
        return attack;
    }



}
