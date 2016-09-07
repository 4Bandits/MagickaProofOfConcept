package com.magicka.helpers;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    private Texture mage;
    private Texture attack;

    public TextureManager(){
        mage = new Texture("Mage.png");
        attack = new Texture("Fire.png");
    }

    public Texture getMage(){
        return mage;
    }

    public Texture getAttack(){
        return attack;
    }



}
